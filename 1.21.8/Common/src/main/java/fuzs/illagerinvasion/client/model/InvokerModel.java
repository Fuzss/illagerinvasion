package fuzs.illagerinvasion.client.model;

import fuzs.illagerinvasion.client.render.entity.NecromancerRenderer;
import fuzs.illagerinvasion.client.render.entity.state.InvokerRenderState;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class InvokerModel extends IllagerModel<InvokerRenderState> {
    private final ModelPart body;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public InvokerModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer(CubeDeformation cubeDeformation) {
        MeshDefinition meshDefinition = NecromancerRenderer.createBodyLayer(cubeDeformation).mesh;
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(16, 20)
                        .addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, cubeDeformation)
                        .texOffs(0, 38)
                        .addBox(-4.0F, 0.0F, -3.0F, 8.0F, 22.0F, 6.0F, cubeDeformation.extend(0.5F)),
                PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    @Override
    public void setupAnim(InvokerRenderState renderState) {
        super.setupAnim(renderState);
        this.body.xRot = this.rightLeg.xRot = this.leftLeg.xRot = 0.15F * renderState.floatAnimationSpeed;
    }
}


