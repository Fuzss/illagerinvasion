package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.init.ModelLayerLocations;
import fuzs.illagerinvasion.client.render.entity.state.SkullBoltRenderState;
import fuzs.illagerinvasion.world.entity.projectile.SkullBolt;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class SkullBoltRenderer extends EntityRenderer<SkullBolt, SkullBoltRenderState> {
    private static final ResourceLocation TEXTURE_LOCATION = IllagerInvasion.id("textures/entity/skullbolt.png");

    private final SkullModel model;

    public SkullBoltRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new SkullModel(context.bakeLayer(ModelLayerLocations.SKULL_BOLT));
    }

    @Override
    protected int getBlockLightLevel(SkullBolt skullbolt, BlockPos pos) {
        return 15;
    }

    @Override
    public void render(SkullBoltRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        VertexConsumer vertexConsumer = bufferSource.getBuffer(this.model.renderType(TEXTURE_LOCATION));
        this.model.setupAnim(0.0F, renderState.yRot, renderState.xRot);
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(renderState, poseStack, bufferSource, packedLight);
    }

    @Override
    public SkullBoltRenderState createRenderState() {
        return new SkullBoltRenderState();
    }

    @Override
    public void extractRenderState(SkullBolt entity, SkullBoltRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.yRot = entity.getYRot(partialTick);
        reusedState.xRot = entity.getXRot(partialTick);
    }
}
