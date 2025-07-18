package fuzs.illagerinvasion.init;

import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;

import java.util.List;
import java.util.Optional;

public class ModStructureSets {
    public static final ResourceKey<StructureSet> FIRECALLER_HUT = ModRegistry.REGISTRIES.makeResourceKey(Registries.STRUCTURE_SET,
            "firecaller_hut");
    public static final ResourceKey<StructureSet> ILLAGER_FORT = ModRegistry.REGISTRIES.makeResourceKey(Registries.STRUCTURE_SET,
            "illager_fort");
    public static final ResourceKey<StructureSet> ILLUSIONER_TOWER = ModRegistry.REGISTRIES.makeResourceKey(Registries.STRUCTURE_SET,
            "illusioner_tower");
    public static final ResourceKey<StructureSet> LABYRINTH = ModRegistry.REGISTRIES.makeResourceKey(Registries.STRUCTURE_SET,
            "labyrinth");
    public static final ResourceKey<StructureSet> SORCERER_HUT = ModRegistry.REGISTRIES.makeResourceKey(Registries.STRUCTURE_SET,
            "sorcerer_hut");

    public static void bootstrap(BootstrapContext<StructureSet> context) {
        context.register(FIRECALLER_HUT,
                new StructureSet(List.of(StructureSet.entry(context.lookup(Registries.STRUCTURE)
                        .getOrThrow(ModStructures.FIRECALLER_HUT))),
                        new RandomSpreadStructurePlacement(Vec3i.ZERO,
                                StructurePlacement.FrequencyReductionMethod.DEFAULT,
                                1.0F,
                                169476076,
                                Optional.empty(),
                                42,
                                28,
                                RandomSpreadType.LINEAR)));
        context.register(ILLAGER_FORT,
                new StructureSet(List.of(StructureSet.entry(context.lookup(Registries.STRUCTURE)
                        .getOrThrow(ModStructures.ILLAGER_FORT))),
                        new RandomSpreadStructurePlacement(Vec3i.ZERO,
                                StructurePlacement.FrequencyReductionMethod.LEGACY_TYPE_1,
                                0.2F,
                                169476082,
                                Optional.of(new StructurePlacement.ExclusionZone(context.lookup(Registries.STRUCTURE_SET)
                                        .getOrThrow(BuiltinStructureSets.VILLAGES), 10)),
                                42,
                                28,
                                RandomSpreadType.LINEAR)));
        context.register(ILLUSIONER_TOWER,
                new StructureSet(List.of(StructureSet.entry(context.lookup(Registries.STRUCTURE)
                        .getOrThrow(ModStructures.ILLUSIONER_TOWER))),
                        new RandomSpreadStructurePlacement(Vec3i.ZERO,
                                StructurePlacement.FrequencyReductionMethod.DEFAULT,
                                1.0F,
                                169476081,
                                Optional.of(new StructurePlacement.ExclusionZone(context.lookup(Registries.STRUCTURE_SET)
                                        .getOrThrow(BuiltinStructureSets.VILLAGES), 10)),
                                42,
                                28,
                                RandomSpreadType.LINEAR)));
        context.register(LABYRINTH,
                new StructureSet(List.of(StructureSet.entry(context.lookup(Registries.STRUCTURE)
                        .getOrThrow(ModStructures.LABYRINTH))),
                        new RandomSpreadStructurePlacement(Vec3i.ZERO,
                                StructurePlacement.FrequencyReductionMethod.DEFAULT,
                                1.0F,
                                169476079,
                                Optional.of(new StructurePlacement.ExclusionZone(context.lookup(Registries.STRUCTURE_SET)
                                        .getOrThrow(BuiltinStructureSets.VILLAGES), 10)),
                                42,
                                28,
                                RandomSpreadType.LINEAR)));
        context.register(SORCERER_HUT,
                new StructureSet(List.of(StructureSet.entry(context.lookup(Registries.STRUCTURE)
                        .getOrThrow(ModStructures.SORCERER_HUT))),
                        new RandomSpreadStructurePlacement(Vec3i.ZERO,
                                StructurePlacement.FrequencyReductionMethod.DEFAULT,
                                1.0F,
                                169476080,
                                Optional.empty(),
                                18,
                                14,
                                RandomSpreadType.LINEAR)));
    }
}
