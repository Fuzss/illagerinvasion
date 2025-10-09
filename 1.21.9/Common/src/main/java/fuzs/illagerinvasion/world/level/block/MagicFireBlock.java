package fuzs.illagerinvasion.world.level.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class MagicFireBlock extends BaseFireBlock {
    public static final MapCodec<MagicFireBlock> CODEC = simpleCodec(MagicFireBlock::new);

    public MagicFireBlock(Properties properties) {
        super(properties, 3.0F);
    }

    @Override
    protected void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity, InsideBlockEffectApplier insideBlockEffectApplier, boolean intersectsPosition) {
        if (!entity.getType().is(EntityTypeTags.ILLAGER_FRIENDS)) {
            int remainingFireTicks = entity.getRemainingFireTicks();
            super.entityInside(blockState, level, blockPos, entity, insideBlockEffectApplier, intersectsPosition);
            insideBlockEffectApplier.runAfter(InsideBlockEffectType.FIRE_IGNITE, (Entity entityX) -> {
                entityX.setRemainingFireTicks(Math.max(20, remainingFireTicks));
            });
        }
    }

    @Override
    protected MapCodec<? extends BaseFireBlock> codec() {
        return CODEC;
    }

    @Override
    protected boolean canBurn(BlockState state) {
        return true;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        return this.canSurvive(state, level, pos) ? super.updateShape(state,
                level,
                scheduledTickAccess,
                pos,
                direction,
                neighborPos,
                neighborState,
                random) : Blocks.AIR.defaultBlockState();
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.scheduleTick(pos, this, getFireTickDelay(level.random));
        if (level.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
            level.removeBlock(pos, false);
        }
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        level.scheduleTick(pos, this, getFireTickDelay(level.random));
    }

    private static int getFireTickDelay(RandomSource random) {
        return 300 + random.nextInt(100);
    }
}

