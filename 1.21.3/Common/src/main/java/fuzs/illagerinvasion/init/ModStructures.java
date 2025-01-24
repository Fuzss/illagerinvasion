package fuzs.illagerinvasion.init;

import fuzs.illagerinvasion.world.level.levelgen.structure.structures.LabyrinthStructure;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class ModStructures {
    public static final Holder.Reference<StructureType<LabyrinthStructure>> LABYRINTH_STRUCTURE_TYPE = ModRegistry.REGISTRIES.register(
            Registries.STRUCTURE_TYPE,
            "labyrinth",
            () -> () -> LabyrinthStructure.CODEC);
    public static final ResourceKey<Structure> FIRECALLER_HUT_STRUCTURE = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.STRUCTURE,
            "firecaller_hut");
    public static final ResourceKey<Structure> ILLAGER_FORT_STRUCTURE = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.STRUCTURE,
            "illager_fort");
    public static final ResourceKey<Structure> ILLUSIONER_TOWER_STRUCTURE = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.STRUCTURE,
            "illusioner_tower");
    public static final ResourceKey<Structure> LABYRINTH_STRUCTURE = ModRegistry.REGISTRIES.makeResourceKey(Registries.STRUCTURE,
            "labyrinth");
    public static final ResourceKey<Structure> SORCERER_HUT_STRUCTURE = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.STRUCTURE,
            "sorcerer_hut");
    public static final ResourceKey<StructureSet> FIRECALLER_HUT_STRUCTURE_SET = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.STRUCTURE_SET,
            "firecaller_hut");
    public static final ResourceKey<StructureSet> ILLAGER_FORT_STRUCTURE_SET = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.STRUCTURE_SET,
            "illager_fort");
    public static final ResourceKey<StructureSet> ILLUSIONER_TOWER_STRUCTURE_SET = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.STRUCTURE_SET,
            "illusioner_tower");
    public static final ResourceKey<StructureSet> LABYRINTH_STRUCTURE_SET = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.STRUCTURE_SET,
            "labyrinth");
    public static final ResourceKey<StructureSet> SORCERER_HUT_STRUCTURE_SET = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.STRUCTURE_SET,
            "sorcerer_hut");
    public static final ResourceKey<StructureTemplatePool> FIRECALLER_HUT_BASE_PLATES_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "firecaller_hut/base_plates");
    public static final ResourceKey<StructureTemplatePool> FIRECALLER_HUT_FEATURES_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "firecaller_hut/features");
    public static final ResourceKey<StructureTemplatePool> FIRECALLER_HUT_HUTS_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "firecaller_hut/huts");
    public static final ResourceKey<StructureTemplatePool> ILLAGER_FORT_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "illager_fort/illager_fort");
    public static final ResourceKey<StructureTemplatePool> ILLUSIONER_TOWER_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "illusioner_tower/illusioner_tower");
    public static final ResourceKey<StructureTemplatePool> LABYRINTH_HALLS_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "labyrinth/halls");
    public static final ResourceKey<StructureTemplatePool> LABYRINTH_ROOMS_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "labyrinth/rooms");
    public static final ResourceKey<StructureTemplatePool> LABYRINTH_TOWERS_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "labyrinth/towers");
    public static final ResourceKey<StructureTemplatePool> SORCERER_HUT_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "sorcerer_hut/sorcerer_hut");
    public static final ResourceKey<StructureTemplatePool> MOBS_ALCHEMIST_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/alchemist");
    public static final ResourceKey<StructureTemplatePool> MOBS_ALLAY_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/allay");
    public static final ResourceKey<StructureTemplatePool> MOBS_ARCHIVIST_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/archivist");
    public static final ResourceKey<StructureTemplatePool> MOBS_BASHER_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/basher");
    public static final ResourceKey<StructureTemplatePool> MOBS_EVOKER_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/evoker");
    public static final ResourceKey<StructureTemplatePool> MOBS_FIRECALLER_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/firecaller");
    public static final ResourceKey<StructureTemplatePool> MOBS_ILLUSIONER_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/illusioner");
    public static final ResourceKey<StructureTemplatePool> MOBS_INQUISITOR_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/inquisitor");
    public static final ResourceKey<StructureTemplatePool> MOBS_LLAMA_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/llama");
    public static final ResourceKey<StructureTemplatePool> MOBS_MARAUDER_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/marauder");
    public static final ResourceKey<StructureTemplatePool> MOBS_NECROMANCER_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/necromancer");
    public static final ResourceKey<StructureTemplatePool> MOBS_PILLAGER_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/pillager");
    public static final ResourceKey<StructureTemplatePool> MOBS_PROVOKER_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/provoker");
    public static final ResourceKey<StructureTemplatePool> MOBS_SORCERER_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/sorcerer");
    public static final ResourceKey<StructureTemplatePool> MOBS_VILLAGER_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/villager");
    public static final ResourceKey<StructureTemplatePool> MOBS_VINDICATOR_TEMPLATE_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/vindicator");

    public static void bootstrap() {
        // NO-OP
    }
}
