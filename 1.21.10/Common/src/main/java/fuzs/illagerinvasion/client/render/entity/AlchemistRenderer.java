package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.model.CustomIllagerModel;
import fuzs.illagerinvasion.client.model.geom.ModModelLayers;
import fuzs.illagerinvasion.world.entity.monster.Alchemist;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AlchemistRenderer extends IllagerRenderer<Alchemist, IllagerRenderState> {
    private static final ResourceLocation TEXTURE_LOCATION = IllagerInvasion.id("textures/entity/alchemist.png");

    public AlchemistRenderer(final EntityRendererProvider.Context context) {
        super(context, new CustomIllagerModel<>(context.bakeLayer(ModModelLayers.ALCHEMIST)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this) {
            @Override
            public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, IllagerRenderState renderState, float yRot, float xRot) {
                if (renderState.isAggressive) {
                    super.submit(poseStack, nodeCollector, packedLight, renderState, yRot, xRot);
                }
            }
        });
        this.model.getHat().visible = true;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = IllagerModel.createBodyLayer().mesh;
        PartDefinition partDefinition = meshDefinition.getRoot().getChild("head").getChild("hat");
        partDefinition.addOrReplaceChild("brim",
                CubeListBuilder.create().texOffs(69, 47).addBox(-8.0F, -8.0F, -6.0F, 16.0F, 16.0F, 1.0F),
                PartPose.rotation(-Mth.HALF_PI, 0.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 128, 64);
    }

    @Override
    public IllagerRenderState createRenderState() {
        return new IllagerRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(IllagerRenderState renderState) {
        return TEXTURE_LOCATION;
    }
}
