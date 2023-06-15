package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.world.entity.monster.Provoker;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class ProvokerRender extends IllagerRenderer<Provoker> {
    private static final ResourceLocation PROVOKER_LOCATION = IllagerInvasion.id("textures/entity/provoker.png");

    public ProvokerRender(EntityRendererProvider.Context context) {
        super(context, new IllagerModel<>(context.bakeLayer(ModelLayers.ILLUSIONER)), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()) {

            @Override
            public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, Provoker provoker, float f, float g, float h, float j, float k, float l) {
                if (provoker.isAggressive() || provoker.isCastingSpell()) {
                    super.render(matrixStack, vertexConsumerProvider, i, provoker, f, g, h, j, k, l);
                }
            }
        });
        this.model.getHat().visible = true;
    }

    @Override
    public ResourceLocation getTextureLocation(Provoker entity) {
        return PROVOKER_LOCATION;
    }
}