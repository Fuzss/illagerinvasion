package fuzs.illagerinvasion.world.entity.projectile;

import fuzs.illagerinvasion.init.ModEntityTypes;
import fuzs.illagerinvasion.init.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class ThrownHatchet extends ThrownTrident implements ItemSupplier {

    public ThrownHatchet(EntityType<? extends ThrownHatchet> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownHatchet(Level level, LivingEntity owner, ItemStack pickupItemStack) {
        this(level, owner.getX(), owner.getEyeY() - 0.1F, owner.getZ(), pickupItemStack);
        this.setOwner(owner);
    }

    public ThrownHatchet(Level level, double x, double y, double z, ItemStack pickupItemStack) {
        this(ModEntityTypes.HATCHET_ENTITY_TYPE.value(), level);
        this.setPickupItemStack(pickupItemStack.copy());
        this.setCustomName(pickupItemStack.get(DataComponents.CUSTOM_NAME));
        if (pickupItemStack.remove(DataComponents.INTANGIBLE_PROJECTILE) != null) {
            this.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }
        this.setPos(x, y, z);
        this.entityData.set(ID_FOIL, pickupItemStack.hasFoil());
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.PLATINUM_INFUSED_HATCHET_ITEM.value());
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        this.setDeltaMovement(this.getDeltaMovement().scale(-0.5));
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
