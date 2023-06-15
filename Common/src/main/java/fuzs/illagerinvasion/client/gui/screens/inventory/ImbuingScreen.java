package fuzs.illagerinvasion.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.world.inventory.ImbuingMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ImbuingScreen extends AbstractContainerScreen<ImbuingMenu> {
    private static final Component BOOK_BIG_COMPONENT = Component.translatable("Book must have at least one enchantment!");
    private static final Component TOO_LOW_COMPONENT = Component.translatable("Book enchantment level is too low!");
    private static final Component BAD_ENCHANT_COMPONENT = Component.translatable("Book enchantment cannot be imbued!");
    private static final Component BAD_ITEM_COMPONENT = Component.translatable("Item is not compatible with this enchantment!");
    private static final ResourceLocation TEXTURE_LOCATION = IllagerInvasion.id("textures/gui/imbue_table.png");

    public ImbuingScreen(ImbuingMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics);
        this.renderBg(guiGraphics, delta, mouseX, mouseY);
        super.render(guiGraphics, mouseX, mouseY, delta);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE_LOCATION);
        guiGraphics.blit(TEXTURE_LOCATION, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        if (ImbuingMenu.bigBook && !ImbuingMenu.lowEnchant && !ImbuingMenu.badEnchant) {
            guiGraphics.blit(TEXTURE_LOCATION, 226, 81, 176, 0, 36, 23);
            guiGraphics.drawString(this.font, BOOK_BIG_COMPONENT, 165, 40, 0xFF6060);
        }
        if (ImbuingMenu.badEnchant && !ImbuingMenu.bigBook && !ImbuingMenu.lowEnchant) {
            guiGraphics.blit(TEXTURE_LOCATION, 226, 81, 176, 0, 36, 23);
            guiGraphics.drawString(this.font, BAD_ENCHANT_COMPONENT, 151, 40, 0xFF6060);
        }
        if (ImbuingMenu.lowEnchant && !ImbuingMenu.bigBook && !ImbuingMenu.badEnchant) {
            guiGraphics.blit(TEXTURE_LOCATION, 226, 81, 176, 0, 36, 23);
            guiGraphics.drawString(this.font, TOO_LOW_COMPONENT, 155, 40, 0xFF6060);
        }
        if (ImbuingMenu.badItem && !ImbuingMenu.badEnchant && !ImbuingMenu.bigBook && !ImbuingMenu.lowEnchant) {
            guiGraphics.blit(TEXTURE_LOCATION, 226, 81, 176, 0, 36, 23);
            guiGraphics.drawString(this.font, BAD_ITEM_COMPONENT, 155, 40, 0xFF6060);
        }
    }
}
