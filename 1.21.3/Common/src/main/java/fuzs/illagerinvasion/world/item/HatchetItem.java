package fuzs.illagerinvasion.world.item;

import fuzs.illagerinvasion.world.entity.projectile.ThrownHatchet;
import fuzs.puzzleslib.api.item.v2.ItemHelper;
import fuzs.puzzleslib.api.util.v1.InteractionResultHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class HatchetItem extends Item implements ProjectileItem {

    public HatchetItem(Item.Properties properties) {
        super(properties);
    }

    public static ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 6.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -1.9, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public boolean canAttackBlock(BlockState blockState, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public boolean releaseUsing(ItemStack itemStack, Level level, LivingEntity user, int remainingUseTicks) {
        if (user instanceof Player player) {
            int i = this.getUseDuration(itemStack, user) - remainingUseTicks;
            if (i >= 10) {
                ItemHelper.hurtAndBreak(itemStack, 1, player, user.getUsedItemHand());
                ThrownHatchet thrownHatchet = new ThrownHatchet(level, player, itemStack);
                thrownHatchet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 1.0f + 0.5f, 1.0f);
                if (player.getAbilities().instabuild) {
                    thrownHatchet.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }
                level.addFreshEntity(thrownHatchet);
                level.playSound(null, thrownHatchet, SoundEvents.TRIDENT_THROW.value(), SoundSource.PLAYERS, 1.0f, 1.0f);
                if (!player.getAbilities().instabuild) {
                    player.getInventory().removeItem(itemStack);
                }
                player.awardStat(Stats.ITEM_USED.get(this));
                return true;
            }
        }

        return false;
    }

    @Override
    public InteractionResult use(Level level, Player user, InteractionHand interactionHand) {
        ItemStack itemInHand = user.getItemInHand(interactionHand);
        if (itemInHand.getDamageValue() >= itemInHand.getMaxDamage() - 1) {
            return InteractionResultHelper.fail(itemInHand);
        } else if (EnchantmentHelper.getTridentSpinAttackStrength(itemInHand, user) > 0.0F && !user.isInWaterOrRain()) {
            return InteractionResultHelper.fail(itemInHand);
        } else {
            user.startUsingItem(interactionHand);
            return InteractionResultHelper.consume(itemInHand);
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        ItemHelper.hurtAndBreak(stack, 1, attacker, InteractionHand.MAIN_HAND);
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getDestroySpeed(level, pos) != 0.0F) {
            ItemHelper.hurtAndBreak(itemStack, 2, miner, InteractionHand.MAIN_HAND);
        }

        return true;
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        ThrownHatchet thrownHatchet = new ThrownHatchet(level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1));
        thrownHatchet.pickup = AbstractArrow.Pickup.ALLOWED;
        return thrownHatchet;
    }
}
