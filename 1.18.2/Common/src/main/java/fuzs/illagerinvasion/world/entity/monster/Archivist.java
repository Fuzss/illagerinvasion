package fuzs.illagerinvasion.world.entity.monster;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.illagerinvasion.util.EnchantToolUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Archivist extends SpellcasterIllager {
    private AbstractIllager enchantTarget;
    private int levitateTargetsCooldown;
    private int enchantAlliesCooldown;

    public Archivist(EntityType<? extends Archivist> entityType, Level world) {
        super(entityType, world);
        this.xpReward = 10;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new Archivist.LookAtTargetOrWololoTarget());
        this.goalSelector.addGoal(4, new LevitateTargetsGoal());
        this.goalSelector.addGoal(3, new EnchantAllyGoal());
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
        return ModRegistry.ARCHIVIST_AMBIENT_SOUND_EVENT.get();
    }


    @Override
    protected void customServerAiStep() {
        --this.levitateTargetsCooldown;
        --this.enchantAlliesCooldown;
        super.customServerAiStep();
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
        return ModRegistry.ARCHIVIST_AMBIENT_SOUND_EVENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModRegistry.ARCHIVIST_DEATH_SOUND_EVENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModRegistry.ARCHIVIST_HURT_SOUND_EVENT.get();
    }

    @Nullable AbstractIllager getEnchantTarget() {
        return this.enchantTarget;
    }

    void setEnchantTarget(@Nullable AbstractIllager entity) {
        this.enchantTarget = entity;
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
            if (Archivist.this.getTarget() != null) {
                Archivist.this.getLookControl().setLookAt(Archivist.this.getTarget(), Archivist.this.getMaxHeadYRot(), Archivist.this.getMaxHeadXRot());
            }
        }
    }

    public class LevitateTargetsGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {

        @Override
        public boolean canUse() {
            if (Archivist.this.getTarget() == null) {
                return false;
            }
            return Archivist.this.levitateTargetsCooldown < 0 && !this.getTargets().isEmpty();
        }

        private void knockBack(Entity entity) {
            double d = entity.getX() - Archivist.this.getX();
            double e = entity.getZ() - Archivist.this.getZ();
            double f = Math.max(d * d + e * e, 0.001);
            entity.push(d / f * 5.0, 0.6, e / f * 5.0);
        }

        protected void knockback(LivingEntity target) {
            this.knockBack(target);
            target.hurtMarked = true;
        }

        private List<LivingEntity> getTargets() {
            return Archivist.this.level.getEntitiesOfClass(LivingEntity.class, Archivist.this.getBoundingBox().inflate(6), entity -> {
                return !(entity instanceof Monster) && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity);
            });
        }

        private void buff(LivingEntity entity) {
            this.knockback(entity);
            entity.hurt(DamageSource.MAGIC, 4.0f);
        }

        @Override
        protected void performSpellCasting() {
            Archivist.this.levitateTargetsCooldown = 220;
            this.getTargets().forEach(this::buff);
            double x = Archivist.this.getX();
            double y = Archivist.this.getY() + 1;
            double z = Archivist.this.getZ();
            ((ServerLevel) Archivist.this.level).sendParticles(ParticleTypes.ENCHANT, x, y, z, 150, 3.0D, 3.0D, 3.0D, 0.1D);
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
            return SoundEvents.ILLUSIONER_PREPARE_BLINDNESS;
        }

        @Override
        protected SpellcasterIllager.IllagerSpell getSpell() {
            return IllagerSpell.FANGS;
        }
    }

    public class EnchantAllyGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {
        private final TargetingConditions closeEnchantableMobPredicate = TargetingConditions.forNonCombat().range(16.0).selector(livingEntity -> !(livingEntity instanceof Archivist));
        private int targetId;

        public boolean canEnchant() {
            AbstractIllager hostileEntity = Archivist.this.getEnchantTarget();
            return hostileEntity != null && EnchantToolUtil.eligibleForEnchant(hostileEntity);
        }

        @Override
        public boolean canUse() {
            if (Archivist.this.getTarget() == null) {
                return false;
            }
            if (Archivist.this.enchantAlliesCooldown >= 0) {
                return false;
            }
            if (Archivist.this.isCastingSpell()) {
                return false;
            }
            List<AbstractIllager> list = Archivist.this.level.getNearbyEntities(AbstractIllager.class, this.closeEnchantableMobPredicate, Archivist.this, Archivist.this.getBoundingBox().inflate(16.0, 4.0, 16.0));
            if (list.isEmpty()) {
                return false;
            }
            Archivist.this.setEnchantTarget(list.get(Archivist.this.random.nextInt(list.size())));
            AbstractIllager hostileEntity = Archivist.this.getEnchantTarget();
            if (hostileEntity.getId() == this.targetId) {
                return false;
            }
            return this.canEnchant();
        }

        @Override
        public void stop() {
            super.stop();
            Archivist.this.setEnchantTarget(null);
        }

        @Override
        protected void performSpellCasting() {
            Monster hostileEntity = Archivist.this.getEnchantTarget();
            if (hostileEntity != null) {
                this.targetId = hostileEntity.getId();
            }
            EnchantToolUtil.enchantHeldGear(hostileEntity);
            double x = hostileEntity.getX();
            double y = hostileEntity.getY() + 1.5;
            double z = hostileEntity.getZ();
            if (Archivist.this.level instanceof ServerLevel) {
                ((ServerLevel) Archivist.this.level).sendParticles(ParticleTypes.ENCHANT, x, y, z, 50, 1.0D, 2.0D, 1.0D, 0.1D);
            }
            Archivist.this.enchantAlliesCooldown = 300;
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
            return ModRegistry.SORCERER_CAST_SOUND_EVENT.get();
        }

        @Override
        protected SpellcasterIllager.IllagerSpell getSpell() {
            return (IllagerSpell) ModRegistry.ENCHANT_ILLAGER_SPELL;
        }
    }
}

