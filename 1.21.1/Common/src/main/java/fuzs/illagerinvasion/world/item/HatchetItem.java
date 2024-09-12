package fuzs.illagerinvasion.world.item;

import fuzs.illagerinvasion.init.ModItems;
import fuzs.illagerinvasion.world.entity.projectile.Hatchet;
import fuzs.puzzleslib.api.item.v2.ItemHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.UseAnim;
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
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -1.9F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level world, BlockPos pos, Player miner) {
        return !miner.isCreative();
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof Player playerEntity)) {
            return;
        }
        int i = this.getUseDuration(stack, user) - remainingUseTicks;
        if (i < 10) {
            return;
        }
        ItemHelper.hurtAndBreak(stack, 1, playerEntity, user.getUsedItemHand());
        Hatchet hatchet = new Hatchet(level, playerEntity, stack);
        hatchet.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), 0.0f, 1.0f + 0.5f, 1.0f);
        if (playerEntity.getAbilities().instabuild) {
            hatchet.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }
        level.addFreshEntity(hatchet);
        level.playSound(null, hatchet, SoundEvents.TRIDENT_THROW.value(), SoundSource.PLAYERS, 1.0f, 1.0f);
        if (!playerEntity.getAbilities().instabuild) {
            playerEntity.getInventory().removeItem(stack);
        }
        playerEntity.awardStat(Stats.ITEM_USED.get(this));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemInHand = user.getItemInHand(hand);
        if (itemInHand.getDamageValue() >= itemInHand.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(itemInHand);
        } else if (EnchantmentHelper.getTridentSpinAttackStrength(itemInHand, user) > 0.0F && !user.isInWaterOrRain()) {
            return InteractionResultHolder.fail(itemInHand);
        } else {
            user.startUsingItem(hand);
            return InteractionResultHolder.consume(itemInHand);
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        ItemHelper.hurtAndBreak(stack, 1, attacker, InteractionHand.MAIN_HAND);
        return true;
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack ingredient) {
        return ingredient.is(ModItems.PLATINUM_SHEET_ITEM.value()) || super.isValidRepairItem(stack, ingredient);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getDestroySpeed(world, pos) != 0.0F) {
            ItemHelper.hurtAndBreak(stack, 2, miner, InteractionHand.MAIN_HAND);
        }

        return true;
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        Hatchet hatchet = new Hatchet(level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1));
        hatchet.pickup = AbstractArrow.Pickup.ALLOWED;
        return hatchet;
    }
}
