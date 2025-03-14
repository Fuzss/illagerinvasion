package fuzs.illagerinvasion.init;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.HashMap;
import java.util.Map;

public class ModLootTables {
    static final Map<ResourceLocation, ResourceKey<LootTable>> LOOT_TABLE_INJECTIONS = new HashMap<>();
    public static final ResourceKey<LootTable> ILLAGER_FORT_TOWER = ModRegistry.REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
            "chests/illager_fort_tower");
    public static final ResourceKey<LootTable> ILLAGER_FORT_GROUND = ModRegistry.REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
            "chests/illager_fort_ground");
    public static final ResourceKey<LootTable> ILLUSIONER_TOWER_ENTRANCE = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.LOOT_TABLE,
            "chests/illusioner_tower_entrance");
    public static final ResourceKey<LootTable> ILLUSIONER_TOWER_STAIRS = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.LOOT_TABLE,
            "chests/illusioner_tower_stairs");
    public static final ResourceKey<LootTable> LABYRINTH = ModRegistry.REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
            "chests/labyrinth");
    public static final ResourceKey<LootTable> LABYRINTH_MAP = ModRegistry.REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
            "chests/labyrinth_map");
    public static final ResourceKey<LootTable> SORCERER_HUT = ModRegistry.REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
            "chests/sorcerer_hut");
    public static final ResourceKey<LootTable> ILLUSIONER_INJECTION = registerLootTableInjection(EntityType.ILLUSIONER.getDefaultLootTable()
            .orElseThrow());
    public static final ResourceKey<LootTable> PILLAGER_INJECTION = registerLootTableInjection(EntityType.PILLAGER.getDefaultLootTable()
            .orElseThrow());
    public static final ResourceKey<LootTable> RAVAGER_INJECTION = registerLootTableInjection(EntityType.RAVAGER.getDefaultLootTable()
            .orElseThrow());

    public static void bootstrap() {
        // NO-OP
    }

    static ResourceKey<LootTable> registerLootTableInjection(ResourceKey<LootTable> resourceKey) {
        ResourceKey<LootTable> newResourceKey = ModRegistry.REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
                "inject/" + resourceKey.location().getPath());
        LOOT_TABLE_INJECTIONS.put(resourceKey.location(), newResourceKey);
        return newResourceKey;
    }

    public static void onLootTableLoad(ResourceLocation resourceLocation, LootTable.Builder lootTable, HolderLookup.Provider registries) {
        if (LOOT_TABLE_INJECTIONS.containsKey(resourceLocation)) {
            lootTable.withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1.0F))
                    .add(NestedLootTable.lootTableReference(LOOT_TABLE_INJECTIONS.get(resourceLocation))));
        }
    }
}
