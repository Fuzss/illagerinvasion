package fuzs.illagerinvasion.client.render.entity;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.model.SurrenderedModel;
import fuzs.illagerinvasion.client.model.geom.ModModelLayers;
import fuzs.illagerinvasion.client.render.entity.state.SurrenderedRenderState;
import fuzs.illagerinvasion.world.entity.monster.Surrendered;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;


public class SurrenderedRenderer extends HumanoidMobRenderer<Surrendered, SurrenderedRenderState, SurrenderedModel> {
    private static final ResourceLocation TEXTURE_LOCATION = IllagerInvasion.id("textures/entity/surrendered.png");
    private static final ResourceLocation CHARGING_TEXTURE_LOCATION = IllagerInvasion.id(
            "textures/entity/surrendered_charge.png");

    public SurrenderedRenderer(EntityRendererProvider.Context context) {
        super(context, new SurrenderedModel(context.bakeLayer(ModModelLayers.SURRENDERED)), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this,
                ArmorModelSet.bake(ModModelLayers.SURRENDERED_ARMOR, context.getModelSet(), SurrenderedModel::new),
                context.getEquipmentRenderer()));
    }

    @Override
    public Vec3 getRenderOffset(SurrenderedRenderState renderState) {
        return new Vec3(0.0, -0.35, 0.0);
    }

    @Override
    public SurrenderedRenderState createRenderState() {
        return new SurrenderedRenderState();
    }

    @Override
    public void extractRenderState(Surrendered entity, SurrenderedRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.isAggressive = entity.isAggressive();
        reusedState.isCharging = entity.isCharging();
    }

    @Override
    public ResourceLocation getTextureLocation(SurrenderedRenderState renderState) {
        return renderState.isCharging ? CHARGING_TEXTURE_LOCATION : TEXTURE_LOCATION;
    }
}

