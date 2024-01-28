package fuzs.illagerinvasion.client.model;

import fuzs.illagerinvasion.world.entity.monster.Surrendered;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelPart;

public class SurrenderedModel<T extends Surrendered> extends SkeletonModel<T> {

    public SurrenderedModel(ModelPart modelPart) {
        super(modelPart);
    }

    @Override
    public void setupAnim(T surrendered, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(surrendered, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        if (surrendered.isCharging()) {
            this.rightArm.xRot = 3.7699115f;
            this.leftArm.xRot = 3.7699115f;
        }
    }
}
