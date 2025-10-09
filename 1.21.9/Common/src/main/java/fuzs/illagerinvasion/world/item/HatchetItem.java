package fuzs.illagerinvasion.world.item;

import fuzs.illagerinvasion.world.entity.projectile.ThrownHatchet;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
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
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;

public class HatchetItem extends TridentItem {

    public HatchetItem(Item.Properties properties) {
        super(properties);
    }

    public static ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 6.0, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, -1.9, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.BOW;
    }

    @Override
    public boolean releaseUsing(ItemStack itemStack, Level level, LivingEntity user, int remainingUseTicks) {
        if (user instanceof Player player) {
            int i = this.getUseDuration(itemStack, user) - remainingUseTicks;
            if (i >= 10 && !itemStack.nextDamageWillBreak() && level instanceof ServerLevel serverLevel) {
                itemStack.hurtWithoutBreaking(1, player);
                ThrownHatchet thrownHatchet = Projectile.spawnProjectileFromRotation(ThrownHatchet::new,
                        serverLevel,
                        itemStack,
                        player,
                        0.0F,
                        1.5F,
                        1.0F);
                if (player.hasInfiniteMaterials()) {
                    thrownHatchet.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                } else {
                    player.getInventory().removeItem(itemStack);
                }
                level.playSound(null,
                        thrownHatchet,
                        SoundEvents.TRIDENT_THROW.value(),
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F);
                player.awardStat(Stats.ITEM_USED.get(this));
                return true;
            }
        }

        return false;
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        ThrownHatchet thrownHatchet = new ThrownHatchet(level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1));
        thrownHatchet.pickup = AbstractArrow.Pickup.ALLOWED;
        return thrownHatchet;
    }
}
