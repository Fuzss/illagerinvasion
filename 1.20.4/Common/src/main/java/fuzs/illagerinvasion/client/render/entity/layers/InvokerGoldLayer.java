package fuzs.illagerinvasion.client.render.entity.layers;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.model.InvokerEntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractIllager;

public class InvokerGoldLayer<T extends AbstractIllager> extends EyesLayer<T, InvokerEntityModel<T>> {
    private static final ResourceLocation INVOKER_GOLD_LOCATION = IllagerInvasion.id("textures/entity/invoker_gold.png");
    private static final RenderType INVOKER_GOLD = RenderType.eyes(INVOKER_GOLD_LOCATION);

    public InvokerGoldLayer(RenderLayerParent<T, InvokerEntityModel<T>> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public RenderType renderType() {
        return INVOKER_GOLD;
    }
}
