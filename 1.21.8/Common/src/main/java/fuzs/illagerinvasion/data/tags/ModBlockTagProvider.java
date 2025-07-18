package fuzs.illagerinvasion.data.tags;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.illagerinvasion.init.ModTags;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ModBlockTagProvider extends AbstractTagProvider<Block> {

    public ModBlockTagProvider(DataProviderContext context) {
        super(Registries.BLOCK, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(ModRegistry.IMBUING_TABLE_BLOCK.value());
        this.tag(BlockTags.FIRE).add(ModRegistry.MAGIC_FIRE_BLOCK.value());
        this.tag(ModTags.MAGIC_FIRE_REPLACEABLE_BLOCK_TAG)
                .add(Blocks.AIR, Blocks.SHORT_GRASS, Blocks.FERN, Blocks.TALL_GRASS);
    }
}
