package fuzs.illagerinvasion.client.handler;

import fuzs.illagerinvasion.handler.PlatinumTrimHandler;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class EnchantmentTooltipHandler {

    public static void onItemTooltip(ItemStack itemStack, List<Component> lines, Item.TooltipContext tooltipContext, @Nullable Player player, TooltipFlag tooltipFlag) {
        if (player != null && itemStack.has(DataComponents.EQUIPPABLE)) {
            Optional<Component> optional = PlatinumTrimHandler.getPlatinumTrim(player.level(), itemStack).map(holder -> holder.material().value().description());
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
                String translationKey = PlatinumTrimHandler.PLATINUM_TRIM_TRANSLATION_KEYS.get(itemStack.get(DataComponents.EQUIPPABLE)
                        .slot());
                Component newComponent = CommonComponents.space().append(Component.translatable(translationKey).withStyle(optional.get().getStyle()));
                if (++index > lines.size()) {
                    lines.add(newComponent);
                } else {
                    lines.add(index, newComponent);
                }
            }
        }
    }
}
