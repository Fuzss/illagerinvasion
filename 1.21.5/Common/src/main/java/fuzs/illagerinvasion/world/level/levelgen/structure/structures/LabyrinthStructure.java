package fuzs.illagerinvasion.world.level.levelgen.structure.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fuzs.illagerinvasion.init.ModRegistry;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

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
    public static final MapCodec<LabyrinthStructure> CODEC = RecordCodecBuilder.<LabyrinthStructure>mapCodec(
            instance -> instance.group(settingsCodec(instance),
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                    ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name")
                            .forGetter(structure -> structure.startJigsawName), Codec.intRange(0, 20)
                            .fieldOf("size")
                            .forGetter(structure -> structure.maxDepth),
                    HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                    Codec.BOOL.fieldOf("use_expansion_hack").forGetter(structure -> structure.useExpansionHack),
                    Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap")
                            .forGetter(structure -> structure.projectStartToHeightmap), Codec.intRange(1, 128)
                            .fieldOf("max_distance_from_center")
                            .forGetter(structure -> structure.maxDistanceFromCenter), Codec.list(PoolAliasBinding.CODEC)
                            .optionalFieldOf("pool_aliases", List.of())
                            .forGetter(structure -> structure.poolAliases),
                    DimensionPadding.CODEC.optionalFieldOf("dimension_padding", DEFAULT_DIMENSION_PADDING)
                            .forGetter(structure -> structure.dimensionPadding),
                    LiquidSettings.CODEC.optionalFieldOf("liquid_settings", DEFAULT_LIQUID_SETTINGS)
                            .forGetter(structure -> structure.liquidSettings)
            ).apply(instance, LabyrinthStructure::new)).validate(
            labyrinthStructure -> verifyRange(labyrinthStructure).map(LabyrinthStructure.class::cast));

    public LabyrinthStructure(StructureSettings settings, Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName, int maxDepth, HeightProvider startHeight, boolean useExpansionHack, Optional<Heightmap.Types> projectStartToHeightmap, int maxDistanceFromCenter, List<PoolAliasBinding> poolAliases, DimensionPadding dimensionPadding, LiquidSettings liquidSettings) {
        super(settings, startPool, startJigsawName, maxDepth, startHeight, useExpansionHack, projectStartToHeightmap,
                maxDistanceFromCenter, poolAliases, dimensionPadding, liquidSettings
        );
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        return super.findGenerationPoint(context).filter(
                generationStub -> generationStub.position().getY() <= SEA_LEVEL - SAFE_UNDERGROUND_TUNNEL_HEIGHT);
    }

    @Override
    public StructureType<?> type() {
        return ModRegistry.LABYRINTH_STRUCTURE_TYPE.value();
    }
}
