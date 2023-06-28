package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.init.ClientModRegistry;
import fuzs.illagerinvasion.client.model.HatIllagerEntityModel;
import fuzs.illagerinvasion.world.entity.monster.Sorcerer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SorcererRender extends MobRenderer<Sorcerer, HatIllagerEntityModel<Sorcerer>> {
    private static final ResourceLocation SORCERER_LOCATION = IllagerInvasion.id("textures/entity/sorcerer.png");

    public SorcererRender(EntityRendererProvider.Context context) {
        super(context, new HatIllagerEntityModel<>(context.bakeLayer(ClientModRegistry.HAT_ILLAGER)), 0.5f);
        this.model.getHat().visible = true;
    }

    @Override
    protected void scale(final Sorcerer sorcerer, final PoseStack matrixStack, final float f) {
        matrixStack.scale(0.95f, 0.95f, 0.95f);
    }

    @Override
    public ResourceLocation getTextureLocation(Sorcerer entity) {
        return SORCERER_LOCATION;
    }
}
