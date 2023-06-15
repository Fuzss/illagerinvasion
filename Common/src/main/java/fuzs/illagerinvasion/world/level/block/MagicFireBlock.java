package fuzs.illagerinvasion.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MagicFireBlock extends BaseFireBlock {

    public MagicFireBlock(Properties properties) {
        super(properties, 3.0f);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!(entity instanceof AbstractIllager || entity instanceof Ravager)) {
            entity.hurt(level.damageSources().inFire(), 3.0F);
            entity.setSecondsOnFire(0);
        }
    }

    @Override
    protected boolean canBurn(BlockState state) {
        return true;
    }
}

