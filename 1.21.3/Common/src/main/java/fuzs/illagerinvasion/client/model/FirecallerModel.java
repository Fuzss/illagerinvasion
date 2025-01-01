package fuzs.illagerinvasion.client.model;


import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.AbstractIllager;

public class FirecallerModel extends CustomIllagerModel<IllagerRenderState> {
    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart staff;

    public FirecallerModel(final ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.leftArm = root.getChild("left_arm");
        this.staff = this.leftArm.getChild("staff");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = IllagerModel.createBodyLayer().mesh;
        PartDefinition partDefinition = meshDefinition.getRoot().getChild("left_arm");
        partDefinition.addOrReplaceChild("staff",
                CubeListBuilder.create().texOffs(112, 0).addBox(0.0F, -4.9F, -9.5F, 1.0F, 28.0F, 1.0F),
                PartPose.rotation(1.2F, 0.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 128, 64);
    }

    @Override
    public void setupAnim(IllagerRenderState renderState) {
        super.setupAnim(renderState);
        this.body.xRot = 0.2F;
        if (!renderState.isRiding && renderState.armPose == AbstractIllager.IllagerArmPose.NEUTRAL) {
            this.leftArm.xRot =
                    Mth.cos(renderState.walkAnimationPos * 0.6662F) * 0.5F * renderState.walkAnimationSpeed * 0.5F -
                            1.2F;
            AnimationUtils.bobModelPart(this.leftArm, renderState.ageInTicks, -1.0F);
            this.staff.visible = true;
        } else {
            this.staff.visible = false;
        }
    }
}

