package fuzs.illagerinvasion.world.entity.monster;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.illagerinvasion.util.SetMagicFireUtil;
import fuzs.illagerinvasion.util.TeleportUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;

import java.util.List;

public class Sorcerer extends SpellcasterIllager {
    private int castTeleportCooldown;
    private int conjureFlamesCooldown;

    public Sorcerer(EntityType<? extends Sorcerer> entityType, Level world) {
        super(entityType, world);
        this.xpReward = 10;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new Sorcerer.LookAtTargetOrWololoTarget());
        this.goalSelector.addGoal(4, new CastTeleportGoal());
        this.goalSelector.addGoal(3, new ConjureFlamesGoal());
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Player.class, 8.0f, 0.6, 1.0));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false));
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return ModRegistry.SORCERER_CELEBRATE_SOUND_EVENT.get();
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        --this.castTeleportCooldown;
        --this.conjureFlamesCooldown;
    }

    @Override
    public boolean isAlliedTo(Entity other) {
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
        if (other instanceof LivingEntity && ((LivingEntity) other).getMobType() == MobType.ILLAGER) {
            return this.getTeam() == null && other.getTeam() == null;
        }
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModRegistry.SORCERER_AMBIENT_SOUND_EVENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModRegistry.SORCERER_DEATH_SOUND_EVENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModRegistry.SORCERER_HURT_SOUND_EVENT.get();
    }

    @Override
    protected SoundEvent getCastingSoundEvent() {
        return ModRegistry.SORCERER_COMPLETE_CAST_SOUND_EVENT.get();
    }

    @Override
    public void applyRaidBuffs(int wave, boolean unused) {

    }

    @Override
    public AbstractIllager.IllagerArmPose getArmPose() {
        if (this.isCastingSpell()) {
            return AbstractIllager.IllagerArmPose.SPELLCASTING;
        }
        return AbstractIllager.IllagerArmPose.CROSSED;
    }

    class LookAtTargetOrWololoTarget extends SpellcasterIllager.SpellcasterCastingSpellGoal {

        @Override
        public void tick() {
            if (Sorcerer.this.getTarget() != null) {
                Sorcerer.this.getLookControl().setLookAt(Sorcerer.this.getTarget(), Sorcerer.this.getMaxHeadYRot(), Sorcerer.this.getMaxHeadXRot());
            }
        }
    }

    public class CastTeleportGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {

        @Override
        public boolean canUse() {
            if (Sorcerer.this.getTarget() == null) {
                return false;
            }
            if (Sorcerer.this.isCastingSpell()) {
                return false;
            }
            return Sorcerer.this.castTeleportCooldown < 0 && !(this.getTargets().isEmpty());
        }

        private List<LivingEntity> getTargets() {
            return Sorcerer.this.level().getEntitiesOfClass(LivingEntity.class, Sorcerer.this.getBoundingBox().inflate(8), entity -> (entity instanceof Player) || (entity instanceof IronGolem));
        }

        @Override
        public boolean canContinueToUse() {
            return !this.getTargets().isEmpty();
        }


        @Override
        protected void performSpellCasting() {
            Sorcerer.this.castTeleportCooldown = 220;
            double x = Sorcerer.this.getX();
            double y = Sorcerer.this.getY() + 1;
            double z = Sorcerer.this.getZ();
            if (Sorcerer.this.level() instanceof ServerLevel) {
                ((ServerLevel) Sorcerer.this.level()).sendParticles(ParticleTypes.WITCH, x, y, z, 30, 0.3D, 0.5D, 0.3D, 0.015D);
            }
            TeleportUtil.tryRandomTeleport(Sorcerer.this);
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
            return ModRegistry.SORCERER_CAST_SOUND_EVENT.get();
        }

        @Override
        protected SpellcasterIllager.IllagerSpell getSpell() {
            return (IllagerSpell) ModRegistry.CONJURE_TELEPORT_ILLAGER_SPELL;
        }
    }

    public class ConjureFlamesGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {

        @Override
        public boolean canUse() {
            if (Sorcerer.this.getTarget() == null) {
                return false;
            }
            if (Sorcerer.this.isCastingSpell()) {
                return false;
            }
            if (Sorcerer.this.conjureFlamesCooldown < 0) {
                return true;
            }
            return false;
        }

        @Override
        protected void performSpellCasting() {
            LivingEntity target = Sorcerer.this.getTarget();
            SetMagicFireUtil.trySetFire(target, Sorcerer.this.level());
            Sorcerer.this.conjureFlamesCooldown = 100;
            target.hurt(Sorcerer.this.damageSources().magic(), 3.0f);
            if (Sorcerer.this.level() instanceof ServerLevel) {
                ((ServerLevel) Sorcerer.this.level()).sendParticles(ModRegistry.MAGIC_FLAME_PARTICLE_TYPE.get(), target.getX(), target.getY() + 1, target.getZ(), 30, 0.3D, 0.5D, 0.3D, 0.08D);
            }
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
            return 140;
        }

        @Override
        protected SoundEvent getSpellPrepareSound() {
            return ModRegistry.SORCERER_CAST_SOUND_EVENT.get();
        }

        @Override
        protected SpellcasterIllager.IllagerSpell getSpell() {
            return (IllagerSpell) ModRegistry.CONJURE_FLAMES_ILLAGER_SPELL;
        }
    }
}
