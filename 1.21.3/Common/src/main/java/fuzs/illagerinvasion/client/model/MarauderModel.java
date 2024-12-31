package fuzs.illagerinvasion.client.model;

import fuzs.illagerinvasion.client.render.entity.state.MarauderRenderState;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

public class MarauderModel extends IllagerModel<MarauderRenderState> {
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
        if (renderState.mainArm == HumanoidArm.RIGHT) {
            if (renderState.isCharging) {
                this.rightArm.xRot = 3.7699115F;
            }
            if (!renderState.isAggressive) {
                this.rightArm.xRot = Mth.cos(renderState.walkAnimationPos * 0.6662F + Mth.PI) * 2.0F *
                        renderState.walkAnimationSpeed * 0.5F;
            }
        }
        if (renderState.mainArm == HumanoidArm.LEFT) {
            if (renderState.isCharging) {
                this.leftArm.xRot = 3.7699115F;
            }
            if (!renderState.isAggressive) {
                this.leftArm.xRot =
                        Mth.cos(renderState.walkAnimationPos * 0.6662F) * 2.0F * renderState.walkAnimationSpeed * 0.5F;
            }
        }
    }
}
