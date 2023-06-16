package fuzs.illagerinvasion.mixin.client;

import fuzs.illagerinvasion.world.entity.monster.Basher;
import fuzs.illagerinvasion.world.entity.monster.Marauder;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IllagerModel.class)
abstract class IllagerModelMixin<T extends AbstractIllager> extends HierarchicalModel<T> {
    @Shadow
    @Final
    private ModelPart rightArm;
    @Shadow
    @Final
    private ModelPart head;
    @Shadow
    @Final
    private ModelPart leftArm;

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/Entity;FFFFF)V", at = @At("TAIL"), cancellable = true)
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo callback) {
        AbstractIllager illagerEntity = (AbstractIllager) entity;
        ItemStack item = illagerEntity.getMainHandItem();
        HumanoidArm mainArm = illagerEntity.getMainArm();
        if (illagerEntity instanceof Basher basher) {
            AbstractIllager.IllagerArmPose state = basher.getArmPose();
            if (state == AbstractIllager.IllagerArmPose.ATTACKING && item.is(Items.SHIELD) && !basher.getStunnedState() && mainArm == HumanoidArm.RIGHT) {
                this.rightArm.xRot = this.rightArm.xRot * 0.5f + 0.05424779f;
                this.rightArm.yRot = -0.5235988f;

            }
            if (state == AbstractIllager.IllagerArmPose.ATTACKING && item.is(Items.SHIELD) && !basher.getStunnedState() && mainArm == HumanoidArm.LEFT) {
                this.leftArm.xRot = this.leftArm.xRot * 0.5f - 0.9424779f;
                this.leftArm.yRot = 0.5235988f;
            }
            if (basher.getStunnedState()) {
                this.head.xRot = 20.35f;
                this.head.yRot = Mth.cos(ageInTicks * 0.8f) * 0.3f;
                this.rightArm.xRot = -0.25f;
                this.leftArm.xRot = -0.25f;
            }
        }
        if (illagerEntity instanceof Marauder marauder) {
            if (mainArm == HumanoidArm.RIGHT) {
                if (marauder.isCharging()) {
                    this.rightArm.xRot = 3.7699115f;
                }
                if (!marauder.isAggressive()) {
                    this.rightArm.xRot = Mth.cos(limbSwing * 0.6662f + (float) Math.PI) * 2.0f * limbSwingAmount * 0.5f;
                }
            } else {
                if (marauder.isCharging()) {
                    this.leftArm.xRot = 3.7699115f;
                }
                if (!marauder.isAggressive()) {
                    this.leftArm.xRot = Mth.cos(limbSwing * 0.6662f) * 2.0f * limbSwingAmount * 0.5f;
                }
            }
        }
    }
}

