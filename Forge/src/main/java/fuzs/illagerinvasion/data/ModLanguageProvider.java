package fuzs.illagerinvasion.data;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractLanguageProvider;
import net.minecraft.data.PackOutput;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(PackOutput packOutput, String modId) {
        super(packOutput, modId);
    }

    @Override
    protected void addTranslations() {
        this.addCreativeModeTab(IllagerInvasion.MOD_NAME);
        this.add("container.imbue", "Imbue");
        this.add("container.imbue.tooManyEnchantments", "Book has too many enchantment!");
        this.add("container.imbue.notAtMaxLevel", "Book enchantment level is too low!");
        this.add("container.imbue.invalidEnchantment", "Book enchantment cannot be imbued!");
        this.add("container.imbue.invalidItem", "Item is not compatible with this enchantment!");
        this.add(ModRegistry.UNUSUAL_DUST_ITEM.get(), "Unusual Dust");
        this.add(ModRegistry.ILLUSIONARY_DUST_ITEM.get(), "Unusual Dust");
        this.add(ModRegistry.LOST_CANDLE_ITEM.get(), "Lost Candle");
        this.add(ModRegistry.HORN_OF_SIGHT_ITEM.get(), "Horn of Sight");
        this.add(ModRegistry.HALLOWED_GEM_ITEM.get(), "Hallowed Gem");
        this.add(ModRegistry.HATCHET_ITEM.get(), "Hatchet");
        this.add(ModRegistry.PLATINUM_CHUNK_ITEM.get(), "Platinum Chunk");
        this.add(ModRegistry.PLATINUM_SHEET_ITEM.get(), "Platinum Sheet");
        this.add(ModRegistry.PRIMAL_ESSENCE_ITEM.get(), "§bPrimal Essence");
        this.add(ModRegistry.PLATINUM_INFUSED_NETHERITE_HELMET_ITEM.get(), "Platinum Infused Netherite Helmet");
        this.add(ModRegistry.PLATINUM_INFUSED_NETHERITE_CHESTPLATE_ITEM.get(), "Platinum Infused Netherite Chestplate");
        this.add(ModRegistry.PLATINUM_INFUSED_NETHERITE_LEGGINGS_ITEM.get(), "Platinum Infused Netherite Leggings");
        this.add(ModRegistry.PLATINUM_INFUSED_NETHERITE_BOOTS_ITEM.get(), "Platinum Infused Netherite Boots");
        this.add(ModRegistry.PLATINUM_INFUSED_NETHERITE_SWORD_ITEM.get(), "Platinum Infused Netherite Sword");
        this.add(ModRegistry.PLATINUM_INFUSED_NETHERITE_PICKAXE_ITEM.get(), "Platinum Infused Netherite Pickaxe");
        this.add(ModRegistry.PLATINUM_INFUSED_NETHERITE_AXE_ITEM.get(), "Platinum Infused Netherite Axe");
        this.add(ModRegistry.PLATINUM_INFUSED_NETHERITE_HOE_ITEM.get(), "Platinum Infused Netherite Hoe");
        this.add(ModRegistry.PLATINUM_INFUSED_NETHERITE_SHOVEL_ITEM.get(), "Platinum Infused Netherite Shovel");
        this.add(ModRegistry.BERSERKING_POTION.get(), "Berserking");
        this.add(ModRegistry.PROVOKER_SPAWN_EGG_ITEM.get(), "Provoker Spawn Egg");
        this.add(ModRegistry.SURRENDERED_SPAWN_EGG_ITEM.get(), "Surrendered Spawn Egg");
        this.add(ModRegistry.NECROMANCER_SPAWN_EGG_ITEM.get(), "Necromancer Egg");
        this.add(ModRegistry.BASHER_SPAWN_EGG_ITEM.get(), "Basher Spawn Egg");
        this.add(ModRegistry.SORCERER_SPAWN_EGG_ITEM.get(), "Sorcerer Spawn Egg");
        this.add(ModRegistry.ARCHIVIST_SPAWN_EGG_ITEM.get(), "Archivist Spawn Egg");
        this.add(ModRegistry.INQUISITOR_SPAWN_EGG_ITEM.get(), "Inquisitor Spawn Egg");
        this.add(ModRegistry.MARAUDER_SPAWN_EGG_ITEM.get(), "Marauder Spawn Egg");
        this.add(ModRegistry.ALCHEMIST_SPAWN_EGG_ITEM.get(), "Alchemist Spawn Egg");
        this.add(ModRegistry.FIRECALLER_SPAWN_EGG_ITEM.get(), "Firecaller Spawn Egg");
        this.add(ModRegistry.IMBUING_TABLE_BLOCK.get(), "Imbuing Table");
        this.add(ModRegistry.MAGIC_FIRE_BLOCK.get(), "Magic Fire");
        this.add(ModRegistry.PROVOKER_ENTITY_TYPE.get(), "Provoker");
        this.add(ModRegistry.INVOKER_ENTITY_TYPE.get(), "Invoker");
        this.add(ModRegistry.SURRENDERED_ENTITY_TYPE.get(), "Surrendered");
        this.add(ModRegistry.NECROMANCER_ENTITY_TYPE.get(), "Necromancer");
        this.add(ModRegistry.BASHER_ENTITY_TYPE.get(), "Basher");
        this.add(ModRegistry.SORCERER_ENTITY_TYPE.get(), "Sorcerer");
        this.add(ModRegistry.ARCHIVIST_ENTITY_TYPE.get(), "Archivist");
        this.add(ModRegistry.INQUISITOR_ENTITY_TYPE.get(), "Inquisitor");
        this.add(ModRegistry.MARAUDER_ENTITY_TYPE.get(), "Marauder");
        this.add(ModRegistry.ALCHEMIST_ENTITY_TYPE.get(), "Alchemist");
        this.add(ModRegistry.FIRECALLER_ENTITY_TYPE.get(), "Firecaller");
        this.add("advancements.husbandry.platinum_infused_netherite_hoe.title", "Why?");
        this.add("advancements.husbandry.platinum_infused_netherite_hoe.description", "Use a platinum sheet to upgrade a hoe); and then definitely decide why you made this decision");
        this.add("advancements.adventure_find_mansion.title", "Home Sweet Mansion");
        this.add("advancements.adventure.find_mansion.description", "Enter a Woodland Mansion); what are those illagers hiding?");
        this.add("advancements.adventure.defeat_invoker.title", "Invoking Some Good Memories");
        this.add("advancements.adventure.defeat_invoker.description", "Defeat the Invoker");
        this.add("advancements.adventure.imbuing_table.title", "Untapped Potential");
        this.add("advancements.adventure.imbuing_table.description", "Obtain an Imbuing Table");
        this.add("advancements.nether.platinum_infused_netherite_armor.title", "Cover me in Platinum");
        this.add("advancements.nether.platinum_infused_netherite_armor.description", "Get a full suit of Platinum Infused Netherite armor");
        this.add("advancements.adventure.horn_of_sight.title", "Don't Toot Your Own Horn");
        this.add("advancements.adventure.horn_of_sight.description", "Obtain a Horn of Sight");
    }
}
