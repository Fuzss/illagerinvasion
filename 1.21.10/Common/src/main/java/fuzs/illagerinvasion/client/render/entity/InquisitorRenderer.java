package fuzs.illagerinvasion.client.render.entity;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.model.geom.ModModelLayers;
import fuzs.illagerinvasion.client.model.InquisitorModel;
import fuzs.illagerinvasion.client.render.entity.state.StunnableIllagerRenderState;
import fuzs.illagerinvasion.world.entity.monster.Inquisitor;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class InquisitorRenderer extends IllagerRenderer<Inquisitor, StunnableIllagerRenderState> {
    private static final ResourceLocation TEXTURE_LOCATION = IllagerInvasion.id("textures/entity/inquisitor.png");

    public InquisitorRenderer(final EntityRendererProvider.Context context) {
        super(context, new InquisitorModel(context.bakeLayer(ModModelLayers.INQUISITOR)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this));
    }

    @Override
    public StunnableIllagerRenderState createRenderState() {
        return new StunnableIllagerRenderState();
    }

    @Override
    public void extractRenderState(Inquisitor entity, StunnableIllagerRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.extractRenderState(entity);
    }

    @Override
    public ResourceLocation getTextureLocation(StunnableIllagerRenderState renderState) {
        return TEXTURE_LOCATION;
    }
}
