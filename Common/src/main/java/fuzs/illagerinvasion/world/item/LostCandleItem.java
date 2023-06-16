package fuzs.illagerinvasion.world.item;

import com.google.common.collect.ImmutableMap;
import fuzs.illagerinvasion.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.function.Supplier;

public class LostCandleItem extends Item {
    private static final Map<TagKey<Block>, Supplier<SoundEvent>> ORE_SOUND_EVENTS = ImmutableMap.of(
            BlockTags.DIAMOND_ORES, ModRegistry.LOST_CANDLE_DIAMOND_SOUND_EVENT::get,
            BlockTags.IRON_ORES, ModRegistry.LOST_CANDLE_IRON_SOUND_EVENT::get,
            BlockTags.GOLD_ORES, ModRegistry.LOST_CANDLE_GOLD_SOUND_EVENT::get,
            BlockTags.COPPER_ORES, ModRegistry.LOST_CANDLE_COPPER_SOUND_EVENT::get,
            BlockTags.COAL_ORES, ModRegistry.LOST_CANDLE_COAL_SOUND_EVENT::get
    );

    public LostCandleItem(Item.Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        $1: if (level.isClientSide) {
            for (BlockPos pos : BlockPos.withinManhattan(player.blockPosition(), 10, 10, 10)) {
                BlockState state = level.getBlockState(pos);
                for (Map.Entry<TagKey<Block>, Supplier<SoundEvent>> entry : ORE_SOUND_EVENTS.entrySet()) {
                    if (tryPlayOreSound(level, player, state, entry.getKey(), entry.getValue().get())) {
                        break $1;
                    }
                }
            }
            level.playSound(player, player.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundSource.AMBIENT, 0.6F, 1.0F);
        } else {
            player.getCooldowns().addCooldown(this, 60);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private static boolean tryPlayOreSound(Level level, Player player, BlockState state, TagKey<Block> tagKey, SoundEvent soundEvent) {
        if (state.is(tagKey)) {
            level.playSound(player, player.blockPosition(), soundEvent, SoundSource.AMBIENT, 1.0F, 1.0F);
            return true;
        }
        return false;
    }
}
