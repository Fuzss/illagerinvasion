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

public class InvokerRender extends MobRenderer<Invoker, InvokerEntityModel<Invoker>> {
    private static final ResourceLocation INVOKER_LOCATION = IllagerInvasion.id("textures/entity/invoker.png");

    public InvokerRender(EntityRendererProvider.Context context) {
        super(context, new InvokerEntityModel<>(context.bakeLayer(ClientModRegistry.CAPED_ILLAGER)), 0.0f);
        this.addLayer(new InvokerGoldLayer<>(this));
        this.addLayer(new InvokerShieldLayer(this, context.getModelSet()));
        this.model.getHat().visible = true;
    }

    @Override
    public void render(Invoker invoker, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        float z = 0.02f * 3.8f % 10;
        float q = Mth.sin((float) (invoker).tickCount * z) * 1.5f * ((float) Math.PI / 180);
        matrixStack.translate(0.0f, 0.3 + q, 0.0f);
        matrixStack.scale(0.95f, 0.95f, 0.95f);
        super.render(invoker, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public ResourceLocation getTextureLocation(Invoker entity) {
        return INVOKER_LOCATION;
    }
}
