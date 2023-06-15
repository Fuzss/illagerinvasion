
package fuzs.illagerinvasion.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.TagParser;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Illusioner.class)
abstract class IllusionerMixin extends AbstractIllager
{
    protected IllusionerMixin(EntityType<? extends AbstractIllager> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "attack(Lnet/minecraft/entity/LivingEntity;F)V", at = @At("HEAD"), cancellable = true)
    public void shootFirework(LivingEntity target, float pullProgress, CallbackInfo callbackInfo) {
        ItemStack firework = new ItemStack(Items.FIREWORK_ROCKET);
        try {
            firework.addTagElement("Fireworks", TagParser.parseTag("{Flight:3,Explosions:[{Type:1,Flicker:0,Trail:0,Colors:[I;2437522],FadeColors:[I;2437522]},{Type:1,Flicker:0,Trail:0,Colors:[I;8073150],FadeColors:[I;8073150]},{Type:1,Flicker:0,Trail:0,Colors:[I;3887386],FadeColors:[I;3887386]}]}"));
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        if (this.random.nextInt(3) == 0 && this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0), entity -> entity instanceof Player).isEmpty()) {
                Projectile projectile = new FireworkRocketEntity(this.level(), firework, this, this.getX(), this.getEyeY() - 0.15000000596046448, this.getZ(), true);
                double d = target.getX() - this.getX();
                double e2 = target.getY(0.3333333333333333) - projectile.getY();
                double f = target.getZ() - this.getZ();
                double g = Math.sqrt(d * d + f * f);
                projectile.shoot(d, e2 + 0.4000000059604645, f - 1.0, 1.6f, (float) (14 - this.level().getDifficulty().getId() * 4));
                this.level().addFreshEntity(projectile);
                callbackInfo.cancel();
            }
    }
}
