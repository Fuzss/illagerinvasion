package fuzs.illagerinvasion.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.init.*;
import fuzs.puzzleslib.api.data.v2.AbstractDatapackRegistriesProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class ModDatapackRegistriesProvider extends AbstractDatapackRegistriesProvider {

    public ModDatapackRegistriesProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addBootstrap(RegistryBoostrapConsumer consumer) {
        consumer.add(Registries.TRIM_MATERIAL, ModDatapackRegistriesProvider::boostrapTrimMaterials);
        consumer.add(Registries.INSTRUMENT, ModDatapackRegistriesProvider::boostrapInstruments);
        consumer.add(Registries.STRUCTURE, ModDatapackRegistriesProvider::bootstrapStructures);
        consumer.add(Registries.STRUCTURE_SET, ModDatapackRegistriesProvider::bootstrapStructureSets);
        consumer.add(Registries.TEMPLATE_POOL, ModDatapackRegistriesProvider::bootstrapTemplatePools);
    }

    static void boostrapTrimMaterials(BootstrapContext<TrimMaterial> context) {
        registerTrimMaterial(context,
                ModRegistry.PLATINUM_TRIM_MATERIAL,
                ModItems.PLATINUM_SHEET_ITEM.value(),
                0x527D7C);
    }

    static void boostrapInstruments(BootstrapContext<Instrument> context) {
        registerInstrument(context,
                ModRegistry.REVEAL_INSTRUMENT,
                ModSoundEvents.HORN_OF_SIGHT_SOUND_EVENT,
                7.0F,
                64.0F);
    }

    static void bootstrapStructures(BootstrapContext<Structure> context) {
        context.register(ModStructures.FIRECALLER_HUT_STRUCTURE,
                new JigsawStructure(new Structure.StructureSettings.Builder(context.lookup(Registries.BIOME)
                        .getOrThrow(ModTags.HAS_FIRECALLER_HUT_BIOME_TAG)).generationStep(GenerationStep.Decoration.SURFACE_STRUCTURES)
                        .terrainAdapation(TerrainAdjustment.BEARD_THIN)
                        .build(),
                        context.lookup(Registries.TEMPLATE_POOL)
                                .getOrThrow(ModStructures.FIRECALLER_HUT_BASE_PLATES_TEMPLATE_POOL),
                        3,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG));
        context.register(ModStructures.ILLAGER_FORT_STRUCTURE,
                new JigsawStructure(new Structure.StructureSettings.Builder(context.lookup(Registries.BIOME)
                        .getOrThrow(ModTags.HAS_ILLAGER_FORT_BIOME_TAG)).spawnOverrides(Map.of(MobCategory.MONSTER,
                                new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE,
                                        WeightedRandomList.create(new MobSpawnSettings.SpawnerData(EntityType.PILLAGER,
                                                        3,
                                                        1,
                                                        1),
                                                new MobSpawnSettings.SpawnerData(ModEntityTypes.MARAUDER_ENTITY_TYPE.value(),
                                                        3,
                                                        1,
                                                        1),
                                                new MobSpawnSettings.SpawnerData(ModEntityTypes.BASHER_ENTITY_TYPE.value(),
                                                        1,
                                                        1,
                                                        1),
                                                new MobSpawnSettings.SpawnerData(ModEntityTypes.PROVOKER_ENTITY_TYPE.value(),
                                                        3,
                                                        1,
                                                        1)))))
                        .generationStep(GenerationStep.Decoration.SURFACE_STRUCTURES)
                        .terrainAdapation(TerrainAdjustment.BEARD_THIN)
                        .build(),
                        context.lookup(Registries.TEMPLATE_POOL).getOrThrow(ModStructures.ILLAGER_FORT_TEMPLATE_POOL),
                        2,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG));
        context.register(ModStructures.ILLUSIONER_TOWER_STRUCTURE,
                new JigsawStructure(new Structure.StructureSettings.Builder(context.lookup(Registries.BIOME)
                        .getOrThrow(ModTags.HAS_ILLUSIONER_TOWER_BIOME_TAG)).generationStep(GenerationStep.Decoration.SURFACE_STRUCTURES)
                        .terrainAdapation(TerrainAdjustment.BEARD_THIN)
                        .build(),
                        context.lookup(Registries.TEMPLATE_POOL)
                                .getOrThrow(ModStructures.ILLUSIONER_TOWER_TEMPLATE_POOL),
                        2,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG));
        context.register(ModStructures.LABYRINTH_STRUCTURE,
                new JigsawStructure(new Structure.StructureSettings.Builder(context.lookup(Registries.BIOME)
                        .getOrThrow(ModTags.HAS_LABYRINTH_BIOME_TAG)).generationStep(GenerationStep.Decoration.SURFACE_STRUCTURES)
                        .terrainAdapation(TerrainAdjustment.ENCAPSULATE)
                        .build(),
                        context.lookup(Registries.TEMPLATE_POOL)
                                .getOrThrow(ModStructures.LABYRINTH_TOWERS_TEMPLATE_POOL),
                        7,
                        ConstantHeight.of(VerticalAnchor.belowTop(40)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG));
        context.register(ModStructures.SORCERER_HUT_STRUCTURE,
                new JigsawStructure(new Structure.StructureSettings.Builder(context.lookup(Registries.BIOME)
                        .getOrThrow(ModTags.HAS_SORCERER_HUT_BIOME_TAG)).generationStep(GenerationStep.Decoration.SURFACE_STRUCTURES)
                        .terrainAdapation(TerrainAdjustment.BEARD_THIN)
                        .build(),
                        context.lookup(Registries.TEMPLATE_POOL).getOrThrow(ModStructures.SORCERER_HUT_TEMPLATE_POOL),
                        2,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG));
    }

    static void bootstrapStructureSets(BootstrapContext<StructureSet> context) {
        context.register(ModStructures.FIRECALLER_HUT_STRUCTURE_SET,
                new StructureSet(List.of(StructureSet.entry(context.lookup(Registries.STRUCTURE)
                        .getOrThrow(ModStructures.FIRECALLER_HUT_STRUCTURE))),
                        new RandomSpreadStructurePlacement(Vec3i.ZERO,
                                StructurePlacement.FrequencyReductionMethod.DEFAULT,
                                1.0F,
                                169476076,
                                Optional.empty(),
                                42,
                                28,
                                RandomSpreadType.LINEAR)));
        context.register(ModStructures.ILLAGER_FORT_STRUCTURE_SET,
                new StructureSet(List.of(StructureSet.entry(context.lookup(Registries.STRUCTURE)
                        .getOrThrow(ModStructures.ILLAGER_FORT_STRUCTURE))),
                        new RandomSpreadStructurePlacement(Vec3i.ZERO,
                                StructurePlacement.FrequencyReductionMethod.LEGACY_TYPE_1,
                                0.2F,
                                169476082,
                                Optional.of(new StructurePlacement.ExclusionZone(context.lookup(Registries.STRUCTURE_SET)
                                        .getOrThrow(BuiltinStructureSets.VILLAGES), 10)),
                                42,
                                28,
                                RandomSpreadType.LINEAR)));
        context.register(ModStructures.ILLUSIONER_TOWER_STRUCTURE_SET,
                new StructureSet(List.of(StructureSet.entry(context.lookup(Registries.STRUCTURE)
                        .getOrThrow(ModStructures.ILLUSIONER_TOWER_STRUCTURE))),
                        new RandomSpreadStructurePlacement(Vec3i.ZERO,
                                StructurePlacement.FrequencyReductionMethod.DEFAULT,
                                1.0F,
                                169476081,
                                Optional.of(new StructurePlacement.ExclusionZone(context.lookup(Registries.STRUCTURE_SET)
                                        .getOrThrow(BuiltinStructureSets.VILLAGES), 10)),
                                42,
                                28,
                                RandomSpreadType.LINEAR)));
        context.register(ModStructures.LABYRINTH_STRUCTURE_SET,
                new StructureSet(List.of(StructureSet.entry(context.lookup(Registries.STRUCTURE)
                        .getOrThrow(ModStructures.LABYRINTH_STRUCTURE))),
                        new RandomSpreadStructurePlacement(Vec3i.ZERO,
                                StructurePlacement.FrequencyReductionMethod.DEFAULT,
                                1.0F,
                                169476079,
                                Optional.of(new StructurePlacement.ExclusionZone(context.lookup(Registries.STRUCTURE_SET)
                                        .getOrThrow(BuiltinStructureSets.VILLAGES), 10)),
                                42,
                                28,
                                RandomSpreadType.LINEAR)));
        context.register(ModStructures.SORCERER_HUT_STRUCTURE_SET,
                new StructureSet(List.of(StructureSet.entry(context.lookup(Registries.STRUCTURE)
                        .getOrThrow(ModStructures.SORCERER_HUT_STRUCTURE))),
                        new RandomSpreadStructurePlacement(Vec3i.ZERO,
                                StructurePlacement.FrequencyReductionMethod.DEFAULT,
                                1.0F,
                                169476080,
                                Optional.empty(),
                                18,
                                14,
                                RandomSpreadType.LINEAR)));
    }

    static void bootstrapTemplatePools(BootstrapContext<StructureTemplatePool> context) {
        context.register(ModStructures.FIRECALLER_HUT_BASE_PLATES_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(legacy("firecaller_hut/base_plate"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.FIRECALLER_HUT_FEATURES_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("firecaller_hut/feature_crops"), 1),
                                Pair.of(single("firecaller_hut/feature_hay"), 1),
                                Pair.of(single("firecaller_hut/feature_obelisk"), 1),
                                Pair.of(single("firecaller_hut/feature_stable"), 1),
                                Pair.of(single("firecaller_hut/feature_well"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.FIRECALLER_HUT_HUTS_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("firecaller_hut/hut"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.ILLAGER_FORT_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("illager_fort/illager_fort"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.ILLUSIONER_TOWER_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(legacy("illusioner_tower/tower_1"), 1),
                                Pair.of(legacy("illusioner_tower/tower_2"), 1),
                                Pair.of(legacy("illusioner_tower/tower_3"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.LABYRINTH_HALLS_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("labyrinth/halls/straight_no_rooms",
                                        LiquidSettings.IGNORE_WATERLOGGING), 1),
                                Pair.of(single("labyrinth/halls/straight_left_room",
                                        LiquidSettings.IGNORE_WATERLOGGING), 1),
                                Pair.of(single("labyrinth/halls/straight_right_room",
                                        LiquidSettings.IGNORE_WATERLOGGING), 1),
                                Pair.of(single("labyrinth/halls/straight_two_rooms",
                                        LiquidSettings.IGNORE_WATERLOGGING), 1),
                                Pair.of(single("labyrinth/halls/left_no_rooms", LiquidSettings.IGNORE_WATERLOGGING), 1),
                                Pair.of(single("labyrinth/halls/right_no_rooms", LiquidSettings.IGNORE_WATERLOGGING),
                                        1),
                                Pair.of(single("labyrinth/halls/straight_and_left_and_right",
                                        LiquidSettings.IGNORE_WATERLOGGING), 1),
                                Pair.of(single("labyrinth/halls/straight_and_left_no_rooms",
                                        LiquidSettings.IGNORE_WATERLOGGING), 1),
                                Pair.of(single("labyrinth/halls/straight_and_left_right_room",
                                        LiquidSettings.IGNORE_WATERLOGGING), 1),
                                Pair.of(single("labyrinth/halls/straight_and_right_left_room",
                                        LiquidSettings.IGNORE_WATERLOGGING), 1),
                                Pair.of(single("labyrinth/halls/straight_and_right_no_rooms",
                                        LiquidSettings.IGNORE_WATERLOGGING), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.LABYRINTH_ROOMS_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("labyrinth/rooms/room_0", LiquidSettings.IGNORE_WATERLOGGING),
                                        1),
                                Pair.of(single("labyrinth/rooms/room_1", LiquidSettings.IGNORE_WATERLOGGING), 1),
                                Pair.of(single("labyrinth/rooms/room_2", LiquidSettings.IGNORE_WATERLOGGING), 1),
                                Pair.of(single("labyrinth/rooms/room_3", LiquidSettings.IGNORE_WATERLOGGING), 1),
                                Pair.of(single("labyrinth/rooms/room_4", LiquidSettings.IGNORE_WATERLOGGING), 1),
                                Pair.of(single("labyrinth/rooms/room_5", LiquidSettings.IGNORE_WATERLOGGING), 1),
                                Pair.of(single("labyrinth/rooms/room_6", LiquidSettings.IGNORE_WATERLOGGING), 1),
                                Pair.of(single("labyrinth/rooms/room_7", LiquidSettings.IGNORE_WATERLOGGING), 1),
                                Pair.of(single("labyrinth/rooms/room_8", LiquidSettings.IGNORE_WATERLOGGING), 1),
                                Pair.of(single("labyrinth/rooms/room_9", LiquidSettings.IGNORE_WATERLOGGING), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.LABYRINTH_TOWERS_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("labyrinth/tower"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.SORCERER_HUT_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(legacy("sorcerer_hut/sorcerer_hut"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.MOBS_ALCHEMIST_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/alchemist"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.MOBS_ALLAY_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/allay"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.MOBS_ARCHIVIST_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/archivist"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.MOBS_BASHER_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/basher"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.MOBS_EVOKER_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/evoker"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.MOBS_FIRECALLER_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/firecaller"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.MOBS_ILLUSIONER_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/illusioner"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.MOBS_INQUISITOR_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/inquisitor"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.MOBS_LLAMA_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/llama"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.MOBS_MARAUDER_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/marauder"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.MOBS_NECROMANCER_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/necromancer"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.MOBS_PILLAGER_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/pillager"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.MOBS_PROVOKER_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/provoker"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.MOBS_SORCERER_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/sorcerer"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.MOBS_VILLAGER_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/villager"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ModStructures.MOBS_VINDICATOR_TEMPLATE_POOL,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/vindicator"), 1)),
                        StructureTemplatePool.Projection.RIGID));
    }

    public static Function<StructureTemplatePool.Projection, LegacySinglePoolElement> legacy(String id) {
        return SinglePoolElement.legacy(IllagerInvasion.id(id).toString());
    }

    public static Function<StructureTemplatePool.Projection, SinglePoolElement> single(String id) {
        return SinglePoolElement.single(IllagerInvasion.id(id).toString());
    }

    public static Function<StructureTemplatePool.Projection, SinglePoolElement> single(String id, LiquidSettings liquidSettings) {
        return SinglePoolElement.single(IllagerInvasion.id(id).toString(), liquidSettings);
    }
}
