package fuzs.illagerinvasion.util;

import fuzs.illagerinvasion.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class SetMagicFireUtil {

    public static void trySetFire(LivingEntity entity, Level level) {
        BlockPos pos = entity.blockPosition();
        if (magicFireCanReplace(level.getBlockState(pos.below()).getBlock())) {
            return;
        }
        for (BlockPos otherPos : BlockPos.withinManhattan(pos, 1, 1, 1)) {
            if (magicFireCanReplace(level.getBlockState(otherPos).getBlock())) {
                level.setBlockAndUpdate(otherPos, ModRegistry.MAGIC_FIRE_BLOCK.get().defaultBlockState());
            }
        }
    }

    private static boolean magicFireCanReplace(Block block) {
        return block == Blocks.AIR || block == Blocks.GRASS || block == Blocks.FERN || block == Blocks.TALL_GRASS;
    }
}
