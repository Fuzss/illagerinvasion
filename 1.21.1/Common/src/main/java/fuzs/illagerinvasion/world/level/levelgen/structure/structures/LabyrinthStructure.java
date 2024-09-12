package fuzs.illagerinvasion.world.level.levelgen.structure.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fuzs.illagerinvasion.init.ModRegistry;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class LabyrinthStructure extends JigsawStructure {
    private static final int SEA_LEVEL = 63;
    private static final int SAFE_UNDERGROUND_TUNNEL_HEIGHT = 16;
    /**
     * Extending {@link JigsawStructure#CODEC} via {@link Codec#xmap(Function, Function)} always throws a
     * {@link NullPointerException}, so we copy everything.
     */
    public static final Codec<LabyrinthStructure> CODEC = ExtraCodecs.validate(RecordCodecBuilder.mapCodec((instance) -> {
        return instance.group(settingsCodec(instance),
                StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter((jigsawStructure) -> {
                    return jigsawStructure.startPool;
                }),
                ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter((jigsawStructure) -> {
                    return jigsawStructure.startJigsawName;
                }),
                Codec.intRange(0, 20).fieldOf("size").forGetter((jigsawStructure) -> {
                    return jigsawStructure.maxDepth;
                }),
                HeightProvider.CODEC.fieldOf("start_height").forGetter((jigsawStructure) -> {
                    return jigsawStructure.startHeight;
                }),
                Codec.BOOL.fieldOf("use_expansion_hack").forGetter((jigsawStructure) -> {
                    return jigsawStructure.useExpansionHack;
                }),
                Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter((jigsawStructure) -> {
                    return jigsawStructure.projectStartToHeightmap;
                }),
                Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter((jigsawStructure) -> {
                    return jigsawStructure.maxDistanceFromCenter;
                }),
                Codec.list(PoolAliasBinding.CODEC)
                        .optionalFieldOf("pool_aliases", List.of())
                        .forGetter((jigsawStructure) -> {
                            return jigsawStructure.poolAliases;
                        })
        ).apply(instance, LabyrinthStructure::new);
    }), (LabyrinthStructure structure) -> verifyRange(structure).map(LabyrinthStructure.class::cast)).codec();

    public LabyrinthStructure(Structure.StructureSettings settings, Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName, int maxDepth, HeightProvider startHeight, boolean useExpansionHack, Optional<Heightmap.Types> projectStartToHeightmap, int maxDistanceFromCenter, List<PoolAliasBinding> poolAliases) {
        super(settings,
                startPool,
                startJigsawName,
                maxDepth,
                startHeight,
                useExpansionHack,
                projectStartToHeightmap,
                maxDistanceFromCenter,
                poolAliases
        );
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        return super.findGenerationPoint(context)
                .filter(generationStub -> generationStub.position().getY() <=
                        SEA_LEVEL - SAFE_UNDERGROUND_TUNNEL_HEIGHT);
    }

    @Override
    public StructureType<?> type() {
        return ModRegistry.LABYRINTH_STRUCTURE_TYPE.value();
    }
}
