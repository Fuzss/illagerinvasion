package fuzs.illagerinvasion.data;

import fuzs.illagerinvasion.init.ModRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagsProvider extends TagsProvider<Biome> {

    protected ModBiomeTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, ExistingFileHelper fileHelper) {
        super(packOutput, Registries.BIOME, lookupProvider, modId, fileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModRegistry.HAS_FIRECALLER_HUT_BIOME_TAG).addTag(BiomeTags.IS_BADLANDS);
        this.tag(ModRegistry.HAS_ILLAGER_FORT_BIOME_TAG).addTag(BiomeTags.IS_TAIGA).add(Biomes.SNOWY_PLAINS);
        this.tag(ModRegistry.HAS_ILLUSIONER_TOWER_BIOME_TAG).add(Biomes.TAIGA).add(Biomes.OLD_GROWTH_PINE_TAIGA).add(Biomes.OLD_GROWTH_SPRUCE_TAIGA).add(Biomes.DARK_FOREST).add(Biomes.SWAMP);
        this.tag(ModRegistry.HAS_SORCERER_HUT_BIOME_TAG).add(Biomes.DARK_FOREST);
    }
}
