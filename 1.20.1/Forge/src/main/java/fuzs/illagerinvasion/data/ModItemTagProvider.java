package fuzs.illagerinvasion.data;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.data.event.GatherDataEvent;

public class ModItemTagProvider extends AbstractTagProvider.Items {

    public ModItemTagProvider(GatherDataEvent evt, String modId) {
        super(evt, modId);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ItemTags.TRIM_MATERIALS).add(ModRegistry.PLATINUM_SHEET_ITEM.get());
    }
}
