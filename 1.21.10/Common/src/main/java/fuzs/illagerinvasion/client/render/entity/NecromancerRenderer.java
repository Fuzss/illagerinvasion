package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.model.CustomIllagerModel;
import fuzs.illagerinvasion.client.model.geom.ModModelLayers;
import fuzs.illagerinvasion.client.render.entity.layers.IllagerArmorLayer;
import fuzs.illagerinvasion.client.render.entity.state.NecromancerRenderState;
import fuzs.illagerinvasion.world.entity.monster.Necromancer;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class NecromancerRenderer extends IllagerRenderer<Necromancer, NecromancerRenderState> {
    private static final ResourceLocation TEXTURE_LOCATION = IllagerInvasion.id("textures/entity/necromancer.png");
    private static final ResourceLocation ARMOR_TEXTURE_LOCATION = IllagerInvasion.id(
            "textures/entity/necromancer_armor.png");

    public NecromancerRenderer(EntityRendererProvider.Context context) {
        super(context, new CustomIllagerModel<>(context.bakeLayer(ModModelLayers.NECROMANCER)), 0.5F);
        this.addLayer(new IllagerArmorLayer<>(this,
                new CustomIllagerModel<>(context.bakeLayer(ModModelLayers.NECROMANCER_ARMOR))) {
            @Override
            protected ResourceLocation getTextureLocation() {
                return ARMOR_TEXTURE_LOCATION;
            }
        });
        this.addLayer(new ItemInHandLayer<>(this) {
            @Override
            public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, NecromancerRenderState renderState, float yRot, float xRot) {
                if (renderState.isCastingSpell) {
                    super.submit(poseStack, nodeCollector, packedLight, renderState, yRot, xRot);
                }
            }
        });
    }

    /**
     * Copied from {@link IllagerModel#createBodyLayer()} to allow for a custom {@link CubeDeformation}.
     */
    public static LayerDefinition createBodyLayer(CubeDeformation cubeDeformation) {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition partDefinition2 = partDefinition.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, cubeDeformation),
                PartPose.ZERO);
        partDefinition2.addOrReplaceChild("hat",
                CubeListBuilder.create()
                        .texOffs(32, 0)
                        .addBox(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, cubeDeformation.extend(0.45F)),
                PartPose.ZERO);
        partDefinition2.addOrReplaceChild("nose",
                CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, cubeDeformation),
                PartPose.offset(0.0F, -2.0F, 0.0F));
        partDefinition.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(16, 20)
                        .addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, cubeDeformation)
                        .texOffs(0, 38)
                        .addBox(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F, cubeDeformation.extend(0.5F)),
                PartPose.ZERO);
        PartDefinition partDefinition3 = partDefinition.addOrReplaceChild("arms",
                CubeListBuilder.create()
                        .texOffs(44, 22)
                        .addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, cubeDeformation)
                        .texOffs(40, 38)
                        .addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, cubeDeformation),
                PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        partDefinition3.addOrReplaceChild("left_shoulder",
                CubeListBuilder.create()
                        .texOffs(44, 22)
                        .mirror()
                        .addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, cubeDeformation),
                PartPose.ZERO);
        partDefinition.addOrReplaceChild("right_leg",
                CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation),
                PartPose.offset(-2.0F, 12.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                        .texOffs(0, 22)
                        .mirror()
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation),
                PartPose.offset(2.0F, 12.0F, 0.0F));
        partDefinition.addOrReplaceChild("right_arm",
                CubeListBuilder.create()
                        .texOffs(40, 46)
                        .addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation),
                PartPose.offset(-5.0F, 2.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_arm",
                CubeListBuilder.create()
                        .texOffs(40, 46)
                        .mirror()
                        .addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation),
                PartPose.offset(5.0F, 2.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void extractRenderState(Necromancer entity, NecromancerRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.isPowered = entity.isPowered();
        reusedState.isCastingSpell = entity.isCastingSpell();
    }

    @Override
    public NecromancerRenderState createRenderState() {
        return new NecromancerRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(NecromancerRenderState renderState) {
        return TEXTURE_LOCATION;
    }
}
