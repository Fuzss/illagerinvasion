package fuzs.illagerinvasion.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class HornOfSightItem extends InstrumentItem {

    public HornOfSightItem(TagKey<Instrument> tagKey, Properties properties) {
        super(tagKey, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        // NO-OP
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        InteractionResult interactionResult = super.use(level, player, usedHand);
        if (interactionResult.consumesAction() && !level.isClientSide) {
            BlockPos pos = player.blockPosition();
            for (LivingEntity livingentity : level.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(48.0))) {
                if (livingentity instanceof Enemy && livingentity.isAlive() && !livingentity.isRemoved() && pos.closerToCenterThan(livingentity.position(), 48.0)) {
                    livingentity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 60));
                }
            }
        }

        return interactionResult;
    }
}

