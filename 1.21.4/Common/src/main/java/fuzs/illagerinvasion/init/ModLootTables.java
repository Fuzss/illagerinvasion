package fuzs.illagerinvasion.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class ModLootTables {
    public static final ResourceKey<LootTable> ILLAGER_FORT_TOWER_LOOT_TABLE = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.LOOT_TABLE,
            "chests/illager_fort_tower");
    public static final ResourceKey<LootTable> ILLAGER_FORT_GROUND_LOOT_TABLE = ModRegistry.REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
            "chests/illager_fort_ground");
    public static final ResourceKey<LootTable> ILLUSIONER_TOWER_ENTRANCE_LOOT_TABLE = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.LOOT_TABLE,
            "chests/illusioner_tower_entrance");
    public static final ResourceKey<LootTable> ILLUSIONER_TOWER_STAIRS_LOOT_TABLE = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.LOOT_TABLE,
            "chests/illusioner_tower_stairs");
    public static final ResourceKey<LootTable> LABYRINTH_LOOT_TABLE = ModRegistry.REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
            "chests/labyrinth");
    public static final ResourceKey<LootTable> LABYRINTH_MAP_LOOT_TABLE = ModRegistry.REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
            "chests/labyrinth_map");
    public static final ResourceKey<LootTable> SORCERER_HUT_LOOT_TABLE = ModRegistry.REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
            "chests/sorcerer_hut");
    public static final ResourceKey<LootTable> ILLUSIONER_INJECT_LOOT_TABLE = ModRegistry.REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
            "entities/inject/illusioner");
    public static final ResourceKey<LootTable> PILLAGER_INJECT_LOOT_TABLE = ModRegistry.REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
            "entities/inject/pillager");
    public static final ResourceKey<LootTable> RAVAGER_INJECT_LOOT_TABLE = ModRegistry.REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
            "entities/inject/ravager");

    public static void bootstrap() {
        // NO-OP
    }
}
