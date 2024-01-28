package fuzs.illagerinvasion.world.item;

import fuzs.illagerinvasion.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.FireChargeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.gameevent.GameEvent;

public class MagicalFireChargeItem extends FireChargeItem {

    public MagicalFireChargeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        boolean bl = false;
        blockPos = blockPos.relative(context.getClickedFace());
        if (ModRegistry.MAGIC_FIRE_BLOCK.get().defaultBlockState().canSurvive(level, blockPos) && BaseFireBlock.canBePlacedAt(level, blockPos, context.getHorizontalDirection())) {
            this.playSound(level, blockPos);
            level.setBlockAndUpdate(blockPos, ModRegistry.MAGIC_FIRE_BLOCK.get().defaultBlockState());
            level.gameEvent(context.getPlayer(), GameEvent.BLOCK_PLACE, blockPos);
            bl = true;
        }

        if (bl) {
            context.getItemInHand().shrink(1);
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return InteractionResult.FAIL;
        }
    }

    private void playSound(Level level, BlockPos pos) {
        RandomSource randomSource = level.getRandom();
        level.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, (randomSource.nextFloat() - randomSource.nextFloat()) * 0.2F + 1.0F);
    }
}
