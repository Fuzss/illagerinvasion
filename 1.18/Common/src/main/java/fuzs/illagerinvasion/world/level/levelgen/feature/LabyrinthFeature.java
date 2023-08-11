package fuzs.illagerinvasion.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.JigsawFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;

public class LabyrinthFeature extends JigsawFeature {

    public LabyrinthFeature(Codec<JigsawConfiguration> codec) {
        super(codec, -40, true, true, LabyrinthFeature::checkLocation);
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    private static boolean checkLocation(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        ChunkPos chunkpos = context.chunkPos();
        int i = chunkpos.x >> 4;
        int j = chunkpos.z >> 4;
        WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(0L));
        worldgenrandom.setSeed((long)(i ^ j << 4) ^ context.seed());
        worldgenrandom.nextInt();
        if (worldgenrandom.nextInt(5) != 0) {
            return false;
        } else {
            return !context.chunkGenerator().hasFeatureChunkInRange(BuiltinStructureSets.VILLAGES, context.seed(), chunkpos.x, chunkpos.z, 10);
        }
    }
}
