package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.model.BasherModel;
import fuzs.illagerinvasion.client.model.geom.ModModelLayers;
import fuzs.illagerinvasion.client.render.entity.state.StunnableIllagerRenderState;
import fuzs.illagerinvasion.world.entity.monster.Basher;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class BasherRenderer extends IllagerRenderer<Basher, StunnableIllagerRenderState> {
    private static final ResourceLocation TEXTURE_LOCATION = IllagerInvasion.id("textures/entity/basher.png");

    public BasherRenderer(EntityRendererProvider.Context context) {
        super(context, new BasherModel(context.bakeLayer(ModModelLayers.BASHER)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this) {
            @Override
            public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, StunnableIllagerRenderState renderState, float yRot, float xRot) {
                if (renderState.isAggressive) {
                    super.submit(poseStack, nodeCollector, packedLight, renderState, yRot, xRot);
                }
            }
        });
    }

    @Override
    public StunnableIllagerRenderState createRenderState() {
        return new StunnableIllagerRenderState();
    }

    @Override
    public void extractRenderState(Basher entity, StunnableIllagerRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.extractRenderState(entity);
    }

    @Override
    public ResourceLocation getTextureLocation(StunnableIllagerRenderState renderState) {
        return TEXTURE_LOCATION;
    }
}
