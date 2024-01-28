package fuzs.illagerinvasion.client.model;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractIllager;

public class CapedIllagerEntityModel<T extends AbstractIllager> extends HierarchicalModel<T> implements ArmedModel, HeadedModel {
    private static final String CAPE = "cape";
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart hat;
    private final ModelPart arms;
    private final ModelPart body;
    private final ModelPart cape;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart rightArm;
    private final ModelPart leftArm;

    public CapedIllagerEntityModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild(PartNames.HEAD);
        this.hat = this.head.getChild(PartNames.HAT);
        this.body = root.getChild(PartNames.BODY);
        this.cape = this.root.getChild(CAPE);
        this.hat.visible = false;
        this.arms = root.getChild(PartNames.ARMS);
        this.leftLeg = root.getChild(PartNames.LEFT_LEG);
        this.rightLeg = root.getChild(PartNames.RIGHT_LEG);
        this.leftArm = root.getChild(PartNames.LEFT_ARM);
        this.rightArm = root.getChild(PartNames.RIGHT_ARM);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition modelPartData2 = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f), PartPose.offset(0.0f, 0.0f, 0.0f));
        modelPartData2.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create().texOffs(32, 0).addBox(-4.0f, -10.0f, -4.0f, 8.0f, 12.0f, 8.0f, new CubeDeformation(0.45f)), PartPose.ZERO);
        modelPartData2.addOrReplaceChild(PartNames.NOSE, CubeListBuilder.create().texOffs(24, 0).addBox(-1.0f, -1.0f, -6.0f, 2.0f, 4.0f, 2.0f), PartPose.offset(0.0f, -2.0f, 0.0f));
        modelPartData.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(16, 20).addBox(-4.0f, 0.0f, -3.0f, 8.0f, 12.0f, 6.0f).texOffs(0, 38).addBox(-4.0f, 0.0f, -3.0f, 8.0f, 19.0f, 6.0f, new CubeDeformation(0.5f)), PartPose.offset(0.0f, 0.0f, 0.0f));
        PartDefinition modelPartData3 = modelPartData.addOrReplaceChild(PartNames.ARMS, CubeListBuilder.create().texOffs(44, 22).addBox(-8.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f).texOffs(40, 38).addBox(-4.0f, 2.0f, -2.0f, 8.0f, 4.0f, 4.0f), PartPose.offsetAndRotation(0.0f, 3.0f, -1.0f, -0.75f, 0.0f, 0.0f));
        modelPartData.addOrReplaceChild(CAPE, CubeListBuilder.create().texOffs(64, 0).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 17.0F, 1.0F, new CubeDeformation(0.6f, 0.7f, 0.0f)), PartPose.offset(0.0f, 1.0f, 3.4f));
        modelPartData3.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(4.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f), PartPose.ZERO);
        modelPartData.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create().texOffs(0, 22).addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f), PartPose.offset(-2.0f, 12.0f, 0.0f));
        modelPartData.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f), PartPose.offset(2.0f, 12.0f, 0.0f));
        modelPartData.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create().texOffs(40, 46).addBox(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f), PartPose.offset(-5.0f, 2.0f, 0.0f));
        modelPartData.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f), PartPose.offset(5.0f, 2.0f, 0.0f));
        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        boolean bl;
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180);
        this.head.xRot = headPitch * ((float) Math.PI / 180);
        if (this.riding) {
            this.rightArm.xRot = -0.62831855f;
            this.rightArm.yRot = 0.0f;
            this.rightArm.zRot = 0.0f;
            this.leftArm.xRot = -0.62831855f;
            this.leftArm.yRot = 0.0f;
            this.leftArm.zRot = 0.0f;
        } else {
            this.rightArm.xRot = Mth.cos(limbSwing * 0.6662f + 3.1415927f) * 2.0f * limbSwingAmount * 0.5f;
            this.rightArm.yRot = 0.0f;
            this.rightArm.zRot = 0.0f;
            this.leftArm.xRot = Mth.cos(limbSwing * 0.6662f) * 0.5f * limbSwingAmount * 0.5f - 1.2f;
            this.leftArm.yRot = 0.0f;
            this.leftArm.zRot = 0.0f;
            this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount * 0.5f;
            this.rightLeg.yRot = 0.0f;
            this.rightLeg.zRot = 0.0f;
            this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662f + 3.1415927f) * 1.4f * limbSwingAmount * 0.5f;
            this.leftLeg.yRot = 0.0f;
            this.leftLeg.zRot = 0.0f;
        }
        AbstractIllager.IllagerArmPose state = ((AbstractIllager) entity).getArmPose();
        if (state == AbstractIllager.IllagerArmPose.ATTACKING) {
            if (((LivingEntity) entity).getMainHandItem().isEmpty()) {
                AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, true, this.attackTime, ageInTicks);
            } else {
                AnimationUtils.swingWeaponDown(this.rightArm, this.leftArm, entity, this.attackTime, ageInTicks);
            }
        } else if (state == AbstractIllager.IllagerArmPose.SPELLCASTING) {
            this.rightArm.z = 0.0f;
            this.rightArm.x = -5.0f;
            this.leftArm.z = 0.0f;
            this.leftArm.x = 5.0f;
            this.rightArm.xRot = Mth.cos(ageInTicks * 0.6662f) * 0.25f;
            this.leftArm.xRot = Mth.cos(ageInTicks * 0.6662f) * 0.25f;
            this.cape.xRot = (Mth.cos(ageInTicks * 0.5222f) * 0.2f) + 0.15f;
            this.rightArm.zRot = 2.3561945f;
            this.leftArm.zRot = -2.3561945f;
            this.rightArm.yRot = 0.0f;
            this.leftArm.yRot = 0.0f;
        } else if (state == AbstractIllager.IllagerArmPose.BOW_AND_ARROW) {
            this.rightArm.yRot = -0.1f + this.head.yRot;
            this.rightArm.xRot = -1.5707964f + this.head.xRot;
            this.leftArm.xRot = -0.9424779f + this.head.xRot;
            this.leftArm.yRot = this.head.yRot - 0.4f;
            this.leftArm.zRot = 1.5707964f;
        } else if (state == AbstractIllager.IllagerArmPose.CROSSBOW_HOLD) {
            AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, true);
        } else if (state == AbstractIllager.IllagerArmPose.CROSSBOW_CHARGE) {
            AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, entity, true);
        } else if (state == AbstractIllager.IllagerArmPose.CELEBRATING) {
            this.rightArm.z = 0.0f;
            this.rightArm.x = -5.0f;
            this.rightArm.xRot = Mth.cos(ageInTicks * 0.6662f) * 0.05f;
            this.rightArm.zRot = 2.670354f;
            this.rightArm.yRot = 0.0f;
            this.leftArm.z = 0.0f;
            this.leftArm.x = 5.0f;
            this.leftArm.xRot = Mth.cos(ageInTicks * 0.6662f) * 0.05f;
            this.leftArm.zRot = -2.3561945f;
            this.leftArm.yRot = 0.0f;
        } else if (state == AbstractIllager.IllagerArmPose.CROSSED) {
            this.cape.xRot = (Mth.cos(limbSwing * 0.3111f + (float) Math.PI) * 0.3f * limbSwingAmount * 0.5f) + 0.15f;
        }
        this.arms.visible = bl = state == AbstractIllager.IllagerArmPose.CROSSED;
        this.leftArm.visible = !bl;
        this.rightArm.visible = !bl;
    }

    private ModelPart getAttackingArm(HumanoidArm arm) {
        if (arm == HumanoidArm.LEFT) {
            return this.leftArm;
        }
        return this.rightArm;
    }

    public ModelPart getHat() {
        return this.hat;
    }

    @Override
    public ModelPart getHead() {
        return this.head;
    }

    @Override
    public void translateToHand(HumanoidArm arm, PoseStack matrices) {
        this.getAttackingArm(arm).translateAndRotate(matrices);
    }
}



