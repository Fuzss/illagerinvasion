package fuzs.illagerinvasion.init;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import fuzs.illagerinvasion.IllagerInvasion;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

import java.util.function.Function;

public class ModTemplatePools {
    public static final ResourceKey<StructureTemplatePool> FIRECALLER_HUT_BASE_PLATES = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "firecaller_hut/base_plates");
    public static final ResourceKey<StructureTemplatePool> FIRECALLER_HUT_FEATURES = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "firecaller_hut/features");
    public static final ResourceKey<StructureTemplatePool> FIRECALLER_HUT_HUTS = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "firecaller_hut/huts");
    public static final ResourceKey<StructureTemplatePool> ILLAGER_FORT = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "illager_fort/illager_fort");
    public static final ResourceKey<StructureTemplatePool> ILLUSIONER_TOWER = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "illusioner_tower/illusioner_tower");
    public static final ResourceKey<StructureTemplatePool> LABYRINTH_HALLS = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "labyrinth/halls");
    public static final ResourceKey<StructureTemplatePool> LABYRINTH_ROOMS = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "labyrinth/rooms");
    public static final ResourceKey<StructureTemplatePool> LABYRINTH_TOWERS = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "labyrinth/towers");
    public static final ResourceKey<StructureTemplatePool> SORCERER_HUT = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "sorcerer_hut/sorcerer_hut");
    public static final ResourceKey<StructureTemplatePool> MOBS_ALCHEMIST = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/alchemist");
    public static final ResourceKey<StructureTemplatePool> MOBS_ALLAY = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/allay");
    public static final ResourceKey<StructureTemplatePool> MOBS_ARCHIVIST = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/archivist");
    public static final ResourceKey<StructureTemplatePool> MOBS_BASHER = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/basher");
    public static final ResourceKey<StructureTemplatePool> MOBS_EVOKER = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/evoker");
    public static final ResourceKey<StructureTemplatePool> MOBS_FIRECALLER = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/firecaller");
    public static final ResourceKey<StructureTemplatePool> MOBS_ILLUSIONER = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/illusioner");
    public static final ResourceKey<StructureTemplatePool> MOBS_INQUISITOR = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/inquisitor");
    public static final ResourceKey<StructureTemplatePool> MOBS_LLAMA = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/llama");
    public static final ResourceKey<StructureTemplatePool> MOBS_MARAUDER = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/marauder");
    public static final ResourceKey<StructureTemplatePool> MOBS_NECROMANCER = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/necromancer");
    public static final ResourceKey<StructureTemplatePool> MOBS_PILLAGER = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/pillager");
    public static final ResourceKey<StructureTemplatePool> MOBS_PROVOKER = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/provoker");
    public static final ResourceKey<StructureTemplatePool> MOBS_SORCERER = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/sorcerer");
    public static final ResourceKey<StructureTemplatePool> MOBS_VILLAGER = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/villager");
    public static final ResourceKey<StructureTemplatePool> MOBS_VINDICATOR = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "mobs/vindicator");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        context.register(FIRECALLER_HUT_BASE_PLATES,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(legacy("firecaller_hut/base_plate"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(FIRECALLER_HUT_FEATURES,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("firecaller_hut/feature_crops"), 1),
                                Pair.of(single("firecaller_hut/feature_hay"), 1),
                                Pair.of(single("firecaller_hut/feature_obelisk"), 1),
                                Pair.of(single("firecaller_hut/feature_stable"), 1),
                                Pair.of(single("firecaller_hut/feature_well"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(FIRECALLER_HUT_HUTS,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("firecaller_hut/hut"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ILLAGER_FORT,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("illager_fort/illager_fort"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(ILLUSIONER_TOWER,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(legacy("illusioner_tower/tower_1"), 1),
                                Pair.of(legacy("illusioner_tower/tower_2"), 1),
                                Pair.of(legacy("illusioner_tower/tower_3"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(LABYRINTH_HALLS,
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
        context.register(LABYRINTH_ROOMS,
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
        context.register(LABYRINTH_TOWERS,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("labyrinth/tower"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(SORCERER_HUT,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(legacy("sorcerer_hut/sorcerer_hut"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(MOBS_ALCHEMIST,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/alchemist"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(MOBS_ALLAY,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/allay"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(MOBS_ARCHIVIST,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/archivist"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(MOBS_BASHER,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/basher"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(MOBS_EVOKER,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/evoker"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(MOBS_FIRECALLER,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/firecaller"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(MOBS_ILLUSIONER,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/illusioner"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(MOBS_INQUISITOR,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/inquisitor"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(MOBS_LLAMA,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/llama"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(MOBS_MARAUDER,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/marauder"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(MOBS_NECROMANCER,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/necromancer"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(MOBS_PILLAGER,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/pillager"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(MOBS_PROVOKER,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/provoker"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(MOBS_SORCERER,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/sorcerer"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(MOBS_VILLAGER,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/villager"), 1)),
                        StructureTemplatePool.Projection.RIGID));
        context.register(MOBS_VINDICATOR,
                new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY),
                        ImmutableList.of(Pair.of(single("mobs/vindicator"), 1)),
                        StructureTemplatePool.Projection.RIGID));
    }

    static Function<StructureTemplatePool.Projection, LegacySinglePoolElement> legacy(String id) {
        return SinglePoolElement.legacy(IllagerInvasion.id(id).toString());
    }

    static Function<StructureTemplatePool.Projection, SinglePoolElement> single(String id) {
        return SinglePoolElement.single(IllagerInvasion.id(id).toString());
    }

    static Function<StructureTemplatePool.Projection, SinglePoolElement> single(String id, LiquidSettings liquidSettings) {
        return SinglePoolElement.single(IllagerInvasion.id(id).toString(), liquidSettings);
    }
}
