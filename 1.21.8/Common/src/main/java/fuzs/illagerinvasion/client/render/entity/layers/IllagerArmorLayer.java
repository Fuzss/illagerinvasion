package fuzs.illagerinvasion.client.render.entity.layers;

import fuzs.illagerinvasion.client.render.entity.state.PowerableRenderState;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;
import net.minecraft.util.Mth;

public abstract class IllagerArmorLayer<S extends IllagerRenderState & PowerableRenderState, M extends IllagerModel<S>> extends EnergySwirlLayer<S, M> {
    private final M model;

    public IllagerArmorLayer(RenderLayerParent<S, M> renderer, M model) {
        super(renderer);
        this.model = model;
    }

    @Override
    protected boolean isPowered(S renderState) {
        return renderState.isPowered();
    }

    @Override
    protected float xOffset(final float tickCount) {
        return Mth.cos(tickCount * 0.2F) * 0.2F;
    }

    @Override
    protected M model() {
        return this.model;
    }
}
