package fuzs.illagerinvasion.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.util.v1.CodecExtras;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.List;
import java.util.Map;

/**
 * This allows for adding data marker structure blocks for spawning entities and chests into already existing structure
 * templates.
 */
public class DataMarkerStructureProcessor extends StructureProcessor {
    public static final MapCodec<DataMarkerStructureProcessor> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    CodecExtras.mapOf(BlockPos.CODEC.fieldOf("position"), Codec.STRING.fieldOf("data_marker"))
                            .fieldOf("metadata")
                            .forGetter(DataMarkerStructureProcessor::metadata))
            .apply(instance, DataMarkerStructureProcessor::new));

    private final Map<BlockPos, String> metadata;

    public DataMarkerStructureProcessor(Map<BlockPos, String> metadata) {
        this.metadata = metadata;
    }

    private Map<BlockPos, String> metadata() {
        return this.metadata;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModRegistry.DATA_MARKER_STRUCTURE_PROCESSOR.value();
    }

    /**
     * @see net.minecraft.world.level.levelgen.structure.TemplateStructurePiece#postProcess(WorldGenLevel,
     *         StructureManager, ChunkGenerator, RandomSource, BoundingBox, ChunkPos, BlockPos)
     */
    @Override
    public List<StructureTemplate.StructureBlockInfo> finalizeProcessing(ServerLevelAccessor serverLevel, BlockPos offset, BlockPos pos, List<StructureTemplate.StructureBlockInfo> originalBlockInfos, List<StructureTemplate.StructureBlockInfo> processedBlockInfos, StructurePlaceSettings settings) {
        BoundingBox boundingBox = settings.getBoundingBox();
        this.metadata.forEach((BlockPos blockPos, String string) -> {
            blockPos = StructureTemplate.calculateRelativePosition(settings, blockPos).offset(offset);
            if (boundingBox == null || boundingBox.isInside(blockPos)) {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.store("mode", StructureMode.LEGACY_CODEC, StructureMode.DATA);
                compoundTag.putString("metadata", string);
                processedBlockInfos.add(new StructureTemplate.StructureBlockInfo(blockPos,
                        Blocks.STRUCTURE_BLOCK.defaultBlockState().rotate(settings.getRotation()),
                        compoundTag));
            }
        });

        return super.finalizeProcessing(serverLevel, offset, pos, originalBlockInfos, processedBlockInfos, settings);
    }
}
