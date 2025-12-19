package fuzs.illagerinvasion.data.client;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.init.ModEntityTypes;
import fuzs.illagerinvasion.init.ModItems;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.illagerinvasion.init.ModSoundEvents;
import fuzs.illagerinvasion.world.inventory.ImbuingMenu;
import fuzs.illagerinvasion.world.level.block.ImbuingTableBlock;
import fuzs.puzzleslib.api.client.data.v2.AbstractLanguageProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.init.v3.registry.ResourceKeyHelper;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations(TranslationBuilder builder) {
        builder.addCreativeModeTab(ModRegistry.CREATIVE_MODE_TAB, IllagerInvasion.MOD_NAME);
        builder.add(ImbuingTableBlock.CONTAINER_IMBUE, "Imbue");
        builder.add(ImbuingMenu.ImbuingState.ENCHANTED_BOOK_MISSING.getComponent(), "%s is missing!");
        builder.add(ImbuingMenu.ImbuingState.HALLOWED_GEM_MISSING.getComponent(), "%s is missing!");
        builder.add(ImbuingMenu.ImbuingState.TOO_MANY_ENCHANTMENTS.getComponent(), "Book has too many enchantments!");
        builder.add(ImbuingMenu.ImbuingState.INVALID_ENCHANTMENT.getComponent(), "Book enchantment cannot be imbued!");
        builder.add(ImbuingMenu.ImbuingState.ENCHANTMENTS_NOT_MATCHING.getComponent(), "Enchantments do not match!");
        builder.add(ImbuingMenu.ImbuingState.LEVELS_NOT_EQUAL.getComponent(), "Enchantment levels are not equal!");
        builder.add(ModItems.LOST_CANDLE_ITEM.value().getDescriptionId() + ".foundNearby", "%s found nearby");
        builder.add(ResourceKeyHelper.getTranslationKey(ModRegistry.REVEAL_INSTRUMENT), "Reveal");
        builder.add(ResourceKeyHelper.getTranslationKey(ModRegistry.PLATINUM_TRIM_MATERIAL), "Platinum Material");
        builder.add(ModItems.UNUSUAL_DUST_ITEM.value(), "Unusual Dust");
        builder.add(ModItems.MAGICAL_FIRE_CHARGE_ITEM.value(), "Magical Fire Charge");
        builder.add(ModItems.ILLUSIONARY_DUST_ITEM.value(), "Illusionary Dust");
        builder.add(ModItems.LOST_CANDLE_ITEM.value(), "Lost Candle");
        builder.add(ModItems.HORN_OF_SIGHT_ITEM.value(), "Horn of Sight");
        builder.add(ModItems.HALLOWED_GEM_ITEM.value(), "Hallowed Gem");
        builder.add(ModItems.PLATINUM_INFUSED_HATCHET_ITEM.value(), "Platinum Infused Hatchet");
        builder.add(ModItems.PLATINUM_CHUNK_ITEM.value(), "Platinum Chunk");
        builder.add(ModItems.PLATINUM_SHEET_ITEM.value(), "Platinum Sheet");
        builder.add(ModItems.PRIMAL_ESSENCE_ITEM.value(), "Primal Essence");
        builder.addPotion(ModRegistry.BERSERKING_POTION, "Berserking");
        builder.add(ModItems.PROVOKER_SPAWN_EGG_ITEM.value(), "Provoker Spawn Egg");
        builder.add(ModItems.SURRENDERED_SPAWN_EGG_ITEM.value(), "Surrendered Spawn Egg");
        builder.add(ModItems.ILLUSIONER_SPAWN_EGG_ITEM.value(), "Illusioner Spawn Egg");
        builder.add(ModItems.NECROMANCER_SPAWN_EGG_ITEM.value(), "Necromancer Spawn Egg");
        builder.add(ModItems.BASHER_SPAWN_EGG_ITEM.value(), "Basher Spawn Egg");
        builder.add(ModItems.SORCERER_SPAWN_EGG_ITEM.value(), "Sorcerer Spawn Egg");
        builder.add(ModItems.ARCHIVIST_SPAWN_EGG_ITEM.value(), "Archivist Spawn Egg");
        builder.add(ModItems.INQUISITOR_SPAWN_EGG_ITEM.value(), "Inquisitor Spawn Egg");
        builder.add(ModItems.MARAUDER_SPAWN_EGG_ITEM.value(), "Marauder Spawn Egg");
        builder.add(ModItems.INVOKER_SPAWN_EGG_ITEM.value(), "Invoker Spawn Egg");
        builder.add(ModItems.ALCHEMIST_SPAWN_EGG_ITEM.value(), "Alchemist Spawn Egg");
        builder.add(ModItems.FIRECALLER_SPAWN_EGG_ITEM.value(), "Firecaller Spawn Egg");
        builder.add(ModRegistry.IMBUING_TABLE_BLOCK.value(), "Imbuing Table");
        builder.add(ModRegistry.MAGIC_FIRE_BLOCK.value(), "Magic Fire");
        builder.add(ModEntityTypes.PROVOKER_ENTITY_TYPE.value(), "Provoker");
        builder.add(ModEntityTypes.INVOKER_ENTITY_TYPE.value(), "Invoker");
        builder.add(ModEntityTypes.SURRENDERED_ENTITY_TYPE.value(), "Surrendered");
        builder.add(ModEntityTypes.NECROMANCER_ENTITY_TYPE.value(), "Necromancer");
        builder.add(ModEntityTypes.BASHER_ENTITY_TYPE.value(), "Basher");
        builder.add(ModEntityTypes.SORCERER_ENTITY_TYPE.value(), "Sorcerer");
        builder.add(ModEntityTypes.ARCHIVIST_ENTITY_TYPE.value(), "Archivist");
        builder.add(ModEntityTypes.INQUISITOR_ENTITY_TYPE.value(), "Inquisitor");
        builder.add(ModEntityTypes.MARAUDER_ENTITY_TYPE.value(), "Marauder");
        builder.add(ModEntityTypes.ALCHEMIST_ENTITY_TYPE.value(), "Alchemist");
        builder.add(ModEntityTypes.FIRECALLER_ENTITY_TYPE.value(), "Firecaller");
        builder.add(ModEntityTypes.SKULL_BOLT_ENTITY_TYPE.value(), "Skull Bolt");
        builder.add(ModEntityTypes.HATCHET_ENTITY_TYPE.value(), "Hatchet");
        builder.add(ModEntityTypes.INVOKER_FANGS_ENTITY_TYPE.value(), "Invoker Fangs");
        builder.add(ModEntityTypes.FLYING_MAGMA_ENTITY_TYPE.value(), "Flying Magma");
        builder.add(ModSoundEvents.HORN_OF_SIGHT_SOUND_EVENT.value(), "Horn of Sight blows");
        builder.add(ModSoundEvents.LOST_CANDLE_FIND_ORE_SOUND_EVENT.value(), "Ore rings");
        builder.add(ModSoundEvents.SURRENDERED_AMBIENT_SOUND_EVENT.value(), "Surrendered clanks");
        builder.add(ModSoundEvents.SURRENDERED_HURT_SOUND_EVENT.value(), "Surrendered hurts");
        builder.add(ModSoundEvents.SURRENDERED_CHARGE_SOUND_EVENT.value(), "Surrendered charges");
        builder.add(ModSoundEvents.SURRENDERED_DEATH_SOUND_EVENT.value(), "Surrendered dies");
        builder.add(ModSoundEvents.NECROMANCER_SUMMON_SOUND_EVENT.value(), "Necromancer summons");
        builder.add(ModSoundEvents.ARCHIVIST_AMBIENT_SOUND_EVENT.value(), "Archivist murmurs");
        builder.add(ModSoundEvents.ARCHIVIST_HURT_SOUND_EVENT.value(), "Archivist hurts");
        builder.add(ModSoundEvents.ARCHIVIST_DEATH_SOUND_EVENT.value(), "Archivist dies");
        builder.add(ModSoundEvents.INVOKER_FANGS_SOUND_EVENT.value(), "Fangs attack");
        builder.add(ModSoundEvents.INVOKER_HURT_SOUND_EVENT.value(), "Invoker hurts");
        builder.add(ModSoundEvents.INVOKER_DEATH_SOUND_EVENT.value(), "Invoker dies");
        builder.add(ModSoundEvents.INVOKER_AMBIENT_SOUND_EVENT.value(), "Invoker murmurs");
        builder.add(ModSoundEvents.INVOKER_COMPLETE_CAST_SOUND_EVENT.value(), "Invoker casts");
        builder.add(ModSoundEvents.INVOKER_TELEPORT_CAST_SOUND_EVENT.value(), "Invoker casts");
        builder.add(ModSoundEvents.INVOKER_FANGS_CAST_SOUND_EVENT.value(), "Invoker casts");
        builder.add(ModSoundEvents.INVOKER_BIG_CAST_SOUND_EVENT.value(), "Invoker casts");
        builder.add(ModSoundEvents.INVOKER_SUMMON_CAST_SOUND_EVENT.value(), "Invoker casts");
        builder.add(ModSoundEvents.INVOKER_SHIELD_BREAK_SOUND_EVENT.value(), "Shield breaks");
        builder.add(ModSoundEvents.INVOKER_SHIELD_CREATE_SOUND_EVENT.value(), "Shield summoned");
        builder.add(ModSoundEvents.ILLAGER_BRUTE_AMBIENT_SOUND_EVENT.value(), "Illager Brute grunts");
        builder.add(ModSoundEvents.ILLAGER_BRUTE_HURT_SOUND_EVENT.value(), "Illager Brute hurts");
        builder.add(ModSoundEvents.ILLAGER_BRUTE_DEATH_SOUND_EVENT.value(), "Illager Brute dies");
        builder.add(ModSoundEvents.PROVOKER_AMBIENT_SOUND_EVENT.value(), "Provoker murmurs");
        builder.add(ModSoundEvents.PROVOKER_HURT_SOUND_EVENT.value(), "Provoker hurts");
        builder.add(ModSoundEvents.PROVOKER_DEATH_SOUND_EVENT.value(), "Provoker dies");
        builder.add(ModSoundEvents.PROVOKER_CELEBRATE_SOUND_EVENT.value(), "Provoker celebrates");
        builder.add(ModSoundEvents.BASHER_AMBIENT_SOUND_EVENT.value(), "Basher murmurs");
        builder.add(ModSoundEvents.BASHER_HURT_SOUND_EVENT.value(), "Basher hurts");
        builder.add(ModSoundEvents.BASHER_DEATH_SOUND_EVENT.value(), "Basher dies");
        builder.add(ModSoundEvents.BASHER_CELEBRATE_SOUND_EVENT.value(), "Basher celebrates");
        builder.add(ModSoundEvents.FIRECALLER_AMBIENT_SOUND_EVENT.value(), "Firecaller murmurs");
        builder.add(ModSoundEvents.FIRECALLER_HURT_SOUND_EVENT.value(), "Firecaller hurts");
        builder.add(ModSoundEvents.FIRECALLER_DEATH_SOUND_EVENT.value(), "Firecaller dies");
        builder.add(ModSoundEvents.FIRECALLER_CAST_SOUND_EVENT.value(), "Firecaller casts");
        builder.add(ModSoundEvents.SORCERER_CAST_SOUND_EVENT.value(), "Sorcerer casts");
        builder.add(ModSoundEvents.SORCERER_COMPLETE_CAST_SOUND_EVENT.value(), "Sorcerer casts");
        builder.add(ModSoundEvents.SORCERER_HURT_SOUND_EVENT.value(), "Sorcerer hurts");
        builder.add(ModSoundEvents.SORCERER_DEATH_SOUND_EVENT.value(), "Sorcerer dies");
        builder.add(ModSoundEvents.SORCERER_AMBIENT_SOUND_EVENT.value(), "Sorcerer murmurs");
        builder.add(ModSoundEvents.SORCERER_CELEBRATE_SOUND_EVENT.value(), "Sorcerer celebrates");
    }
}
