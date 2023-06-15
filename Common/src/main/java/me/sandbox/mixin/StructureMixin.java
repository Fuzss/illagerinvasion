package me.sandbox.mixin;

import me.sandbox.world.ProcessorRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(StructureTemplate.class)
public class StructureMixin {

    @Inject(
            method = "place(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/structure/StructurePlacementData;Ljava/util/Random;I)Z",
            at = @At(value = "HEAD")
    )
    private void preventAutoWaterlogging(ServerLevelAccessor world, BlockPos pos, BlockPos pivot, StructurePlaceSettings placementData, Random random, int flags, CallbackInfoReturnable<Boolean> cir) {

        if(placementData.getProcessors().stream().anyMatch(processor ->
                ((StructureProcessorAccessor)processor).callGetType() == ProcessorRegistry.NO_WATERLOG_PROCESSOR)) {
            placementData.setKeepLiquids(false);
        }
    }
}