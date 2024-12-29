package fuzs.illagerinvasion.world.level.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class MagicFireBlock extends BaseFireBlock {
    public static final MapCodec<MagicFireBlock> CODEC = simpleCodec(MagicFireBlock::new);

    public MagicFireBlock(Properties properties) {
        super(properties, 3.0F);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!entity.getType().is(EntityTypeTags.ILLAGER_FRIENDS)) {
            entity.hurt(level.damageSources().inFire(), 3.0F);
            entity.igniteForTicks(0);
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
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        return this.canSurvive(state, level, currentPos) ? super.updateShape(state, direction, neighborState, level, currentPos, neighborPos) : Blocks.AIR.defaultBlockState();
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

