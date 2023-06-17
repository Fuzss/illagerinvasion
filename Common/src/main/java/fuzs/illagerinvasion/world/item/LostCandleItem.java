package fuzs.illagerinvasion.world.item;

import fuzs.illagerinvasion.init.ModRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class LostCandleItem extends Item {

    public LostCandleItem(Item.Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        $1: if (level.isClientSide) {
            for (BlockPos pos : BlockPos.withinManhattan(player.blockPosition(), 8, 8, 8)) {
                BlockState state = level.getBlockState(pos);
                for (CandleOreType type : CandleOreType.values()) {
                    if (this.tryPlayOreSound(level, player, state, type)) {
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

    private boolean tryPlayOreSound(Level level, Player player, BlockState state, CandleOreType type) {
        if (state.is(type.blocks)) {
            level.playSound(player, player.blockPosition(), type.soundEvent.get(), SoundSource.AMBIENT, 1.0F, 1.0F);
            player.displayClientMessage(Component.translatable(this.getDescriptionId() + ".foundNearby", type.component), true);
            return true;
        }
        return false;
    }

    public enum CandleOreType {
        DIAMOND(Blocks.DIAMOND_ORE, BlockTags.DIAMOND_ORES, ModRegistry.LOST_CANDLE_DIAMOND_SOUND_EVENT::get, ChatFormatting.AQUA),
        IRON(Blocks.IRON_ORE, BlockTags.IRON_ORES, ModRegistry.LOST_CANDLE_IRON_SOUND_EVENT::get, ChatFormatting.GRAY),
        GOLD(Blocks.GOLD_ORE, BlockTags.GOLD_ORES, ModRegistry.LOST_CANDLE_GOLD_SOUND_EVENT::get, ChatFormatting.GOLD),
        COPPER(Blocks.COPPER_ORE, BlockTags.COPPER_ORES, ModRegistry.LOST_CANDLE_COPPER_SOUND_EVENT::get, ChatFormatting.GOLD),
        COAL(Blocks.COAL_ORE, BlockTags.COAL_ORES, ModRegistry.LOST_CANDLE_COAL_SOUND_EVENT::get, ChatFormatting.GRAY);

        public final TagKey<Block> blocks;
        public final Supplier<SoundEvent> soundEvent;
        public final Component component;

        CandleOreType(Block name, TagKey<Block> blocks, Supplier<SoundEvent> soundEvent, ChatFormatting chatFormatting) {
            this.blocks = blocks;
            this.soundEvent = soundEvent;
            this.component = name.getName().withStyle(chatFormatting);
        }
    }
}
