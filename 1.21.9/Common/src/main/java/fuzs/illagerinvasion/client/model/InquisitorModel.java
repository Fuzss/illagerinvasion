package fuzs.illagerinvasion.client.model;

import fuzs.illagerinvasion.client.render.entity.state.StunnableIllagerRenderState;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.Items;

public class InquisitorModel extends CustomIllagerModel<StunnableIllagerRenderState> {
    private final ModelPart head;
    private final ModelPart rightArm;
    private final ModelPart leftArm;

    public InquisitorModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.leftArm = root.getChild("left_arm");
        this.rightArm = root.getChild("right_arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = IllagerModel.createBodyLayer().mesh;
        PartDefinition partDefinition = meshDefinition.getRoot().getChild("right_arm");
        PartDefinition partDefinition1 = meshDefinition.getRoot().getChild("left_arm");
        partDefinition1.addOrReplaceChild("left_shoulder_pad",
                CubeListBuilder.create()
                        .texOffs(0, 64)
                        .mirror()
                        .addBox(-0.7F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(1.1F)),
                PartPose.ZERO);
        partDefinition.addOrReplaceChild("right_shoulder_pad",
                CubeListBuilder.create()
                        .texOffs(0, 64)
                        .addBox(-3.3F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(1.1F)),
                PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 64, 128);
    }

    @Override
    public void setupAnim(StunnableIllagerRenderState renderState) {
        super.setupAnim(renderState);
        if (renderState.armPose == AbstractIllager.IllagerArmPose.ATTACKING) {
            if (renderState.offHandItem.is(Items.SHIELD)) {
                if (renderState.mainArm == HumanoidArm.RIGHT) {
                    this.leftArm.xRot = -1.05F;
                    this.leftArm.yRot = 0.5235988F;
                }
                if (renderState.mainArm == HumanoidArm.LEFT) {
                    this.rightArm.xRot = 200.0F;
                    this.rightArm.yRot = -0.5235988F;
                }
            }
            if (renderState.isStunned) {
                this.head.zRot = 0.3F * Mth.sin(0.45F * renderState.ageInTicks);
                this.head.xRot = 0.4F;
                this.rightArm.xRot = 200.0F;
                this.rightArm.yRot = 0.5235988F;
                this.leftArm.xRot = 200.0F;
                this.leftArm.yRot = -0.5235988F;
            }
        }
    }
}
