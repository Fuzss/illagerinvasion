package fuzs.illagerinvasion.data.tags;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class ModBiomeTagProvider extends AbstractTagProvider<Biome> {

    public ModBiomeTagProvider(DataProviderContext context) {
        super(Registries.BIOME, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.add(ModRegistry.HAS_FIRECALLER_HUT_BIOME_TAG).addTag(BiomeTags.IS_BADLANDS);
        this.add(ModRegistry.HAS_ILLAGER_FORT_BIOME_TAG).addTag(BiomeTags.IS_TAIGA).add(Biomes.SNOWY_PLAINS);
        this.add(ModRegistry.HAS_ILLUSIONER_TOWER_BIOME_TAG).add(Biomes.TAIGA).add(Biomes.OLD_GROWTH_PINE_TAIGA).add(
                Biomes.OLD_GROWTH_SPRUCE_TAIGA).add(Biomes.DARK_FOREST).add(Biomes.SWAMP);
        this.add(ModRegistry.HAS_SORCERER_HUT_BIOME_TAG).add(Biomes.DARK_FOREST);
        this.add(ModRegistry.HAS_LABYRINTH_BIOME_TAG).addTag(BiomeTags.IS_TAIGA).addTag(BiomeTags.IS_JUNGLE).addTag(
                BiomeTags.IS_FOREST).addTag(BiomeTags.IS_SAVANNA);
    }
}
