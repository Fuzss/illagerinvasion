package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.world.entity.monster.Surrendered;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.phys.Vec3;


public class SurrenderedRender extends SkeletonRenderer {
    private static final ResourceLocation SURRENDERED_LOCATION = IllagerInvasion.id("textures/entity/surrendered.png");
    private static final ResourceLocation SURRENDERED_CHARGE_LOCATION = IllagerInvasion.id("textures/entity/surrendered_charge.png");

    public SurrenderedRender(EntityRendererProvider.Context context) {
        super(context, ModelLayers.SKELETON, ModelLayers.SKELETON_INNER_ARMOR, ModelLayers.SKELETON_OUTER_ARMOR);
    }

    @Override
    protected void scale(AbstractSkeleton abstractSkeletonEntity, PoseStack matrixStack, float f) {
        matrixStack.scale(0.85f, 0.85f, 0.85f);
    }

    @Override
    public Vec3 getRenderOffset(AbstractSkeleton entity, float partialTicks) {
        return new Vec3(0.0, -0.35, 0.0);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractSkeleton abstractSkeletonEntity) {
        return ((Surrendered) abstractSkeletonEntity).isCharging() ? SURRENDERED_CHARGE_LOCATION : SURRENDERED_LOCATION;
    }
}

