package fuzs.illagerinvasion.util;

import fuzs.puzzleslib.api.item.v2.ToolTypeHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Map;

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
            enchantWeaponItem(mainHandItem, entity.getRandom());
            entity.setItemSlot(equipmentSlot, mainHandItem);
        }
    }

    private static void enchantWeaponItem(ItemStack stack, RandomSource randomSource) {
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
        if (ToolTypeHelper.INSTANCE.isBow(stack)) {
            enchantments.put(Enchantments.POWER_ARROWS, 3);
        } else if (ToolTypeHelper.INSTANCE.isSword(stack) || ToolTypeHelper.INSTANCE.isAxe(stack)) {
            enchantments.put(Enchantments.SHARPNESS, 3);
        } else if (ToolTypeHelper.INSTANCE.isCrossbow(stack)) {
            if (randomSource.nextInt(2) == 0) {
                enchantments.put(Enchantments.PIERCING, 4);
            } else {
                enchantments.put(Enchantments.MULTISHOT, 1);
            }
        } else if (ToolTypeHelper.INSTANCE.isTridentLike(stack)) {
            enchantments.put(Enchantments.IMPALING, 3);
        }

        EnchantmentHelper.setEnchantments(enchantments, stack);
    }
}
