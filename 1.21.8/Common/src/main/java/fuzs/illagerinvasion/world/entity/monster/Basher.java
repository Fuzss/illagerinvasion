package fuzs.illagerinvasion.world.entity.monster;

import fuzs.illagerinvasion.init.ModSoundEvents;
import fuzs.puzzleslib.api.item.v2.EnchantingHelper;
import fuzs.puzzleslib.api.item.v2.ToolTypeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.creaking.Creaking;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

public class Basher extends AbstractIllager implements Stunnable {
    private static final String TAG_STUN_TICKS = "Stunned";
    private static final String TAG_BLOCKED_COUNT = "BlockedCount";
    private static final EntityDataAccessor<Boolean> DATA_STUNNED = SynchedEntityData.defineId(Basher.class,
            EntityDataSerializers.BOOLEAN);

    private int stunTicks;
    private int blockedCount;

    public Basher(EntityType<? extends Basher> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Creaking.class, 8.0F, 1.0, 1.2));
        this.goalSelector.addGoal(2, new AbstractIllager.RaiderOpenDoorGoal(this));
        this.goalSelector.addGoal(3, new Raider.HoldGroundAttackGoal(this, 10.0F));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    protected void customServerAiStep(ServerLevel serverLevel) {
        if (!this.isNoAi() && GoalUtils.hasGroundPathNavigation(this)) {
            boolean isRaided = serverLevel.isRaided(this.blockPosition());
            ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(isRaided);
            super.customServerAiStep(serverLevel);
        }
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        valueOutput.putInt(TAG_STUN_TICKS, this.stunTicks);
        valueOutput.putInt(TAG_BLOCKED_COUNT, this.blockedCount);
        super.addAdditionalSaveData(valueOutput);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        this.setStunTicks(valueInput.getIntOr(TAG_STUN_TICKS, 0));
        this.blockedCount = valueInput.getIntOr(TAG_BLOCKED_COUNT, 0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_STUNNED, false);
    }

    @Override
    public boolean isStunned() {
        return this.entityData.get(DATA_STUNNED);
    }

    public void setStunTicks(int stunTicks) {
        this.stunTicks = Math.max(0, stunTicks);
        this.entityData.set(DATA_STUNNED, this.stunTicks > 0);
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
        if (this.isAlive() && this.stunTicks > 0) {
            this.setStunTicks(--this.stunTicks);
        }
    }

    @Override
    protected boolean isImmobile() {
        return super.isImmobile() || this.isStunned();
    }

    @Override
    public AbstractIllager.IllagerArmPose getArmPose() {
        if (this.isCelebrating()) {
            return AbstractIllager.IllagerArmPose.CELEBRATING;
        } else if (this.isAggressive()) {
            return IllagerArmPose.ATTACKING;
        } else {
            return IllagerArmPose.CROSSED;
        }
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return ModSoundEvents.BASHER_CELEBRATE_SOUND_EVENT.value();
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float damageAmount) {
        Entity attacker = damageSource.getEntity();
        boolean hasShield = this.getMainHandItem().is(Items.SHIELD);
        if (this.isAggressive()) {
            if (attacker instanceof LivingEntity) {
                ItemStack attackerMainHand = ((LivingEntity) attacker).getMainHandItem();
                ItemStack basherMainHand = this.getMainHandItem();
                if ((ToolTypeHelper.INSTANCE.isAxe(attackerMainHand) || attacker instanceof IronGolem
                        || this.blockedCount >= 4) && basherMainHand.is(Items.SHIELD)) {
                    this.playSound(SoundEvents.SHIELD_BREAK.value(), 1.0F, 1.0F);
                    this.setStunTicks(60);
                    serverLevel.sendParticles((ParticleOptions) new ItemParticleOption(ParticleTypes.ITEM,
                            basherMainHand), this.getX(), this.getY() + 1.5, this.getZ(), 30, 0.3, 0.2, 0.3, 0.003);
                    this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_AXE));
                    return super.hurtServer(serverLevel, damageSource, damageAmount);
                }
            }
            if (damageSource.getDirectEntity() instanceof AbstractArrow && hasShield) {
                this.playSound(SoundEvents.SHIELD_BLOCK.value(), 1.0F, 1.0F);
                ++this.blockedCount;
                return false;
            }
            if (damageSource.getDirectEntity() instanceof LivingEntity && hasShield) {
                this.playSound(SoundEvents.SHIELD_BLOCK.value(), 1.0F, 1.0F);
                ++this.blockedCount;
                return false;
            }
        }
        return super.hurtServer(serverLevel, damageSource, damageAmount);
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason entitySpawnReason, @Nullable SpawnGroupData spawnGroupData) {
        spawnGroupData = super.finalizeSpawn(level, difficulty, entitySpawnReason, spawnGroupData);
        ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
        this.populateDefaultEquipmentSlots(level.getRandom(), difficulty);
        this.populateDefaultEquipmentEnchantments(level, level.getRandom(), difficulty);
        return spawnGroupData;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficulty) {
        if (this.getCurrentRaid() == null) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.SHIELD));
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.BASHER_AMBIENT_SOUND_EVENT.value();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.BASHER_DEATH_SOUND_EVENT.value();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSoundEvents.BASHER_HURT_SOUND_EVENT.value();
    }

    @Override
    public void applyRaidBuffs(ServerLevel level, int wave, boolean unused) {
        ItemStack itemStack = new ItemStack(Items.SHIELD);
        Raid raid = this.getCurrentRaid();
        int enchantmentLevel = wave > raid.getNumGroups(Difficulty.NORMAL) ? 2 : 1;
        if (this.random.nextFloat() <= raid.getEnchantOdds()) {
            Holder<Enchantment> enchantment = EnchantingHelper.lookup(level, Enchantments.UNBREAKING);
            itemStack.enchant(enchantment, enchantmentLevel);
        }
        this.setItemSlot(EquipmentSlot.MAINHAND, itemStack);
    }
}
