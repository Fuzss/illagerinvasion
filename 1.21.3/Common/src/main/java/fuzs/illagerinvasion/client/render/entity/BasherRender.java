package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.world.entity.monster.Basher;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class BasherRender extends IllagerRenderer<Basher> {
    private static final ResourceLocation BASHER_LOCATION = IllagerInvasion.id("textures/entity/basher.png");

    public BasherRender(EntityRendererProvider.Context context) {
        super(context, new IllagerModel<>(context.bakeLayer(ModelLayers.VINDICATOR)), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()) {

            @Override
            public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, Basher vindicatorEntity, float f, float g, float h, float j, float k, float l) {
                if (vindicatorEntity.isAggressive()) {
                    super.render(matrixStack, vertexConsumerProvider, i, vindicatorEntity, f, g, h, j, k, l);
                }
            }
        });
    }

    @Override
    public ResourceLocation getTextureLocation(Basher vindicatorEntity) {
        return BASHER_LOCATION;
    }
}
