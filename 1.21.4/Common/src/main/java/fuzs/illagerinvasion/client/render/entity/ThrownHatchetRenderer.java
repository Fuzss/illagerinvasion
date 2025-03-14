package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fuzs.illagerinvasion.client.render.entity.state.ThrownHatchetRenderState;
import fuzs.illagerinvasion.world.entity.projectile.ThrownHatchet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.state.ThrownItemRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ThrownHatchetRenderer extends ThrownItemRenderer<ThrownHatchet> {
    private final ItemModelResolver itemModelResolver;

    public ThrownHatchetRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemModelResolver = context.getItemModelResolver();
    }

    @Override
    public void render(ThrownItemRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(1.45F, 1.45F, 1.45F);
        poseStack.mulPose(Axis.YP.rotationDegrees(((ThrownHatchetRenderState) renderState).yRot + 90.0F));
        float rotation = ((ThrownHatchetRenderState) renderState).isInGround ? 1.0F : renderState.ageInTicks;
        poseStack.mulPose(Axis.ZP.rotationDegrees(((ThrownHatchetRenderState) renderState).xRot + 90.0F * rotation));
        poseStack.translate(0.1F, -0.2F, 0.0F);
        renderState.item.render(poseStack, bufferSource, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    @Override
    public ThrownItemRenderState createRenderState() {
        return new ThrownHatchetRenderState();
    }

    @Override
    public void extractRenderState(ThrownHatchet entity, ThrownItemRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        ((ThrownHatchetRenderState) reusedState).yRot = entity.getYRot(partialTick);
        ((ThrownHatchetRenderState) reusedState).xRot = entity.getXRot(partialTick);
        if (entity.isFoil()) {
            ItemStack itemStack = entity.getItem().copy();
            itemStack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
            this.itemModelResolver.updateForNonLiving(reusedState.item, itemStack, ItemDisplayContext.GROUND, entity);
        }
        ((ThrownHatchetRenderState) reusedState).isInGround = entity.isInGround();
    }
}
