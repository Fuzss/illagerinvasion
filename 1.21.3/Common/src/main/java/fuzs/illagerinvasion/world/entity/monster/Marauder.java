package fuzs.illagerinvasion.world.entity.monster;

import fuzs.illagerinvasion.init.ModItems;
import fuzs.illagerinvasion.world.entity.ai.goal.HatchetAttackGoal;
import fuzs.illagerinvasion.world.entity.projectile.Hatchet;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class Marauder extends AbstractIllager implements RangedAttackMob {
    private static final EntityDataAccessor<Boolean> DATA_CHARGING = SynchedEntityData.defineId(Marauder.class, EntityDataSerializers.BOOLEAN);

    public Marauder(EntityType<? extends Marauder> entityType, Level world) {
        super(entityType, world);
        this.xpReward = 5;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(2, new HatchetAttackGoal(this, 1.0, 100, 8.0f));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false));
    }

    public boolean isCharging() {
        return this.entityData.get(DATA_CHARGING);
    }

    public void setCharging(boolean charging) {
        this.entityData.set(DATA_CHARGING, charging);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.PLATINUM_INFUSED_HATCHET_ITEM.value()));
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_CHARGING, false);
    }


    @Override
    public void performRangedAttack(LivingEntity target, float pullProgress) {
        Hatchet hatchet = new Hatchet(this.level(), this, new ItemStack(ModItems.PLATINUM_INFUSED_HATCHET_ITEM.value()));
        double d = target.getX() - this.getX();
        double e = target.getY(0.3333333333333333) - hatchet.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f);
        hatchet.shoot(d, e + g * (double) 0.2f, f, 1.2f, 14 - this.level().getDifficulty().getId() * 4);
        this.playSound(SoundEvents.TRIDENT_THROW.value(), 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
        this.level().addFreshEntity(hatchet);
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.PILLAGER_CELEBRATE;
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return 0.0F;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.isCharging() ? 0.24 : 0.3);
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
        if (other instanceof LivingEntity livingEntity && livingEntity.getType().is(EntityTypeTags.ILLAGER_FRIENDS)) {
            return this.getTeam() == null && other.getTeam() == null;
        }
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PILLAGER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.PILLAGER_HURT;
    }

    @Override
    public void applyRaidBuffs(ServerLevel level, int wave, boolean unused) {
        // NO-OP
    }

    @Override
    public AbstractIllager.IllagerArmPose getArmPose() {
        if (this.isAggressive()) {
            return IllagerArmPose.ATTACKING;
        } else {
            return IllagerArmPose.NEUTRAL;
        }
    }
}
