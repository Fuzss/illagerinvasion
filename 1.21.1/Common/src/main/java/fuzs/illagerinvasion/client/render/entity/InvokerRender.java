package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.init.ClientModRegistry;
import fuzs.illagerinvasion.client.model.InvokerEntityModel;
import fuzs.illagerinvasion.client.render.entity.layers.InvokerGoldLayer;
import fuzs.illagerinvasion.client.render.entity.layers.InvokerShieldLayer;
import fuzs.illagerinvasion.world.entity.monster.Invoker;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;

public class InvokerRender extends MobRenderer<Invoker, InvokerEntityModel<Invoker>> {
    private static final ResourceLocation INVOKER_LOCATION = IllagerInvasion.id("textures/entity/invoker.png");

    public InvokerRender(EntityRendererProvider.Context context) {
        super(context, new InvokerEntityModel<>(context.bakeLayer(ClientModRegistry.CAPED_ILLAGER)), 0.0f);
        this.addLayer(new InvokerGoldLayer<>(this));
        this.addLayer(new InvokerShieldLayer(this, context.getModelSet()));
        this.model.getHat().visible = true;
    }

    @Override
    protected void setupRotations(Invoker entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) {
        super.setupRotations(entity, poseStack, bob, yBodyRot, partialTick, scale);
        float rotation = Mth.sin((entity.tickCount + partialTick) * 0.1F) * Mth.DEG_TO_RAD;
        poseStack.mulPose((new Quaternionf()).rotationZYX(rotation, 0.0F, -rotation));
        poseStack.translate(0.0F, 0.3F + rotation, 0.0F);
    }

    @Override
    public void render(Invoker invoker, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
//        float z = 0.02f * 3.8f % 10;
//        float q = Mth.sin((float) (invoker).tickCount * 0.1f) * ((float) Math.PI / 180);
//        matrixStack.translate(0.0f, 0.3 + q, 0.0f);
        super.render(invoker, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    protected void scale(Invoker livingEntity, PoseStack poseStack, float partialTickTime) {
        super.scale(livingEntity, poseStack, partialTickTime);
        poseStack.scale(0.95f, 0.95f, 0.95f);
    }

    @Override
    public ResourceLocation getTextureLocation(Invoker entity) {
        return INVOKER_LOCATION;
    }
}
