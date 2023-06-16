package fuzs.illagerinvasion.client.handler;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnchantmentTooltipHandler {

    public static void onItemTooltip(ItemStack stack, @Nullable Player player, List<Component> lines, TooltipFlag context) {
        // just a stupid thing to turn enchantments above max level purple, saw this in the Goblin Trader mod and that's quite cool
        $1: for (Component component : lines) {
            if (component instanceof MutableComponent mutableComponent && mutableComponent.getContents() instanceof TranslatableContents enchantmentContents && enchantmentContents.getKey().startsWith("enchantment.")) {
                for (Component sibling : mutableComponent.getSiblings()) {
                    if (sibling.getContents() instanceof TranslatableContents levelContents && levelContents.getKey().startsWith("enchantment.level.")) {
                        int level;
                        try {
                            level = Integer.parseInt(levelContents.getKey().replace("enchantment.level.", ""));
                        } catch (NumberFormatException ignored) {
                            continue $1;
                        }
                        ResourceLocation resourceLocation = ResourceLocation.tryParse(enchantmentContents.getKey().replace("enchantment.", "").replace(".", ":"));
                        if (resourceLocation != null && BuiltInRegistries.ENCHANTMENT.containsKey(resourceLocation) && level > BuiltInRegistries.ENCHANTMENT.get(resourceLocation).getMaxLevel()) {
                            mutableComponent.withStyle(ChatFormatting.LIGHT_PURPLE);
                        }
                    }
                }
            }
        }
    }
}
