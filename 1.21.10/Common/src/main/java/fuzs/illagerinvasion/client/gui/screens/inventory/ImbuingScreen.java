package fuzs.illagerinvasion.client.gui.screens.inventory;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.world.inventory.ImbuingMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CyclingSlotBackground;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class ImbuingScreen extends AbstractContainerScreen<ImbuingMenu> {
    private static final ResourceLocation TEXTURE_LOCATION = IllagerInvasion.id(
            "textures/gui/container/imbuing_table.png");
    private static final ResourceLocation EMPTY_SLOT_HELMET = ResourceLocation.withDefaultNamespace(
            "container/slot/helmet");
    private static final ResourceLocation EMPTY_SLOT_CHESTPLATE = ResourceLocation.withDefaultNamespace(
            "container/slot/chestplate");
    private static final ResourceLocation EMPTY_SLOT_LEGGINGS = ResourceLocation.withDefaultNamespace(
            "container/slot/leggings");
    private static final ResourceLocation EMPTY_SLOT_BOOTS = ResourceLocation.withDefaultNamespace(
            "container/slot/boots");
    private static final ResourceLocation EMPTY_SLOT_HOE = ResourceLocation.withDefaultNamespace("container/slot/hoe");
    private static final ResourceLocation EMPTY_SLOT_AXE = ResourceLocation.withDefaultNamespace("container/slot/axe");
    private static final ResourceLocation EMPTY_SLOT_SWORD = ResourceLocation.withDefaultNamespace(
            "container/slot/sword");
    private static final ResourceLocation EMPTY_SLOT_SHOVEL = ResourceLocation.withDefaultNamespace(
            "container/slot/shovel");
    private static final ResourceLocation EMPTY_SLOT_PICKAXE = ResourceLocation.withDefaultNamespace(
            "container/slot/pickaxe");
    private static final ResourceLocation EMPTY_SLOT_BOOK = IllagerInvasion.id("container/slot/book");
    private static final ResourceLocation EMPTY_SLOT_GEM = IllagerInvasion.id("container/slot/ruby");
    private static final List<ResourceLocation> EMPTY_SLOT_BOOK_ICONS = List.of(EMPTY_SLOT_BOOK);
    private static final List<ResourceLocation> EMPTY_SLOT_TOOL_ICONS = List.of(EMPTY_SLOT_HELMET,
            EMPTY_SLOT_CHESTPLATE,
            EMPTY_SLOT_LEGGINGS,
            EMPTY_SLOT_BOOTS,
            EMPTY_SLOT_HOE,
            EMPTY_SLOT_AXE,
            EMPTY_SLOT_SWORD,
            EMPTY_SLOT_SHOVEL,
            EMPTY_SLOT_PICKAXE);
    private static final List<ResourceLocation> EMPTY_SLOT_GEM_ICONS = List.of(EMPTY_SLOT_GEM);

    private final CyclingSlotBackground bookIcon = new CyclingSlotBackground(0);
    private final CyclingSlotBackground toolIcon = new CyclingSlotBackground(1);
    private final CyclingSlotBackground gemIcon = new CyclingSlotBackground(2);

    public ImbuingScreen(ImbuingMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        this.bookIcon.tick(EMPTY_SLOT_BOOK_ICONS);
        this.toolIcon.tick(EMPTY_SLOT_TOOL_ICONS);
        this.gemIcon.tick(EMPTY_SLOT_GEM_ICONS);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
        if (this.menu.getImbuingState().showTooltip()) {
            if (this.isHovering(74, 32, 28, 20, mouseX, mouseY)) {
                guiGraphics.setTooltipForNextFrame(this.menu.getImbuingState().getComponent(), mouseX, mouseY);
            }
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                TEXTURE_LOCATION,
                this.leftPos,
                this.topPos,
                0,
                0,
                this.imageWidth,
                this.imageHeight,
                256,
                256);
        this.bookIcon.render(this.menu, guiGraphics, partialTick, this.leftPos, this.topPos);
        this.toolIcon.render(this.menu, guiGraphics, partialTick, this.leftPos, this.topPos);
        this.gemIcon.render(this.menu, guiGraphics, partialTick, this.leftPos, this.topPos);
        if (this.menu.getImbuingState().showTooltip()) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                    TEXTURE_LOCATION,
                    this.leftPos + 74,
                    this.topPos + 32,
                    176,
                    0,
                    28,
                    20,
                    256,
                    256);
        }
    }
}
