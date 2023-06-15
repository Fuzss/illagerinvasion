package me.sandbox.mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.sandbox.entity.monster.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.PatrolSpawner;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Random;
import org.spongepowered.asm.mixin.Mixin;

@Mixin( PatrolSpawner.class)
public abstract class MarauderPatrolMixin
{
    @Inject(at = { @At("HEAD") }, cancellable = true, method = { "spawnPillager(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;Z)Z" })
    public void spawnMarauder(final ServerLevel world, final BlockPos pos, final Random random, final boolean captain, final CallbackInfoReturnable<Boolean> cir) {
        final BlockState bs = world.getBlockState(pos);
        if (!NaturalSpawner.isValidEmptySpawnBlock(world, pos, bs, bs.getFluidState(), EntityType.PILLAGER)) {
            cir.cancel();
        }
        if (!PatrollingMonster.checkPatrollingMonsterSpawnRules(EntityType.PILLAGER, world, MobSpawnType.PATROL, pos, random)) {
            cir.cancel();
        }
        final int randvalue = random.nextInt(2);
        if (randvalue == 0) {
            final PatrollingMonster marauder = EntityRegistry.MARAUDER.create(world);
            if (marauder != null) {
                if (captain) {
                    marauder.setPatrolLeader(true);
                    marauder.findPatrolTarget();
                }
                marauder.setPos(pos.getX(), pos.getY(), pos.getZ());
                marauder.finalizeSpawn(world, world.getCurrentDifficultyAt(pos), MobSpawnType.PATROL, null, null);
                world.addFreshEntityWithPassengers(marauder);
            }
        }
    }
}
