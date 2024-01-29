package fuzs.illagerinvasion.data.tags;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.AbstractTagProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

public class ModBlockTagProvider extends AbstractTagProvider.Blocks {

    public ModBlockTagProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(ModRegistry.IMBUING_TABLE_BLOCK.value());
        this.tag(BlockTags.FIRE).add(ModRegistry.MAGIC_FIRE_BLOCK.value());
    }
}
