package fuzs.illagerinvasion.world.entity.monster;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.init.v3.registry.LookupHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
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
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.PathfindingContext;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class Inquisitor extends AbstractIllager {
    private static final EntityDataAccessor<Boolean> STUNNED = SynchedEntityData.defineId(Inquisitor.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FINAL_ROAR = SynchedEntityData.defineId(Inquisitor.class, EntityDataSerializers.BOOLEAN);

    public boolean finalRoar;
    public int stunTick = 40;
    public boolean isStunned;
    public int blockedCount;

    public Inquisitor(final EntityType<? extends Inquisitor> entityType, final Level world) {
        super(entityType, world);
        this.xpReward = 25;
        this.setPathfindingMalus(PathType.LEAVES, 0.0F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, false));
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
        }
        super.customServerAiStep();
    }

    @Override
    public void aiStep() {
        if (this.horizontalCollision && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            boolean bl = false;
            final AABB box = this.getBoundingBox().inflate(1.0);
            for (final BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(box.minX), Mth.floor(box.minY), Mth.floor(box.minZ), Mth.floor(box.maxX), Mth.floor(box.maxY), Mth.floor(box.maxZ))) {
                final Block block = this.level().getBlockState(blockPos).getBlock();
                if (block instanceof LeavesBlock || block instanceof DoorBlock || block instanceof WebBlock) {
                    bl = this.level().destroyBlock(blockPos, true, this) || bl;
                    if (block instanceof DoorBlock) {
                        this.playSound(SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR, 1.0f, 1.0f);
                    }
                }
            }
        }
        super.aiStep();
    }

    @Override
    public boolean hasLineOfSight(final Entity entity) {
        return !this.getStunnedState() && super.hasLineOfSight(entity);
    }

    @Override
    protected boolean isImmobile() {
        return super.isImmobile() || this.getStunnedState();
    }

    @Override
    public void addAdditionalSaveData(final CompoundTag nbt) {
        nbt.putBoolean("Stunned", this.isStunned);
        nbt.putBoolean("FinalRoar", this.finalRoar);
        super.addAdditionalSaveData(nbt);
    }

    @Override
    public void readAdditionalSaveData(final CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setStunnedState(nbt.getBoolean("Stunned"));
        this.setFinalRoarState(nbt.getBoolean("FinalRoar"));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STUNNED, false);
        builder.define(FINAL_ROAR, false);
    }

    public boolean getStunnedState() {
        return this.entityData.get(STUNNED);
    }

    public void setStunnedState(final boolean isStunned) {
        this.entityData.set(STUNNED, isStunned);
    }

    public boolean getFinalRoarState() {
        return this.entityData.get(FINAL_ROAR);
    }

    public void setFinalRoarState(final boolean hasdoneRoar) {
        this.entityData.set(FINAL_ROAR, hasdoneRoar);
    }

    @Override
    public AbstractIllager.IllagerArmPose getArmPose() {
        if (this.isCelebrating()) {
            return AbstractIllager.IllagerArmPose.CELEBRATING;
        }
        if (this.isAggressive()) {
            return AbstractIllager.IllagerArmPose.ATTACKING;
        }
        return AbstractIllager.IllagerArmPose.NEUTRAL;
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.VINDICATOR_CELEBRATE;
    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        return new Navigation(this, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isAlive()) {
            return;
        }
        if (this.getStunnedState()) {
            --this.stunTick;
            if (this.stunTick <= 0) {
                this.setStunnedState(false);
                this.stunTick = 40;
            }
        }
    }

    private List<LivingEntity> getTargets() {
        return (this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(8.0), entity -> !(entity instanceof Monster)));
    }

    private void knockBack(final Entity entity) {
        final double d = entity.getX() - this.getX();
        final double e = entity.getZ() - this.getZ();
        final double f = Math.max(d * d + e * e, 0.001);
        entity.push(d / f * 0.6, 0.4, e / f * 0.6);
    }

    @Override
    protected void blockedByShield(final LivingEntity target) {
        this.knockBack(target);
        target.hurtMarked = true;
        target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 0));
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(final ServerLevelAccessor level, final DifficultyInstance difficulty, final MobSpawnType spawnReason, @Nullable final SpawnGroupData entityData) {
        final SpawnGroupData entityData2 = super.finalizeSpawn(level, difficulty, spawnReason, entityData);
        ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
        this.populateDefaultEquipmentSlots(level.getRandom(), difficulty);
        this.populateDefaultEquipmentEnchantments(level, level.getRandom(), difficulty);
        return entityData2;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, final DifficultyInstance difficulty) {
        if (this.getCurrentRaid() == null) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
            this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        }
    }

    @Override
    public boolean isAlliedTo(final Entity other) {
        return super.isAlliedTo(other) || (other instanceof LivingEntity livingEntity && livingEntity.getType().is(
                EntityTypeTags.ILLAGER_FRIENDS) && this.getTeam() == null && other.getTeam() == null);
    }

    @Override
    public boolean hurt(final DamageSource source, final float amount) {
        final Entity attacker = source.getEntity();
        final boolean hasShield = this.getOffhandItem().is(Items.SHIELD);
        if (this.isAggressive()) {
            if (attacker instanceof LivingEntity) {
                final ItemStack item = ((LivingEntity) attacker).getMainHandItem();
                final ItemStack basherItem = this.getOffhandItem();
                final boolean isShield = basherItem.is(Items.SHIELD);
                if ((item.is(ItemTags.AXES) || attacker instanceof IronGolem || this.blockedCount >= 4) && isShield) {
                    this.playSound(SoundEvents.SHIELD_BREAK, 1.0f, 1.0f);
                    this.setStunnedState(true);
                    if (this.level() instanceof ServerLevel) {
                        ((ServerLevel) this.level()).sendParticles((ParticleOptions) new ItemParticleOption(ParticleTypes.ITEM, basherItem), this.getX(), this.getY() + 1.5, this.getZ(), 30, 0.3, 0.2, 0.3, 0.003);
                        ((ServerLevel) this.level()).sendParticles((ParticleOptions) ParticleTypes.CLOUD, this.getX(), this.getY() + 1.0, this.getZ(), 30, 0.3, 0.3, 0.3, 0.1);
                        this.playSound(SoundEvents.RAVAGER_ROAR, 1.0f, 1.0f);
                        this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    }
                    this.getTargets().forEach(this::blockedByShield);
                    return super.hurt(source, amount);
                }
            }
            if (source.getDirectEntity() instanceof AbstractArrow && hasShield) {
                this.playSound(SoundEvents.SHIELD_BLOCK, 1.0f, 1.0f);
                ++this.blockedCount;
                return false;
            }
            if (source.getDirectEntity() instanceof LivingEntity && hasShield) {
                ++this.blockedCount;
                this.playSound(SoundEvents.SHIELD_BLOCK, 1.0f, 1.0f);
                return false;
            }
        }
        return super.hurt(source, amount);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModRegistry.ILLAGER_BRUTE_AMBIENT_SOUND_EVENT.value();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModRegistry.ILLAGER_BRUTE_DEATH_SOUND_EVENT.value();
    }

    @Override
    protected SoundEvent getHurtSound(final DamageSource source) {
        return ModRegistry.ILLAGER_BRUTE_HURT_SOUND_EVENT.value();
    }

    @Override
    public void applyRaidBuffs(ServerLevel level, int wave, boolean unused) {
        final ItemStack mainHandItem = new ItemStack(Items.STONE_SWORD);
        final ItemStack offHandItem = new ItemStack(Items.SHIELD);
        final Raid raid = this.getCurrentRaid();
        if (this.random.nextFloat() <= raid.getEnchantOdds()) {
            int enchantmentLevel = wave > raid.getNumGroups(Difficulty.NORMAL) ? 2 : 1;
            Holder<Enchantment> enchantment = LookupHelper.lookupEnchantment(level, Enchantments.SHARPNESS);
            mainHandItem.enchant(enchantment, enchantmentLevel);
        }
        this.setItemSlot(EquipmentSlot.MAINHAND, mainHandItem);
        this.setItemSlot(EquipmentSlot.OFFHAND, offHandItem);
    }

    static class NodeEvaluator extends WalkNodeEvaluator {
        @Override
        public Set<PathType> getPathTypeWithinMobBB(PathfindingContext context, int x, int y, int z) {
            Set<PathType> set = super.getPathTypeWithinMobBB(context, x, y, z);
            if (set.remove(PathType.LEAVES)) {
                set.add(PathType.OPEN);
            }
            return set;
        }
    }

    static class Navigation extends GroundPathNavigation {

        public Navigation(final Mob mobEntity, final Level world) {
            super(mobEntity, world);
        }

        @Override
        protected PathFinder createPathFinder(final int range) {
            this.nodeEvaluator = new NodeEvaluator();
            return new PathFinder(this.nodeEvaluator, range);
        }
    }
}