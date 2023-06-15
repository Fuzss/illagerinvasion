package fuzs.illagerinvasion.world.item;

import fuzs.illagerinvasion.init.ModRegistry;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class HornOfSightItem extends Item {

    public HornOfSightItem(Properties settings) {
        super(settings);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof Player playerEntity)) {
            return;
        }
        ItemStack itemStack = playerEntity.getProjectile(stack);
        if (itemStack.isEmpty()) {
            return;
        }
        if (BowItem.getPowerForTime(this.getUseDuration(stack) - remainingUseTicks) < 0.1F) {
            return;
        }
        playerEntity.awardStat(Stats.ITEM_USED.get(this));
        playerEntity.getCooldowns().addCooldown(this, 30);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 60;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPYGLASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        user.playSound(ModRegistry.HORN_OF_SIGHT_SOUND_EVENT.get(), 1.0f, 1.0f);
        ItemStack itemInHand = user.getItemInHand(hand);
        user.level().getEntitiesOfClass(LivingEntity.class, user.getBoundingBox().inflate(30), entity -> !(entity instanceof Player)).forEach(entity -> {
            entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 400, 0));
        });
        user.startUsingItem(hand);
        user.getCooldowns().addCooldown(this, 80);
        return InteractionResultHolder.consume(itemInHand);
    }
}

