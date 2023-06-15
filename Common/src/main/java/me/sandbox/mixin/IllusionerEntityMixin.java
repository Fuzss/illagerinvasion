
package me.sandbox.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.nbt.TagParser;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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
import java.util.List;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Illusioner.class)
public class IllusionerEntityMixin extends AbstractIllager
{
    protected IllusionerEntityMixin(final EntityType<? extends AbstractIllager> entityType, final Level world) {
        super(entityType, world);
    }

    @Inject(at = { @At("HEAD") }, cancellable = true, method = { "attack(Lnet/minecraft/entity/LivingEntity;F)V" })
    public void shootFirework(final LivingEntity target, final float pullProgress, final CallbackInfo callbackInfo) {
        int randvalue;
        final ItemStack firework = new ItemStack(Items.FIREWORK_ROCKET);
        if (FabricLoader.getInstance().isModLoaded("friendsandfoes")) {
            try {
                firework.addTagElement("Fireworks", TagParser.parseTag("{Flight:3,Explosions:[{Type:1,Flicker:0,Trail:0,Colors:[I;8073150],FadeColors:[I;8073150]},{Type:1,Flicker:0,Trail:0,Colors:[I;15790320],FadeColors:[I;15790320]},{Type:1,Flicker:0,Trail:0,Colors:[I;11250603],FadeColors:[I;11250603]}]}"));
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
            }
            randvalue = this.random.nextInt(8);
        }  else {
            try {
                firework.addTagElement("Fireworks", TagParser.parseTag("{Flight:3,Explosions:[{Type:1,Flicker:0,Trail:0,Colors:[I;2437522],FadeColors:[I;2437522]},{Type:1,Flicker:0,Trail:0,Colors:[I;8073150],FadeColors:[I;8073150]},{Type:1,Flicker:0,Trail:0,Colors:[I;3887386],FadeColors:[I;3887386]}]}"));
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
            }
            randvalue = this.random.nextInt(3);
        }
            if (randvalue == 0 && this.getNearbyPlayers().isEmpty()) {
                final Projectile projectileEntity = new FireworkRocketEntity(this.level, firework, this, this.getX(), this.getEyeY() - 0.15000000596046448, this.getZ(), true);
                final double d = target.getX() - this.getX();
                final double e2 = target.getY(0.3333333333333333) - projectileEntity.getY();
                final double f = target.getZ() - this.getZ();
                final double g = Math.sqrt(d * d + f * f);
                projectileEntity.shoot(d, e2 + 0.4000000059604645, f - 1.0, 1.6f, (float) (14 - this.level.getDifficulty().getId() * 4));
                this.level.addFreshEntity(projectileEntity);
                callbackInfo.cancel();
            }
        }

    private List<LivingEntity> getNearbyPlayers() {
        return this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0), entity -> entity instanceof Player);
    }

    public void applyRaidBuffs(final int wave, final boolean unused) {
    }

    public SoundEvent getCelebrateSound() {
        return SoundEvents.ILLUSIONER_AMBIENT;
    }
}
