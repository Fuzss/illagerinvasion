package fuzs.illagerinvasion.mixin;


import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.illagerinvasion.world.entity.monster.Archivist;
import fuzs.illagerinvasion.world.entity.monster.Basher;
import fuzs.illagerinvasion.world.entity.monster.Invoker;
import fuzs.illagerinvasion.world.entity.monster.Provoker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.WoodlandMansionPieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.Random;

@Mixin(WoodlandMansionPieces.WoodlandMansionPiece.class)
abstract class WoodlandMansionPiecesMixin {

    @Inject(at = @At("HEAD"), cancellable = true, method = "handleMetadata(Ljava/lang/String;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/ServerWorldAccess;Ljava/util/Random;Lnet/minecraft/util/math/BlockBox;)V")
    public void handleDataMarker(String metadata, BlockPos pos, ServerLevelAccessor world, Random random, BoundingBox boundingBox, CallbackInfo ci) {
        Random randomValue = new Random();
        int value = randomValue.nextInt(8);
        if (metadata.equals("Provoker")) {
            Provoker provoker = ModRegistry.PROVOKER_ENTITY_TYPE.get().create(world.getLevel());
            Objects.requireNonNull(provoker, "provoker is null");
            provoker.setPersistenceRequired();
            provoker.moveTo(pos, 0.0f, 0.0f);
            provoker.finalizeSpawn(world, world.getCurrentDifficultyAt(provoker.blockPosition()), MobSpawnType.STRUCTURE, null, null);
            world.addFreshEntityWithPassengers(provoker);
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
        }
        int value2 = randomValue.nextInt(2);
        if (metadata.equals("Warrior") && value2 == 1) {
            Basher basher = ModRegistry.BASHER_ENTITY_TYPE.get().create(world.getLevel());
            Objects.requireNonNull(basher, "basher is null");
            basher.setPersistenceRequired();
            basher.moveTo(pos, 0.0f, 0.0f);
            basher.finalizeSpawn(world, world.getCurrentDifficultyAt(basher.blockPosition()), MobSpawnType.STRUCTURE, null, null);
            world.addFreshEntityWithPassengers(basher);
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
        }
        if (metadata.equals("Archivist")) {
            Archivist archivist = ModRegistry.ARCHIVIST_ENTITY_TYPE.get().create(world.getLevel());
            Objects.requireNonNull(archivist, "archivist is null");
            archivist.setPersistenceRequired();
            archivist.moveTo(pos, 0.0f, 0.0f);
            archivist.finalizeSpawn(world, world.getCurrentDifficultyAt(archivist.blockPosition()), MobSpawnType.STRUCTURE, null, null);
            world.addFreshEntityWithPassengers(archivist);
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
        }
        if (metadata.equals("invoker")) {
            Invoker invoker = ModRegistry.INVOKER_ENTITY_TYPE.get().create(world.getLevel());
            Objects.requireNonNull(invoker, "invoker is null");
            invoker.setPersistenceRequired();
            invoker.moveTo(pos, 0.0f, 0.0f);
            invoker.finalizeSpawn(world, world.getCurrentDifficultyAt(invoker.blockPosition()), MobSpawnType.STRUCTURE, null, null);
            world.addFreshEntityWithPassengers(invoker);
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
        }
    }
}