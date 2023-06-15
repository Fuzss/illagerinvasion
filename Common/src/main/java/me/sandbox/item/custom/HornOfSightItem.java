package me.sandbox.item.custom;

import fuzs.illagerinvasion.init.ModRegistry;
import net.minecraft.item.*;
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
import java.util.List;

public class HornOfSightItem extends Item {

    public HornOfSightItem(Properties settings) {
        super(settings);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        int i;
        float f;
        if (!(user instanceof Player)) {
            return;
        }
        Player playerEntity = (Player)user;
        ItemStack itemStack = playerEntity.getProjectile(stack);
        if (itemStack.isEmpty()) {
            return;
        }
        if ((double)(f = BowItem.getPowerForTime(i = this.getUseDuration(stack) - remainingUseTicks)) < 0.1) {
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

    private List<LivingEntity> getTargets(Player user) {
        return user.level.getEntitiesOfClass(LivingEntity.class, user.getBoundingBox().inflate(30), entity -> (entity instanceof LivingEntity) && !(entity instanceof Player));
    }
    private void glow(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 400, 0));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        user.playSound(ModRegistry.HORN_OF_SIGHT_SOUND_EVENT, 1.0f, 1.0f);
            ItemStack itemStack = user.getItemInHand(hand);
                getTargets(user).forEach(this::glow);
                user.startUsingItem(hand);
                user.getCooldowns().addCooldown(this, 80);
                return InteractionResultHolder.consume(itemStack);
            }
    }

