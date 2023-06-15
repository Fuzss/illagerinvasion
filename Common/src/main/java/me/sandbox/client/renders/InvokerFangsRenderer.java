package me.sandbox.client.renders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.init.ClientModRegistry;
import me.sandbox.client.model.InvokerFangsModel;
import me.sandbox.entity.monster.InvokerFangs;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class InvokerFangsRenderer extends EntityRenderer<InvokerFangs> {
    private static final ResourceLocation INVOKER_FANGS_LOCATION = IllagerInvasion.id("textures/entity/invoker_fangs.png");

    private final InvokerFangsModel<InvokerFangs> model;

    public InvokerFangsRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new InvokerFangsModel<>(context.bakeLayer(ClientModRegistry.INVOKER_FANGS));
    }

    @Override
    public void render(InvokerFangs invokerFangs, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        float animationProgress = invokerFangs.getAnimationProgress(g);
        if (animationProgress == 0.0f) {
            return;
        }
        float j = 2.0f;
        if (animationProgress > 0.9f) {
            j = (float) ((double) j * ((1.0 - (double) animationProgress) / (double) 0.1f));
        }
        matrixStack.pushPose();
        matrixStack.scale(1.15f, 1.15f, 1.15f);
        matrixStack.mulPose(Axis.YP.rotationDegrees(90.0f - invokerFangs.getYRot()));
        matrixStack.scale(-j, -j, j);
        float k = 0.03125f;
        matrixStack.translate(0.0, -0.626f, 0.0);
        matrixStack.scale(0.5f, 0.5f, 0.5f);
        this.model.setupAnim(invokerFangs, animationProgress, 0.0f, 0.0f, invokerFangs.getYRot(), invokerFangs.getXRot());
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.renderType(this.getTextureLocation(invokerFangs)));
        this.model.renderToBuffer(matrixStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.popPose();
        super.render(invokerFangs, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public ResourceLocation getTextureLocation(InvokerFangs InvokerFangs) {
        return INVOKER_FANGS_LOCATION;
    }
}
