package fuzs.illagerinvasion.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.AbstractIllager;

public class HatIllagerEntityModel<T extends AbstractIllager> extends HierarchicalModel<T> implements ArmedModel, HeadedModel {
    private static final String UPPER_HAT = "upper_hat";
    private static final String MIDDLE_HAT = "middle_hat";
    private static final String LOWER_HAT = "lower_hat";
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart hat;
    private final ModelPart arms;
    private final ModelPart body;
    private final ModelPart lower_hat;
    private final ModelPart middle_hat;
    private final ModelPart upper_hat;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart rightArm;
    private final ModelPart leftArm;

    public HatIllagerEntityModel(final ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.hat = this.head.getChild("hat");
        this.body = root.getChild("body");
        this.upper_hat = this.head.getChild("upper_hat");
        this.middle_hat = this.head.getChild("middle_hat");
        this.lower_hat = this.head.getChild("lower_hat");
        this.hat.visible = false;
        this.arms = root.getChild("arms");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
        this.leftArm = root.getChild("left_arm");
        this.rightArm = root.getChild("right_arm");
    }

    public static LayerDefinition getTexturedModelData() {
        final MeshDefinition meshDefinition = new MeshDefinition();
        final PartDefinition partDefinition = meshDefinition.getRoot();
        final PartDefinition partDefinition1 = partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition partDefinition3 = partDefinition1.addOrReplaceChild("hat",
                CubeListBuilder.create()
                        .texOffs(32, 0)
                        .addBox(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.45F)),
                PartPose.ZERO);
        partDefinition3.addOrReplaceChild("lower_hat", CubeListBuilder.create().texOffs(0, 64).addBox(-7.0F, -10.0F, -7.0F, 14.0F, 1.0F, 14.0F), PartPose.offset(0.0F, -1.0F, 0.0F)).addOrReplaceChild("middle_hat", CubeListBuilder.create().texOffs(0, 80).addBox(-4.0F, -19.76f, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(-0.05f)), PartPose.rotation(-0.05F, 0.0F, 0.0F)).addOrReplaceChild("upper_hat", CubeListBuilder.create().texOffs(0, 98).addBox(-4.0F, -19.76f, 3.9f, 8.0F, 5.0F, 5.0F, new CubeDeformation(-0.05f)), PartPose.ZERO);
        partDefinition1.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F), PartPose.offset(0.0F, -2.0F, 0.0F));
        partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F).texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.5f)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition partDefinition2 = partDefinition.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F).texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F), PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75f, 0.0F, 0.0F));
        partDefinition2.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F), PartPose.ZERO);
        partDefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-2.0F, 12.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(2.0F, 12.0F, 0.0F));
        partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(5.0F, 2.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 128);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * 0.017453292f;
        this.head.xRot = headPitch * 0.017453292f;
        if (this.riding) {
            this.rightArm.xRot = -0.62831855f;
            this.rightArm.yRot = 0.0F;
            this.rightArm.zRot = 0.0F;
            this.leftArm.xRot = -0.62831855f;
            this.leftArm.yRot = 0.0F;
            this.leftArm.zRot = 0.0F;
            this.rightLeg.xRot = -1.4137167f;
            this.rightLeg.yRot = 0.31415927f;
            this.rightLeg.zRot = 0.07853982f;
            this.leftLeg.xRot = -1.4137167f;
            this.leftLeg.yRot = -0.31415927f;
            this.leftLeg.zRot = -0.07853982f;
        } else {
            this.rightArm.xRot = Mth.cos(limbSwing * 0.6662f + 3.1415927f) * 2.0F * limbSwingAmount * 0.5f;
            this.rightArm.yRot = 0.0F;
            this.rightArm.zRot = 0.0F;
            this.leftArm.xRot = Mth.cos(limbSwing * 0.6662f) * 2.0F * limbSwingAmount * 0.5f;
            this.leftArm.yRot = 0.0F;
            this.leftArm.zRot = 0.0F;
            this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount * 0.5f;
            this.rightLeg.yRot = 0.0F;
            this.rightLeg.zRot = 0.0F;
            this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662f + 3.1415927f) * 1.4f * limbSwingAmount * 0.5f;
            this.leftLeg.yRot = 0.0F;
            this.leftLeg.zRot = 0.0F;
        }
        final AbstractIllager.IllagerArmPose state = entity.getArmPose();
        if (state == AbstractIllager.IllagerArmPose.ATTACKING) {
            if (((LivingEntity) entity).getMainHandItem().isEmpty()) {
                AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, true, this.attackTime, ageInTicks);
            } else {
                AnimationUtils.swingWeaponDown(this.rightArm, this.leftArm, (Mob) entity, this.attackTime, ageInTicks);
            }
        } else if (state == AbstractIllager.IllagerArmPose.SPELLCASTING) {
            this.rightArm.z = 0.0F;
            this.rightArm.x = -5.0F;
            this.leftArm.z = 0.0F;
            this.leftArm.x = 5.0F;
            this.rightArm.xRot = Mth.cos(ageInTicks * 0.6662f) * 0.25f;
            this.leftArm.xRot = Mth.cos(ageInTicks * 0.6662f) * 0.25f;
            this.rightArm.zRot = 2.3561945f;
            this.leftArm.zRot = -2.3561945f;
            this.rightArm.yRot = 0.0F;
            this.leftArm.yRot = 0.0F;
        } else if (state == AbstractIllager.IllagerArmPose.BOW_AND_ARROW) {
            this.rightArm.yRot = -0.1f + this.head.yRot;
            this.rightArm.xRot = -1.5707964f + this.head.xRot;
            this.leftArm.xRot = -0.9424779f + this.head.xRot;
            this.leftArm.yRot = this.head.yRot - 0.4f;
            this.leftArm.zRot = 1.5707964f;
        } else if (state == AbstractIllager.IllagerArmPose.CROSSBOW_HOLD) {
            AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, true);
        } else if (state == AbstractIllager.IllagerArmPose.CROSSBOW_CHARGE) {
            AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, (LivingEntity) entity, true);
        } else if (state == AbstractIllager.IllagerArmPose.CELEBRATING) {
            this.rightArm.z = 0.0F;
            this.rightArm.x = -5.0F;
            this.rightArm.xRot = Mth.cos(ageInTicks * 0.6662f) * 0.05f;
            this.rightArm.zRot = 2.670354f;
            this.rightArm.yRot = 0.0F;
            this.leftArm.z = 0.0F;
            this.leftArm.x = 5.0F;
            this.leftArm.xRot = Mth.cos(ageInTicks * 0.6662f) * 0.05f;
            this.leftArm.zRot = -2.3561945f;
            this.leftArm.yRot = 0.0F;
        }
        final boolean bl = this.arms.visible = (state == AbstractIllager.IllagerArmPose.CROSSED);
        this.leftArm.visible = !bl;
        this.rightArm.visible = !bl;
    }

    private ModelPart getAttackingArm(final HumanoidArm arm) {
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
    public void translateToHand(final HumanoidArm arm, final PoseStack matrices) {
        this.getAttackingArm(arm).translateAndRotate(matrices);
    }
}
