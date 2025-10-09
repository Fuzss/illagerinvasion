package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fuzs.illagerinvasion.client.render.entity.state.FlyingMagmaRenderState;
import fuzs.illagerinvasion.world.entity.projectile.FlyingMagma;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.Blocks;

public class FlyingMagmaRenderer extends EntityRenderer<FlyingMagma, FlyingMagmaRenderState> {
    private final BlockRenderDispatcher blockRenderDispatcher;

    public FlyingMagmaRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5F;
        this.blockRenderDispatcher = context.getBlockRenderDispatcher();
    }

    @Override
    public FlyingMagmaRenderState createRenderState() {
        return new FlyingMagmaRenderState();
    }

    @Override
    public void extractRenderState(FlyingMagma entity, FlyingMagmaRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.xRot = entity.getXRot(partialTick);
        reusedState.yRot = entity.getYRot(partialTick);
    }

    @Override
    public void submit(FlyingMagmaRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot));
        poseStack.mulPose(Axis.ZP.rotationDegrees(renderState.xRot));
        nodeCollector.submitBlock(poseStack,
                Blocks.MAGMA_BLOCK.defaultBlockState(),
                renderState.lightCoords,
                OverlayTexture.NO_OVERLAY,
                renderState.outlineColor);
        poseStack.popPose();
        super.submit(renderState, poseStack, nodeCollector, cameraRenderState);
    }
}
