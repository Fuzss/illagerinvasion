package fuzs.illagerinvasion.world.entity.monster;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.config.ServerConfig;
import fuzs.illagerinvasion.init.ModEntityTypes;
import fuzs.illagerinvasion.init.ModSoundEvents;
import fuzs.illagerinvasion.util.TeleportUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Invoker extends SpellcasterIllager {
    private static final EntityDataAccessor<Boolean> DATA_IS_SHIELDED = SynchedEntityData.defineId(Invoker.class,
            EntityDataSerializers.BOOLEAN);

    private final ServerBossEvent bossBar = new ServerBossEvent(this.getDisplayName(),
            BossEvent.BossBarColor.YELLOW,
            BossEvent.BossBarOverlay.PROGRESS);
    public int areaDamageCooldown;
    public int teleportCooldown;
    public boolean isAoeCasting = false;
    public int fangaoecooldown;

    public Invoker(EntityType<? extends Invoker> entityType, Level world) {
        super(entityType, world);
        this.xpReward = Enemy.XP_REWARD_BOSS;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SpellcasterIllager.SpellcasterCastingSpellGoal());
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Player.class, 8.0F, 0.6, 1.0));
        this.goalSelector.addGoal(5, new AreaDamageGoal());
        this.goalSelector.addGoal(4, new CastTeleportGoal());
        this.goalSelector.addGoal(5, new SummonVexGoal());
        this.goalSelector.addGoal(5, new ConjureAoeFangsGoal());
        this.goalSelector.addGoal(6, new ConjureFangsGoal());
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
        this.targetSelector.addGoal(2,
                new NearestAttackableTargetGoal<Player>(this, Player.class, true).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3,
                new NearestAttackableTargetGoal<AbstractVillager>(this,
                        AbstractVillager.class,
                        false).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, false));
    }

    public boolean isPowered() {
        return this.isShielded();
    }

    @Override
    public boolean doHurtTarget(ServerLevel serverLevel, Entity target) {
        if (!super.doHurtTarget(serverLevel, target)) {
            return false;
        }
        if (target instanceof LivingEntity) {
            ((LivingEntity) target).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0), this);
        }
        return true;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_IS_SHIELDED, false);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
        valueOutput.putBoolean("Invul", this.isShielded());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        this.setShielded(valueInput.getBooleanOr("Invul", false));
        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
        }
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.EVOKER_CELEBRATE;
    }

    public boolean isShielded() {
        return this.entityData.get(DATA_IS_SHIELDED);
    }

    public void setShielded(boolean isShielded) {
        this.entityData.set(DATA_IS_SHIELDED, isShielded);
    }

    @Override
    protected void customServerAiStep(ServerLevel serverLevel) {
        --this.teleportCooldown;
        --this.areaDamageCooldown;
        --this.fangaoecooldown;
        super.customServerAiStep(serverLevel);
        this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());
        if (this.isAoeCasting && this.isCastingSpell()) {
            this.sendSpellParticles(serverLevel, ParticleTypes.SMOKE, 2, 0.06D);
        }
        Vec3 deltaMovement = this.getDeltaMovement();
        if (!this.onGround() && deltaMovement.y < 0.0) {
            this.setDeltaMovement(deltaMovement.multiply(1.0, 0.6, 1.0));
        }
        serverLevel.sendParticles(ParticleTypes.SMOKE,
                this.getX(),
                this.getY() + 0.3,
                this.getZ(),
                1,
                0.2D,
                0.2D,
                0.2D,
                0.005D);
    }

    private void sendSpellParticles(ServerLevel serverLevel, ParticleOptions particleTypes, int count, double speed) {
        float g = this.yBodyRot * ((float) Math.PI / 180) + Mth.cos((float) this.tickCount * 0.6662F) * 0.25F;
        float h = Mth.cos(g);
        float i = Mth.sin(g);
        serverLevel.sendParticles(particleTypes,
                this.getX() + (double) h * 0.6,
                this.getY() + 1.8,
                this.getZ() + (double) i,
                count,
                0.0D,
                0.0D,
                0.0D,
                speed);
        serverLevel.sendParticles(particleTypes,
                this.getX() - (double) h * 0.6,
                this.getY() + 1.8,
                this.getZ() - (double) i,
                count,
                0.0D,
                0.0D,
                0.0D,
                speed);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        if (IllagerInvasion.CONFIG.get(ServerConfig.class).invokerBossBar) {
            this.bossBar.addPlayer(player);
        }
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        if (IllagerInvasion.CONFIG.get(ServerConfig.class).invokerBossBar) {
            this.bossBar.removePlayer(player);
        }
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        // NO-OP
    }

    @Override
    public boolean considersEntityAsAlly(Entity entity) {
        if (entity == this) {
            return true;
        } else if (super.considersEntityAsAlly(entity)) {
            return true;
        } else {
            if (entity instanceof Vex vex && vex.getOwner() != null) {
                return this.considersEntityAsAlly(vex.getOwner());
            }

            return false;
        }
    }

    @Override
    public AbstractIllager.IllagerArmPose getArmPose() {
        if (this.isAggressive()) {
            return AbstractIllager.IllagerArmPose.ATTACKING;
        } else if (this.isCastingSpell()) {
            return IllagerArmPose.SPELLCASTING;
        } else {
            return IllagerArmPose.CROSSED;
        }
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float damageAmount) {
        if (damageSource.is(DamageTypeTags.IS_PROJECTILE)) {
            Entity entity = damageSource.getDirectEntity();
            if (entity != null && entity.getType().is(EntityTypeTags.IMPACT_PROJECTILES)) {
                if (!this.isShielded() && this.random.nextInt(2) == 0) {
                    this.playSound(SoundEvents.SHIELD_BLOCK.value(),
                            1.0f,
                            0.8F + serverLevel.random.nextFloat() * 0.4F);
                    this.setShielded(true);
                }
            }
        } else if (this.isShielded() && this.random.nextInt(3) == 0) {
            serverLevel.sendParticles(ParticleTypes.CRIT,
                    this.getX(),
                    this.getY() + 1,
                    this.getZ(),
                    30,
                    0.5D,
                    0.7D,
                    0.5D,
                    0.5D);
            this.playSound(ModSoundEvents.INVOKER_SHIELD_BREAK_SOUND_EVENT.value(),
                    1.0f,
                    0.8F + serverLevel.random.nextFloat() * 0.4F);
            this.setShielded(false);
        }

        return super.hurtServer(serverLevel, damageSource, damageAmount);
    }

    @Override
    public boolean isInvulnerableTo(ServerLevel serverLevel, DamageSource damageSource) {
        return super.isInvulnerableTo(serverLevel, damageSource) || damageSource.is(DamageTypeTags.WITCH_RESISTANT_TO)
                || this.isShielded() && damageSource.is(DamageTypeTags.IS_PROJECTILE);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.INVOKER_AMBIENT_SOUND_EVENT.value();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.INVOKER_DEATH_SOUND_EVENT.value();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSoundEvents.INVOKER_HURT_SOUND_EVENT.value();
    }

    @Override
    protected SoundEvent getCastingSoundEvent() {
        return ModSoundEvents.INVOKER_COMPLETE_CAST_SOUND_EVENT.value();
    }

    @Override
    public void applyRaidBuffs(ServerLevel level, int wave, boolean unused) {
        // NO-OP
    }

    @Override
    public boolean canBeLeader() {
        return false;
    }

    class SummonVexGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {
        private static final TargetingConditions CLOSE_VEX_PREDICATE = TargetingConditions.forNonCombat()
                .range(16.0)
                .ignoreLineOfSight()
                .ignoreInvisibilityTesting();

        @Override
        public boolean canUse() {
            if (!super.canUse()) {
                return false;
            } else {
                return ((ServerLevel) Invoker.this.level()).getNearbyEntities(Surrendered.class,
                        CLOSE_VEX_PREDICATE,
                        Invoker.this,
                        Invoker.this.getBoundingBox().inflate(20.0)).size() < 3;
            }
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
            ServerLevel serverLevel = (ServerLevel) Invoker.this.level();
            for (int i = 0; i < 4; ++i) {
                BlockPos blockPos = Invoker.this.blockPosition()
                        .offset(-2 + Invoker.this.random.nextInt(5), 1, -2 + Invoker.this.random.nextInt(5));
                Surrendered surrendered = ModEntityTypes.SURRENDERED_ENTITY_TYPE.value()
                        .create(Invoker.this.level(), EntitySpawnReason.MOB_SUMMONED);
                if (surrendered != null) {
                    surrendered.snapTo(blockPos, 0.0f, 0.0f);
                    surrendered.finalizeSpawn(serverLevel,
                            Invoker.this.level().getCurrentDifficultyAt(blockPos),
                            EntitySpawnReason.MOB_SUMMONED,
                            null);
                    surrendered.setOwner(Invoker.this);
                    surrendered.setBoundOrigin(blockPos);
                    surrendered.setLimitedLife(20 * (30 + Invoker.this.random.nextInt(90)));
                    serverLevel.addFreshEntityWithPassengers(surrendered);
                }
            }
        }

        @Override
        protected SoundEvent getSpellPrepareSound() {
            return ModSoundEvents.INVOKER_SUMMON_CAST_SOUND_EVENT.value();
        }

        @Override
        protected SpellcasterIllager.IllagerSpell getSpell() {
            return SpellcasterIllager.IllagerSpell.SUMMON_VEX;
        }
    }

    class ConjureFangsGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {

        @Override
        protected int getCastingTime() {
            return 40;
        }

        @Override
        protected int getCastingInterval() {
            return 100;
        }

        @Override
        protected void performSpellCasting() {
            LivingEntity livingEntity = Invoker.this.getTarget();
            double d = Math.min(livingEntity.getY(), Invoker.this.getY());
            double e = Math.max(livingEntity.getY(), Invoker.this.getY()) + 1.0;
            float f = (float) Mth.atan2(livingEntity.getZ() - Invoker.this.getZ(),
                    livingEntity.getX() - Invoker.this.getX());

            if (Invoker.this.distanceToSqr(livingEntity) < 9.0) {
                float g;
                int i;
                for (i = 0; i < 5; ++i) {
                    g = f + (float) i * (float) Math.PI * 0.4f;
                    this.conjureFangs(Invoker.this.getX() + (double) Mth.cos(g) * 1.5,
                            Invoker.this.getZ() + (double) Mth.sin(g) * 1.5,
                            d,
                            e,
                            g,
                            0);
                }
                for (i = 0; i < 8; ++i) {
                    g = f + (float) i * (float) Math.PI * 2.0f / 8.0f + 1.2566371f;
                    this.conjureFangs(Invoker.this.getX() + (double) Mth.cos(g) * 2.5,
                            Invoker.this.getZ() + (double) Mth.sin(g) * 2.5,
                            d,
                            e,
                            g,
                            3);
                }
                for (i = 0; i < 8; ++i) {
                    g = f + (float) i * (float) Math.PI * 2.0f / 8.0f + 1.2566371f;
                    this.conjureFangs(Invoker.this.getX() + (double) Mth.cos(g) * 3.5,
                            Invoker.this.getZ() + (double) Mth.sin(g) * 2.5,
                            d,
                            e,
                            g,
                            3);
                }
            } else {
                for (int i = 0; i < 16; ++i) {
                    double h = 1.25 * (double) (i + 1);
                    this.conjureFangs(Invoker.this.getX() + (double) Mth.cos(f) * h,
                            Invoker.this.getZ() + (double) Mth.sin(f) * h,
                            d,
                            e,
                            f,
                            i);
                }
                for (int i = 0; i < 16; ++i) {
                    double h = 1.25 * (double) (i + 1);
                    this.conjureFangs(Invoker.this.getX() + (double) Mth.cos(f + 0.4f) * h,
                            Invoker.this.getZ() + (double) Mth.sin(f + 0.3f) * h,
                            d,
                            e,
                            f,
                            i);
                }
                for (int i = 0; i < 16; ++i) {
                    double h = 1.25 * (double) (i + 1);
                    this.conjureFangs(Invoker.this.getX() + (double) Mth.cos(f - 0.4f) * h,
                            Invoker.this.getZ() + (double) Mth.sin(f - 0.3f) * h,
                            d,
                            e,
                            f,
                            i);
                }
            }
        }

        private void conjureFangs(double x, double z, double maxY, double y, float yaw, int warmup) {
            ServerLevel serverLevel = getServerLevel(Invoker.this.level());
            BlockPos blockPos = BlockPos.containing(x, y, z);
            int minY = Mth.floor(maxY) - 1;

            while (blockPos.getY() >= minY) {
                BlockPos blockPosBelow = blockPos.below();
                if (serverLevel.getBlockState(blockPosBelow).isFaceSturdy(serverLevel, blockPosBelow, Direction.UP)) {
                    double verticalOffset = 0.0;
                    if (!serverLevel.isEmptyBlock(blockPos)) {
                        VoxelShape shape = serverLevel.getBlockState(blockPos).getCollisionShape(serverLevel, blockPos);
                        if (!shape.isEmpty()) {
                            verticalOffset = shape.max(Direction.Axis.Y);
                        }
                    }

                    serverLevel.addFreshEntity(new InvokerFangs(serverLevel,
                            x,
                            (double) blockPos.getY() + 0.2 + verticalOffset,
                            z,
                            yaw,
                            warmup,
                            Invoker.this));
                    break;
                }
                blockPos = blockPosBelow;
            }
        }

        @Override
        protected SoundEvent getSpellPrepareSound() {
            return ModSoundEvents.INVOKER_FANGS_CAST_SOUND_EVENT.value();
        }

        @Override
        protected SpellcasterIllager.IllagerSpell getSpell() {
            return IllagerSpell.FANGS;
        }
    }

    public class AreaDamageGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {

        @Override
        public boolean canUse() {
            if (Invoker.this.getTarget() == null) {
                return false;
            } else if (Invoker.this.areaDamageCooldown < 0) {
                Invoker.this.isAoeCasting = true;
                return true;
            } else {
                return false;
            }
        }

        private void knockBack(Entity entity) {
            double d = entity.getX() - Invoker.this.getX();
            double e = entity.getZ() - Invoker.this.getZ();
            double f = Math.max(d * d + e * e, 0.001);
            entity.push(d / f * 6, 0.65, e / f * 6);
        }

        protected void knockback(LivingEntity target) {
            this.knockBack(target);
            target.hurtMarked = true;
        }

        @Override
        public void stop() {
            Invoker.this.isAoeCasting = false;
            super.stop();
        }

        private void buff(LivingEntity entity) {
            this.knockback(entity);
            entity.hurtServer(getServerLevel(Invoker.this.level()),
                    Invoker.this.damageSources().indirectMagic(Invoker.this, Invoker.this),
                    11.0F);
            double x = entity.getX();
            double y = entity.getY() + 2.0;
            double z = entity.getZ();
            ((ServerLevel) Invoker.this.level()).sendParticles(ParticleTypes.SMOKE,
                    x,
                    y,
                    z,
                    10,
                    0.2D,
                    0.2D,
                    0.2D,
                    0.015D);
        }

        @Override
        protected void performSpellCasting() {
            Invoker.this.areaDamageCooldown = 300;
            Invoker.this.level()
                    .getEntitiesOfClass(LivingEntity.class,
                            Invoker.this.getBoundingBox().inflate(6),
                            entity -> !(entity instanceof AbstractIllager) && !(entity instanceof Surrendered)
                                    && !(entity instanceof Ravager) && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(
                                    entity))
                    .forEach(this::buff);
            Invoker.this.isAoeCasting = false;
            double posx = Invoker.this.getX();
            double posy = Invoker.this.getY();
            double posz = Invoker.this.getZ();
            ((ServerLevel) Invoker.this.level()).sendParticles(ParticleTypes.LARGE_SMOKE,
                    posx,
                    posy + 1,
                    posz,
                    350,
                    1.0D,
                    0.8D,
                    1.0D,
                    0.3D);
        }

        @Override
        protected int getCastWarmupTime() {
            return 50;
        }

        @Override
        protected int getCastingTime() {
            return 50;
        }

        @Override
        protected int getCastingInterval() {
            return 400;
        }

        @Override
        protected SoundEvent getSpellPrepareSound() {
            return ModSoundEvents.INVOKER_BIG_CAST_SOUND_EVENT.value();
        }

        @Override
        protected SpellcasterIllager.IllagerSpell getSpell() {
            return IllagerSpell.BLINDNESS;
        }
    }

    public class CastTeleportGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {

        @Override
        public boolean canUse() {
            if (Invoker.this.getTarget() == null) {
                return false;
            } else if (Invoker.this.isCastingSpell()) {
                return false;
            } else {
                return Invoker.this.teleportCooldown < 0 && !(this.getTargets().isEmpty());
            }
        }

        private List<LivingEntity> getTargets() {
            return Invoker.this.level()
                    .getEntitiesOfClass(LivingEntity.class,
                            Invoker.this.getBoundingBox().inflate(6),
                            entity -> ((entity instanceof Player && !((Player) entity).getAbilities().instabuild))
                                    || (entity instanceof IronGolem));
        }

        @Override
        public boolean canContinueToUse() {
            return !this.getTargets().isEmpty();
        }

        @Override
        public void stop() {
            super.stop();
        }

        @Override
        public void start() {
            super.start();
            Invoker.this.teleportCooldown = 180;
        }

        @Override
        protected void performSpellCasting() {
            double x = Invoker.this.getX();
            double y = Invoker.this.getY() + 1.0;
            double z = Invoker.this.getZ();
            if (Invoker.this.level() instanceof ServerLevel) {
                ((ServerLevel) Invoker.this.level()).sendParticles(ParticleTypes.SMOKE,
                        x,
                        y,
                        z,
                        30,
                        0.3D,
                        0.5D,
                        0.3D,
                        0.015D);
            }
            TeleportUtil.tryRandomTeleport(Invoker.this);
        }

        @Override
        protected int getCastWarmupTime() {
            return 30;
        }

        @Override
        protected int getCastingTime() {
            return 30;
        }

        @Override
        protected int getCastingInterval() {
            return 400;
        }

        @Override
        protected SoundEvent getSpellPrepareSound() {
            return ModSoundEvents.INVOKER_TELEPORT_CAST_SOUND_EVENT.value();
        }

        @Override
        protected SpellcasterIllager.IllagerSpell getSpell() {
            return IllagerSpell.BLINDNESS;
        }
    }

    public class ConjureAoeFangsGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {

        @Override
        public boolean canUse() {
            if (Invoker.this.getTarget() == null) {
                return false;
            } else if (this.getTargets().isEmpty()) {
                return false;
            } else if (Invoker.this.isCastingSpell()) {
                return false;
            } else {
                return Invoker.this.fangaoecooldown < 0;
            }
        }

        private List<LivingEntity> getTargets() {
            return Invoker.this.level()
                    .getEntitiesOfClass(LivingEntity.class,
                            Invoker.this.getBoundingBox().inflate(18),
                            entity -> !(entity instanceof Monster));
        }

        private void conjureFangs(double x, double z, double maxY, double y, float yaw, int warmup) {
            ServerLevel serverLevel = getServerLevel(Invoker.this.level());
            BlockPos currentPos = BlockPos.containing(x, y, z);
            int minY = Mth.floor(maxY) - 1;

            while (currentPos.getY() >= minY) {
                BlockPos belowPos = currentPos.below();
                if (!serverLevel.getBlockState(belowPos).isFaceSturdy(serverLevel, belowPos, Direction.UP)) {
                    currentPos = belowPos;
                } else {
                    double verticalOffset = 0.0;
                    if (!serverLevel.isEmptyBlock(currentPos)) {
                        VoxelShape shape = serverLevel.getBlockState(currentPos)
                                .getCollisionShape(serverLevel, currentPos);
                        if (!shape.isEmpty()) {
                            verticalOffset = shape.max(Direction.Axis.Y);
                        }
                    }

                    serverLevel.addFreshEntity(new InvokerFangs(serverLevel,
                            x,
                            (double) currentPos.getY() + 0.2 + verticalOffset,
                            z,
                            yaw,
                            warmup,
                            Invoker.this));
                    break;
                }
            }
        }

        @Override
        protected void performSpellCasting() {
            for (LivingEntity livingEntity : this.getTargets()) {
                double d = Math.min(livingEntity.getY(), Invoker.this.getY());
                double e = Math.max(livingEntity.getY(), Invoker.this.getY()) + 1.0;
                float f = (float) Mth.atan2(livingEntity.getZ() - Invoker.this.getZ(),
                        livingEntity.getX() - Invoker.this.getX());
                for (int i = 0; i < 5; ++i) {
                    float g = f + (float) i * (float) Math.PI * 0.4f;
                    this.conjureFangs(livingEntity.getX() + (double) Mth.cos(g) * 1.5,
                            livingEntity.getZ() + (double) Mth.sin(g) * 1.5,
                            d,
                            e,
                            g,
                            4);
                }
            }
            Invoker.this.fangaoecooldown = 100;
        }

        @Override
        protected int getCastingTime() {
            return 40;
        }

        @Override
        protected int getCastingInterval() {
            return 100;
        }

        @Override
        protected SoundEvent getSpellPrepareSound() {
            return ModSoundEvents.INVOKER_FANGS_CAST_SOUND_EVENT.value();
        }

        @Override
        protected SpellcasterIllager.IllagerSpell getSpell() {
            return IllagerSpell.FANGS;
        }
    }
}

