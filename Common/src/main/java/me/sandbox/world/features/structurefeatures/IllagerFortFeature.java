package me.sandbox.world.features.structurefeatures;

import com.mojang.serialization.Codec;
import me.sandbox.config.IllagerExpansionConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.structure.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.JigsawFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import java.util.Optional;


public class IllagerFortFeature
        extends JigsawFeature {
    public IllagerFortFeature(Codec<JigsawConfiguration> configCodec) {
        super(configCodec, 0, true, true, IllagerFortFeature::canGenerate);
    }

    private static boolean canGenerate(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        ChunkPos chunkPos = context.chunkPos();
        int i = chunkPos.x >> 4;
        int j = chunkPos.z >> 4;
        WorldgenRandom chunkRandom = new WorldgenRandom(new LegacyRandomSource(0L));
        chunkRandom.setSeed((long)(i ^ j << 4) ^ context.seed());
        chunkRandom.nextInt();
        if (chunkRandom.nextInt(5) != 0) {
            return false;
        }
        return !context.chunkGenerator().hasFeatureChunkInRange(BuiltinStructureSets.VILLAGES, context.seed(), chunkPos.x, chunkPos.z, 10);
    }
}

