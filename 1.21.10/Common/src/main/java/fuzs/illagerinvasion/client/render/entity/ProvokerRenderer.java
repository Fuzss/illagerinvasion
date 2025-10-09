package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.model.CustomIllagerModel;
import fuzs.illagerinvasion.client.model.geom.ModModelLayers;
import fuzs.illagerinvasion.client.render.entity.state.SpellcasterIllagerRenderState;
import fuzs.illagerinvasion.world.entity.monster.Provoker;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class ProvokerRenderer extends IllagerRenderer<Provoker, SpellcasterIllagerRenderState> {
    private static final ResourceLocation TEXTURE_LOCATION = IllagerInvasion.id("textures/entity/provoker.png");

    public ProvokerRenderer(EntityRendererProvider.Context context) {
        super(context, new CustomIllagerModel<>(context.bakeLayer(ModModelLayers.PROVOKER)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this) {
            @Override
            public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, SpellcasterIllagerRenderState renderState, float yRot, float xRot) {
                if (renderState.isAggressive || renderState.isCastingSpell) {
                    super.submit(poseStack, nodeCollector, packedLight, renderState, yRot, xRot);
                }
            }
        });
        this.model.getHat().visible = true;
    }

    @Override
    public SpellcasterIllagerRenderState createRenderState() {
        return new SpellcasterIllagerRenderState();
    }

    @Override
    public void extractRenderState(Provoker entity, SpellcasterIllagerRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.isCastingSpell = entity.isCastingSpell();
    }

    @Override
    public ResourceLocation getTextureLocation(SpellcasterIllagerRenderState renderState) {
        return TEXTURE_LOCATION;
    }
}