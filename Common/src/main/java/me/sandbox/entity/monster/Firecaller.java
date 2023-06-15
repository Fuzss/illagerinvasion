package me.sandbox.entity.monster;


import fuzs.illagerinvasion.init.ModRegistry;
import me.sandbox.entity.projectile.FlyingMagma;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Firecaller extends SpellcasterIllager {
    @Nullable
    private Sheep wololoTarget;
    private int cooldown = 160;
    private int aoecooldown = 300;
    private AttributeMap attributeContainer;

    public Firecaller(final EntityType<? extends Firecaller> entityType, final Level world) {
        super(entityType, world);
        this.xpReward = 15;
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LookAtTargetOrWololoTarget());
        this.goalSelector.addGoal(4, new ConjureSkullGoal());
        this.goalSelector.addGoal(3, new AreaDamageGoal());
        this.goalSelector.addGoal(5, new AvoidEntityGoal<Player>(this, Player.class, 8.0f, 0.6, 1.0));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, false));

    }

    public AttributeMap getAttributes() {
        if (this.attributeContainer == null) {
            this.attributeContainer = new AttributeMap(Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 32.0).add(Attributes.MOVEMENT_SPEED, 0.38).build());
        }
        return this.attributeContainer;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
    }


    public void readAdditionalSaveData(final CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
    }

    public SoundEvent getCelebrateSound() {
        return SoundEvents.ILLUSIONER_AMBIENT;
    }

    public void addAdditionalSaveData(final CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
    }

    protected void customServerAiStep() {
        super.customServerAiStep();
        --this.cooldown;
        --this.aoecooldown;
    }

    public boolean hurt(final DamageSource source, final float amount) {
        final boolean bl2 = super.hurt(source, amount);
        return bl2;
    }

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

    protected SoundEvent getAmbientSound() {
        return ModRegistry.FIRECALLER_AMBIENT_SOUND_EVENT;
    }

    protected SoundEvent getDeathSound() {
        return ModRegistry.FIRECALLER_DEATH_SOUND_EVENT;
    }

    protected SoundEvent getHurtSound(final DamageSource source) {
        return ModRegistry.FIRECALLER_HURT_SOUND_EVENT;
    }

    @Nullable Sheep getWololoTarget() {
        return this.wololoTarget;
    }

    protected SoundEvent getCastingSoundEvent() {
        return SoundEvents.FIRECHARGE_USE;
    }

    public void applyRaidBuffs(final int wave, final boolean unused) {
    }

    public AbstractIllager.IllagerArmPose getArmPose() {
        if (this.isCastingSpell()) {
            return AbstractIllager.IllagerArmPose.SPELLCASTING;
        }
        return AbstractIllager.IllagerArmPose.CROSSED;
    }

    class LookAtTargetOrWololoTarget extends SpellcasterIllager.SpellcasterCastingSpellGoal {

        @Override
        public void tick() {
            if (Firecaller.this.getTarget() != null) {
                Firecaller.this.getLookControl().setLookAt(Firecaller.this.getTarget(), Firecaller.this.getMaxHeadYRot(), Firecaller.this.getMaxHeadXRot());
            } else if (Firecaller.this.getWololoTarget() != null) {
                Firecaller.this.getLookControl().setLookAt(Firecaller.this.getWololoTarget(), Firecaller.this.getMaxHeadYRot(), Firecaller.this.getMaxHeadXRot());
            }
        }
    }

    public class ConjureSkullGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {
        private List<LivingEntity> getTargets() {
            return Firecaller.this.level().getEntitiesOfClass(LivingEntity.class, Firecaller.this.getBoundingBox().inflate(5), entity -> (entity instanceof Player) || (entity instanceof IronGolem));
        }

        @Override
        public boolean canUse() {
            if (Firecaller.this.getTarget() == null) {
                return false;
            }
            if (Firecaller.this.cooldown > 0) {
                return false;
            }
            return Firecaller.this.cooldown < 0 && !Firecaller.this.isCastingSpell() && this.getTargets().isEmpty();
        }

        @Override
        public void stop() {
            super.stop();
        }

        @Override
        public void tick() {
            if (Firecaller.this.level() instanceof ServerLevel) {
                ((ServerLevel) Firecaller.this.level()).sendParticles(ParticleTypes.FLAME, Firecaller.this.getX(), Firecaller.this.getY() + 2.5, Firecaller.this.getZ(), 2, 0.2D, 0.2D, 0.2D, 0.05D);
                ((ServerLevel) Firecaller.this.level()).sendParticles(ParticleTypes.LARGE_SMOKE, Firecaller.this.getX(), Firecaller.this.getY() + 2.5, Firecaller.this.getZ(), 2, 0.2D, 0.2D, 0.2D, 0.05D);
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
                ((ServerLevel) Firecaller.this.level()).sendParticles(ParticleTypes.SMOKE, x, y, z, 40, 0.4D, 0.4D, 0.4D, 0.15D);
            }
            Firecaller.this.cooldown = 160;
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
            return ModRegistry.FIRECALLER_CAST_SOUND_EVENT;
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
            }
            if (Firecaller.this.isCastingSpell()) {
                return false;
            }
            return Firecaller.this.aoecooldown <= 0;
        }

        private List<LivingEntity> getTargets() {
            return Firecaller.this.level().getEntitiesOfClass(LivingEntity.class, Firecaller.this.getBoundingBox().inflate(6), entity -> !(entity instanceof AbstractIllager) && !(entity instanceof Surrendered) && !(entity instanceof Ravager));
        }


        @Override
        public void stop() {
            super.stop();
        }

        private void buff(LivingEntity entity) {
            entity.push(0.0f, 1.2f, 0.0f);
            entity.hurt(Firecaller.this.damageSources().magic(), 6.0f);
            entity.setRemainingFireTicks(120);
            double x = entity.getX();
            double y = entity.getY() + 1;
            double z = entity.getZ();
            ((ServerLevel) Firecaller.this.level()).sendParticles(ParticleTypes.SMOKE, x, y + 1, z, 10, 0.2D, 0.2D, 0.2D, 0.015D);
            BlockPos blockPos = entity.blockPosition();
            Firecaller.this.level().setBlockAndUpdate(blockPos, Blocks.FIRE.defaultBlockState());
        }

        @Override
        protected void performSpellCasting() {
            this.getTargets().forEach(this::buff);
            Firecaller.this.aoecooldown = 300;
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
            return ModRegistry.FIRECALLER_CAST_SOUND_EVENT;
        }

        @Override
        protected SpellcasterIllager.IllagerSpell getSpell() {
            return (IllagerSpell) ModRegistry.PROVOKE_ILLAGER_SPELL;
        }
    }
}

