package fuzs.illagerinvasion.world.entity.monster;

import fuzs.illagerinvasion.world.entity.ai.goal.RangedBowAttackWithoutStrafingGoal;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.creaking.Creaking;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrownLingeringPotion;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.StreamSupport;

public class Alchemist extends AbstractIllager implements RangedAttackMob {
    private static final EntityDataAccessor<Boolean> DATA_HOLDING_POTION = SynchedEntityData.defineId(Alchemist.class,
            EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_HOLDING_BOW = SynchedEntityData.defineId(Alchemist.class,
            EntityDataSerializers.BOOLEAN);

    public int potionCooldown = 160;

    public Alchemist(EntityType<? extends Alchemist> entityType, Level level) {
        super(entityType, level);
        this.xpReward = Enemy.XP_REWARD_LARGE;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Creaking.class, 8.0F, 1.0, 1.2));
        this.goalSelector.addGoal(2, new Raider.HoldGroundAttackGoal(this, 10.0F));
        this.goalSelector.addGoal(4, new AlchemistRangedAttackGoal(this, 0.85, 20, 15.0F));
        this.goalSelector.addGoal(4, new RangedBowAttackWithoutStrafingGoal<>(this, 0.85, 30, 15.0F));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
        this.targetSelector.addGoal(2,
                new NearestAttackableTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3,
                new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason entitySpawnReason, @Nullable SpawnGroupData spawnGroupData) {
        spawnGroupData = super.finalizeSpawn(level, difficulty, entitySpawnReason, spawnGroupData);
        RandomSource randomSource = level.getRandom();
        this.populateDefaultEquipmentSlots(randomSource, difficulty);
        return spawnGroupData;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
    }

    @Override
    public void performRangedAttack(LivingEntity target, float pullProgress) {
        ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        if (itemStack.is(Items.LINGERING_POTION) && itemStack.getOrDefault(DataComponents.POTION_CONTENTS,
                PotionContents.EMPTY).potion().isPresent()) {
            Vec3 deltaMovement = target.getDeltaMovement();
            double d = target.getX() + deltaMovement.x - this.getX();
            double e = target.getEyeY() - 1.1 - this.getY();
            double f = target.getZ() + deltaMovement.z - this.getZ();
            double g = Math.sqrt(d * d + f * f);
            if (this.level() instanceof ServerLevel serverLevel) {
                ThrownLingeringPotion thrownPotion = Projectile.spawnProjectileUsingShoot(ThrownLingeringPotion::new,
                        serverLevel,
                        itemStack,
                        this,
                        d,
                        e + g * 0.2,
                        f,
                        0.75F,
                        8.0F);
                thrownPotion.setXRot(thrownPotion.getXRot() + 20.0F);
            }
            this.playSound(SoundEvents.WITCH_THROW, 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
            this.setBowState(true);
        } else {
            ItemStack itemInHand = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, Items.BOW));
            ItemStack projectileItem = this.getProjectile(itemInHand);
            AbstractArrow abstractArrow = ProjectileUtil.getMobArrow(this, projectileItem, pullProgress, itemInHand);
            double d = target.getX() - this.getX();
            double e = target.getY(0.33) - abstractArrow.getY();
            double f = target.getZ() - this.getZ();
            double g = Math.sqrt(d * d + f * f);
            if (this.level() instanceof ServerLevel serverLevel) {
                Projectile.spawnProjectileUsingShoot(abstractArrow,
                        serverLevel,
                        projectileItem,
                        d,
                        e + g * (double) 0.2F,
                        f,
                        1.6F,
                        (float) (14 - serverLevel.getDifficulty().getId() * 4));
            }
            this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        }
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        valueOutput.putBoolean("BowState", this.getBowState());
        valueOutput.putBoolean("PotionState", this.getPotionState());
        super.addAdditionalSaveData(valueOutput);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        this.setPotionState(valueInput.getBooleanOr("PotionState", false));
        this.setBowState(valueInput.getBooleanOr("BowState", false));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(Alchemist.DATA_HOLDING_POTION, false);
        builder.define(Alchemist.DATA_HOLDING_BOW, true);
    }

    public boolean getPotionState() {
        return this.entityData.get(DATA_HOLDING_POTION);
    }

    public void setPotionState(boolean potionState) {
        this.entityData.set(DATA_HOLDING_POTION, potionState);
    }

    public boolean getBowState() {
        return this.entityData.get(Alchemist.DATA_HOLDING_BOW);
    }

    public void setBowState(boolean bowState) {
        this.entityData.set(Alchemist.DATA_HOLDING_BOW, bowState);
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.EVOKER_CELEBRATE;
    }

    @Override
    protected void customServerAiStep(ServerLevel serverLevel) {
        List<AreaEffectCloud> nearbyClouds = serverLevel.getEntitiesOfClass(AreaEffectCloud.class,
                this.getBoundingBox().inflate(30.0),
                Entity::isAlive);
        for (AreaEffectCloud areaEffectCloud : nearbyClouds) {
            this.removeEffectsInCloud(areaEffectCloud);
        }
        --this.potionCooldown;
        if (this.potionCooldown <= 0) {
            this.setPotionState(true);
            this.potionCooldown = 160;
        }
        ItemStack mainHandItem = this.getItemInHand(InteractionHand.MAIN_HAND);
        if (this.getBowState() && mainHandItem.is(Items.LINGERING_POTION)) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
            this.setPotionState(false);
        }
        if (this.getPotionState() && mainHandItem.is(Items.BOW)) {
            Holder<Potion> potion = switch (this.random.nextInt(3)) {
                case 0 -> Potions.POISON;
                case 1 -> Potions.SLOWNESS;
                case 2 -> Potions.WEAKNESS;
                default -> throw new RuntimeException();
            };
            this.setItemSlot(EquipmentSlot.MAINHAND, PotionContents.createItemStack(Items.LINGERING_POTION, potion));
            this.setBowState(false);
        }
        super.customServerAiStep(serverLevel);
    }

    private void removeEffectsInCloud(AreaEffectCloud cloudEntity) {
        List<? extends LivingEntity> nearbyIllagers = this.level()
                .getEntitiesOfClass(AbstractIllager.class, cloudEntity.getBoundingBox().inflate(0.3), Entity::isAlive);
        for (LivingEntity entity : nearbyIllagers) {
            StreamSupport.stream(cloudEntity.potionContents.getAllEffects().spliterator(), false)
                    .findAny()
                    .map(MobEffectInstance::getEffect)
                    .ifPresent(entity::removeEffect);
        }
    }

    @Override
    public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
        return mobEffectInstance.getEffect().is(MobEffects.POISON) && super.canBeAffected(mobEffectInstance);
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
    public void applyRaidBuffs(ServerLevel level, int wave, boolean unused) {
        // NO-OP
    }

    @Override
    public AbstractIllager.IllagerArmPose getArmPose() {
        if (this.isAggressive()) {
            ItemStack itemInHand = this.getMainHandItem();
            if (itemInHand.is(Items.BOW)) {
                return IllagerArmPose.BOW_AND_ARROW;
            } else if (itemInHand.is(Items.LINGERING_POTION)) {
                return AbstractIllager.IllagerArmPose.ATTACKING;
            }
        }

        return AbstractIllager.IllagerArmPose.CROSSED;
    }

    @Override
    public boolean canFireProjectileWeapon(ProjectileWeaponItem projectileWeapon) {
        return projectileWeapon == Items.BOW;
    }

    class AlchemistRangedAttackGoal extends RangedAttackGoal {

        public AlchemistRangedAttackGoal(RangedAttackMob rangedAttackMob, double speedModifier, int attackInterval, float attackRadius) {
            super(rangedAttackMob, speedModifier, attackInterval, attackRadius);
        }

        @Override
        public boolean canUse() {
            return super.canUse() && Alchemist.this.getMainHandItem().is(Items.LINGERING_POTION);
        }

        @Override
        public void start() {
            super.start();
            Alchemist.this.setAggressive(true);
        }

        @Override
        public void stop() {
            super.stop();
            Alchemist.this.setAggressive(false);
        }
    }
}
