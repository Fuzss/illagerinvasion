package fuzs.illagerinvasion.world.entity.monster;

import com.google.common.collect.Maps;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.item.v2.ToolTypeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class Basher extends AbstractIllager {
    private static final String TAG_STUN_TICKS = "Stunned";
    private static final EntityDataAccessor<Boolean> DATA_STUNNED = SynchedEntityData.defineId(Basher.class, EntityDataSerializers.BOOLEAN);

    private int stunTicks;
    private int blockedCount;

    public Basher(EntityType<? extends Basher> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new AbstractIllager.RaiderOpenDoorGoal(this));
        this.goalSelector.addGoal(4, new AttackGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0f));
    }

    @Override
    protected void customServerAiStep() {
        if (!this.isNoAi() && GoalUtils.hasGroundPathNavigation(this)) {
            boolean bl = ((ServerLevel) this.level()).isRaided(this.blockPosition());
            ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(bl);
            super.customServerAiStep();
        }
    }

    @Override
    public boolean hasLineOfSight(Entity entity) {
        if (this.getStunnedState()) {
            return false;
        }
        return super.hasLineOfSight(entity);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        nbt.putInt(TAG_STUN_TICKS, this.stunTicks);
        super.addAdditionalSaveData(nbt);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setStunTicks(nbt.getInt(TAG_STUN_TICKS));
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_STUNNED, false);
        super.defineSynchedData();
    }

    public boolean getStunnedState() {
        return this.entityData.get(DATA_STUNNED);
    }

    public void setStunTicks(int stunTicks) {
        this.stunTicks = stunTicks;
        this.entityData.set(DATA_STUNNED, stunTicks > 0);
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return 0.0F;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isAlive()) {
            return;
        }
        if (this.stunTicks > 0) {
            this.setStunTicks(this.stunTicks - 1);
        }
    }

    @Override
    protected boolean isImmobile() {
        return super.isImmobile() || this.getStunnedState();
    }

    @Override
    public AbstractIllager.IllagerArmPose getArmPose() {
        if (this.isCelebrating()) {
            return AbstractIllager.IllagerArmPose.CELEBRATING;
        }
        if (this.isAggressive()) {
            return IllagerArmPose.ATTACKING;
        }
        return AbstractIllager.IllagerArmPose.CROSSED;
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return ModRegistry.BASHER_CELEBRATE_SOUND_EVENT.get();
    }

    @Override
    public boolean hurt(final DamageSource source, final float amount) {
        final Entity attacker = source.getEntity();
        final boolean hasShield = this.getMainHandItem().is(Items.SHIELD);
        if (this.isAggressive()) {
            if (attacker instanceof LivingEntity) {
                final ItemStack attackerMainHand = ((LivingEntity) attacker).getMainHandItem();
                final ItemStack basherMainHand = this.getMainHandItem();
                if ((ToolTypeHelper.INSTANCE.isAxe(attackerMainHand) || attacker instanceof IronGolem || this.blockedCount >= 4) && basherMainHand.is(Items.SHIELD)) {
                    this.playSound(SoundEvents.SHIELD_BREAK, 1.0f, 1.0f);
                    this.setStunTicks(60);
                    if (this.level() instanceof ServerLevel) {
                        ((ServerLevel) this.level()).sendParticles((ParticleOptions) new ItemParticleOption(ParticleTypes.ITEM, basherMainHand), this.getX(), this.getY() + 1.5, this.getZ(), 30, 0.3, 0.2, 0.3, 0.003);
                        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_AXE));
                    }
                    return super.hurt(source, amount);
                }
            }
            if (source.getDirectEntity() instanceof AbstractArrow && hasShield) {
                this.playSound(SoundEvents.SHIELD_BLOCK, 1.0f, 1.0f);
                ++this.blockedCount;
                return false;
            }
            if (source.getDirectEntity() instanceof LivingEntity && hasShield) {
                this.playSound(SoundEvents.SHIELD_BLOCK, 1.0f, 1.0f);
                ++this.blockedCount;
                return false;
            }
        }
        return super.hurt(source, amount);
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityNbt) {
        SpawnGroupData entityData2 = super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityNbt);
        ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
        this.populateDefaultEquipmentSlots(world.getRandom(), difficulty);
        this.populateDefaultEquipmentEnchantments(world.getRandom(), difficulty);
        return entityData2;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficulty) {
        if (this.getCurrentRaid() == null) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.SHIELD));
        }
    }

    @Override
    public boolean isAlliedTo(Entity other) {
        if (super.isAlliedTo(other)) {
            return true;
        }
        if (other instanceof LivingEntity && ((LivingEntity) other).getMobType() == MobType.ILLAGER) {
            return this.getTeam() == null && other.getTeam() == null;
        }
        return false;
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return ModRegistry.BASHER_AMBIENT_SOUND_EVENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModRegistry.BASHER_DEATH_SOUND_EVENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModRegistry.BASHER_HURT_SOUND_EVENT.get();
    }

    @Override
    public void applyRaidBuffs(int wave, boolean unused) {
        boolean bl;
        ItemStack itemStack = new ItemStack(Items.SHIELD);
        Raid raid = this.getCurrentRaid();
        int i = 1;
        if (wave > raid.getNumGroups(Difficulty.NORMAL)) {
            i = 2;
        }
        boolean bl2 = bl = this.random.nextFloat() <= raid.getEnchantOdds();
        if (bl) {
            HashMap<Enchantment, Integer> map = Maps.newHashMap();
            map.put(Enchantments.UNBREAKING, i);
            EnchantmentHelper.setEnchantments(map, itemStack);
        }
        this.setItemSlot(EquipmentSlot.MAINHAND, itemStack);
    }

    static class AttackGoal extends MeleeAttackGoal {
        public AttackGoal(Basher vindicator) {
            super(vindicator, 1.0, false);

        }

        @Override
        protected double getAttackReachSqr(LivingEntity entity) {
            if (this.mob.getVehicle() instanceof Ravager) {
                float f = this.mob.getVehicle().getBbWidth() - 0.1f;
                return f * 2.0f * (f * 2.0f) + entity.getBbWidth();
            }
            return super.getAttackReachSqr(entity);
        }
    }
}
