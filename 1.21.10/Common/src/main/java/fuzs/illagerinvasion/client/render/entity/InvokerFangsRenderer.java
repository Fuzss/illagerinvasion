package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.model.InvokerFangsModel;
import fuzs.illagerinvasion.client.model.geom.ModModelLayers;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EvokerFangsRenderer;
import net.minecraft.client.renderer.entity.state.EvokerFangsRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class InvokerFangsRenderer extends EvokerFangsRenderer {
    private static final ResourceLocation TEXTURE_LOCATION = IllagerInvasion.id("textures/entity/invoker_fangs.png");

    private final InvokerFangsModel model;

    public InvokerFangsRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new InvokerFangsModel(context.bakeLayer(ModModelLayers.INVOKER_FANGS));
    }

    @Override
    public void submit(EvokerFangsRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        if (renderState.biteProgress != 0.0F) {
            poseStack.pushPose();
            poseStack.scale(1.15F, 1.15F, 1.15F);
            poseStack.mulPose(Axis.YP.rotationDegrees(90.0F - renderState.yRot));
            float scale = 2.0F * (renderState.biteProgress > 0.9F ? ((1.0F - renderState.biteProgress) / 0.1F) : 1.0F);
            poseStack.scale(-scale, -scale, scale);
            poseStack.translate(0.0F, -0.626F, 0.0F);
            poseStack.scale(0.5F, 0.5F, 0.5F);
            nodeCollector.submitModel(this.model,
                    renderState,
                    poseStack,
                    this.model.renderType(TEXTURE_LOCATION),
                    renderState.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    renderState.outlineColor,
                    null);
            poseStack.popPose();
            super.submit(renderState, poseStack, nodeCollector, cameraRenderState);
        }
    }
}
