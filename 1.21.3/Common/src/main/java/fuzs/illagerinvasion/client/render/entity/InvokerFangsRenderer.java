package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.init.ModelLayerLocations;
import fuzs.illagerinvasion.client.model.InvokerFangsModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EvokerFangsRenderer;
import net.minecraft.client.renderer.entity.state.EvokerFangsRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class InvokerFangsRenderer extends EvokerFangsRenderer {
    private static final ResourceLocation TEXTURE_LOCATION = IllagerInvasion.id("textures/entity/invoker_fangs.png");

    private final InvokerFangsModel model;

    public InvokerFangsRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new InvokerFangsModel(context.bakeLayer(ModelLayerLocations.INVOKER_FANGS));
    }

    @Override
    public void render(EvokerFangsRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        float biteProgress = renderState.biteProgress;
        if (biteProgress != 0.0F) {
            poseStack.pushPose();
            poseStack.scale(1.15F, 1.15F, 1.15F);
            poseStack.mulPose(Axis.YP.rotationDegrees(90.0F - renderState.yRot));
            float biteProgressScale = 2.0F * (biteProgress > 0.9F ? ((1.0F - biteProgress) / 0.1F) : 1.0F);
            poseStack.scale(-biteProgressScale, -biteProgressScale, biteProgressScale);
            poseStack.translate(0.0F, -0.626F, 0.0F);
            poseStack.scale(0.5F, 0.5F, 0.5F);
            this.model.setupAnim(renderState);
            VertexConsumer vertexConsumer = bufferSource.getBuffer(this.model.renderType(TEXTURE_LOCATION));
            this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
            super.render(renderState, poseStack, bufferSource, packedLight);
        }
    }
}
