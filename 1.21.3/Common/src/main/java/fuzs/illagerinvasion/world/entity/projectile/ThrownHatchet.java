package fuzs.illagerinvasion.world.entity.projectile;

import fuzs.illagerinvasion.init.ModEntityTypes;
import fuzs.illagerinvasion.init.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
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

public class ThrownHatchet extends AbstractArrow implements ItemSupplier {
    private static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.defineId(ThrownHatchet.class, EntityDataSerializers.BOOLEAN);

    private boolean dealtDamage;

    public ThrownHatchet(EntityType<? extends ThrownHatchet> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownHatchet(Level level, LivingEntity shooter, ItemStack pickupItemStack) {
        super(ModEntityTypes.HATCHET_ENTITY_TYPE.value(), shooter, level, pickupItemStack, null);
        this.entityData.set(ID_FOIL, pickupItemStack.hasFoil());
    }

    public ThrownHatchet(Level level, double x, double y, double z, ItemStack pickupItemStack) {
        super(ModEntityTypes.HATCHET_ENTITY_TYPE.value(), x, y, z, level, pickupItemStack, pickupItemStack);
        this.entityData.set(ID_FOIL, pickupItemStack.hasFoil());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ID_FOIL, false);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.PLATINUM_INFUSED_HATCHET_ITEM.value());
    }

    @Override
    public ItemStack getWeaponItem() {
        return this.getPickupItemStackOrigin();
    }

    public boolean isFoil() {
        return this.entityData.get(ID_FOIL);
    }

    @Override
    @Nullable
    protected EntityHitResult findHitEntity(Vec3 currentPosition, Vec3 nextPosition) {
        return this.dealtDamage ? null : super.findHitEntity(currentPosition, nextPosition);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        float damageAmount = 8.0F;
        Entity entity2 = this.getOwner();
        DamageSource damageSource = this.damageSources().trident(this, (Entity)(entity2 == null ? this : entity2));
        if (this.level() instanceof ServerLevel serverLevel) {
            damageAmount = EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), entity, damageSource, damageAmount);
        }

        this.dealtDamage = true;
        if (entity.hurtOrSimulate(damageSource, damageAmount)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (this.level() instanceof ServerLevel serverLevel) {
                EnchantmentHelper.doPostAttackEffectsWithItemSource(serverLevel, entity, damageSource, this.getWeaponItem());
            }

            if (entity instanceof LivingEntity livingEntity) {
                this.doKnockback(livingEntity, damageSource);
                this.doPostHurtEffects(livingEntity);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
        this.playSound(SoundEvents.TRIDENT_HIT, 1.0F, 1.0F);
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
        this.dealtDamage = nbt.getBoolean("DealtDamage");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("DealtDamage", this.dealtDamage);
    }

    @Override
    protected float getWaterInertia() {
        return 0.99F;
    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }

    @Override
    public boolean isInGround() {
        return super.isInGround();
    }

    @Override
    public ItemStack getItem() {
        return this.getPickupItemStackOrigin();
    }
}
