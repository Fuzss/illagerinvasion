package fuzs.illagerinvasion.init;

import fuzs.extensibleenums.api.v2.BuiltInEnumFactories;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.world.inventory.ImbuingMenu;
import fuzs.illagerinvasion.world.level.block.ImbuingTableBlock;
import fuzs.illagerinvasion.world.level.block.MagicFireBlock;
import fuzs.illagerinvasion.world.level.levelgen.structure.pools.LegacySingleNoLiquidPoolElement;
import fuzs.illagerinvasion.world.level.levelgen.structure.pools.SingleNoLiquidPoolElement;
import fuzs.illagerinvasion.world.level.levelgen.structure.structures.LabyrinthStructure;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import fuzs.puzzleslib.api.init.v3.tags.TagFactory;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootTable;

public class ModRegistry {
    static final RegistryManager REGISTRIES = RegistryManager.from(IllagerInvasion.MOD_ID);
    public static final Holder.Reference<Block> IMBUING_TABLE_BLOCK = REGISTRIES.registerBlock("imbuing_table",
            ImbuingTableBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK));
    public static final Holder.Reference<Block> MAGIC_FIRE_BLOCK = REGISTRIES.registerBlock("magic_fire",
            MagicFireBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_FIRE).mapColor(MapColor.COLOR_PURPLE).randomTicks());
    public static final Holder.Reference<Potion> BERSERKING_POTION = REGISTRIES.registerPotion("berserking",
            (String name) -> new Potion(name,
                    new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 1),
                    new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 1)));
    public static final Holder.Reference<Potion> LONG_BERSERKING_POTION = REGISTRIES.registerPotion("long_berserking",
            () -> new Potion("berserking",
                    new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 0),
                    new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0)));
    public static final Holder.Reference<Potion> STRONG_BERSERKING_POTION = REGISTRIES.registerPotion(
            "strong_berserking",
            () -> new Potion("berserking",
                    new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, 2),
                    new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 2)));
    public static final Holder.Reference<MenuType<ImbuingMenu>> IMBUING_MENU_TYPE = REGISTRIES.registerMenuType(
            "imbuing",
            () -> ImbuingMenu::new);
    public static final Holder.Reference<SimpleParticleType> MAGIC_FLAME_PARTICLE_TYPE = REGISTRIES.register(Registries.PARTICLE_TYPE,
            "magic_flame",
            () -> new SimpleParticleType(false));
    public static final Holder.Reference<SimpleParticleType> NECROMANCER_BUFF_PARTICLE_TYPE = REGISTRIES.register(
            Registries.PARTICLE_TYPE,
            "necromancer_buff",
            () -> new SimpleParticleType(false));
    public static final Holder.Reference<StructurePoolElementType<SinglePoolElement>> SINGLE_POOL_ELEMENT_TYPE = REGISTRIES.register(
            Registries.STRUCTURE_POOL_ELEMENT,
            "single_pool_element",
            () -> () -> SingleNoLiquidPoolElement.CODEC);
    public static final Holder.Reference<StructurePoolElementType<LegacySinglePoolElement>> LEGACY_SINGLE_POOL_ELEMENT_TYPE = REGISTRIES.register(
            Registries.STRUCTURE_POOL_ELEMENT,
            "legacy_single_pool_element",
            () -> () -> LegacySingleNoLiquidPoolElement.CODEC);
    public static final Holder.Reference<StructureType<LabyrinthStructure>> LABYRINTH_STRUCTURE_TYPE = REGISTRIES.register(
            Registries.STRUCTURE_TYPE,
            "labyrinth",
            () -> () -> LabyrinthStructure.CODEC);

    public static final ResourceKey<TrimMaterial> PLATINUM_TRIM_MATERIAL = REGISTRIES.makeResourceKey(Registries.TRIM_MATERIAL,
            "platinum");
    public static final ResourceKey<Instrument> REVEAL_INSTRUMENT = REGISTRIES.makeResourceKey(Registries.INSTRUMENT,
            "reveal");

    static final TagFactory TAGS = TagFactory.make(IllagerInvasion.MOD_ID);
    public static final TagKey<Enchantment> IMBUING_ENCHANTMENT_TAG = TAGS.registerEnchantmentTag("imbuing");
    public static final TagKey<Instrument> HORN_OF_SIGHT_INSTRUMENT_TAG = TAGS.registerTagKey(Registries.INSTRUMENT,
            "horn_of_sight");
    public static final TagKey<Biome> HAS_FIRECALLER_HUT_BIOME_TAG = TAGS.registerBiomeTag(
            "has_structure/firecaller_hut");
    public static final TagKey<Biome> HAS_ILLAGER_FORT_BIOME_TAG = TAGS.registerBiomeTag("has_structure/illager_fort");
    public static final TagKey<Biome> HAS_ILLUSIONER_TOWER_BIOME_TAG = TAGS.registerBiomeTag(
            "has_structure/illusioner_tower");
    public static final TagKey<Biome> HAS_SORCERER_HUT_BIOME_TAG = TAGS.registerBiomeTag("has_structure/sorcerer_hut");
    public static final TagKey<Biome> HAS_LABYRINTH_BIOME_TAG = TAGS.registerBiomeTag("has_structure/labyrinth");
    public static final TagKey<Block> MAGIC_FIRE_REPLACEABLE_BLOCK_TAG = TAGS.registerBlockTag("magic_fire_replaceable");

    public static final ResourceKey<LootTable> ILLAGER_FORT_TOWER_LOOT_TABLE = REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
            "chests/illager_fort_tower");
    public static final ResourceKey<LootTable> ILLAGER_FORT_GROUND_LOOT_TABLE = REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
            "chests/illager_fort_ground");
    public static final ResourceKey<LootTable> ILLUSIONER_TOWER_ENTRANCE_LOOT_TABLE = REGISTRIES.makeResourceKey(
            Registries.LOOT_TABLE,
            "chests/illusioner_tower_entrance");
    public static final ResourceKey<LootTable> ILLUSIONER_TOWER_STAIRS_LOOT_TABLE = REGISTRIES.makeResourceKey(
            Registries.LOOT_TABLE,
            "chests/illusioner_tower_stairs");
    public static final ResourceKey<LootTable> LABYRINTH_LOOT_TABLE = REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
            "chests/labyrinth");
    public static final ResourceKey<LootTable> LABYRINTH_MAP_LOOT_TABLE = REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
            "chests/labyrinth_map");
    public static final ResourceKey<LootTable> SORCERER_HUT_LOOT_TABLE = REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
            "chests/sorcerer_hut");
    public static final ResourceKey<LootTable> ILLUSIONER_INJECT_LOOT_TABLE = REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
            "entities/inject/illusioner");
    public static final ResourceKey<LootTable> PILLAGER_INJECT_LOOT_TABLE = REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
            "entities/inject/pillager");
    public static final ResourceKey<LootTable> RAVAGER_INJECT_LOOT_TABLE = REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
            "entities/inject/ravager");

    public static final SpellcasterIllager.IllagerSpell ENCHANT_ILLAGER_SPELL = registerIllagerSpell("enchant",
            121,
            161,
            161);
    public static final SpellcasterIllager.IllagerSpell CONJURE_FLAMES_ILLAGER_SPELL = registerIllagerSpell(
            "conjure_flames",
            194,
            41,
            36);
    public static final SpellcasterIllager.IllagerSpell CONJURE_TELEPORT_ILLAGER_SPELL = registerIllagerSpell(
            "conjure_teleport",
            64,
            35,
            81);
    public static final SpellcasterIllager.IllagerSpell NECRO_RAISE_ILLAGER_SPELL = registerIllagerSpell("necro_raise",
            78,
            73,
            52);
    public static final SpellcasterIllager.IllagerSpell CONJURE_SKULL_BOLT_ILLAGER_SPELL = registerIllagerSpell(
            "conjure_skull_bolt",
            44,
            53,
            26);
    public static final SpellcasterIllager.IllagerSpell PROVOKE_ILLAGER_SPELL = registerIllagerSpell("provoke",
            235,
            123,
            109);

    private static SpellcasterIllager.IllagerSpell registerIllagerSpell(String internalName, int redColor, int greenColor, int blueColor) {
        return BuiltInEnumFactories.INSTANCE.createIllagerSpell(IllagerInvasion.id(internalName),
                redColor / 255.0,
                greenColor / 255.0,
                blueColor / 255.0);
    }

    public static void bootstrap() {
        ModItems.bootstrap();
        ModEntityTypes.bootstrap();
        ModSoundEvents.bootstrap();
    }
}
