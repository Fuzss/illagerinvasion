package fuzs.illagerinvasion.world.inventory;

import fuzs.illagerinvasion.init.ModRegistry;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Map;

public class ImbuingMenu extends AbstractContainerMenu {
    private final Container input;
    private final ResultContainer output = new ResultContainer();
    private final ContainerLevelAccess access;
    public final DataSlot invalidState;

    public ImbuingMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public ImbuingMenu(int containerId, Inventory inventory, final ContainerLevelAccess access) {
        super(ModRegistry.IMBUING_MENU_TYPE.get(), containerId);
        this.access = access;
        this.invalidState = this.addDataSlot(DataSlot.standalone());
        this.input = new SimpleContainer(3) {

            @Override
            public void setChanged() {
                super.setChanged();
                ImbuingMenu.this.slotsChanged(this);
            }
        };
        this.addSlot(new Slot(this.input, 0, 26, 54) {

            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(Items.ENCHANTED_BOOK);
            }
        });
        this.addSlot(new Slot(this.input, 1, 80, 54) {

            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.isEnchanted();
            }
        });
        this.addSlot(new Slot(this.input, 2, 134, 54) {

            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModRegistry.HALLOWED_GEM_ITEM.get());
            }
        });
        this.addSlot(new Slot(this.output, 3, 80, 14) {

            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public boolean mayPickup(Player playerEntity) {
                return true;
            }

            @Override
            public void onTake(Player playerEntity, ItemStack itemStack) {
                ImbuingMenu.this.input.setItem(0, ItemStack.EMPTY);
                ImbuingMenu.this.input.setItem(1, ItemStack.EMPTY);
                ItemStack stack = ImbuingMenu.this.input.getItem(2);
                stack.shrink(1);
                ImbuingMenu.this.input.setItem(2, stack);
                ImbuingMenu.this.invalidState.set(InvalidImbuingState.ALL_GOOD.ordinal());
                playerEntity.playSound(ModRegistry.SORCERER_COMPLETE_CAST_SOUND_EVENT.get(), 1.0f, 1.0f);
            }
        });
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void slotsChanged(Container inventory) {
        if (inventory == this.input) {
            this.updateResult();
        }
        super.slotsChanged(inventory);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, ModRegistry.IMBUING_TABLE_BLOCK.get());
    }

    public void updateResult() {
        ItemStack imbuingItem = this.input.getItem(1);
        ItemStack bookStack = this.input.getItem(0);
        ItemStack gem = this.input.getItem(2);
        ItemStack imbuingResult = imbuingItem.copy();
        if (!bookStack.isEmpty() && !gem.isEmpty() && !imbuingItem.isEmpty()) {
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(bookStack);
            if (enchantments.size() != 1) {
                this.invalidState.set(InvalidImbuingState.TOO_MANY_ENCHANTMENTS.ordinal());
            } else {
                Enchantment enchantment = enchantments.keySet().iterator().next();
                if (!Registry.ENCHANTMENT.createIntrusiveHolder(enchantment).is(ModRegistry.IMBUING_ENCHANTMENT_TAG)) {
                    this.invalidState.set(InvalidImbuingState.INVALID_ENCHANTMENT.ordinal());
                } else if (enchantments.getOrDefault(enchantment, 0) != enchantment.getMaxLevel()) {
                    this.invalidState.set(InvalidImbuingState.NOT_AT_MAX_LEVEL.ordinal());
                } else if (!enchantment.canEnchant(imbuingItem)) {
                    this.invalidState.set(InvalidImbuingState.INVALID_ITEM.ordinal());
                } else {
                    Map<Enchantment, Integer> imbueMap = EnchantmentHelper.getEnchantments(imbuingItem);
                    if (imbueMap.getOrDefault(enchantment, 0) != enchantment.getMaxLevel()) {
                        this.invalidState.set(InvalidImbuingState.AT_WRONG_LEVEL.ordinal());
                    } else {
                        int imbueLevel = enchantments.get(enchantment) + 1;
                        imbueMap.put(enchantment, imbueLevel);
                        EnchantmentHelper.setEnchantments(imbueMap, imbuingResult);
                        this.output.setItem(0, imbuingResult);
                        this.invalidState.set(InvalidImbuingState.ALL_GOOD.ordinal());
                        return;
                    }
                }
            }
        } else {
            this.invalidState.set(InvalidImbuingState.ALL_GOOD.ordinal());
        }
        this.output.setItem(0, ItemStack.EMPTY);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (index == 3) {
                if (!this.moveItemStackTo(itemStack2, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemStack2, itemStack);
            } else if (index == 0 || index == 1 || index == 2) {
                if (!this.moveItemStackTo(itemStack2, 3, 39, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 3 && index < 39) {
                if (!this.moveItemStackTo(itemStack2, 0, 3, false)) {
                    return ItemStack.EMPTY;
                }
            }
            if (itemStack2.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, itemStack2);
        }
        return itemStack;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute((level, blockPos) -> {
            this.clearContainer(player, this.input);
        });
    }

    public enum InvalidImbuingState {
        ALL_GOOD(new TextComponent("")),
        TOO_MANY_ENCHANTMENTS(new TranslatableComponent("container.imbue.tooManyEnchantments")),
        NOT_AT_MAX_LEVEL(new TranslatableComponent("container.imbue.notAtMaxLevel")),
        AT_WRONG_LEVEL(new TranslatableComponent("container.imbue.atWrongLevel")),
        INVALID_ENCHANTMENT(new TranslatableComponent("container.imbue.invalidEnchantment")),
        INVALID_ITEM(new TranslatableComponent("container.imbue.invalidItem"));

        public final Component component;

        InvalidImbuingState(Component component) {
            this.component = component;
        }

        public String getTranslationKey() {
            return ((TranslatableComponent) this.component).getKey();
        }
    }
}