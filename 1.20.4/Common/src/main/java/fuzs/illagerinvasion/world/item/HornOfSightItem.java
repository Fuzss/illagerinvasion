package fuzs.illagerinvasion.world.item;

import fuzs.illagerinvasion.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class HornOfSightItem extends InstrumentItem {

    public HornOfSightItem(Properties properties, TagKey<Instrument> tagKey) {
        super(properties, tagKey);
    }

    private static void tryAddInstrument(ItemStack stack) {
        if (!stack.hasTag() || !stack.getTag().contains("instrument", Tag.TAG_STRING)) {
            CompoundTag compoundTag = stack.getOrCreateTag();
            compoundTag.putString("instrument", ModRegistry.REVEAL_INSTRUMENT.getResourceLocation().toString());
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        tryAddInstrument(player.getItemInHand(usedHand));
        InteractionResultHolder<ItemStack> result = super.use(level, player, usedHand);
        if (result.getResult().shouldAwardStats() && !level.isClientSide) {
            BlockPos pos = player.blockPosition();
            for (LivingEntity livingentity : level.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(48.0))) {
                if (livingentity instanceof Enemy && livingentity.isAlive() && !livingentity.isRemoved() && pos.closerToCenterThan(livingentity.position(), 48.0)) {
                    livingentity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 60));
                }
            }
        }
        return result;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        tryAddInstrument(stack);
        return super.getUseDuration(stack);
    }
}

