package fuzs.illagerinvasion.util;

import fuzs.puzzleslib.api.item.v2.ToolTypeHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public class EnchantToolUtil {

    public static boolean eligibleForEnchant(LivingEntity entity) {
        return ToolTypeHelper.INSTANCE.isWeapon(entity.getMainHandItem()) || ToolTypeHelper.INSTANCE.isWeapon(entity.getOffhandItem());
    }

    public static void enchantHeldGear(LivingEntity entity) {
        tryEnchantHeldGear(entity, EquipmentSlot.MAINHAND, entity.getMainHandItem());
        tryEnchantHeldGear(entity, EquipmentSlot.OFFHAND, entity.getOffhandItem());
    }

    private static void tryEnchantHeldGear(LivingEntity entity, EquipmentSlot equipmentSlot, ItemStack mainHandItem) {
        if (ToolTypeHelper.INSTANCE.isWeapon(mainHandItem)) {
            HolderLookup.RegistryLookup<Enchantment> registryLookup = entity.registryAccess().lookupOrThrow(
                    Registries.ENCHANTMENT);
            enchantWeaponItem(mainHandItem, entity.getRandom(), registryLookup);
            entity.setItemSlot(equipmentSlot, mainHandItem);
        }
    }

    private static void enchantWeaponItem(ItemStack itemStack, RandomSource randomSource, HolderLookup.RegistryLookup<Enchantment> registryLookup) {
        ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(EnchantmentHelper.getEnchantmentsForCrafting(itemStack));
        if (ToolTypeHelper.INSTANCE.isBow(itemStack)) {
            enchantments.upgrade(registryLookup.getOrThrow(Enchantments.POWER), 3);
        } else if (ToolTypeHelper.INSTANCE.isSword(itemStack) || ToolTypeHelper.INSTANCE.isAxe(itemStack)) {
            enchantments.upgrade(registryLookup.getOrThrow(Enchantments.SHARPNESS), 3);
        } else if (ToolTypeHelper.INSTANCE.isCrossbow(itemStack)) {
            if (randomSource.nextInt(2) == 0) {
                enchantments.upgrade(registryLookup.getOrThrow(Enchantments.PIERCING), 4);
            } else {
                enchantments.upgrade(registryLookup.getOrThrow(Enchantments.MULTISHOT), 1);
            }
        } else if (ToolTypeHelper.INSTANCE.isTridentLike(itemStack)) {
            enchantments.upgrade(registryLookup.getOrThrow(Enchantments.IMPALING), 3);
        }

        EnchantmentHelper.setEnchantments(itemStack, enchantments.toImmutable());
    }
}
