package fuzs.illagerinvasion.mixin;

import fuzs.illagerinvasion.init.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.level.levelgen.PatrolSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PatrolSpawner.class)
abstract class PatrolSpawnerMixin {

    @ModifyVariable(method = "spawnPatrolMember", at = @At("STORE"), ordinal = 0)
    private PatrollingMonster spawnPatrolMember(PatrollingMonster patrollingMonster, ServerLevel serverLevel, BlockPos pos, RandomSource random, boolean leader) {
        return random.nextInt(2) == 0 ?
                ModEntityTypes.MARAUDER_ENTITY_TYPE.value().create(serverLevel, EntitySpawnReason.PATROL) :
                patrollingMonster;
    }
}
