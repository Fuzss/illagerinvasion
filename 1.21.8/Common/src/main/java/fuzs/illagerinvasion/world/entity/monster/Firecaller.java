package fuzs.illagerinvasion.world.entity.monster;

import fuzs.illagerinvasion.init.ModSoundEvents;
import fuzs.illagerinvasion.world.entity.projectile.FlyingMagma;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.monster.creaking.Creaking;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class Firecaller extends SpellcasterIllager {
    private int conjureSkullCooldown = 160;
    private int areaDamageCooldown = 300;

    public Firecaller(EntityType<? extends Firecaller> entityType, Level level) {
        super(entityType, level);
        this.xpReward = Enemy.XP_REWARD_LARGE;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Creaking.class, 8.0F, 1.0, 1.2));
        this.goalSelector.addGoal(2, new SpellcasterIllager.SpellcasterCastingSpellGoal());
        this.goalSelector.addGoal(4, new ConjureSkullGoal());
        this.goalSelector.addGoal(3, new AreaDamageGoal());
        this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Player.class, 8.0f, 0.6, 1.0));
        this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, IronGolem.class, 12.0f, 0.6, 1.0));
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
    public boolean canBeLeader() {
        return false;
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.ILLUSIONER_AMBIENT;
    }

    @Override
    protected void customServerAiStep(ServerLevel serverLevel) {
        super.customServerAiStep(serverLevel);
        --this.conjureSkullCooldown;
        --this.areaDamageCooldown;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.FIRECALLER_AMBIENT_SOUND_EVENT.value();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.FIRECALLER_DEATH_SOUND_EVENT.value();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSoundEvents.FIRECALLER_HURT_SOUND_EVENT.value();
    }

    @Override
    protected SoundEvent getCastingSoundEvent() {
        return SoundEvents.FIRECHARGE_USE;
    }

    @Override
    public void applyRaidBuffs(ServerLevel level, int wave, boolean unused) {
        // NO-OP
    }

    @Override
    public AbstractIllager.IllagerArmPose getArmPose() {
        return this.isCastingSpell() ? IllagerArmPose.SPELLCASTING : IllagerArmPose.NEUTRAL;
    }

    public class ConjureSkullGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {

        @Override
        public boolean canUse() {
            if (Firecaller.this.getTarget() == null) {
                return false;
            } else if (Firecaller.this.conjureSkullCooldown > 0) {
                return false;
            } else {
                return Firecaller.this.conjureSkullCooldown < 0 && !Firecaller.this.isCastingSpell() &&
                        this.getTargets().isEmpty();
            }
        }

        private List<LivingEntity> getTargets() {
            return Firecaller.this.level()
                    .getEntitiesOfClass(LivingEntity.class,
                            Firecaller.this.getBoundingBox().inflate(5),
                            entity -> (entity instanceof Player) || (entity instanceof IronGolem));
        }

        @Override
        public void tick() {
            if (Firecaller.this.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.FLAME,
                        Firecaller.this.getX(),
                        Firecaller.this.getY() + 2.5,
                        Firecaller.this.getZ(),
                        2,
                        0.2D,
                        0.2D,
                        0.2D,
                        0.05D);
                serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE,
                        Firecaller.this.getX(),
                        Firecaller.this.getY() + 2.5,
                        Firecaller.this.getZ(),
                        2,
                        0.2D,
                        0.2D,
                        0.2D,
                        0.05D);
            }
            super.tick();
        }

        private void shootSkullAt(LivingEntity target) {
            this.shootSkullAt(target.getX(), target.getY() + (double) target.getEyeHeight() * 0.5, target.getZ());
        }

        private void shootSkullAt(double targetX, double targetY, double targetZ) {
            double d = Firecaller.this.getX();
            double e = Firecaller.this.getY() + 2.5;
            double f = Firecaller.this.getZ();
            double g = targetX - d;
            double h = targetY - e;
            double i = targetZ - f;
            FlyingMagma Magma = new FlyingMagma(Firecaller.this.level(), Firecaller.this, g, h, i);
            Magma.setOwner(Firecaller.this);
            Magma.setPosRaw(d, e, f);
            Firecaller.this.level().addFreshEntity(Magma);
        }

        @Override
        protected void performSpellCasting() {
            this.shootSkullAt(Firecaller.this.getTarget());
            if (Firecaller.this.level() instanceof ServerLevel) {
                double x = Firecaller.this.getX();
                double y = Firecaller.this.getY() + 2.5;
                double z = Firecaller.this.getZ();
                ((ServerLevel) Firecaller.this.level()).sendParticles(ParticleTypes.SMOKE,
                        x,
                        y,
                        z,
                        40,
                        0.4D,
                        0.4D,
                        0.4D,
                        0.15D);
            }
            Firecaller.this.conjureSkullCooldown = 160;
        }

        @Override
        protected int getCastWarmupTime() {
            return 60;
        }

        @Override
        protected int getCastingTime() {
            return 60;
        }

        @Override
        protected int getCastingInterval() {
            return 400;
        }

        @Override
        protected SoundEvent getSpellPrepareSound() {
            return ModSoundEvents.FIRECALLER_CAST_SOUND_EVENT.value();
        }

        @Override
        protected SpellcasterIllager.IllagerSpell getSpell() {
            return IllagerSpell.WOLOLO;
        }
    }

    public class AreaDamageGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {

        @Override
        public boolean canUse() {
            if (Firecaller.this.getTarget() == null) {
                return false;
            } else if (Firecaller.this.isCastingSpell()) {
                return false;
            } else {
                return Firecaller.this.areaDamageCooldown <= 0;
            }
        }

        private List<LivingEntity> getTargets() {
            return Firecaller.this.level()
                    .getEntitiesOfClass(LivingEntity.class,
                            Firecaller.this.getBoundingBox().inflate(6),
                            entity -> !(entity instanceof AbstractIllager) && !(entity instanceof Surrendered) &&
                                    !(entity instanceof Ravager));
        }

        @Override
        protected void performSpellCasting() {
            this.getTargets().forEach(this::buff);
            Firecaller.this.areaDamageCooldown = 300;
        }

        private void buff(LivingEntity livingEntity) {
            livingEntity.push(0.0F, 1.2F, 0.0F);
            livingEntity.hurtServer((ServerLevel) Firecaller.this.level(),
                    Firecaller.this.damageSources().indirectMagic(Firecaller.this, Firecaller.this),
                    6.0F);
            livingEntity.setRemainingFireTicks(120);
            double x = livingEntity.getX();
            double y = livingEntity.getY() + 1;
            double z = livingEntity.getZ();
            ((ServerLevel) Firecaller.this.level()).sendParticles(ParticleTypes.SMOKE,
                    x,
                    y + 1,
                    z,
                    10,
                    0.2D,
                    0.2D,
                    0.2D,
                    0.015D);
            BlockPos blockPos = livingEntity.blockPosition();
            Firecaller.this.level().setBlockAndUpdate(blockPos, Blocks.FIRE.defaultBlockState());
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
            return ModSoundEvents.FIRECALLER_CAST_SOUND_EVENT.value();
        }

        @Override
        protected SpellcasterIllager.IllagerSpell getSpell() {
            return IllagerSpell.WOLOLO;
        }
    }
}

