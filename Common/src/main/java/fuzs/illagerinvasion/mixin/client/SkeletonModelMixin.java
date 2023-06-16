package fuzs.illagerinvasion.mixin.client;

import fuzs.illagerinvasion.world.entity.monster.Surrendered;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.RangedAttackMob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkeletonModel.class)
abstract class SkeletonModelMixin<T extends Mob & RangedAttackMob> extends HumanoidModel<T> {

    public SkeletonModelMixin(ModelPart root) {
        super(root);
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/Entity;FFFFF)V", at = @At("TAIL"))
    public void setupAnim(Entity mobEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo callback) {
        if (mobEntity instanceof Surrendered surrendered) {
            if (surrendered.isCharging()) {
                this.rightArm.xRot = 3.7699115f;
                this.leftArm.xRot = 3.7699115f;
            }
        }
    }
}
