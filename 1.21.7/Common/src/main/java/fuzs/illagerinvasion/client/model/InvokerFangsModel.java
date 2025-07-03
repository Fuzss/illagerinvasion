package fuzs.illagerinvasion.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.EvokerFangsRenderState;
import net.minecraft.util.Mth;

public class InvokerFangsModel extends EntityModel<EvokerFangsRenderState> {
    private final ModelPart base;
    private final ModelPart upperJaw;
    private final ModelPart lowerJaw;
    private final ModelPart spike1;
    private final ModelPart spike2;
    private final ModelPart spike3;
    private final ModelPart spike4;

    public InvokerFangsModel(ModelPart root) {
        super(root);
        this.base = root.getChild("base");
        this.upperJaw = root.getChild("upper_jaw");
        this.lowerJaw = root.getChild("lower_jaw");
        this.spike1 = root.getChild("spike1");
        this.spike2 = root.getChild("spike2");
        this.spike3 = root.getChild("spike3");
        this.spike4 = root.getChild("spike4");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        CubeListBuilder modelPartBuilder2 = CubeListBuilder.create()
                .texOffs(100, 0)
                .addBox(-0.2F, 0.0F, -0.1F, 6.0F, 25.0F, 0.0F);
        modelPartData.addOrReplaceChild("base",
                CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 10.0F, 12.0F, 10.0F),
                PartPose.offset(-5.0F, 24.0F, -5.0F));
        modelPartData.addOrReplaceChild("spike1", modelPartBuilder2, PartPose.offset(-0.2F, 0.0F, -0.1F));
        modelPartData.addOrReplaceChild("spike2", modelPartBuilder2, PartPose.offset(-0.2F, 0.0F, -0.1F));
        modelPartData.addOrReplaceChild("spike3", modelPartBuilder2, PartPose.offset(-0.2F, 0.0F, -0.1F));
        modelPartData.addOrReplaceChild("spike4", modelPartBuilder2, PartPose.offset(-0.2F, 0.0F, -0.1F));
        CubeListBuilder modelPartBuilder = CubeListBuilder.create()
                .texOffs(40, 0)
                .addBox(0.0F, 0.0F, 0.0F, 4.0F, 14.0F, 8.0F);
        modelPartData.addOrReplaceChild("upper_jaw", modelPartBuilder, PartPose.offset(1.5F, 24.0F, -4.0F));
        modelPartData.addOrReplaceChild("lower_jaw",
                modelPartBuilder,
                PartPose.offsetAndRotation(-1.5F, 24.0F, 4.0F, 0.0F, Mth.PI, 0.0F));
        return LayerDefinition.create(modelData, 128, 64);
    }

    @Override
    public void setupAnim(EvokerFangsRenderState renderState) {
        super.setupAnim(renderState);
        float biteProgress = renderState.biteProgress;
        float jawRotationProgress = Math.min(biteProgress * 2.0F, 1.0F);
        jawRotationProgress = 1.0F - jawRotationProgress * jawRotationProgress * jawRotationProgress;
        this.upperJaw.zRot = -Mth.cos(jawRotationProgress * 1.8F) - 8.95F * 1.0688F;
        this.lowerJaw.zRot = Mth.cos(jawRotationProgress * 1.8F) + 8.95F * 1.0688F;
        float h = (biteProgress + Mth.sin(biteProgress * 2.7F)) * 0.6F * 12.0F;
        this.spike1.yRot = 1.0F;
        this.spike2.yRot = -1.0F;
        this.spike3.yRot = -2.0F;
        this.spike4.yRot = 2.0F;
        this.lowerJaw.y = this.upperJaw.y = 24.0F - h;
        this.base.y = this.upperJaw.y;
        this.spike1.y = this.upperJaw.y * Mth.cos(h / 4.0F) + 1.6F;
        this.spike2.y = this.upperJaw.y * Mth.cos(h / 4.0F) + 1.6F;
        this.spike3.y = this.upperJaw.y * Mth.cos(h / 4.0F) + 1.6F;
        this.spike4.y = this.upperJaw.y * Mth.cos(h / 4.0F) + 1.6F;
    }
}

