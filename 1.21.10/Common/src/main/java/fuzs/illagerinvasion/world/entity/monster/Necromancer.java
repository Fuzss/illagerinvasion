package fuzs.illagerinvasion.world.entity.monster;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.illagerinvasion.init.ModSoundEvents;
import fuzs.illagerinvasion.world.entity.projectile.SkullBolt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.creaking.Creaking;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Necromancer extends SpellcasterIllager {
    private static final EntityDataAccessor<Boolean> DATA_IS_SHIELDED = SynchedEntityData.defineId(Necromancer.class,
            EntityDataSerializers.BOOLEAN);

    private int conjureSkullCooldown;

    public Necromancer(EntityType<? extends Necromancer> entityType, Level world) {
        super(entityType, world);
        this.xpReward = Enemy.XP_REWARD_LARGE;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Creaking.class, 8.0F, 1.0, 1.2));
        this.goalSelector.addGoal(2, new SpellcasterIllager.SpellcasterCastingSpellGoal());
        this.goalSelector.addGoal(4, new ConjureSkullGoal());
        this.goalSelector.addGoal(3, new SummonUndeadGoal());
        this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Player.class, 8.0f, 0.6, 1.0));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
        this.targetSelector.addGoal(2,
                new NearestAttackableTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3,
                new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_IS_SHIELDED, false);
    }

    public boolean getShieldedState() {
        return this.entityData.get(DATA_IS_SHIELDED);
    }

    public void setShieldedState(boolean isShielded) {
        this.entityData.set(DATA_IS_SHIELDED, isShielded);
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.ILLUSIONER_AMBIENT;
    }

    @Override
    protected void customServerAiStep(ServerLevel serverLevel) {
        super.customServerAiStep(serverLevel);
        --this.conjureSkullCooldown;
        List<Mob> mobs = serverLevel.getEntitiesOfClass(Mob.class,
                this.getBoundingBox().inflate(10.0),
                (Mob mob) -> mob.getType().is(EntityTypeTags.UNDEAD));
        if (!mobs.isEmpty()) {
            mobs.forEach(this::doUndeadLinkLogic);
            if (this.tickCount % 10 == 0) {
                mobs.forEach(this::doUndeadLinkParticle);
            }
        }
        if (this.tickCount % 20 == 0) {
            this.setShieldedState(!mobs.isEmpty());
        }
        if (this.getTarget() != null) {
            mobs = serverLevel.getEntitiesOfClass(Mob.class,
                    this.getBoundingBox().inflate(30.0),
                    (Mob mob) -> mob.getType().is(EntityTypeTags.UNDEAD));
            mobs.forEach(this::setUndeadTarget);
        }
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return !target.getType().is(EntityTypeTags.UNDEAD) && super.canAttack(target);
    }

    public void doUndeadLinkLogic(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 10, 0));
        entity.addEffect(new MobEffectInstance(MobEffects.SPEED, 10, 0));
        entity.clearFire();
        if (entity instanceof Mob mob) {
            mob.setTarget(this.getTarget());
        }
    }

    public void setUndeadTarget(LivingEntity entity) {
        if (this.getTarget() != null && entity instanceof Mob) {
            ((Mob) entity).setTarget(this.getTarget());
        }
    }

    public void doUndeadLinkParticle(LivingEntity entity) {
        if (this.level() instanceof ServerLevel serverLevel) {
            double x = entity.getX();
            double y = entity.getY();
            double z = entity.getZ();
            serverLevel.sendParticles(ModRegistry.NECROMANCER_BUFF_PARTICLE_TYPE.value(),
                    x,
                    y + 1.0,
                    z,
                    1,
                    0.4,
                    0.5,
                    0.4,
                    0.015);
            serverLevel.sendParticles(ModRegistry.NECROMANCER_BUFF_PARTICLE_TYPE.value(),
                    this.getX(),
                    this.getY() + 1.0,
                    this.getZ(),
                    1,
                    0.4,
                    0.5,
                    0.4,
                    0.015);
        }
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float damageAmount) {
        return super.hurtServer(serverLevel,
                damageSource,
                this.getShieldedState() ? damageAmount / 2.0F : damageAmount);
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
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ILLUSIONER_HURT;
    }

    @Override
    protected SoundEvent getCastingSoundEvent() {
        return SoundEvents.EVOKER_CAST_SPELL;
    }

    @Override
    public void applyRaidBuffs(ServerLevel level, int wave, boolean unused) {
        // NO-OP
    }

    public boolean isPowered() {
        return this.getShieldedState();
    }

    class SummonUndeadGoal extends SpellcasterUseSpellGoal {
        private TargetingConditions closeVexPredicate = TargetingConditions.forNonCombat()
                .range(16.0)
                .ignoreLineOfSight()
                .ignoreInvisibilityTesting();
        private int spellcount;

        @Override
        public boolean canUse() {
            if (!super.canUse()) {
                return false;
            }
            if (this.spellcount >= 4) {
                return false;
            }
            int i = ((ServerLevel) Necromancer.this.level()).getNearbyEntities(Zombie.class,
                    this.closeVexPredicate,
                    Necromancer.this,
                    Necromancer.this.getBoundingBox().inflate(16.0)).size();
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
            int spawnAmount = Necromancer.this.level().isDarkOutside() ? 4 : 2;
            for (int i = 0; i < spawnAmount; ++i) {
                this.summonUndead(EntityType.ZOMBIE);
            }

            this.summonUndead(EntityType.SKELETON);
            ++this.spellcount;
        }

        private void summonUndead(EntityType<? extends Mob> entityType) {
            ServerLevel serverLevel = (ServerLevel) Necromancer.this.level();
            BlockPos blockPos = Necromancer.this.blockPosition()
                    .offset(-2 + Necromancer.this.random.nextInt(5), 1, -2 + Necromancer.this.random.nextInt(5));
            Mob mob = entityType.create(serverLevel, EntitySpawnReason.MOB_SUMMONED);
            mob.snapTo(blockPos, 0.0F, 0.0F);
            mob.finalizeSpawn(serverLevel,
                    serverLevel.getCurrentDifficultyAt(blockPos),
                    EntitySpawnReason.MOB_SUMMONED,
                    null);
            serverLevel.addFreshEntityWithPassengers(mob);
        }

        @Override
        protected SoundEvent getSpellPrepareSound() {
            return ModSoundEvents.NECROMANCER_SUMMON_SOUND_EVENT.value();
        }

        @Override
        protected IllagerSpell getSpell() {
            return IllagerSpell.FANGS;
        }
    }

    public class ConjureSkullGoal extends SpellcasterUseSpellGoal {

        @Override
        public boolean canUse() {
            if (Necromancer.this.getTarget() == null) {
                return false;
            } else if (Necromancer.this.conjureSkullCooldown > 0) {
                return false;
            } else if (Necromancer.this.conjureSkullCooldown <= 0) {
                return true;
            } else {
                return true;
            }
        }

        /**
         * @see net.minecraft.world.entity.boss.wither.WitherBoss#performRangedAttack(int, double, double, double,
         *         boolean)
         */
        private void shootSkullAt(double targetX, double targetY, double targetZ) {
            double d = Necromancer.this.getX();
            double e = Necromancer.this.getY() + 2.5;
            double f = Necromancer.this.getZ();
            double g = targetX - d;
            double h = targetY - e;
            double i = targetZ - f;
            Vec3 vec3 = new Vec3(g, h, i);
            SkullBolt skullbolt = new SkullBolt(Necromancer.this.level(), Necromancer.this, vec3.normalize());
            skullbolt.setOwner(Necromancer.this);
            skullbolt.setPosRaw(d, e, f);
            Necromancer.this.level().addFreshEntity(skullbolt);
        }

        @Override
        protected void performSpellCasting() {
            this.shootSkullAt(Necromancer.this.getTarget());
            if (Necromancer.this.level() instanceof ServerLevel serverLevel) {
                double x = Necromancer.this.getX();
                double y = Necromancer.this.getY() + 2.5;
                double z = Necromancer.this.getZ();
                serverLevel.sendParticles(ParticleTypes.SMOKE, x, y, z, 40, 0.4D, 0.4D, 0.4D, 0.15D);
            }

            Necromancer.this.conjureSkullCooldown = 100;
        }

        private void shootSkullAt(LivingEntity target) {
            this.shootSkullAt(target.getX(), target.getY() + (double) target.getEyeHeight() * 0.5, target.getZ());
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
            return IllagerSpell.BLINDNESS;
        }
    }
}
