package fuzs.illagerinvasion.world.level.block;

import fuzs.illagerinvasion.world.inventory.ImbuingMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ImbuingTableBlock extends Block {
    private static final Component CONTAINER_TITLE = Component.translatable("container.imbue");

    public ImbuingTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(world, pos));
            return InteractionResult.CONSUME;
        }
    }

    @Override
    @Nullable
    public MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
        return new SimpleMenuProvider((syncId, inventory, player) -> new ImbuingMenu(syncId, inventory, ContainerLevelAccess.create(world, pos)), CONTAINER_TITLE);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        for (int i = 0; i < 3; i++) {
            double speedX = random.nextGaussian() * 0.2;
            double speedZ = random.nextGaussian() * 0.2;
            double x = random.nextGaussian();
            double z = random.nextGaussian();
            speedX = Math.abs(speedX) * -Math.signum(x);
            speedZ = Math.abs(speedZ) * -Math.signum(z);
            level.addParticle(ParticleTypes.ENCHANT, pos.getX() + 0.5 + x, pos.getY() + 1.0 + Math.abs(random.nextGaussian()), pos.getZ() + 0.5 + z, speedX, Math.abs(random.nextGaussian()) * 0.05, speedZ);
        }
    }
}
