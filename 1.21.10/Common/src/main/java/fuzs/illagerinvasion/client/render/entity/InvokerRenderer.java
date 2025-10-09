package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.model.geom.ModModelLayers;
import fuzs.illagerinvasion.client.model.InvokerModel;
import fuzs.illagerinvasion.client.render.entity.layers.IllagerArmorLayer;
import fuzs.illagerinvasion.client.render.entity.layers.InvokerGoldLayer;
import fuzs.illagerinvasion.client.render.entity.state.InvokerRenderState;
import fuzs.illagerinvasion.world.entity.monster.Invoker;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;

public class InvokerRenderer extends IllagerRenderer<Invoker, InvokerRenderState> {
    private static final ResourceLocation TEXTURE_LOCATION = IllagerInvasion.id("textures/entity/invoker.png");
    private static final ResourceLocation ARMOR_TEXTURE_LOCATION = ResourceLocationHelper.withDefaultNamespace(
            "textures/entity/wither/wither_armor.png");

    public InvokerRenderer(EntityRendererProvider.Context context) {
        super(context, new InvokerModel(context.bakeLayer(ModModelLayers.INVOKER)), 0.0F);
        this.addLayer(new InvokerGoldLayer(this));
        this.addLayer(new IllagerArmorLayer<>(this,
                new InvokerModel(context.bakeLayer(ModModelLayers.INVOKER_ARMOR))) {
            @Override
            protected ResourceLocation getTextureLocation() {
                return ARMOR_TEXTURE_LOCATION;
            }
        });
        this.model.getHat().visible = true;
    }

    @Override
    public InvokerRenderState createRenderState() {
        return new InvokerRenderState();
    }

    @Override
    protected void setupRotations(InvokerRenderState renderState, PoseStack poseStack, float bodyRot, float scale) {
        super.setupRotations(renderState, poseStack, bodyRot, scale);
        float rotation = Mth.sin(renderState.ageInTicks * 0.1F) * Mth.DEG_TO_RAD;
        poseStack.mulPose((new Quaternionf()).rotationZYX(rotation, 0.0F, -rotation));
        poseStack.translate(0.0F, 0.3F + rotation, 0.0F);
    }

    @Override
    public void extractRenderState(Invoker entity, InvokerRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.isPowered = entity.isPowered();
        if (!reusedState.isRiding) {
            reusedState.floatAnimationSpeed = reusedState.walkAnimationSpeed;
            // this is enough from the walking animation not to play for the legs
            reusedState.walkAnimationSpeed = reusedState.walkAnimationPos = 0.0F;
        }
    }

    @Override
    public ResourceLocation getTextureLocation(InvokerRenderState renderState) {
        return TEXTURE_LOCATION;
    }
}
