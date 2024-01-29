package fuzs.illagerinvasion.data.tags;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.AbstractTagProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;

public class ModItemTagProvider extends AbstractTagProvider.Items {

    public ModItemTagProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(ItemTags.TRIM_MATERIALS).add(ModRegistry.PLATINUM_SHEET_ITEM.value());
    }
}
