package fuzs.illagerinvasion.world.entity.monster;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.illagerinvasion.world.entity.projectile.SkullBolt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;

import java.util.List;

public class Necromancer extends SpellcasterIllager implements PowerableMob {
    private static final EntityDataAccessor<Boolean> DATA_IS_SHIELDED = SynchedEntityData.defineId(Necromancer.class, EntityDataSerializers.BOOLEAN);
    
    private int conjureSkullCooldown;
    private int particleTicks;

    public Necromancer(final EntityType<? extends Necromancer> entityType, final Level world) {
        super(entityType, world);
        this.xpReward = 15;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LookAtTargetOrWololoTarget());
        this.goalSelector.addGoal(4, new ConjureSkullGoal());
        this.goalSelector.addGoal(3, new SummonUndeadGoal());
        this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Player.class, 8.0f, 0.6, 1.0));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_SHIELDED, false);
    }

    public boolean getShieldedState() {
        return this.entityData.get(DATA_IS_SHIELDED);
    }

    public void setShieldedState(final boolean isShielded) {
        this.entityData.set(DATA_IS_SHIELDED, isShielded);
    }

    @Override
    public void readAdditionalSaveData(final CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.ILLUSIONER_AMBIENT;
    }

    @Override
    public void addAdditionalSaveData(final CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        --this.conjureSkullCooldown;
        if (!this.getNearUndeadForLink().isEmpty()) {
            ++this.particleTicks;
            this.setShieldedState(true);
            this.getNearUndeadForLink().forEach(this::doUndeadLinkLogic);
            if (this.particleTicks == 10) {
                this.getNearUndeadForLink().forEach(this::doUndeadLinkParticle);
                this.particleTicks = 0;
            }
        } else {
            this.setShieldedState(false);
        }
        if (this.getTarget() instanceof Skeleton) {
            this.setTarget(null);
        }
        if (!this.getNearUndeadForTarget().isEmpty()) {
            this.getNearUndeadForTarget().forEach(this::setUndeadTarget);
        }
    }

    public List<LivingEntity> getNearUndeadForLink() {
        return this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(10.0), entity -> entity.getMobType() == MobType.UNDEAD);
    }

    public List<LivingEntity> getNearUndeadForTarget() {
        return this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(30.0), entity -> entity.getMobType() == MobType.UNDEAD);
    }

    public void doUndeadLinkLogic(final LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 5, 0));
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 5, 0));
        if (entity instanceof Mob) {
            ((Mob) entity).setTarget(this.getTarget());
        }
        if (entity instanceof Skeleton) {
            entity.setRemainingFireTicks(0);
        }
    }

    public void setUndeadTarget(final LivingEntity entity) {
        if (this.getTarget() != null && entity instanceof Mob) {
            ((Mob) entity).setTarget(this.getTarget());
        }
    }

    public void doUndeadLinkParticle(final LivingEntity entity) {
        final double x = entity.getX();
        final double y = entity.getY();
        final double z = entity.getZ();
        if (this.level() instanceof ServerLevel && this.particleTicks == 10) {
            ((ServerLevel) this.level()).sendParticles(ModRegistry.NECROMANCER_BUFF_PARTICLE_TYPE.get(), x, y + 1.0, z, 1, 0.4, 0.5, 0.4, 0.015);
            ((ServerLevel) this.level()).sendParticles(ModRegistry.NECROMANCER_BUFF_PARTICLE_TYPE.get(), this.getX(), this.getY() + 1.0, this.getZ(), 1, 0.4, 0.5, 0.4, 0.015);
        }
    }

    @Override
    public boolean hurt(final DamageSource source, final float amount) {
        if (this.getShieldedState()) {
            final float halfamount = amount / 2.0f;
            return super.hurt(source, halfamount);
        }
        final boolean bl2 = super.hurt(source, amount);
        return bl2;
    }

    @Override
    public boolean isAlliedTo(final Entity other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (super.isAlliedTo(other)) {
            return true;
        }
        if (other instanceof Vex) {
            return this.isAlliedTo(((Vex) other).getOwner());
        }
        return other instanceof LivingEntity && ((LivingEntity) other).getMobType() == MobType.ILLAGER && this.getTeam() == null && other.getTeam() == null;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ILLUSIONER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ILLUSIONER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(final DamageSource source) {
        return SoundEvents.ILLUSIONER_HURT;
    }

    @Override
    protected SoundEvent getCastingSoundEvent() {
        return SoundEvents.EVOKER_CAST_SPELL;
    }

    @Override
    public void applyRaidBuffs(final int wave, final boolean unused) {
    }

    @Override
    public IllagerArmPose getArmPose() {
        if (this.isCastingSpell()) {
            return IllagerArmPose.SPELLCASTING;
        }
        return IllagerArmPose.CROSSED;
    }

    @Override
    public boolean isPowered() {
        return this.getShieldedState();
    }

    class LookAtTargetOrWololoTarget extends SpellcasterCastingSpellGoal {

        @Override
        public void tick() {
            if (Necromancer.this.getTarget() != null) {
                Necromancer.this.getLookControl().setLookAt(Necromancer.this.getTarget(), Necromancer.this.getMaxHeadYRot(), Necromancer.this.getMaxHeadXRot());
            }
        }
    }

    class SummonUndeadGoal extends SpellcasterUseSpellGoal {
        private final TargetingConditions closeVexPredicate = TargetingConditions.forNonCombat().range(16.0).ignoreLineOfSight().ignoreInvisibilityTesting();
        private int spellcount;

        SummonUndeadGoal() {
        }

        @Override
        public boolean canUse() {
            if (!super.canUse()) {
                return false;
            }
            if (this.spellcount >= 4) {
                return false;
            }
            int i = Necromancer.this.level().getNearbyEntities(Zombie.class, this.closeVexPredicate, Necromancer.this, Necromancer.this.getBoundingBox().inflate(16.0)).size();
            return Necromancer.this.random.nextInt(5) + 1 > i;
        }

        @Override
        protected int getCastingTime() {
            return 100;
        }

        @Override
        protected int getCastingInterval() {
            return 340;
        }

        @Override
        protected void performSpellCasting() {
            if (Necromancer.this.level().isNight()) {
                ServerLevel serverWorld = (ServerLevel) Necromancer.this.level();
                for (int i = 0; i < 3; ++i) {
                    BlockPos blockPos = Necromancer.this.blockPosition().offset(-2 + Necromancer.this.random.nextInt(5), 1, -2 + Necromancer.this.random.nextInt(5));
                    Zombie zombieEntity = EntityType.ZOMBIE.create(Necromancer.this.level());
                    zombieEntity.moveTo(blockPos, 0.0f, 0.0f);
                    zombieEntity.finalizeSpawn(serverWorld, Necromancer.this.level().getCurrentDifficultyAt(blockPos), MobSpawnType.MOB_SUMMONED, null, null);
                    serverWorld.addFreshEntityWithPassengers(zombieEntity);
                }
                BlockPos blockPos = Necromancer.this.blockPosition().offset(-2 + Necromancer.this.random.nextInt(5), 1, -2 + Necromancer.this.random.nextInt(5));
                Skeleton skeletonEntity = EntityType.SKELETON.create(Necromancer.this.level());
                skeletonEntity.moveTo(blockPos, 0.0f, 0.0f);
                skeletonEntity.finalizeSpawn(serverWorld, Necromancer.this.level().getCurrentDifficultyAt(blockPos), MobSpawnType.MOB_SUMMONED, null, null);
                serverWorld.addFreshEntityWithPassengers(skeletonEntity);
            }
            if (Necromancer.this.level().isDay()) {
                ServerLevel serverWorld = (ServerLevel) Necromancer.this.level();
                for (int i = 0; i < 2; ++i) {
                    BlockPos blockPos = Necromancer.this.blockPosition().offset(-2 + Necromancer.this.random.nextInt(5), 1, -2 + Necromancer.this.random.nextInt(5));
                    Husk zombieEntity = EntityType.HUSK.create(Necromancer.this.level());
                    zombieEntity.moveTo(blockPos, 0.0f, 0.0f);
                    zombieEntity.finalizeSpawn(serverWorld, Necromancer.this.level().getCurrentDifficultyAt(blockPos), MobSpawnType.MOB_SUMMONED, null, null);
                    serverWorld.addFreshEntityWithPassengers(zombieEntity);
                }
                BlockPos blockPos = Necromancer.this.blockPosition().offset(-2 + Necromancer.this.random.nextInt(5), 1, -2 + Necromancer.this.random.nextInt(5));
                Skeleton skeletonEntity = EntityType.SKELETON.create(Necromancer.this.level());
                skeletonEntity.moveTo(blockPos, 0.0f, 0.0f);
                skeletonEntity.finalizeSpawn(serverWorld, Necromancer.this.level().getCurrentDifficultyAt(blockPos), MobSpawnType.MOB_SUMMONED, null, null);
                serverWorld.addFreshEntityWithPassengers(skeletonEntity);
            }
            ++this.spellcount;
        }

        @Override
        protected SoundEvent getSpellPrepareSound() {
            return ModRegistry.NECROMANCER_SUMMON_SOUND_EVENT.get();
        }

        @Override
        protected IllagerSpell getSpell() {
            return (IllagerSpell) ModRegistry.NECRO_RAISE_ILLAGER_SPELL;
        }
    }


    public class ConjureSkullGoal extends SpellcasterUseSpellGoal {

        @Override
        public boolean canUse() {
            if (Necromancer.this.getTarget() == null) {
                return false;
            }
            if (Necromancer.this.conjureSkullCooldown > 0) {
                return false;
            }
            if (Necromancer.this.conjureSkullCooldown <= 0) {
                return true;
            }
            return true;
        }

        @Override
        public void stop() {
            super.stop();
        }

        private void shootSkullAt(LivingEntity target) {
            this.shootSkullAt(target.getX(), target.getY() + (double) target.getEyeHeight() * 0.5, target.getZ());
        }

        private void shootSkullAt(double targetX, double targetY, double targetZ) {
            double d = Necromancer.this.getX();
            double e = Necromancer.this.getY() + 2.5;
            double f = Necromancer.this.getZ();
            double g = targetX - d;
            double h = targetY - e;
            double i = targetZ - f;
            SkullBolt skullbolt = new SkullBolt(Necromancer.this.level(), Necromancer.this, g, h, i);
            skullbolt.setOwner(Necromancer.this);
            skullbolt.setPosRaw(d, e, f);
            Necromancer.this.level().addFreshEntity(skullbolt);
        }

        @Override
        protected void performSpellCasting() {
            this.shootSkullAt(Necromancer.this.getTarget());
            if (Necromancer.this.level() instanceof ServerLevel) {
                double x = Necromancer.this.getX();
                double y = Necromancer.this.getY() + 2.5;
                double z = Necromancer.this.getZ();
                ((ServerLevel) Necromancer.this.level()).sendParticles(ParticleTypes.SMOKE, x, y, z, 40, 0.4D, 0.4D, 0.4D, 0.15D);
            }
            Necromancer.this.conjureSkullCooldown = 100;
        }

        @Override
        protected int getCastWarmupTime() {
            return 40;
        }

        @Override
        protected int getCastingTime() {
            return 60;
        }

        @Override
        protected int getCastingInterval() {
            return 140;
        }

        @Override
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOKER_PREPARE_ATTACK;
        }

        @Override
        protected IllagerSpell getSpell() {
            return (IllagerSpell) ModRegistry.CONJURE_SKULL_BOLT_ILLAGER_SPELL;
        }
    }
}
