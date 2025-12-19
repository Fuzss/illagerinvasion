package fuzs.illagerinvasion.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ModStructures {
    public static final ResourceKey<Structure> FIRECALLER_HUT = ModRegistry.REGISTRIES.makeResourceKey(Registries.STRUCTURE,
            "firecaller_hut");
    public static final ResourceKey<Structure> ILLAGER_FORT = ModRegistry.REGISTRIES.makeResourceKey(Registries.STRUCTURE,
            "illager_fort");
    public static final ResourceKey<Structure> ILLUSIONER_TOWER = ModRegistry.REGISTRIES.makeResourceKey(Registries.STRUCTURE,
            "illusioner_tower");
    public static final ResourceKey<Structure> LABYRINTH = ModRegistry.REGISTRIES.makeResourceKey(Registries.STRUCTURE,
            "labyrinth");
    public static final ResourceKey<Structure> SORCERER_HUT = ModRegistry.REGISTRIES.makeResourceKey(Registries.STRUCTURE,
            "sorcerer_hut");

    public static void bootstrap(BootstrapContext<Structure> context) {
        context.register(FIRECALLER_HUT,
                new JigsawStructure(new Structure.StructureSettings.Builder(context.lookup(Registries.BIOME)
                        .getOrThrow(ModTags.HAS_FIRECALLER_HUT_BIOME_TAG)).generationStep(GenerationStep.Decoration.SURFACE_STRUCTURES)
                        .terrainAdapation(TerrainAdjustment.BEARD_THIN)
                        .build(),
                        context.lookup(Registries.TEMPLATE_POOL)
                                .getOrThrow(ModTemplatePools.FIRECALLER_HUT_BASE_PLATES),
                        3,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG));
        context.register(ILLAGER_FORT,
                new JigsawStructure(new Structure.StructureSettings.Builder(context.lookup(Registries.BIOME)
                        .getOrThrow(ModTags.HAS_ILLAGER_FORT_BIOME_TAG)).spawnOverrides(Map.of(MobCategory.MONSTER,
                                new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE,
                                        WeightedList.<MobSpawnSettings.SpawnerData>builder()
                                                .add(new MobSpawnSettings.SpawnerData(EntityType.PILLAGER, 1, 1), 3)
                                                .add(new MobSpawnSettings.SpawnerData(ModEntityTypes.MARAUDER_ENTITY_TYPE.value(),
                                                        1,
                                                        1), 3)
                                                .add(new MobSpawnSettings.SpawnerData(ModEntityTypes.BASHER_ENTITY_TYPE.value(),
                                                        1,
                                                        1), 1)
                                                .add(new MobSpawnSettings.SpawnerData(ModEntityTypes.PROVOKER_ENTITY_TYPE.value(),
                                                        1,
                                                        1), 3)
                                                .build())))
                        .generationStep(GenerationStep.Decoration.SURFACE_STRUCTURES)
                        .terrainAdapation(TerrainAdjustment.BEARD_THIN)
                        .build(),
                        context.lookup(Registries.TEMPLATE_POOL).getOrThrow(ModTemplatePools.ILLAGER_FORT),
                        2,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG));
        context.register(ILLUSIONER_TOWER,
                new JigsawStructure(new Structure.StructureSettings.Builder(context.lookup(Registries.BIOME)
                        .getOrThrow(ModTags.HAS_ILLUSIONER_TOWER_BIOME_TAG)).generationStep(GenerationStep.Decoration.SURFACE_STRUCTURES)
                        .terrainAdapation(TerrainAdjustment.BEARD_THIN)
                        .build(),
                        context.lookup(Registries.TEMPLATE_POOL).getOrThrow(ModTemplatePools.ILLUSIONER_TOWER),
                        2,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG));
        context.register(LABYRINTH,
                new JigsawStructure(new Structure.StructureSettings.Builder(context.lookup(Registries.BIOME)
                        .getOrThrow(ModTags.HAS_LABYRINTH_BIOME_TAG)).generationStep(GenerationStep.Decoration.SURFACE_STRUCTURES)
                        .terrainAdapation(TerrainAdjustment.BURY)
                        .build(),
                        context.lookup(Registries.TEMPLATE_POOL).getOrThrow(ModTemplatePools.LABYRINTH_TOWERS),
                        Optional.empty(),
                        7,
                        ConstantHeight.of(VerticalAnchor.absolute(-40)),
                        false,
                        Optional.of(Heightmap.Types.WORLD_SURFACE_WG),
                        new JigsawStructure.MaxDistance(80),
                        List.of(),
                        JigsawStructure.DEFAULT_DIMENSION_PADDING,
                        LiquidSettings.IGNORE_WATERLOGGING));
        context.register(SORCERER_HUT,
                new JigsawStructure(new Structure.StructureSettings.Builder(context.lookup(Registries.BIOME)
                        .getOrThrow(ModTags.HAS_SORCERER_HUT_BIOME_TAG)).generationStep(GenerationStep.Decoration.SURFACE_STRUCTURES)
                        .terrainAdapation(TerrainAdjustment.BEARD_THIN)
                        .build(),
                        context.lookup(Registries.TEMPLATE_POOL).getOrThrow(ModTemplatePools.SORCERER_HUT),
                        2,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG));
    }
}
