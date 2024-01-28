package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.init.ClientModRegistry;
import fuzs.illagerinvasion.client.model.ArmoredIllagerEntityModel;
import fuzs.illagerinvasion.world.entity.monster.Inquisitor;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class InquisitorRender extends MobRenderer<Inquisitor, ArmoredIllagerEntityModel<Inquisitor>> {
    private static final ResourceLocation INQUISITOR_LOCATION = IllagerInvasion.id("textures/entity/inquisitor.png");

    public InquisitorRender(final EntityRendererProvider.Context context) {
        super(context, new ArmoredIllagerEntityModel<>(context.bakeLayer(ClientModRegistry.ARMORED_ILLAGER)), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.model.getHat().visible = false;
    }

    @Override
    protected void scale(final Inquisitor inquisitor, final PoseStack matrixStack, final float f) {
        matrixStack.scale(1.1f, 1.1f, 1.1f);
    }

    @Override
    public ResourceLocation getTextureLocation(final Inquisitor entity) {
        return INQUISITOR_LOCATION;
    }
}
