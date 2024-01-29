package fuzs.illagerinvasion.client.handler;

import fuzs.illagerinvasion.handler.PlatinumTrimHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class EnchantmentTooltipHandler {

    public static void onItemTooltip$1(ItemStack stack, @Nullable Player player, List<Component> lines, TooltipFlag context) {
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

    public static void onItemTooltip$2(ItemStack stack, @Nullable Player player, List<Component> lines, TooltipFlag context) {
        if (player != null && stack.getItem() instanceof ArmorItem item) {
            Optional<Component> optional = PlatinumTrimHandler.getPlatinumTrim(player.level(), stack).map(t -> t.material().value().description());
            if (optional.isPresent()) {
                int index = -1;
                $1: for (int i = 0; i < lines.size(); i++) {
                    Component component = lines.get(i);
                    if (component.getContents().equals(CommonComponents.SPACE.getContents())) {
                        for (Component sibling : component.getSiblings()) {
                            if (sibling == optional.get()) {
                                index = i;
                                break $1;
                            }
                        }
                    }
                }
                Component newComponent = CommonComponents.space().append(Component.translatable(PlatinumTrimHandler.PLATINUM_TRIM_TRANSLATION_KEYS.get(item.getType()))).withStyle(optional.get().getStyle());
                if (++index > lines.size()) {
                    lines.add(newComponent);
                } else {
                    lines.add(index, newComponent);
                }
            }
        }
    }
}
