package me.sandbox.world;

import com.google.common.collect.ImmutableList;
import fuzs.illagerinvasion.IllagerInvasion;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public class ProcessorRegistry {
    public static StructureProcessorType<NoWaterlogProcessor> NO_WATERLOG_PROCESSOR = () -> NoWaterlogProcessor.CODEC;

    public static final Holder<StructureProcessorList> WATERLOGGED_LIST = ProcessorRegistry.register("waterlogged_processor_list", ImmutableList.of(new NoWaterlogProcessor()));


    public static void registerProcessors() {
        Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(IllagerInvasion.MOD_ID, "waterlog"), NO_WATERLOG_PROCESSOR);
    }
    public static Holder<StructureProcessorList> register(String id, ImmutableList<StructureProcessor> processorList) {
        ResourceLocation identifier = new ResourceLocation(IllagerInvasion.MOD_ID, id);
        StructureProcessorList structureProcessorList = new StructureProcessorList(processorList);
        return BuiltinRegistries.register(BuiltinRegistries.PROCESSOR_LIST, identifier, structureProcessorList);
    }
}
