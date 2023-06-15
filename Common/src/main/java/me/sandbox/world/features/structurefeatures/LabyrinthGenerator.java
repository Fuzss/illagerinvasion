package me.sandbox.world.features.structurefeatures;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import me.sandbox.world.ProcessorRegistry;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class LabyrinthGenerator {
    public static final Holder<StructureTemplatePool> STRUCTURE_POOLS = Pools.register(new StructureTemplatePool(new ResourceLocation("illagerexp:labyrinth_entrance_pool"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.single("illagerexp:labyrinth/labyrinth_entrance", ProcessorRegistry.WATERLOGGED_LIST), 1)), StructureTemplatePool.Projection.RIGID));

    public static void init() {
    }
}
