package fuzs.illagerinvasion.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.world.inventory.ImbuingMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ImbuingScreen extends AbstractContainerScreen<ImbuingMenu> {
    private static final ResourceLocation TEXTURE_LOCATION = IllagerInvasion.id("textures/gui/container/imbuing_table.png");

    public ImbuingScreen(ImbuingMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        this.renderBackground(poseStack);
        this.renderBg(poseStack, delta, mouseX, mouseY);
        super.render(poseStack, mouseX, mouseY, delta);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float delta, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TEXTURE_LOCATION);
        blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
        ImbuingMenu.InvalidImbuingState state = ImbuingMenu.InvalidImbuingState.values()[this.menu.invalidState.get()];
        if (state != ImbuingMenu.InvalidImbuingState.ALL_GOOD) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, TEXTURE_LOCATION);
            blit(poseStack, this.leftPos + 74, this.topPos + 32, 176, 0, 28, 21, 256, 256);
            if (this.isHovering(74, 32, 28, 21, mouseX, mouseY)) {
                this.renderTooltip(poseStack, state.component, mouseX, mouseY);
            }
        }
    }
}
