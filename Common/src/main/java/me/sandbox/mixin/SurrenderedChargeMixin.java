package me.sandbox.mixin;


import me.sandbox.entity.monster.Surrendered;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkeletonModel.class)
public class SurrenderedChargeMixin<T extends Mob> extends HumanoidModel<T> {

    public SurrenderedChargeMixin(ModelPart root) {
        super(root);
    }

    @Inject(at = @At("TAIL"), cancellable = true, method = "setAngles(Lnet/minecraft/entity/mob/MobEntity;FFFFF)V")
    public void chargingAnimation(T mobEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (mobEntity instanceof Surrendered) {
            if (((Surrendered)mobEntity).isCharging()) {
                this.rightArm.xRot = 3.7699115f;
                this.leftArm.xRot = 3.7699115f;
            }
        }
    }
}
