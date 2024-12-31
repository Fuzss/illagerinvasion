package fuzs.illagerinvasion.client.model;

import fuzs.illagerinvasion.client.render.entity.state.SurrenderedRenderState;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelPart;

public class SurrenderedModel extends SkeletonModel<SurrenderedRenderState> {

    public SurrenderedModel(ModelPart root) {
        super(root);
    }

    @Override
    public void setupAnim(SurrenderedRenderState renderState) {
        super.setupAnim(renderState);
        if (renderState.isCharging) {
            this.rightArm.xRot = 3.7699115F;
            this.leftArm.xRot = 3.7699115F;
        }
    }
}
