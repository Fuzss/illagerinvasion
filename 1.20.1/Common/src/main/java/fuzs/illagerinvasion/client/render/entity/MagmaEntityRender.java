package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fuzs.illagerinvasion.world.entity.projectile.FlyingMagma;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.Blocks;

public class MagmaEntityRender extends EntityRenderer<FlyingMagma> {
    private final BlockRenderDispatcher blockRenderDispatcher;

    public MagmaEntityRender(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5F;
        this.blockRenderDispatcher = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(FlyingMagma FlyingMagma, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        matrixStack.pushPose();
        matrixStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(g, FlyingMagma.yRotO, FlyingMagma.getYRot())));
        matrixStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(g, FlyingMagma.xRotO, FlyingMagma.getXRot())));
        this.blockRenderDispatcher.renderSingleBlock(Blocks.MAGMA_BLOCK.defaultBlockState(), matrixStack, vertexConsumerProvider, i, OverlayTexture.NO_OVERLAY);
        matrixStack.popPose();
        super.render(FlyingMagma, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public ResourceLocation getTextureLocation(FlyingMagma FlyingMagma) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
