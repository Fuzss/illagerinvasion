package me.sandbox.mixin;

import me.sandbox.entity.monster.Basher;
import me.sandbox.entity.monster.Marauder;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.util.Mth;
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
public class IllagerModelMixin<T extends AbstractIllager>{


    @Shadow @Final private ModelPart rightArm;

    @Shadow @Final private ModelPart head;

    @Shadow @Final private ModelPart leftArm;

    @Inject(at = @At("TAIL"), cancellable = true, method = "setAngles(Lnet/minecraft/entity/mob/IllagerEntity;FFFFF)V")
    public void chargingAnimation(T illagerEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        ItemStack item = illagerEntity.getMainHandItem();
        HumanoidArm mainArm= illagerEntity.getMainArm();
        if (illagerEntity instanceof Basher) {
            AbstractIllager.IllagerArmPose state = illagerEntity.getArmPose();
            if (state == AbstractIllager.IllagerArmPose.ATTACKING && item.is(Items.SHIELD) && !(((Basher) illagerEntity).isStunned) && mainArm == HumanoidArm.RIGHT) {
                this.rightArm.xRot = this.rightArm.xRot * 0.5f + 0.05424779f;
                this.rightArm.yRot = -0.5235988f;

            }
            if (state == AbstractIllager.IllagerArmPose.ATTACKING && item.is(Items.SHIELD) && !(((Basher) illagerEntity).isStunned) && mainArm == HumanoidArm.LEFT) {
                this.leftArm.xRot = this.leftArm.xRot * 0.5f - 0.9424779f;
                this.leftArm.yRot = 0.5235988f;
            }
            if (((Basher)illagerEntity).getStunnedState()) {
                this.head.xRot = 20.35f;
                this.head.yRot = Mth.cos(h * 0.8f) * 0.3f;
                this.rightArm.xRot = -0.25f;
                this.leftArm.xRot = -0.25f;
            }
        }
        if (illagerEntity instanceof Marauder) {
            if (mainArm == HumanoidArm.RIGHT) {
                if (((Marauder) illagerEntity).isCharging()) {
                    this.rightArm.xRot = 3.7699115f;
                }
                if (!illagerEntity.isAggressive()) {
                    this.rightArm.xRot = Mth.cos(f * 0.6662f + (float) Math.PI) * 2.0f * g * 0.5f;
                }
            } else {
                if (((Marauder) illagerEntity).isCharging()) {
                    this.leftArm.xRot = 3.7699115f;
                }
                if (!illagerEntity.isAggressive()) {
                    this.leftArm.xRot = Mth.cos(f * 0.6662f) * 2.0f * g * 0.5f;
                }
            }
        }
    }
}

