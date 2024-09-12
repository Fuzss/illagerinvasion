package fuzs.illagerinvasion.handler;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.config.ServerConfig;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import fuzs.puzzleslib.api.event.v1.data.DefaultedFloat;
import fuzs.puzzleslib.api.event.v1.data.DefaultedInt;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class PlatinumTrimHandler {
    public static final String INSIGHT_TRANSLATION_KEY = "trim_material." + IllagerInvasion.MOD_ID + ".platinum.effect.insight";
    public static final String AGILITY_TRANSLATION_KEY = "trim_material." + IllagerInvasion.MOD_ID + ".platinum.effect.agility";
    public static final String ENDURANCE_TRANSLATION_KEY = "trim_material." + IllagerInvasion.MOD_ID + ".platinum.effect.endurance";
    public static final String FEATHERWEIGHT_TRANSLATION_KEY = "trim_material." + IllagerInvasion.MOD_ID + ".platinum.effect.featherweight";
    public static final Map<ArmorItem.Type, String> PLATINUM_TRIM_TRANSLATION_KEYS = Collections.unmodifiableMap(Util.make(new EnumMap<>(ArmorItem.Type.class), (EnumMap<ArmorItem.Type, String> enumMap) -> {
        enumMap.put(ArmorItem.Type.BOOTS, FEATHERWEIGHT_TRANSLATION_KEY);
        enumMap.put(ArmorItem.Type.LEGGINGS, ENDURANCE_TRANSLATION_KEY);
        enumMap.put(ArmorItem.Type.CHESTPLATE, AGILITY_TRANSLATION_KEY);
        enumMap.put(ArmorItem.Type.HELMET, INSIGHT_TRANSLATION_KEY);
    }));

    public static EventResult onBreakSpeed(Player player, BlockState state, DefaultedFloat breakSpeed) {
        if (player.getInventory().getDestroySpeed(state) == 1.0F) {
            if (PlatinumTrimHandler.hasPlatinumTrim(player, EquipmentSlot.CHEST)) {
                breakSpeed.mapFloat(f -> f * 1.5F);
            }
        }
        return EventResult.PASS;
    }

    public static EventResult onLivingExperienceDrop(LivingEntity entity, @Nullable Player attackingPlayer, DefaultedInt droppedExperience) {
        if (attackingPlayer != null && hasPlatinumTrim(attackingPlayer, EquipmentSlot.HEAD)) {
            droppedExperience.mapDefaultInt(i -> i + (int) Math.max(1.0F, i * 0.2F));
        }
        return EventResult.PASS;
    }

    public static EventResult onFarmlandTrample(Level level, BlockPos pos, BlockState state, float fallDistance, Entity entity) {
        return entity instanceof Player player && hasPlatinumTrim(player, EquipmentSlot.FEET) ? EventResult.INTERRUPT : EventResult.PASS;
    }

    public static boolean hasPlatinumTrim(Player player, EquipmentSlot equipmentSlot) {
        return getPlatinumTrim(player.level(), player.getItemBySlot(equipmentSlot)).isPresent();
    }

    public static Optional<ArmorTrim> getPlatinumTrim(Level level, ItemStack itemStack) {
        if (!IllagerInvasion.CONFIG.get(ServerConfig.class).platinumTrimEffects) return Optional.empty();
        ArmorTrim armorTrim = itemStack.get(DataComponents.TRIM);
        return armorTrim != null && armorTrim.material().is(ModRegistry.PLATINUM_TRIM_MATERIAL) ? Optional.of(armorTrim) : Optional.empty();
    }
}
