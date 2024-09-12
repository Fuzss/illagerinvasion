package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fuzs.illagerinvasion.world.entity.projectile.Hatchet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;

public class HatchetRender extends ThrownItemRenderer<Hatchet> {
    private final ItemRenderer itemRenderer;

    public HatchetRender(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(Hatchet hatchet, float f, float g, PoseStack poseStack, MultiBufferSource vertexConsumerProvider, int light) {
        poseStack.scale(1.45f, 1.45f, 1.45f);
        float age = hatchet.getAgeException();
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(g, hatchet.yRotO, hatchet.getYRot()) - 270.0f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(g, hatchet.xRotO, hatchet.getXRot()) + 90.0f * age));
        poseStack.translate(0.1f, -0.2f, 0.0f);
        BakedModel bakedModel = this.itemRenderer.getItemModelShaper().getItemModel(hatchet.getItem());
        this.itemRenderer.render(hatchet.getItem(), ItemDisplayContext.GROUND, false, poseStack, vertexConsumerProvider, light, OverlayTexture.NO_OVERLAY, bakedModel);
        poseStack.popPose();
    }
}
