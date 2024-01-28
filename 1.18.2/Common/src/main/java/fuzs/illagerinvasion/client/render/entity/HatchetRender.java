package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import fuzs.illagerinvasion.world.entity.projectile.Hatchet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;

public class HatchetRender extends ThrownItemRenderer<Hatchet> {
    private final ItemRenderer itemRenderer;

    public HatchetRender(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();

    }

    @Override
    public void render(Hatchet hatchet, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int light) {
        matrixStack.scale(1.45f, 1.45f, 1.45f);
        float age = hatchet.getAgeException();
        matrixStack.pushPose();
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(g, hatchet.yRotO, hatchet.getYRot()) - 270.0f));
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(g, hatchet.xRotO, hatchet.getXRot()) + 90.0f * age));
        matrixStack.translate(0.1f, -0.2f, 0.0f);
        BakedModel bakedModel = this.itemRenderer.getItemModelShaper().getItemModel(hatchet.getItem());
        this.itemRenderer.render(hatchet.getItem(), ItemTransforms.TransformType.GROUND, false, matrixStack, vertexConsumerProvider, light, OverlayTexture.NO_OVERLAY, bakedModel);
        matrixStack.popPose();
    }
}
