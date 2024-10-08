package fuzs.illagerinvasion.mixin;


import fuzs.illagerinvasion.init.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.structures.WoodlandMansionPieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WoodlandMansionPieces.WoodlandMansionPiece.class)
abstract class WoodlandMansionPieceMixin {

    @Inject(method = "handleDataMarker", at = @At("HEAD"), cancellable = true)
    public void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box, CallbackInfo callback) {
        EntityType<? extends Mob> entityType = switch (name) {
            case "Provoker" -> ModEntityTypes.PROVOKER_ENTITY_TYPE.value();
            case "Warrior" -> random.nextInt(2) == 0 ? ModEntityTypes.BASHER_ENTITY_TYPE.value() : null;
            case "Archivist" -> ModEntityTypes.ARCHIVIST_ENTITY_TYPE.value();
            case "invoker" -> ModEntityTypes.INVOKER_ENTITY_TYPE.value();
            default -> null;
        };
        if (entityType != null) {
            Mob mob = entityType.create(level.getLevel());
            mob.setPersistenceRequired();
            mob.moveTo(pos, 0.0f, 0.0f);
            mob.finalizeSpawn(level, level.getCurrentDifficultyAt(mob.blockPosition()), MobSpawnType.STRUCTURE, null);
            level.addFreshEntityWithPassengers(mob);
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
            callback.cancel();
        }
    }
}