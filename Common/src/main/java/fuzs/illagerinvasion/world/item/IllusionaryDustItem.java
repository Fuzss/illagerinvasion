package fuzs.illagerinvasion.world.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


public class IllusionaryDustItem extends Item {

    public IllusionaryDustItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player playerEntity, InteractionHand hand) {
        ItemStack itemStack = playerEntity.getItemInHand(hand);
        double x = playerEntity.getX();
        double y = playerEntity.getY();
        double z = playerEntity.getZ();
        world.playLocalSound(x, y, z, SoundEvents.ILLUSIONER_MIRROR_MOVE, SoundSource.PLAYERS, 1.0f, 1.0f, false);
        if (world instanceof ServerLevel) {
            ((ServerLevel) world).sendParticles(ParticleTypes.CLOUD, x, y + 1, z, 15, 0.5D, 0.5D, 0.5D, 0.15D);
            playerEntity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 1200));
            playerEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200));
            playerEntity.getCooldowns().addCooldown(this, 100);
            if (!playerEntity.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
        }
        return InteractionResultHolder.success(itemStack);
    }
}
