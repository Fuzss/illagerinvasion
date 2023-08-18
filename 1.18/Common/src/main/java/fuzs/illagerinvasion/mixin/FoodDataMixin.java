//package fuzs.illagerinvasion.mixin;
//
//import fuzs.illagerinvasion.handler.PlatinumTrimHandler;
//import net.minecraft.world.entity.EquipmentSlot;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.food.FoodData;
//import org.jetbrains.annotations.Nullable;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.ModifyVariable;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Mixin(FoodData.class)
//abstract class FoodDataMixin {
//    @Shadow
//    private float saturationLevel;
//    @Nullable
//    private Player illagerinvasion$lastPlayer;
//
//    @Inject(method = "tick", at = @At("HEAD"))
//    public void tick(Player player, CallbackInfo callback) {
//        // this should better be a capability on player that tracks last exhaustion once during player tick, but nah for now
//        this.illagerinvasion$lastPlayer = player;
//    }
//
//    @ModifyVariable(method = "addExhaustion", at = @At("HEAD"), argsOnly = true)
//    public float addExhaustion(float exhaustion) {
//        // filter out exhaustion from regenerating health
//        if (exhaustion != 6.0F && exhaustion != this.saturationLevel) {
//            if (this.illagerinvasion$lastPlayer != null && PlatinumTrimHandler.hasPlatinumTrim(this.illagerinvasion$lastPlayer, EquipmentSlot.LEGS)) {
//                if (exhaustion > 0.0F) {
//                    exhaustion /= 2.0F;
//                } else if (exhaustion < 0.0F) {
//                    exhaustion *= 2.0F;
//                }
//                return exhaustion;
//            }
//        }
//        return exhaustion;
//    }
//}
