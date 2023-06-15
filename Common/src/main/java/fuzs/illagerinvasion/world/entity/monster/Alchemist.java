package fuzs.illagerinvasion.world.entity.monster;

import fuzs.illagerinvasion.world.entity.ai.goal.PotionBowAttackGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Alchemist extends AbstractIllager implements RangedAttackMob {
    private static final EntityDataAccessor<Boolean> DATA_HOLDING_POTION = SynchedEntityData.defineId(Alchemist.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_HOLDING_BOW = SynchedEntityData.defineId(Alchemist.class, EntityDataSerializers.BOOLEAN);

    public int potionCooldown;
    private AttributeMap attributeContainer;

    public Alchemist(final EntityType<? extends Alchemist> entityType, final Level world) {
        super(entityType, world);
        this.potionCooldown = 160;
        this.xpReward = 10;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(4, new PotionBowAttackGoal<Alchemist>(this, 0.5, 20, 15.0f));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false));
    }

    @Override
    public AttributeMap getAttributes() {
        if (this.attributeContainer == null) {
            this.attributeContainer = new AttributeMap(Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 23.0).add(Attributes.MOVEMENT_SPEED, 0.38).build());
        }
        return this.attributeContainer;
    }

    @Override
    public SpawnGroupData finalizeSpawn(final ServerLevelAccessor world, final DifficultyInstance difficulty, final MobSpawnType spawnReason, @Nullable final SpawnGroupData entityData, @Nullable final CompoundTag entityNbt) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void performRangedAttack(final LivingEntity target, final float pullProgress) {
        if (this.getItemBySlot(EquipmentSlot.MAINHAND).is(Items.LINGERING_POTION)) {
            final Potion potion = PotionUtils.getPotion(this.getItemBySlot(EquipmentSlot.MAINHAND));
            final Vec3 deltaMovement = target.getDeltaMovement();
            final double d = target.getX() + deltaMovement.x - this.getX();
            final double e = target.getEyeY() - 1.100000023841858 - this.getY();
            final double f = target.getZ() + deltaMovement.z - this.getZ();
            final double g = Math.sqrt(d * d + f * f);
            final ThrownPotion potionEntity = new ThrownPotion(this.level(), this);
            potionEntity.setItem(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), potion));
            potionEntity.setXRot(potionEntity.getXRot() + 20.0f);
            potionEntity.shoot(d, e + g * 0.2, f, 0.75f, 8.0f);
            if (!this.isSilent()) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.WITCH_THROW, this.getSoundSource(), 1.0f, 0.8f + this.random.nextFloat() * 0.4f);
            }
            this.level().addFreshEntity(potionEntity);
            this.setBowState(true);
            return;
        }
        final ItemStack itemStack = this.getProjectile(this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, Items.BOW)));
        final AbstractArrow persistentProjectileEntity = ProjectileUtil.getMobArrow(this, itemStack, pullProgress);
        final double d = target.getX() - this.getX();
        final double e = target.getY(0.3333333333333333) - persistentProjectileEntity.getY();
        final double f = target.getZ() - this.getZ();
        final double g = Math.sqrt(d * d + f * f);
        persistentProjectileEntity.shoot(d, e + g * 0.20000000298023224, f, 1.6f, (float) (14 - this.level().getDifficulty().getId() * 4));
        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
        this.level().addFreshEntity(persistentProjectileEntity);
    }

    @Override
    public void addAdditionalSaveData(final CompoundTag nbt) {
        nbt.putBoolean("BowState", this.getBowState());
        nbt.putBoolean("PotionState", this.getPotionState());
        super.addAdditionalSaveData(nbt);
    }

    private List<AreaEffectCloud> getNearbyClouds() {
        return this.level().getEntitiesOfClass(AreaEffectCloud.class, this.getBoundingBox().inflate(30.0), Entity::isAlive);
    }

    private void cancelEffect(final AreaEffectCloud areaEffectCloudEntity, final LivingEntity entity) {
        final Potion potion = areaEffectCloudEntity.getPotion();
        final MobEffectInstance statusEffectInstance = potion.getEffects().get(0);
        final MobEffect statusEffect = statusEffectInstance.getEffect();
        if (potion.getEffects().size() > 0) {
            entity.removeEffect(statusEffect);
        }
    }

    private void removeEffectsinCloud(final AreaEffectCloud cloudEntity) {
        final List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, cloudEntity.getBoundingBox().inflate(0.3), Entity::isAlive);
        for (final LivingEntity entity : list) {
            if (entity instanceof AbstractIllager) {
                this.cancelEffect(cloudEntity, entity);
            }
        }
    }

    @Override
    public void readAdditionalSaveData(final CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setPotionState(nbt.getBoolean("PotionState"));
        this.setBowState(nbt.getBoolean("BowState"));
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(Alchemist.DATA_HOLDING_POTION, false);
        this.entityData.define(Alchemist.DATA_HOLDING_BOW, true);
        super.defineSynchedData();
    }

    public boolean getPotionState() {
        return this.entityData.get(DATA_HOLDING_POTION);
    }

    public void setPotionState(final boolean potionState) {
        this.entityData.set(DATA_HOLDING_POTION, potionState);
    }

    public boolean getBowState() {
        return this.entityData.get(Alchemist.DATA_HOLDING_BOW);
    }

    public void setBowState(final boolean bowState) {
        this.entityData.set(Alchemist.DATA_HOLDING_BOW, bowState);
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.EVOKER_CELEBRATE;
    }

    @Override
    protected void customServerAiStep() {
        if (!this.getNearbyClouds().isEmpty()) {
            this.getNearbyClouds().forEach(this::removeEffectsinCloud);
        }
        --this.potionCooldown;
        if (this.potionCooldown <= 0) {
            this.setPotionState(true);
            this.potionCooldown = 160;
        }
        final ItemStack mainhand = this.getItemInHand(InteractionHand.MAIN_HAND);
        if (this.getBowState() && mainhand.is(Items.LINGERING_POTION)) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
            this.setPotionState(false);
        }
        if (this.getPotionState() && mainhand.is(Items.BOW)) {
            Potion potion = null;
            final int randvalue = this.random.nextInt(3);
            if (randvalue == 0) {
                potion = Potions.POISON;
            }
            if (randvalue == 1) {
                potion = Potions.SLOWNESS;
            }
            if (randvalue == 2) {
                potion = Potions.WEAKNESS;
            }
            this.setItemSlot(EquipmentSlot.MAINHAND, PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), potion));
            this.setBowState(false);
        }
        super.customServerAiStep();
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
    public void applyRaidBuffs(final int wave, final boolean unused) {

    }

    @Override
    public AbstractIllager.IllagerArmPose getArmPose() {
        if (this.isAggressive() && this.getItemBySlot(EquipmentSlot.MAINHAND).is(Items.BOW)) {
            return AbstractIllager.IllagerArmPose.BOW_AND_ARROW;
        }
        if (this.isAggressive() && this.getItemBySlot(EquipmentSlot.MAINHAND).is(Items.LINGERING_POTION)) {
            return AbstractIllager.IllagerArmPose.ATTACKING;
        }
        return AbstractIllager.IllagerArmPose.CROSSED;
    }
}
