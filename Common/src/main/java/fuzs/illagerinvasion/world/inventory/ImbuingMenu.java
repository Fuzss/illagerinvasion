package fuzs.illagerinvasion.world.inventory;

import fuzs.illagerinvasion.init.ModRegistry;
import me.sandbox.item.ItemRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Map;

public class ImbuingMenu extends AbstractContainerMenu {
    public static boolean bigBook;
    public static boolean badEnchant;
    public static boolean lowEnchant;
    public static boolean badItem;
    private final Container input = new SimpleContainer(3) {

        @Override
        public void setChanged() {
            super.setChanged();
            ImbuingMenu.this.slotsChanged(this);
        }
    };
    private final ResultContainer output = new ResultContainer();
    private final ContainerLevelAccess access;

    public ImbuingMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public ImbuingMenu(int containerId, Inventory inventory, final ContainerLevelAccess access) {
        super(ModRegistry.IMBUING_MENU_TYPE.get(), containerId);
        this.access = access;
        this.addSlot(new Slot(this.input, 0, 26, 54) {

            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(Items.ENCHANTED_BOOK);
            }

            @Override
            public void onTake(Player playerEntity, ItemStack itemStack) {
                ImbuingMenu.this.updateBooleans(false);
            }
        });
        this.addSlot(new Slot(this.input, 1, 80, 54) {

            @Override
            public void onTake(Player playerEntity, ItemStack itemStack) {
                ImbuingMenu.this.updateBooleans(false);
            }
        });
        this.addSlot(new Slot(this.input, 2, 134, 54) {

            @Override
            public void onTake(Player playerEntity, ItemStack itemStack) {
                ImbuingMenu.this.updateBooleans(false);
            }

            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ItemRegistry.HALLOWED_GEM);
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
                ImbuingMenu.this.input.getItem(2).grow(-1);
                ImbuingMenu.this.updateBooleans(false);
                playerEntity.playSound(ModRegistry.SORCERER_COMPLETE_CAST_SOUND_EVENT, 1.0f, 1.0f);
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
        super.slotsChanged(inventory);
        if (inventory == this.input) {
            this.updateResult();
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    protected boolean isUsableAsAddition(ItemStack stack) {
        return false;
    }

    public void updateBooleans(boolean value) {
        bigBook = value;
        badEnchant = value;
        lowEnchant = value;
        badItem = value;
    }

    public void updateResult() {
        ItemStack imbuingItem = this.input.getItem(1);
        ItemStack book = this.input.getItem(0);
        ItemStack gem = this.input.getItem(2);
        ItemStack imbuingResult = imbuingItem.copy();
        Map<Enchantment, Integer> bookmap = EnchantmentHelper.getEnchantments(book);
        if (!book.isEmpty() && !gem.isEmpty() && !imbuingItem.isEmpty()) {
            for (Enchantment bookEnchantment : bookmap.keySet()) {
                if (bookmap.size() > 1) {
                    bigBook = true;
                } else if (!BuiltInRegistries.ENCHANTMENT.wrapAsHolder(bookEnchantment).is(ModRegistry.IMBUING_ENCHANTMENT_TAG)) {
                    badEnchant = true;
                } else if (bookmap.getOrDefault(bookEnchantment, 0) != bookEnchantment.getMaxLevel()) {
                    lowEnchant = true;
                } else if (!bookEnchantment.canEnchant(imbuingItem)) {
                    badItem = true;
                } else {
                    int imbueLevel = bookmap.get(bookEnchantment) + 1;
                    Map<Enchantment, Integer> imbueMap = EnchantmentHelper.getEnchantments(imbuingItem);
                    for (Enchantment imbueEnchant : imbueMap.keySet()) {
                        int level = imbueMap.getOrDefault(imbueEnchant, 0);
                        bookmap.put(imbueEnchant, level);
                    }
                    bookmap.put(bookEnchantment, imbueLevel);
                    EnchantmentHelper.setEnchantments(bookmap, imbuingResult);
                    this.output.setItem(0, imbuingResult);
                    this.updateBooleans(false);
                    this.broadcastChanges();
                }
            }
        } else {
            this.output.setItem(0, ItemStack.EMPTY);
        }
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
                int i = this.isUsableAsAddition(itemStack) ? 1 : 0;
                if (!this.moveItemStackTo(itemStack2, i, 3, false)) {
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
}