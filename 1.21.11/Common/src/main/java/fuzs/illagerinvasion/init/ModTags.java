package fuzs.illagerinvasion.init;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.puzzleslib.api.init.v3.tags.TagFactory;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class ModTags {
    static final TagFactory TAGS = TagFactory.make(IllagerInvasion.MOD_ID);
    public static final TagKey<Block> MAGIC_FIRE_REPLACEABLE_BLOCK_TAG = TAGS.registerBlockTag("magic_fire_replaceable");
    public static final TagKey<Biome> HAS_LABYRINTH_BIOME_TAG = TAGS.registerBiomeTag("has_structure/labyrinth");
    public static final TagKey<Biome> HAS_SORCERER_HUT_BIOME_TAG = TAGS.registerBiomeTag("has_structure/sorcerer_hut");
    public static final TagKey<Biome> HAS_ILLUSIONER_TOWER_BIOME_TAG = TAGS.registerBiomeTag(
            "has_structure/illusioner_tower");
    public static final TagKey<Biome> HAS_ILLAGER_FORT_BIOME_TAG = TAGS.registerBiomeTag("has_structure/illager_fort");
    public static final TagKey<Biome> HAS_FIRECALLER_HUT_BIOME_TAG = TAGS.registerBiomeTag(
            "has_structure/firecaller_hut");
    public static final TagKey<Block> ORES_BLOCK_TAG = TagFactory.COMMON.registerBlockTag("ores");

    public static void bootstrap() {
        // NO-OP
    }
}
