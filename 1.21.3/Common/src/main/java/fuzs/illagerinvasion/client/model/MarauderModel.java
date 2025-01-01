package fuzs.illagerinvasion.client.model;

import fuzs.illagerinvasion.client.render.entity.state.MarauderRenderState;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.AbstractIllager;

public class MarauderModel extends CustomIllagerModel<MarauderRenderState> {
    private final ModelPart rightArm;
    private final ModelPart leftArm;

    public MarauderModel(ModelPart root) {
        super(root);
        this.leftArm = root.getChild("left_arm");
        this.rightArm = root.getChild("right_arm");
    }

    @Override
    public void setupAnim(MarauderRenderState renderState) {
        super.setupAnim(renderState);
        if (renderState.armPose == AbstractIllager.IllagerArmPose.ATTACKING) {
            if (!renderState.getMainHandItem().isEmpty()) {
                if (renderState.mainArm == HumanoidArm.RIGHT) {
                    this.rightArm.xRot -= Mth.PI / 4.0F * renderState.chargingProgress;
                } else {
                    this.leftArm.xRot -= Mth.PI / 4.0F * renderState.chargingProgress;
                }
            }
        }
    }
}
