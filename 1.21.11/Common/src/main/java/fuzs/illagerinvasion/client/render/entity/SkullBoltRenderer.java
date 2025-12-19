package fuzs.illagerinvasion.client.render.entity;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.model.geom.ModModelLayers;
import net.minecraft.client.model.object.skull.SkullModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WitherSkullRenderer;
import net.minecraft.client.renderer.entity.state.WitherSkullRenderState;
import net.minecraft.resources.Identifier;

public class SkullBoltRenderer extends WitherSkullRenderer {
    private static final Identifier TEXTURE_LOCATION = IllagerInvasion.id("textures/entity/skullbolt.png");

    public SkullBoltRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new SkullModel(context.bakeLayer(ModModelLayers.SKULL_BOLT));
    }

    @Override
    protected Identifier getTextureLocation(WitherSkullRenderState renderState) {
        return TEXTURE_LOCATION;
    }
}
