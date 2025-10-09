package fuzs.illagerinvasion.client.render.entity;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.model.geom.ModModelLayers;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WitherSkullRenderer;
import net.minecraft.client.renderer.entity.state.WitherSkullRenderState;
import net.minecraft.resources.ResourceLocation;

public class SkullBoltRenderer extends WitherSkullRenderer {
    private static final ResourceLocation TEXTURE_LOCATION = IllagerInvasion.id("textures/entity/skullbolt.png");

    private final SkullModel model;

    public SkullBoltRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new SkullModel(context.bakeLayer(ModModelLayers.SKULL_BOLT));
    }

    @Override
    protected ResourceLocation getTextureLocation(WitherSkullRenderState renderState) {
        return TEXTURE_LOCATION;
    }
}
