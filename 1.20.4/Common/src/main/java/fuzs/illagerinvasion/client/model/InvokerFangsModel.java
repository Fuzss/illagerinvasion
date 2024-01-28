package fuzs.illagerinvasion.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class InvokerFangsModel<T extends Entity> extends HierarchicalModel<T> {
    private static final String BASE = "base";
    private static final String UPPER_JAW = "upper_jaw";
    private static final String LOWER_JAW = "lower_jaw";
    private static final String SPIKE1 = "spike1";
    private static final String SPIKE2 = "spike2";
    private static final String SPIKE3 = "spike3";
    private static final String SPIKE4 = "spike4";
    private final ModelPart root;
    private final ModelPart base;
    private final ModelPart upperJaw;
    private final ModelPart lowerJaw;
    private final ModelPart spike1;
    private final ModelPart spike2;
    private final ModelPart spike3;
    private final ModelPart spike4;


    public InvokerFangsModel(ModelPart root) {
        this.root = root;
        this.base = root.getChild(BASE);
        this.upperJaw = root.getChild(UPPER_JAW);
        this.lowerJaw = root.getChild(LOWER_JAW);
        this.spike1 = root.getChild(SPIKE1);
        this.spike2 = root.getChild(SPIKE2);
        this.spike3 = root.getChild(SPIKE3);
        this.spike4 = root.getChild(SPIKE4);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        CubeListBuilder modelPartBuilder2 = CubeListBuilder.create().texOffs(100, 0).addBox(-0.2F, 0.0F, -0.1F, 6.0f, 25.0f, 0.0f);
        modelPartData.addOrReplaceChild(BASE, CubeListBuilder.create().texOffs(0, 0).addBox(0.0f, 0.0f, 0.0f, 10.0f, 12.0f, 10.0f), PartPose.offset(-5.0f, 24.0f, -5.0f));
        modelPartData.addOrReplaceChild(SPIKE1, modelPartBuilder2, PartPose.offset(-0.2f, 0.0f, -0.1f));
        modelPartData.addOrReplaceChild(SPIKE2, modelPartBuilder2, PartPose.offset(-0.2f, 0.0f, -0.1f));
        modelPartData.addOrReplaceChild(SPIKE3, modelPartBuilder2, PartPose.offset(-0.2f, 0.0f, -0.1f));
        modelPartData.addOrReplaceChild(SPIKE4, modelPartBuilder2, PartPose.offset(-0.2f, 0.0f, -0.1f));
        CubeListBuilder modelPartBuilder = CubeListBuilder.create().texOffs(40, 0).addBox(0.0f, 0.0f, 0.0f, 4.0f, 14.0f, 8.0f);
        modelPartData.addOrReplaceChild(UPPER_JAW, modelPartBuilder, PartPose.offset(1.5f, 24.0f, -4.0f));
        modelPartData.addOrReplaceChild(LOWER_JAW, modelPartBuilder, PartPose.offsetAndRotation(-1.5f, 24.0f, 4.0f, 0.0f, (float) Math.PI, 0.0f));
        return LayerDefinition.create(modelData, 128, 64);
    }

    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float f = limbAngle * 2.0f;
        if (f > 1.0f) {
            f = 1.0f;
        }
        f = 1.0f - f * f * f;
        this.upperJaw.zRot = -Mth.cos(f * 1.8f) - 8.95f * 1.0688f;
        this.lowerJaw.zRot = Mth.cos(f * 1.8f) + 8.95f * 1.0688f;
        float g = (limbAngle + Mth.sin(limbAngle * 2.7f)) * 0.6f * 12.0f;
        this.spike1.yRot = 1f;
        this.spike2.yRot = -1f;
        this.spike3.yRot = -2f;
        this.spike4.yRot = 2f;
        this.lowerJaw.y = this.upperJaw.y = 24.0f - g;
        this.base.y = this.upperJaw.y;
        this.spike1.y = this.upperJaw.y * Mth.cos(g / 4.0f) + 1.6f;
        this.spike2.y = this.upperJaw.y * Mth.cos(g / 4.0f) + 1.6f;
        this.spike3.y = this.upperJaw.y * Mth.cos(g / 4.0f) + 1.6f;
        this.spike4.y = this.upperJaw.y * Mth.cos(g / 4.0f) + 1.6f;
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}

