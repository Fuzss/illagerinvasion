package me.sandbox.util;

import com.google.common.collect.Maps;
import fuzs.puzzleslib.api.item.v2.ToolTypeHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.HashMap;
import java.util.Random;

public class EnchantToolUtil {
    Item mainhanditem;
    Item offhanditem;
    ItemStack mainStack;
    ItemStack offStack;

    public boolean eligibleForEnchant(LivingEntity entity) {
        this.mainhanditem = entity.getItemBySlot(EquipmentSlot.MAINHAND).getItem();
        this.offhanditem = entity.getItemBySlot(EquipmentSlot.OFFHAND).getItem();
        return (this.mainhanditem instanceof BowItem || this.mainhanditem instanceof CrossbowItem || this.mainhanditem instanceof SwordItem || this.mainhanditem instanceof AxeItem || this.offhanditem instanceof BowItem
        || this.offhanditem instanceof CrossbowItem || this.offhanditem instanceof SwordItem || this.offhanditem instanceof AxeItem);

        ItemStack itemInMainHand = entity.getMainHandItem();
        return ToolTypeHelper.INSTANCE.isBow(itemInMainHand) || ToolTypeHelper.INSTANCE.isCrossbow(itemInMainHand) || ToolTypeHelper.INSTANCE.isSword(itemInMainHand) || ToolTypeHelper.INSTANCE.isAxe(itemInMainHand);
    }

    public static void doEnchant(Enchantment enchantment, int enchantLevel, LivingEntity entity) {
        HashMap<Enchantment, Integer> map = EnchantmentHelper.getEnchantments();
        map.put(enchantment, enchantLevel);
        EnchantmentHelper.setEnchantments(map, entity.getMainHandItem());
        EnchantmentHelper.setEnchantments(map, entity.getOffhandItem());
    }

    public void enchantHeldGear(LivingEntity entity) {
        this.mainhanditem = entity.getItemBySlot(EquipmentSlot.MAINHAND).getItem();
        this.offhanditem = entity.getItemBySlot(EquipmentSlot.OFFHAND).getItem();
        this.mainStack = entity.getItemBySlot(EquipmentSlot.MAINHAND);
        this.offStack = entity.getItemBySlot(EquipmentSlot.OFFHAND);
        if (this.mainhanditem instanceof BowItem || this.offhanditem instanceof BowItem) {
            this.doEnchant(Enchantments.POWER_ARROWS, 3, entity);
            }
        if (this.mainhanditem instanceof AxeItem || this.mainhanditem instanceof SwordItem || this.offhanditem instanceof SwordItem || this.offhanditem instanceof AxeItem) {
            this.doEnchant(Enchantments.SHARPNESS, 3, entity);
        }
        if (this.mainhanditem instanceof CrossbowItem || this.offhanditem instanceof CrossbowItem) {
            Random random = new Random();
            int randvalue = random.nextInt(2);
            if (randvalue == 1) {
                this.doEnchant(Enchantments.PIERCING, 4, entity);
            }
            if (randvalue == 0) {
                this.doEnchant(Enchantments.MULTISHOT, 1, entity);
            }
        }
        entity.setItemSlot(EquipmentSlot.MAINHAND, this.mainStack);
        entity.setItemSlot(EquipmentSlot.OFFHAND, this.offStack);
    }
}
