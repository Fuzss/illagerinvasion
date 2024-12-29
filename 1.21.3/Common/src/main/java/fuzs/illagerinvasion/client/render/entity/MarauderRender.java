package fuzs.illagerinvasion.client.render.entity;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.world.entity.monster.Marauder;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class MarauderRender extends IllagerRenderer<Marauder> {
    private static final ResourceLocation SAVAGER_LOCATION = IllagerInvasion.id("textures/entity/savager.png");

    public MarauderRender(EntityRendererProvider.Context context) {
        super(context, new IllagerModel<>(context.bakeLayer(ModelLayers.VINDICATOR)), 0.5f);
        this.addLayer(new ItemInHandLayer<Marauder, IllagerModel<Marauder>>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(Marauder vindicatorEntity) {
        return SAVAGER_LOCATION;
    }
}
