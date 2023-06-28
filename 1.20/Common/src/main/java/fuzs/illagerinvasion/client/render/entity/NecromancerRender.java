package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.render.entity.layers.NecromancerShieldLayer;
import fuzs.illagerinvasion.world.entity.monster.Necromancer;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class NecromancerRender extends IllagerRenderer<Necromancer> {
    private static final ResourceLocation TEXTURE_LOCATION = IllagerInvasion.id("textures/entity/necromancer.png");

    public NecromancerRender(EntityRendererProvider.Context context) {
        super(context, new IllagerModel<>(context.bakeLayer(ModelLayers.EVOKER)), 0.5f);
        this.addLayer(new NecromancerShieldLayer(this, context.getModelSet()));
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()) {

            @Override
            public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, Necromancer Necromancer, float f, float g, float h, float j, float k, float l) {
                if (Necromancer.isCastingSpell()) {
                    super.render(matrixStack, vertexConsumerProvider, i, Necromancer, f, g, h, j, k, l);
                }
            }
        });
        this.model.getHat().visible = false;
    }

    @Override
    public ResourceLocation getTextureLocation(Necromancer entity) {
        return TEXTURE_LOCATION;
    }
}
