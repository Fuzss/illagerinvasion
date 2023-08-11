package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.model.SurrenderedModel;
import fuzs.illagerinvasion.world.entity.monster.Surrendered;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;


public class SurrenderedRender extends HumanoidMobRenderer<Surrendered, SurrenderedModel<Surrendered>> {
    private static final ResourceLocation SURRENDERED_LOCATION = IllagerInvasion.id("textures/entity/surrendered.png");
    private static final ResourceLocation SURRENDERED_CHARGE_LOCATION = IllagerInvasion.id("textures/entity/surrendered_charge.png");

    public SurrenderedRender(EntityRendererProvider.Context context) {
        this(context, ModelLayers.SKELETON, ModelLayers.SKELETON_INNER_ARMOR, ModelLayers.SKELETON_OUTER_ARMOR);
    }

    public SurrenderedRender(EntityRendererProvider.Context context, ModelLayerLocation modelLayerLocation, ModelLayerLocation modelLayerLocation2, ModelLayerLocation modelLayerLocation3) {
        super(context, new SurrenderedModel<>(context.bakeLayer(modelLayerLocation)), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, new SurrenderedModel<>(context.bakeLayer(modelLayerLocation2)), new SurrenderedModel<>(context.bakeLayer(modelLayerLocation3))));
    }

    @Override
    protected void scale(Surrendered surrendered, PoseStack matrixStack, float f) {
        matrixStack.scale(0.85f, 0.85f, 0.85f);
    }

    @Override
    public Vec3 getRenderOffset(Surrendered surrendered, float partialTicks) {
        return new Vec3(0.0, -0.35, 0.0);
    }

    @Override
    public ResourceLocation getTextureLocation(Surrendered surrendered) {
        return surrendered.isCharging() ? SURRENDERED_CHARGE_LOCATION : SURRENDERED_LOCATION;
    }
}

