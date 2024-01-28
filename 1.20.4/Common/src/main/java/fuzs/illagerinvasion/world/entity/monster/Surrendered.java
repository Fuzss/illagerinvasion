package fuzs.illagerinvasion.world.entity.monster;

import fuzs.illagerinvasion.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class Surrendered extends Skeleton {
    protected static final EntityDataAccessor<Byte> DATA_VEX_FLAGS = SynchedEntityData.defineId(Surrendered.class, EntityDataSerializers.BYTE);
    private static final int CHARGING_FLAG = 1;

    @Nullable Mob owner;
    @Nullable
    private BlockPos bounds;
    private boolean alive;
    private int lifeTicks;

    public Surrendered(EntityType<? extends Surrendered> entityType, Level world) {
        super(entityType, world);
        this.moveControl = new Surrendered.VexMoveControl(this);
    }

    @Override
    public void move(MoverType movementType, Vec3 movement) {
        super.move(movementType, movement);
        this.checkInsideBlocks();
    }

    @Override
    public void tick() {
        super.tick();
        this.setNoGravity(true);
        if (this.alive && --this.lifeTicks <= 0) {
            this.lifeTicks = 20;
            this.hurt(this.damageSources().starve(), 1.0f);
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new Surrendered.ChargeTargetGoal());
        this.goalSelector.addGoal(8, new Surrendered.LookAtTargetGoal());
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
        this.targetSelector.addGoal(2, new Surrendered.TrackOwnerTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VEX_FLAGS, (byte) 0);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.8F;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.contains("BoundX")) {
            this.bounds = new BlockPos(nbt.getInt("BoundX"), nbt.getInt("BoundY"), nbt.getInt("BoundZ"));
        }
        if (nbt.contains("LifeTicks")) {
            this.setLifeTicks(nbt.getInt("LifeTicks"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        if (this.bounds != null) {
            nbt.putInt("BoundX", this.bounds.getX());
            nbt.putInt("BoundY", this.bounds.getY());
            nbt.putInt("BoundZ", this.bounds.getZ());
        }
        if (this.alive) {
            nbt.putInt("LifeTicks", this.lifeTicks);
        }
    }

    @Nullable
    public Mob getOwner() {
        return this.owner;
    }

    public void setOwner(Mob owner) {
        this.owner = owner;
    }

    @Nullable
    public BlockPos getBounds() {
        return this.bounds;
    }

    public void setBounds(@Nullable BlockPos pos) {
        this.bounds = pos;
    }

    private boolean areFlagsSet(int mask) {
        byte i = this.entityData.get(DATA_VEX_FLAGS);
        return (i & mask) != 0;
    }

    private void setVexFlag(int mask, boolean value) {
        int i = this.entityData.get(DATA_VEX_FLAGS).byteValue();
        if (value) {
            i |= mask;
        } else {
            i &= ~mask;
        }
        this.entityData.set(DATA_VEX_FLAGS, (byte) (i & 0xFF));
    }

    public boolean isCharging() {
        return this.areFlagsSet(CHARGING_FLAG);
    }

    public void setCharging(boolean charging) {
        this.setVexFlag(CHARGING_FLAG, charging);
    }

    public void setLifeTicks(int lifeTicks) {
        this.alive = true;
        this.lifeTicks = lifeTicks;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (!super.doHurtTarget(target)) {
            return false;
        }
        if (target instanceof LivingEntity) {
            ((LivingEntity) target).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1), this);
        }
        return true;
    }

    @Override
    public void aiStep() {
        if (!this.level().isClientSide()) {
            for (int i = 0; i < 2; ++i) {
                ((ServerLevel) this.level()).sendParticles(ParticleTypes.WHITE_ASH, this.xo, this.yo + 1.2, this.zo, 2, 0.2D, 0D, 0.2D, 0.025D);
            }
        }
        super.aiStep();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModRegistry.SURRENDERED_AMBIENT_SOUND_EVENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModRegistry.SURRENDERED_DEATH_SOUND_EVENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModRegistry.SURRENDERED_HURT_SOUND_EVENT.get();
    }

    @Override
    public float getLightLevelDependentMagicValue() {
        return 1.0f;
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityNbt) {
        this.populateDefaultEquipmentSlots(world.getRandom(), difficulty);
        this.populateDefaultEquipmentEnchantments(world.getRandom(), difficulty);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.AIR));
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0f);
    }

    class VexMoveControl extends MoveControl {

        public VexMoveControl(Surrendered owner) {
            super(owner);
        }

        @Override
        public void tick() {
            if (this.operation != MoveControl.Operation.MOVE_TO) {
                return;
            }
            Vec3 vec3d = new Vec3(this.wantedX - Surrendered.this.getX(), this.wantedY - Surrendered.this.getY(), this.wantedZ - Surrendered.this.getZ());
            double d = vec3d.length();
            if (d < Surrendered.this.getBoundingBox().getSize()) {
                this.operation = MoveControl.Operation.WAIT;
                Surrendered.this.setDeltaMovement(Surrendered.this.getDeltaMovement().scale(0.5));
            } else {
                Surrendered.this.setDeltaMovement(Surrendered.this.getDeltaMovement().add(vec3d.scale(this.speedModifier * 0.05 / d)));
                if (Surrendered.this.getTarget() == null) {
                    Vec3 vec3d2 = Surrendered.this.getDeltaMovement();
                    Surrendered.this.setYRot(-((float) Mth.atan2(vec3d2.x, vec3d2.z)) * 57.295776f);
                    Surrendered.this.yBodyRot = Surrendered.this.getYRot();
                } else {
                    double e = Surrendered.this.getTarget().getX() - Surrendered.this.getX();
                    double f = Surrendered.this.getTarget().getZ() - Surrendered.this.getZ();
                    Surrendered.this.setYRot(-((float) Mth.atan2(e, f)) * 57.295776f);
                    Surrendered.this.yBodyRot = Surrendered.this.getYRot();
                }
            }
        }
    }

    class ChargeTargetGoal extends Goal {

        public ChargeTargetGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (Surrendered.this.getTarget() != null && !Surrendered.this.getMoveControl().hasWanted() && Surrendered.this.random.nextInt(Surrendered.ChargeTargetGoal.reducedTickDelay(7)) == 0) {
                return Surrendered.this.distanceToSqr(Surrendered.this.getTarget()) > 4.0;
            }
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            return Surrendered.this.getMoveControl().hasWanted() && Surrendered.this.isCharging() && Surrendered.this.getTarget() != null && Surrendered.this.getTarget().isAlive();
        }

        @Override
        public void start() {
            LivingEntity livingEntity = Surrendered.this.getTarget();
            if (livingEntity != null) {
                Vec3 vec3d = livingEntity.getEyePosition();
                Surrendered.this.moveControl.setWantedPosition(vec3d.x, vec3d.y, vec3d.z, 1.0);
            }
            Surrendered.this.setCharging(true);
            Surrendered.this.playSound(ModRegistry.SURRENDERED_CHARGE_SOUND_EVENT.get(), 1.0f, 1.0f);
        }

        @Override
        public void stop() {
            Surrendered.this.setCharging(false);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = Surrendered.this.getTarget();
            if (livingEntity == null) {
                return;
            }
            if (Surrendered.this.getBoundingBox().intersects(livingEntity.getBoundingBox())) {
                Surrendered.this.doHurtTarget(livingEntity);
                Surrendered.this.setCharging(false);
            } else {
                double d = Surrendered.this.distanceToSqr(livingEntity);
                if (d < 9.0) {
                    Vec3 vec3d = livingEntity.getEyePosition();
                    Surrendered.this.moveControl.setWantedPosition(vec3d.x, vec3d.y, vec3d.z, 1.0);
                }
            }
        }
    }

    class LookAtTargetGoal extends Goal {

        public LookAtTargetGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !Surrendered.this.getMoveControl().hasWanted() && Surrendered.this.random.nextInt(Surrendered.LookAtTargetGoal.reducedTickDelay(7)) == 0;
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void tick() {
            BlockPos blockPos = Surrendered.this.getBounds();
            if (blockPos == null) {
                blockPos = Surrendered.this.blockPosition();
            }
            for (int i = 0; i < 3; ++i) {
                BlockPos blockPos2 = blockPos.offset(Surrendered.this.random.nextInt(15) - 7, Surrendered.this.random.nextInt(11) - 5, Surrendered.this.random.nextInt(15) - 7);
                if (!Surrendered.this.level().isEmptyBlock(blockPos2)) continue;
                Surrendered.this.moveControl.setWantedPosition((double) blockPos2.getX() + 0.5, (double) blockPos2.getY() + 0.5, (double) blockPos2.getZ() + 0.5, 0.25);
                if (Surrendered.this.getTarget() != null) break;
                Surrendered.this.getLookControl().setLookAt((double) blockPos2.getX() + 0.5, (double) blockPos2.getY() + 0.5, (double) blockPos2.getZ() + 0.5, 180.0f, 20.0f);
                break;
            }
        }
    }

    class TrackOwnerTargetGoal extends TargetGoal {

        private final TargetingConditions targetPredicate;

        public TrackOwnerTargetGoal(PathfinderMob mob) {
            super(mob, false);
            this.targetPredicate = TargetingConditions.forNonCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
        }

        @Override
        public boolean canUse() {
            return Surrendered.this.owner != null && Surrendered.this.owner.getTarget() != null && this.canAttack(Surrendered.this.owner.getTarget(), this.targetPredicate);
        }

        @Override
        public void start() {
            Surrendered.this.setTarget(Surrendered.this.owner.getTarget());
            super.start();
        }
    }
}
