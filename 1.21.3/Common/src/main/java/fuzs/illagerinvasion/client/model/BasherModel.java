package fuzs.illagerinvasion.client.model;

import fuzs.illagerinvasion.client.render.entity.state.StunnableIllagerRenderState;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BasherModel extends IllagerModel<StunnableIllagerRenderState> {
    private final ModelPart head;
    private final ModelPart rightArm;
    private final ModelPart leftArm;

    public BasherModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.leftArm = root.getChild("left_arm");
        this.rightArm = root.getChild("right_arm");
    }

    @Override
    public void setupAnim(StunnableIllagerRenderState renderState) {
        super.setupAnim(renderState);
        ItemStack item = renderState.getMainHandItem();
        if (renderState.armPose == AbstractIllager.IllagerArmPose.ATTACKING && item.is(Items.SHIELD) &&
                !renderState.isStunned) {
            if (renderState.mainArm == HumanoidArm.RIGHT) {
                this.rightArm.xRot = this.rightArm.xRot * 0.5F + 0.05424779F;
                this.rightArm.yRot = -0.5235988F;

            }
            if (renderState.mainArm == HumanoidArm.LEFT) {
                this.leftArm.xRot = this.leftArm.xRot * 0.5F - 0.9424779F;
                this.leftArm.yRot = 0.5235988F;
            }
        }
        if (renderState.isStunned) {
            this.head.xRot = 20.35F;
            this.head.yRot = Mth.cos(renderState.ageInTicks * 0.8F) * 0.3F;
            this.rightArm.xRot = -0.25F;
            this.leftArm.xRot = -0.25F;
        }
    }
}
