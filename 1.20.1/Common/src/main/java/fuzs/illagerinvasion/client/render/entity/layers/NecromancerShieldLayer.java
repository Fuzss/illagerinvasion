package fuzs.illagerinvasion.client.render.entity.layers;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.init.ClientModRegistry;
import fuzs.illagerinvasion.world.entity.monster.Necromancer;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class NecromancerShieldLayer extends EnergySwirlLayer<Necromancer, IllagerModel<Necromancer>> {
    private static final ResourceLocation TEXTURE_LOCATION = IllagerInvasion.id("textures/entity/necromancer_armor1.png");

    private final IllagerModel<Necromancer> model;

    public NecromancerShieldLayer(final RenderLayerParent<Necromancer, IllagerModel<Necromancer>> context, final EntityModelSet loader) {
        super(context);
        this.model = new IllagerModel<>(loader.bakeLayer(ClientModRegistry.NECROMANCER_SHIELD));
    }

    @Override
    protected float xOffset(final float partialAge) {
        return Mth.cos(partialAge * 0.2f) * 0.2f;
    }

    @Override
    protected ResourceLocation getTextureLocation() {
        return NecromancerShieldLayer.TEXTURE_LOCATION;
    }

    @Override
    protected IllagerModel<Necromancer> model() {
        return this.model;
    }
}
