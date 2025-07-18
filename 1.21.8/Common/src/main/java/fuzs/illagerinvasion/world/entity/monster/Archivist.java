package fuzs.illagerinvasion.world.entity.monster;

import fuzs.illagerinvasion.init.ModSoundEvents;
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
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.monster.creaking.Creaking;
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
        this.xpReward = Enemy.XP_REWARD_LARGE;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Creaking.class, 8.0F, 1.0, 1.2));
        this.goalSelector.addGoal(2, new SpellcasterIllager.SpellcasterCastingSpellGoal());
        this.goalSelector.addGoal(3, new EnchantAllyGoal());
        this.goalSelector.addGoal(4, new LevitateTargetsGoal());
        this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Player.class, 8.0F, 0.6, 1.0));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false));
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return ModSoundEvents.ARCHIVIST_AMBIENT_SOUND_EVENT.value();
    }


    @Override
    protected void customServerAiStep(ServerLevel serverLevel) {
        --this.levitateTargetsCooldown;
        --this.enchantAlliesCooldown;
        super.customServerAiStep(serverLevel);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.ARCHIVIST_AMBIENT_SOUND_EVENT.value();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.ARCHIVIST_DEATH_SOUND_EVENT.value();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSoundEvents.ARCHIVIST_HURT_SOUND_EVENT.value();
    }

    @Nullable AbstractIllager getEnchantTarget() {
        return this.enchantTarget;
    }

    void setEnchantTarget(@Nullable AbstractIllager entity) {
        this.enchantTarget = entity;
    }

    @Override
    protected SoundEvent getCastingSoundEvent() {
        return ModSoundEvents.SORCERER_COMPLETE_CAST_SOUND_EVENT.value();
    }

    @Override
    public void applyRaidBuffs(ServerLevel level, int wave, boolean unused) {
        // NO-OP
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
            return Archivist.this.level().getEntitiesOfClass(LivingEntity.class, Archivist.this.getBoundingBox().inflate(6), entity -> {
                return !(entity instanceof Monster) && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity);
            });
        }

        @Override
        protected void performSpellCasting() {
            Archivist.this.levitateTargetsCooldown = 220;
            this.getTargets().forEach(this::buff);
            double x = Archivist.this.getX();
            double y = Archivist.this.getY() + 1;
            double z = Archivist.this.getZ();
            ((ServerLevel) Archivist.this.level()).sendParticles(ParticleTypes.ENCHANT, x, y, z, 150, 3.0D, 3.0D, 3.0D, 0.1D);
        }

        private void buff(LivingEntity entity) {
            this.knockback(entity);
            entity.hurtServer((ServerLevel) Archivist.this.level(), Archivist.this.damageSources().indirectMagic(Archivist.this, Archivist.this), 4.0f);
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
        private final TargetingConditions closeEnchantableMobPredicate = TargetingConditions.forNonCombat().range(16.0).selector((LivingEntity livingEntity, ServerLevel serverLevel) -> !(livingEntity instanceof Archivist));
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
            List<AbstractIllager> list = ((ServerLevel) Archivist.this.level()).getNearbyEntities(AbstractIllager.class, this.closeEnchantableMobPredicate, Archivist.this, Archivist.this.getBoundingBox().inflate(16.0, 4.0, 16.0));
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
            if (Archivist.this.level() instanceof ServerLevel) {
                ((ServerLevel) Archivist.this.level()).sendParticles(ParticleTypes.ENCHANT, x, y, z, 50, 1.0D, 2.0D, 1.0D, 0.1D);
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
            return ModSoundEvents.SORCERER_CAST_SOUND_EVENT.value();
        }

        @Override
        protected SpellcasterIllager.IllagerSpell getSpell() {
            return IllagerSpell.SUMMON_VEX;
        }
    }
}

