package fuzs.illagerinvasion.world.level.levelgen.structure.structure.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fuzs.illagerinvasion.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

import java.util.Optional;
import java.util.function.Function;

public class LabyrinthStructure extends Structure {
    private static final int SEA_LEVEL = 63;
    private static final int SAFE_UNDERGROUND_TUNNEL_HEIGHT = 16;
    /**
     * Extending {@link JigsawStructure#CODEC} via {@link Codec#xmap(Function, Function)} always throws a {@link NullPointerException}, so we copy everything.
     */
    public static final Codec<LabyrinthStructure> CODEC = ExtraCodecs.validate(RecordCodecBuilder.mapCodec((instance) -> {
        return instance.group(settingsCodec(instance), StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter((jigsawStructure) -> {
            return jigsawStructure.startPool;
        }), ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter((jigsawStructure) -> {
            return jigsawStructure.startJigsawName;
        }), Codec.intRange(0, 20).fieldOf("size").forGetter((jigsawStructure) -> {
            return jigsawStructure.maxDepth;
        }), HeightProvider.CODEC.fieldOf("start_height").forGetter((jigsawStructure) -> {
            return jigsawStructure.startHeight;
        }), Codec.BOOL.fieldOf("use_expansion_hack").forGetter((jigsawStructure) -> {
            return jigsawStructure.useExpansionHack;
        }), Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter((jigsawStructure) -> {
            return jigsawStructure.projectStartToHeightmap;
        }), Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter((jigsawStructure) -> {
            return jigsawStructure.maxDistanceFromCenter;
        })).apply(instance, LabyrinthStructure::new);
    }), LabyrinthStructure::verifyRange).codec();
    public final Holder<StructureTemplatePool> startPool;
    public final Optional<ResourceLocation> startJigsawName;
    public final int maxDepth;
    public final HeightProvider startHeight;
    public final boolean useExpansionHack;
    public final Optional<Heightmap.Types> projectStartToHeightmap;
    public final int maxDistanceFromCenter;

    public static DataResult<LabyrinthStructure> verifyRange(LabyrinthStructure structure) {
        int i = switch (structure.terrainAdaptation()) {
            case NONE -> 0;
            case BURY, BEARD_THIN, BEARD_BOX -> 12;
        };
        return structure.maxDistanceFromCenter + i > 128 ? DataResult.error(() -> {
            return "Structure size including terrain adaptation must not exceed 128";
        }) : DataResult.success(structure);
    }

    public LabyrinthStructure(Structure.StructureSettings settings, Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName, int maxDepth, HeightProvider startHeight, boolean useExpansionHack, Optional<Heightmap.Types> projectStartToHeightmap, int maxDistanceFromCenter) {
        super(settings);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.maxDepth = maxDepth;
        this.startHeight = startHeight;
        this.useExpansionHack = useExpansionHack;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        return this._findGenerationPoint(context)
                .filter(generationStub -> generationStub.position().getY() <=
                        SEA_LEVEL - SAFE_UNDERGROUND_TUNNEL_HEIGHT);
    }

    public Optional<Structure.GenerationStub> _findGenerationPoint(Structure.GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        int i = this.startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
        BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), i, chunkPos.getMinBlockZ());
        return JigsawPlacement.addPieces(context, this.startPool, this.startJigsawName, this.maxDepth, blockPos, this.useExpansionHack, this.projectStartToHeightmap, this.maxDistanceFromCenter);
    }

    @Override
    public StructureType<?> type() {
        return ModRegistry.LABYRINTH_STRUCTURE_TYPE.get();
    }
}
