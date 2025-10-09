package fuzs.illagerinvasion.client.model;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.AbstractIllager;

public class CustomIllagerModel<T extends IllagerRenderState> extends IllagerModel<T> {
    public static final MeshTransformer SCALE_TRANSFORMER = MeshTransformer.scaling(0.9375F);

    private final ModelPart head;
    private final ModelPart rightArm;
    private final ModelPart leftArm;

    public CustomIllagerModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.leftArm = root.getChild("left_arm");
        this.rightArm = root.getChild("right_arm");
    }

    @Override
    public void setupAnim(T renderState) {
        super.setupAnim(renderState);
        // rework arm swing for other arm when attacking with a melee weapon to take walking animation into account
        if (renderState.armPose == AbstractIllager.IllagerArmPose.ATTACKING) {
            if (!renderState.getMainHandItem().isEmpty()) {
                if (renderState.mainArm == HumanoidArm.RIGHT) {
                    this.leftArm.xRot = Mth.cos(renderState.walkAnimationPos * 0.19F) * Math.min(0.5F,
                            2.0F * renderState.walkAnimationSpeed * 0.5F);
                    this.leftArm.yRot = this.leftArm.zRot = 0.0F;
                    AnimationUtils.bobModelPart(this.leftArm, renderState.ageInTicks, -1.0F);
                } else {
                    this.rightArm.xRot = Mth.cos(renderState.walkAnimationPos * 0.19F + Mth.PI) * Math.min(0.5F,
                            2.0F * renderState.walkAnimationSpeed * 0.5F);
                    this.rightArm.yRot = this.rightArm.zRot = 0.0F;
                    AnimationUtils.bobModelPart(this.rightArm, renderState.ageInTicks, 1.0F);
                }
            }
        }
        // fix illager model not supporting left-handed bow
        if (renderState.armPose == AbstractIllager.IllagerArmPose.BOW_AND_ARROW) {
            if (renderState.mainArm == HumanoidArm.LEFT) {
                this.leftArm.yRot = 0.1F + this.head.yRot;
                this.leftArm.xRot = -Mth.HALF_PI + this.head.xRot;
                this.leftArm.zRot = 0.0F;
                this.rightArm.xRot = -0.9424779F + this.head.xRot;
                this.rightArm.yRot = this.head.yRot + 0.4F;
                this.rightArm.zRot = -Mth.HALF_PI;
            }
        }
        // fix neutral pose lacking any arm bobbing, making arms appear completely static
        if (renderState.armPose == AbstractIllager.IllagerArmPose.NEUTRAL) {
            AnimationUtils.bobArms(this.rightArm, this.leftArm, renderState.ageInTicks);
        }
    }
}
