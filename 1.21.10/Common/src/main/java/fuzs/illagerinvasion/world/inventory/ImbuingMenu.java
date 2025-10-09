package fuzs.illagerinvasion.world.inventory;

import fuzs.illagerinvasion.init.ModItems;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.illagerinvasion.init.ModSoundEvents;
import fuzs.illagerinvasion.world.item.enchantment.ImbuingEnchantmentLevel;
import fuzs.puzzleslib.api.container.v1.QuickMoveRuleSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ImbuingMenu extends AbstractContainerMenu {
    private final Container input;
    private final ResultContainer output = new ResultContainer();
    private final ContainerLevelAccess access;
    public final DataSlot imbuingState;

    public ImbuingMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public ImbuingMenu(int containerId, Inventory inventory, final ContainerLevelAccess access) {
        super(ModRegistry.IMBUING_MENU_TYPE.value(), containerId);
        this.access = access;
        this.imbuingState = this.addDataSlot(DataSlot.standalone());
        this.input = new SimpleContainer(3) {

            @Override
            public void setChanged() {
                super.setChanged();
                ImbuingMenu.this.slotsChanged(this);
            }
        };
        this.addSlot(new Slot(this.input, 0, 26, 54) {

            @Override
            public boolean mayPlace(ItemStack itemStack) {
                return itemStack.is(Items.ENCHANTED_BOOK);
            }
        });
        this.addSlot(new Slot(this.input, 1, 80, 54) {

            @Override
            public boolean mayPlace(ItemStack itemStack) {
                return EnchantmentHelper.hasAnyEnchantments(itemStack);
            }
        });
        this.addSlot(new Slot(this.input, 2, 134, 54) {

            @Override
            public boolean mayPlace(ItemStack itemStack) {
                return itemStack.is(ModItems.HALLOWED_GEM_ITEM);
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
                ImbuingMenu.this.imbuingState.set(ImbuingState.ALL_GOOD.ordinal());
                playerEntity.playSound(ModSoundEvents.SORCERER_COMPLETE_CAST_SOUND_EVENT.value(), 1.0f, 1.0f);
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
        this.slotsChanged(this.input);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, ModRegistry.IMBUING_TABLE_BLOCK.value());
    }

    @Override
    public void slotsChanged(Container inventory) {
        if (inventory == this.input) {
            this.access.execute((Level level, BlockPos blockPos) -> {
                this.updateResult();
            });
        }
        super.slotsChanged(inventory);
    }

    protected void updateResult() {
        ItemStack bookItem = this.input.getItem(0);
        ItemStack imbuingItem = this.input.getItem(1);
        ItemStack gemItem = this.input.getItem(2);
        ImbuingState imbuingState = this.selectImbuingState(bookItem, imbuingItem, gemItem);
        this.imbuingState.set(imbuingState.ordinal());
        ItemStack itemStack;
        if (imbuingState == ImbuingState.ALL_GOOD) {
            ItemEnchantments bookEnchantments = EnchantmentHelper.getEnchantmentsForCrafting(bookItem);
            Holder<Enchantment> enchantment = bookEnchantments.keySet().iterator().next();
            itemStack = imbuingItem.copy();
            int imbuedLevel = bookEnchantments.getLevel(enchantment) + 1;
            itemStack.enchant(enchantment, imbuedLevel);
        } else {
            itemStack = ItemStack.EMPTY;
        }
        this.output.setItem(0, itemStack);
    }

    protected ImbuingState selectImbuingState(ItemStack bookItem, ItemStack imbuingItem, ItemStack gemItem) {
        if (bookItem.isEmpty()) {
            return ImbuingState.ENCHANTED_BOOK_MISSING;
        } else if (gemItem.isEmpty()) {
            return ImbuingState.HALLOWED_GEM_MISSING;
        } else if (imbuingItem.isEmpty()) {
            return ImbuingState.ITEM_MISSING;
        } else {
            ItemEnchantments bookEnchantments = EnchantmentHelper.getEnchantmentsForCrafting(bookItem);
            if (bookEnchantments.size() != 1) {
                return ImbuingState.TOO_MANY_ENCHANTMENTS;
            } else {
                Holder<Enchantment> enchantment = bookEnchantments.keySet().iterator().next();
                int bookEnchantmentLevel = bookEnchantments.getLevel(enchantment);
                if (!ImbuingEnchantmentLevel.isSupportedByImbuing(enchantment) ||
                        bookEnchantmentLevel >= ImbuingEnchantmentLevel.getImbuingMaxEnchantmentLevel(enchantment)) {
                    return ImbuingState.INVALID_ENCHANTMENT;
                } else {
                    ItemEnchantments itemEnchantments = EnchantmentHelper.getEnchantmentsForCrafting(imbuingItem);
                    int itemEnchantmentLevel = itemEnchantments.getLevel(enchantment);
                    if (itemEnchantmentLevel == 0) {
                        return ImbuingState.ENCHANTMENTS_NOT_MATCHING;
                    } else if (itemEnchantmentLevel != bookEnchantmentLevel) {
                        return ImbuingState.LEVELS_NOT_EQUAL;
                    } else {
                        return ImbuingState.ALL_GOOD;
                    }
                }
            }
        }
    }

    public ImbuingState getImbuingState() {
        return ImbuingMenu.ImbuingState.values()[this.imbuingState.get()];
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return QuickMoveRuleSet.of(this, this::moveItemStackTo)
                .addContainerSlotRule(0, 2, 1)
                .addInventoryRules()
                .addInventoryCompartmentRules()
                .quickMoveStack(player, index);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute((Level level, BlockPos blockPos) -> {
            this.clearContainer(player, this.input);
        });
    }

    public enum ImbuingState {
        ALL_GOOD(null),
        ENCHANTED_BOOK_MISSING("container.imbue.enchantedBookMissing") {
            @Override
            public Component getComponent() {
                return Component.translatable(this.translationKey, Items.ENCHANTED_BOOK.getName());
            }
        },
        ITEM_MISSING(null),
        HALLOWED_GEM_MISSING("container.imbue.hallowedGemMissing") {
            @Override
            public Component getComponent() {
                return Component.translatable(this.translationKey, ModItems.HALLOWED_GEM_ITEM.value().getName());
            }
        },
        TOO_MANY_ENCHANTMENTS("container.imbue.tooManyEnchantments"),
        INVALID_ENCHANTMENT("container.imbue.invalidEnchantment"),
        ENCHANTMENTS_NOT_MATCHING("container.imbue.enchantmentsNotMatching"),
        LEVELS_NOT_EQUAL("container.imbue.levelsNotEqual");

        @Nullable
        final String translationKey;

        ImbuingState(@Nullable String translationKey) {
            this.translationKey = translationKey;
        }

        public Component getComponent() {
            return this.translationKey != null ? Component.translatable(this.translationKey) : CommonComponents.EMPTY;
        }

        public boolean showTooltip() {
            return this != ALL_GOOD && this != ITEM_MISSING;
        }
    }
}