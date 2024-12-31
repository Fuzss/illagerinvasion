package fuzs.illagerinvasion.mixin;


import fuzs.illagerinvasion.init.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.structures.WoodlandMansionPieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(WoodlandMansionPieces.WoodlandMansionPiece.class)
abstract class WoodlandMansionPieceMixin {

    @ModifyVariable(method = "handleDataMarker", at = @At("STORE"))
    public List<Mob> handleDataMarker(List<Mob> list, String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {
        EntityType<? extends Mob> entityType = switch (name) {
            case "Provoker" -> ModEntityTypes.PROVOKER_ENTITY_TYPE.value();
            case "Warrior" -> random.nextInt(2) == 0 ? ModEntityTypes.BASHER_ENTITY_TYPE.value() : null;
            case "Archivist" -> ModEntityTypes.ARCHIVIST_ENTITY_TYPE.value();
            case "invoker" -> ModEntityTypes.INVOKER_ENTITY_TYPE.value();
            default -> null;
        };
        if (entityType != null) {
            list.add(entityType.create(level.getLevel(), EntitySpawnReason.STRUCTURE));
        }
        return list;
    }
}