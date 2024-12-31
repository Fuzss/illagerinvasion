package fuzs.illagerinvasion.client.init;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.puzzleslib.api.client.init.v1.ModelLayerFactory;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class ClientModRegistry {
    static final ModelLayerFactory FACTORY = ModelLayerFactory.from(IllagerInvasion.MOD_ID);
    public static final ModelLayerLocation CAPED_ILLAGER = FACTORY.register("caped_illager");
    public static final ModelLayerLocation INVOKER_SHIELD = FACTORY.register("invoker_shield");
    public static final ModelLayerLocation NECROMANCER_SHIELD = FACTORY.register("necromancer_shield");
    public static final ModelLayerLocation INVOKER_FANGS = FACTORY.register("invoker_fangs");
    public static final ModelLayerLocation HAT_ILLAGER = FACTORY.register("hat_illager");
    public static final ModelLayerLocation ARMORED_ILLAGER = FACTORY.register("armored_illager");
    public static final ModelLayerLocation STAFF_ILLAGER = FACTORY.register("staff_illager");
    public static final ModelLayerLocation BRIM_HAT_ILLAGER = FACTORY.register("brim_hat_illager");

    /**
     * Copied from {@link IllagerModel#createBodyLayer()} to allow for a custom {@link CubeDeformation}.
     */
    public static LayerDefinition createIllagerBodyLayer(CubeDeformation cubeDeformation) {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition partDefinition2 = partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, cubeDeformation), PartPose.offset(0.0F, 0.0F, 0.0F));
        partDefinition2.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, cubeDeformation.extend(0.45F)), PartPose.ZERO);
        partDefinition2.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, cubeDeformation), PartPose.offset(0.0F, -2.0F, 0.0F));
        partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, cubeDeformation).texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F, cubeDeformation.extend(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition partDefinition3 = partDefinition.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, cubeDeformation).texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, cubeDeformation), PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        partDefinition3.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, cubeDeformation), PartPose.ZERO);
        partDefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation), PartPose.offset(-2.0F, 12.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation), PartPose.offset(2.0F, 12.0F, 0.0F));
        partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation), PartPose.offset(-5.0F, 2.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation), PartPose.offset(5.0F, 2.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    public static LayerDefinition createCapedIllagerBodyLayer(CubeDeformation cubeDeformation) {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition partDefinition2 = partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, cubeDeformation), PartPose.offset(0.0F, 0.0F, 0.0F));
        partDefinition2.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, cubeDeformation.extend(0.45F)), PartPose.ZERO);
        partDefinition2.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, cubeDeformation), PartPose.offset(0.0F, -2.0F, 0.0F));
        partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, cubeDeformation).texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, cubeDeformation.extend(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));
//        partDefinition.addOrReplaceChild("cape", CubeListBuilder.create().texOffs(64, 0).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 17.0F, 1.0F, cubeDeformation.extend(0.6f, 0.7f, 0.0F)), PartPose.offset(0.0F, 1.0F, 3.4f));
        PartDefinition partDefinition3 = partDefinition.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, cubeDeformation).texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, cubeDeformation), PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        partDefinition3.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, cubeDeformation), PartPose.ZERO);
        partDefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation), PartPose.offset(-2.0F, 12.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation), PartPose.offset(2.0F, 12.0F, 0.0F));
        partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation), PartPose.offset(-5.0F, 2.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation), PartPose.offset(5.0F, 2.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 128, 128);
    }
}
