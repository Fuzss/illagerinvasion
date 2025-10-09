package fuzs.illagerinvasion.world.item;

import fuzs.puzzleslib.api.util.v1.InteractionResultHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class IllusionaryDustItem extends Item {

    public IllusionaryDustItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemInHand = player.getItemInHand(interactionHand);
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        level.playLocalSound(x, y, z, SoundEvents.ILLUSIONER_MIRROR_MOVE, SoundSource.PLAYERS, 1.0f, 1.0f, false);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.CLOUD, x, y + 1, z, 15, 0.5D, 0.5D, 0.5D, 0.15D);
            player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 1200));
            player.addEffect(new MobEffectInstance(MobEffects.SPEED, 200));
            player.getCooldowns().addCooldown(itemInHand, 100);
            if (!player.getAbilities().instabuild) {
                itemInHand.shrink(1);
            }
        }
        return InteractionResultHelper.success(itemInHand);
    }
}
