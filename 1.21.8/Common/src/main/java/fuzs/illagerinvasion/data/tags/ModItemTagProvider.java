package fuzs.illagerinvasion.data.tags;

import fuzs.illagerinvasion.init.ModItems;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

public class ModItemTagProvider extends AbstractTagProvider<Item> {

    public ModItemTagProvider(DataProviderContext context) {
        super(Registries.ITEM, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(ItemTags.TRIM_MATERIALS).add(ModItems.PLATINUM_SHEET_ITEM.value());
        this.tag(ItemTags.DURABILITY_ENCHANTABLE).add(ModItems.PLATINUM_INFUSED_HATCHET_ITEM);
        this.tag(ItemTags.SHARP_WEAPON_ENCHANTABLE).add(ModItems.PLATINUM_INFUSED_HATCHET_ITEM);
    }
}
