package fuzs.illagerinvasion.client.render.entity;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.model.geom.ModModelLayers;
import fuzs.illagerinvasion.client.model.CustomIllagerModel;
import fuzs.illagerinvasion.world.entity.monster.Sorcerer;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;
import net.minecraft.resources.ResourceLocation;

public class SorcererRenderer extends IllagerRenderer<Sorcerer, IllagerRenderState> {
    private static final ResourceLocation TEXTURE_LOCATION = IllagerInvasion.id("textures/entity/sorcerer.png");

    public SorcererRenderer(EntityRendererProvider.Context context) {
        super(context, new CustomIllagerModel<>(context.bakeLayer(ModModelLayers.SORCERER)), 0.5F);
        this.model.getHat().visible = true;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = IllagerModel.createBodyLayer().mesh;
        PartDefinition partDefinition = meshDefinition.getRoot().getChild("head").getChild("hat");
        partDefinition.addOrReplaceChild("lower_hat",
                        CubeListBuilder.create().texOffs(0, 64).addBox(-7.0F, -10.0F, -7.0F, 14.0F, 1.0F, 14.0F),
                        PartPose.offset(0.0F, -1.0F, 0.0F))
                .addOrReplaceChild("middle_hat",
                        CubeListBuilder.create()
                                .texOffs(0, 80)
                                .addBox(-4.0F, -19.76F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(-0.05F)),
                        PartPose.rotation(-0.05F, 0.0F, 0.0F))
                .addOrReplaceChild("upper_hat",
                        CubeListBuilder.create()
                                .texOffs(0, 98)
                                .addBox(-4.0F, -19.76F, 3.9F, 8.0F, 5.0F, 5.0F, new CubeDeformation(-0.05F)),
                        PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 64, 128);
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
