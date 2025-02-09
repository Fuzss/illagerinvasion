package fuzs.illagerinvasion.world.item;

import fuzs.illagerinvasion.init.ModSoundEvents;
import fuzs.puzzleslib.api.util.v1.InteractionResultHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
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

public class LostCandleItem extends Item {

    public LostCandleItem(Item.Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (level.isClientSide) {
            if (!this.iterateNearbyBlocks(player, level)) {
                level.playSound(player, player.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundSource.AMBIENT, 0.6F, 1.0F);
            }
        } else {
            player.getCooldowns().addCooldown(context.getItemInHand(), 60);
        }
        return InteractionResultHelper.sidedSuccess(level.isClientSide);
    }

    private boolean iterateNearbyBlocks(Player player, Level level) {
        for (BlockPos blockPos : BlockPos.withinManhattan(player.blockPosition(), 8, 8, 8)) {
            BlockState blockState = level.getBlockState(blockPos);
            for (CandleOreType type : CandleOreType.values()) {
                if (this.tryPlayOreSound(level, player, blockState, type)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean tryPlayOreSound(Level level, Player player, BlockState blockState, CandleOreType candleOreType) {
        if (blockState.is(candleOreType.blocks)) {
            level.playSound(player, player.blockPosition(), candleOreType.soundEvent.value(), SoundSource.AMBIENT, 1.0F, 1.0F);
            player.displayClientMessage(Component.translatable(this.getDescriptionId() + ".foundNearby", candleOreType.component), true);
            return true;
        } else {
            return false;
        }
    }

    public enum CandleOreType {
        DIAMOND(Blocks.DIAMOND_ORE, BlockTags.DIAMOND_ORES, ModSoundEvents.LOST_CANDLE_DIAMOND_SOUND_EVENT, ChatFormatting.AQUA),
        IRON(Blocks.IRON_ORE, BlockTags.IRON_ORES, ModSoundEvents.LOST_CANDLE_IRON_SOUND_EVENT, ChatFormatting.GRAY),
        GOLD(Blocks.GOLD_ORE, BlockTags.GOLD_ORES, ModSoundEvents.LOST_CANDLE_GOLD_SOUND_EVENT, ChatFormatting.GOLD),
        COPPER(Blocks.COPPER_ORE, BlockTags.COPPER_ORES, ModSoundEvents.LOST_CANDLE_COPPER_SOUND_EVENT, ChatFormatting.GOLD),
        COAL(Blocks.COAL_ORE, BlockTags.COAL_ORES, ModSoundEvents.LOST_CANDLE_COAL_SOUND_EVENT, ChatFormatting.GRAY);

        public final TagKey<Block> blocks;
        public final Holder<SoundEvent> soundEvent;
        public final Component component;

        CandleOreType(Block name, TagKey<Block> blocks, Holder<SoundEvent> soundEvent, ChatFormatting chatFormatting) {
            this.blocks = blocks;
            this.soundEvent = soundEvent;
            this.component = name.getName().withStyle(chatFormatting);
        }
    }
}
