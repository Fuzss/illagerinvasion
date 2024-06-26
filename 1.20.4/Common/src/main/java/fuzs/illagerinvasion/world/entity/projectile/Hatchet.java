package fuzs.illagerinvasion.world.entity.projectile;

import fuzs.illagerinvasion.init.ModRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Hatchet extends AbstractArrow implements ItemSupplier {
    private static final EntityDataAccessor<Boolean> DATA_ENCHANTED = SynchedEntityData.defineId(Hatchet.class, EntityDataSerializers.BOOLEAN);

    private ItemStack hatchetStack;
    private boolean dealtDamage;

    public Hatchet(EntityType<? extends Hatchet> entityType, Level level) {
        super(entityType, level, new ItemStack(ModRegistry.PLATINUM_INFUSED_HATCHET_ITEM.value()));
        this.hatchetStack = new ItemStack(ModRegistry.PLATINUM_INFUSED_HATCHET_ITEM.value());
    }

    public Hatchet(Level level, LivingEntity owner, ItemStack stack) {
        super(ModRegistry.HATCHET_ENTITY_TYPE.value(), owner, level, stack);
        this.hatchetStack = new ItemStack(ModRegistry.PLATINUM_INFUSED_HATCHET_ITEM.value());
        this.hatchetStack = stack.copy();
        this.entityData.set(DATA_ENCHANTED, stack.hasFoil());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ENCHANTED, false);
    }


    @Override
    public ItemStack getPickupItem() {
        return this.hatchetStack.copy();
    }

    public boolean isEnchanted() {
        return this.entityData.get(DATA_ENCHANTED);
    }

    @Override
    @Nullable
    protected EntityHitResult findHitEntity(Vec3 currentPosition, Vec3 nextPosition) {
        return this.dealtDamage ? null : super.findHitEntity(currentPosition, nextPosition);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity owner = this.getOwner();
        Entity target = entityHitResult.getEntity();
        float f = 8.0f;
        if (target instanceof LivingEntity livingEntity) {
            f += EnchantmentHelper.getDamageBonus(this.hatchetStack, livingEntity.getMobType());
        }
        DamageSource damageSource = this.damageSources().trident(this, owner == null ? this : owner);
        this.dealtDamage = true;
        SoundEvent soundEvent = SoundEvents.TRIDENT_HIT;
        if (target.hurt(damageSource, f)) {
            if (target.getType() == EntityType.ENDERMAN) {
                return;
            }
            if (target instanceof LivingEntity livingEntity) {
                if (owner instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingEntity, owner);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity) owner, livingEntity);
                }
                this.doPostHurtEffects(livingEntity);
            }
        }
        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
        float g = 1.0f;
        this.playSound(soundEvent, g, 1.0f);
    }


    @Override
    protected boolean tryPickup(Player player) {
        return super.tryPickup(player) || this.isNoPhysics() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem());
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    public void playerTouch(Player player) {
        if (this.ownedBy(player) || this.getOwner() == null) {
            super.playerTouch(player);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.contains("Hatchet", 10)) {
            this.hatchetStack = ItemStack.of(nbt.getCompound("Hatchet"));
        }
        this.dealtDamage = nbt.getBoolean("DealtDamage");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.put("Hatchet", this.hatchetStack.save(new CompoundTag()));
        nbt.putBoolean("DealtDamage", this.dealtDamage);
    }


    @Override
    protected float getWaterInertia() {
        return 0.99f;
    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(ModRegistry.PLATINUM_INFUSED_HATCHET_ITEM.value());
    }

    public float getAgeException() {
        if (!this.inGround) {
            return this.tickCount;
        }
        return 1.0f;
    }
}
