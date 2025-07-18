package fuzs.illagerinvasion.world.entity.monster;

import fuzs.illagerinvasion.init.ModSoundEvents;
import fuzs.puzzleslib.api.item.v2.EnchantingHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.creaking.Creaking;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public class Inquisitor extends AbstractIllager implements Stunnable {
    private static final EntityDataAccessor<Boolean> STUNNED = SynchedEntityData.defineId(Inquisitor.class,
            EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FINAL_ROAR = SynchedEntityData.defineId(Inquisitor.class,
            EntityDataSerializers.BOOLEAN);

    public boolean finalRoar;
    public int stunTick = 40;
    public boolean isStunned;
    public int blockedCount;

    public Inquisitor(EntityType<? extends Inquisitor> entityType, Level world) {
        super(entityType, world);
        this.xpReward = Enemy.XP_REWARD_HUGE;
        this.setPathfindingMalus(PathType.LEAVES, 0.0F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Creaking.class, 8.0F, 1.0, 1.2));
        this.goalSelector.addGoal(4, new Raider.HoldGroundAttackGoal(this, 10.0F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    protected void customServerAiStep(ServerLevel serverLevel) {
        if (!this.isNoAi() && GoalUtils.hasGroundPathNavigation(this)) {
            boolean isRaided = serverLevel.isRaided(this.blockPosition());
            ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(isRaided);
        }
        super.customServerAiStep(serverLevel);
    }

    @Override
    public void aiStep() {
        if (this.horizontalCollision && this.level() instanceof ServerLevel serverLevel && serverLevel.getGameRules()
                .getBoolean(GameRules.RULE_MOBGRIEFING)) {
            AABB box = this.getBoundingBox().inflate(1.0);
            for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(box.minX),
                    Mth.floor(box.minY),
                    Mth.floor(box.minZ),
                    Mth.floor(box.maxX),
                    Mth.floor(box.maxY),
                    Mth.floor(box.maxZ))) {
                Block block = serverLevel.getBlockState(blockPos).getBlock();
                if (block instanceof LeavesBlock || block instanceof DoorBlock || block instanceof WebBlock) {
                    if (serverLevel.destroyBlock(blockPos, true, this) && block instanceof DoorBlock) {
                        this.playSound(SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR, 1.0f, 1.0f);
                    }
                }
            }
        }
        super.aiStep();
    }

    @Override
    public boolean hasLineOfSight(Entity entity) {
        return !this.isStunned() && super.hasLineOfSight(entity);
    }

    @Override
    protected boolean isImmobile() {
        return super.isImmobile() || this.isStunned();
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        valueOutput.putBoolean("Stunned", this.isStunned);
        valueOutput.putBoolean("FinalRoar", this.finalRoar);
        super.addAdditionalSaveData(valueOutput);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        this.setStunnedState(valueInput.getBooleanOr("Stunned", false));
        this.setFinalRoarState(valueInput.getBooleanOr("FinalRoar", false));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STUNNED, false);
        builder.define(FINAL_ROAR, false);
    }

    @Override
    public boolean isStunned() {
        return this.entityData.get(STUNNED);
    }

    public void setStunnedState(boolean isStunned) {
        this.entityData.set(STUNNED, isStunned);
    }

    public boolean getFinalRoarState() {
        return this.entityData.get(FINAL_ROAR);
    }

    public void setFinalRoarState(boolean hasdoneRoar) {
        this.entityData.set(FINAL_ROAR, hasdoneRoar);
    }

    @Override
    public AbstractIllager.IllagerArmPose getArmPose() {
        if (this.isCelebrating()) {
            return AbstractIllager.IllagerArmPose.CELEBRATING;
        } else if (this.isAggressive()) {
            return AbstractIllager.IllagerArmPose.ATTACKING;
        } else {
            return IllagerArmPose.NEUTRAL;
        }
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.VINDICATOR_CELEBRATE;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isAlive() && this.isStunned()) {
            --this.stunTick;
            if (this.stunTick <= 0) {
                this.setStunnedState(false);
                this.stunTick = 40;
            }
        }
    }

    @Override
    protected void blockedByItem(LivingEntity target) {
        this.knockBack(target);
        target.hurtMarked = true;
        target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 0));
    }

    private void knockBack(Entity entity) {
        double d = entity.getX() - this.getX();
        double e = entity.getZ() - this.getZ();
        double f = Math.max(d * d + e * e, 0.001);
        entity.push(d / f * 0.6, 0.4, e / f * 0.6);
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason entitySpawnReason, @Nullable SpawnGroupData spawnGroupData) {
        spawnGroupData = super.finalizeSpawn(level, difficulty, entitySpawnReason, spawnGroupData);
        ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
        this.populateDefaultEquipmentSlots(level.getRandom(), difficulty);
        this.populateDefaultEquipmentEnchantments(level, level.getRandom(), difficulty);
        return spawnGroupData;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficulty) {
        if (this.getCurrentRaid() == null) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
            this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        }
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float damageAmount) {
        Entity attacker = damageSource.getEntity();
        boolean hasShield = this.getOffhandItem().is(Items.SHIELD);
        if (this.isAggressive()) {
            if (attacker instanceof LivingEntity livingEntity) {
                ItemStack itemInHand = livingEntity.getMainHandItem();
                ItemStack shieldItem = this.getOffhandItem();
                if ((itemInHand.is(ItemTags.AXES) || attacker instanceof IronGolem || this.blockedCount >= 4)
                        && shieldItem.is(Items.SHIELD)) {
                    this.playSound(SoundEvents.SHIELD_BREAK.value(), 1.0f, 1.0f);
                    this.setStunnedState(true);
                    if (this.level() instanceof ServerLevel) {
                        serverLevel.sendParticles((ParticleOptions) new ItemParticleOption(ParticleTypes.ITEM,
                                shieldItem), this.getX(), this.getY() + 1.5, this.getZ(), 30, 0.3, 0.2, 0.3, 0.003);
                        serverLevel.sendParticles((ParticleOptions) ParticleTypes.CLOUD,
                                this.getX(),
                                this.getY() + 1.0,
                                this.getZ(),
                                30,
                                0.3,
                                0.3,
                                0.3,
                                0.1);
                        this.playSound(SoundEvents.RAVAGER_ROAR, 1.0f, 1.0f);
                        this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    }
                    this.level()
                            .getEntitiesOfClass(LivingEntity.class,
                                    this.getBoundingBox().inflate(8.0),
                                    entity -> !(entity instanceof Monster))
                            .forEach(this::blockedByItem);
                    return super.hurtServer(serverLevel, damageSource, damageAmount);
                }
            }
            if (damageSource.getDirectEntity() instanceof AbstractArrow && hasShield) {
                this.playSound(SoundEvents.SHIELD_BLOCK.value(), 1.0f, 1.0f);
                ++this.blockedCount;
                return false;
            }
            if (damageSource.getDirectEntity() instanceof LivingEntity && hasShield) {
                ++this.blockedCount;
                this.playSound(SoundEvents.SHIELD_BLOCK.value(), 1.0f, 1.0f);
                return false;
            }
        }
        return super.hurtServer(serverLevel, damageSource, damageAmount);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.ILLAGER_BRUTE_AMBIENT_SOUND_EVENT.value();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.ILLAGER_BRUTE_DEATH_SOUND_EVENT.value();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSoundEvents.ILLAGER_BRUTE_HURT_SOUND_EVENT.value();
    }

    @Override
    public void applyRaidBuffs(ServerLevel serverLevel, int wave, boolean unused) {
        ItemStack mainHandItem = new ItemStack(Items.STONE_SWORD);
        ItemStack offHandItem = new ItemStack(Items.SHIELD);
        Raid raid = this.getCurrentRaid();
        if (this.random.nextFloat() <= raid.getEnchantOdds()) {
            int enchantmentLevel = wave > raid.getNumGroups(Difficulty.NORMAL) ? 2 : 1;
            Holder<Enchantment> enchantment = EnchantingHelper.lookup(serverLevel, Enchantments.SHARPNESS);
            mainHandItem.enchant(enchantment, enchantmentLevel);
        }
        this.setItemSlot(EquipmentSlot.MAINHAND, mainHandItem);
        this.setItemSlot(EquipmentSlot.OFFHAND, offHandItem);
    }
}