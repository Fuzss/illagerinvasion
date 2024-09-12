package fuzs.illagerinvasion.data.client;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.handler.PlatinumTrimHandler;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.illagerinvasion.world.inventory.ImbuingMenu;
import fuzs.illagerinvasion.world.level.block.ImbuingTableBlock;
import fuzs.puzzleslib.api.client.data.v2.AbstractLanguageProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations(TranslationBuilder builder) {
        builder.addCreativeModeTab(IllagerInvasion.MOD_ID, IllagerInvasion.MOD_NAME);
        builder.add(ImbuingTableBlock.CONTAINER_IMBUE, "Imbue");
        builder.add(ImbuingMenu.InvalidImbuingState.TOO_MANY_ENCHANTMENTS.getTranslationKey(), "Book has too many enchantment!");
        builder.add(ImbuingMenu.InvalidImbuingState.NOT_AT_MAX_LEVEL.getTranslationKey(), "Book enchantment level is too low!");
        builder.add(ImbuingMenu.InvalidImbuingState.AT_WRONG_LEVEL.getTranslationKey(), "Item enchantment level is wrong!");
        builder.add(ImbuingMenu.InvalidImbuingState.INVALID_ENCHANTMENT.getTranslationKey(), "Book enchantment cannot be imbued!");
        builder.add(ImbuingMenu.InvalidImbuingState.INVALID_ITEM.getTranslationKey(), "Item is not compatible with this enchantment!");
        builder.add(ModRegistry.LOST_CANDLE_ITEM.value().getDescriptionId() + ".foundNearby", "%s found nearby");
        builder.add("instrument", ModRegistry.REVEAL_INSTRUMENT, "Reveal");
        builder.add("trim_material", ModRegistry.PLATINUM_TRIM_MATERIAL, "Platinum Material");
        builder.add(PlatinumTrimHandler.INSIGHT_TRANSLATION_KEY, "Insight Effect");
        builder.add(PlatinumTrimHandler.AGILITY_TRANSLATION_KEY, "Agility Effect");
        builder.add(PlatinumTrimHandler.ENDURANCE_TRANSLATION_KEY, "Endurance Effect");
        builder.add(PlatinumTrimHandler.FEATHERWEIGHT_TRANSLATION_KEY, "Featherweight Effect");
        builder.add(ModRegistry.UNUSUAL_DUST_ITEM.value(), "Unusual Dust");
        builder.add(ModRegistry.MAGICAL_FIRE_CHARGE_ITEM.value(), "Magical Fire Charge");
        builder.add(ModRegistry.ILLUSIONARY_DUST_ITEM.value(), "Illusionary Dust");
        builder.add(ModRegistry.LOST_CANDLE_ITEM.value(), "Lost Candle");
        builder.add(ModRegistry.HORN_OF_SIGHT_ITEM.value(), "Horn of Sight");
        builder.add(ModRegistry.HALLOWED_GEM_ITEM.value(), "Hallowed Gem");
        builder.add(ModRegistry.PLATINUM_INFUSED_HATCHET_ITEM.value(), "Platinum Infused Hatchet");
        builder.add(ModRegistry.PLATINUM_CHUNK_ITEM.value(), "Platinum Chunk");
        builder.add(ModRegistry.PLATINUM_SHEET_ITEM.value(), "Platinum Sheet");
        builder.add(ModRegistry.PRIMAL_ESSENCE_ITEM.value(), "Primal Essence");
        builder.add(ModRegistry.BERSERKING_POTION.value(), "Berserking");
        builder.add(ModRegistry.PROVOKER_SPAWN_EGG_ITEM.value(), "Provoker Spawn Egg");
        builder.add(ModRegistry.SURRENDERED_SPAWN_EGG_ITEM.value(), "Surrendered Spawn Egg");
        builder.add(ModRegistry.ILLUSIONER_SPAWN_EGG_ITEM.value(), "Illusioner Spawn Egg");
        builder.add(ModRegistry.NECROMANCER_SPAWN_EGG_ITEM.value(), "Necromancer Spawn Egg");
        builder.add(ModRegistry.BASHER_SPAWN_EGG_ITEM.value(), "Basher Spawn Egg");
        builder.add(ModRegistry.SORCERER_SPAWN_EGG_ITEM.value(), "Sorcerer Spawn Egg");
        builder.add(ModRegistry.ARCHIVIST_SPAWN_EGG_ITEM.value(), "Archivist Spawn Egg");
        builder.add(ModRegistry.INQUISITOR_SPAWN_EGG_ITEM.value(), "Inquisitor Spawn Egg");
        builder.add(ModRegistry.MARAUDER_SPAWN_EGG_ITEM.value(), "Marauder Spawn Egg");
        builder.add(ModRegistry.INVOKER_SPAWN_EGG_ITEM.value(), "Invoker Spawn Egg");
        builder.add(ModRegistry.ALCHEMIST_SPAWN_EGG_ITEM.value(), "Alchemist Spawn Egg");
        builder.add(ModRegistry.FIRECALLER_SPAWN_EGG_ITEM.value(), "Firecaller Spawn Egg");
        builder.add(ModRegistry.IMBUING_TABLE_BLOCK.value(), "Imbuing Table");
        builder.add(ModRegistry.MAGIC_FIRE_BLOCK.value(), "Magic Fire");
        builder.add(ModRegistry.PROVOKER_ENTITY_TYPE.value(), "Provoker");
        builder.add(ModRegistry.INVOKER_ENTITY_TYPE.value(), "Invoker");
        builder.add(ModRegistry.SURRENDERED_ENTITY_TYPE.value(), "Surrendered");
        builder.add(ModRegistry.NECROMANCER_ENTITY_TYPE.value(), "Necromancer");
        builder.add(ModRegistry.BASHER_ENTITY_TYPE.value(), "Basher");
        builder.add(ModRegistry.SORCERER_ENTITY_TYPE.value(), "Sorcerer");
        builder.add(ModRegistry.ARCHIVIST_ENTITY_TYPE.value(), "Archivist");
        builder.add(ModRegistry.INQUISITOR_ENTITY_TYPE.value(), "Inquisitor");
        builder.add(ModRegistry.MARAUDER_ENTITY_TYPE.value(), "Marauder");
        builder.add(ModRegistry.ALCHEMIST_ENTITY_TYPE.value(), "Alchemist");
        builder.add(ModRegistry.FIRECALLER_ENTITY_TYPE.value(), "Firecaller");
        builder.add(ModRegistry.HORN_OF_SIGHT_SOUND_EVENT.value(),"Horn of Sight blows");
        builder.add(ModRegistry.LOST_CANDLE_DIAMOND_SOUND_EVENT.value(),"Diamonds ring");
        builder.add(ModRegistry.LOST_CANDLE_IRON_SOUND_EVENT.value(),"Iron rings");
        builder.add(ModRegistry.LOST_CANDLE_COAL_SOUND_EVENT.value(),"Coal rings");
        builder.add(ModRegistry.LOST_CANDLE_COPPER_SOUND_EVENT.value(),"Copper rings");
        builder.add(ModRegistry.LOST_CANDLE_GOLD_SOUND_EVENT.value(),"Gold rings");
        builder.add(ModRegistry.SURRENDERED_AMBIENT_SOUND_EVENT.value(),"Surrendered clanks");
        builder.add(ModRegistry.SURRENDERED_HURT_SOUND_EVENT.value(),"Surrendered hurts");
        builder.add(ModRegistry.SURRENDERED_CHARGE_SOUND_EVENT.value(),"Surrendered charges");
        builder.add(ModRegistry.SURRENDERED_DEATH_SOUND_EVENT.value(),"Surrendered dies");
        builder.add(ModRegistry.NECROMANCER_SUMMON_SOUND_EVENT.value(),"Necromancer summons");
        builder.add(ModRegistry.ARCHIVIST_AMBIENT_SOUND_EVENT.value(),"Archivist murmurs");
        builder.add(ModRegistry.ARCHIVIST_HURT_SOUND_EVENT.value(),"Archivist hurts");
        builder.add(ModRegistry.ARCHIVIST_DEATH_SOUND_EVENT.value(),"Archivist dies");
        builder.add(ModRegistry.INVOKER_FANGS_SOUND_EVENT.value(),"Fangs attack");
        builder.add(ModRegistry.INVOKER_HURT_SOUND_EVENT.value(),"Invoker hurts");
        builder.add(ModRegistry.INVOKER_DEATH_SOUND_EVENT.value(),"Invoker dies");
        builder.add(ModRegistry.INVOKER_AMBIENT_SOUND_EVENT.value(),"Invoker murmurs");
        builder.add(ModRegistry.INVOKER_COMPLETE_CAST_SOUND_EVENT.value(),"Invoker casts");
        builder.add(ModRegistry.INVOKER_TELEPORT_CAST_SOUND_EVENT.value(),"Invoker casts");
        builder.add(ModRegistry.INVOKER_FANGS_CAST_SOUND_EVENT.value(),"Invoker casts");
        builder.add(ModRegistry.INVOKER_BIG_CAST_SOUND_EVENT.value(),"Invoker casts");
        builder.add(ModRegistry.INVOKER_SUMMON_CAST_SOUND_EVENT.value(),"Invoker casts");
        builder.add(ModRegistry.INVOKER_SHIELD_BREAK_SOUND_EVENT.value(),"Shield breaks");
        builder.add(ModRegistry.INVOKER_SHIELD_CREATE_SOUND_EVENT.value(),"Shield summoned");
        builder.add(ModRegistry.ILLAGER_BRUTE_AMBIENT_SOUND_EVENT.value(),"Illager Brute grunts");
        builder.add(ModRegistry.ILLAGER_BRUTE_HURT_SOUND_EVENT.value(),"Illager Brute hurts");
        builder.add(ModRegistry.ILLAGER_BRUTE_DEATH_SOUND_EVENT.value(),"Illager Brute dies");
        builder.add(ModRegistry.PROVOKER_AMBIENT_SOUND_EVENT.value(),"Provoker murmurs");
        builder.add(ModRegistry.PROVOKER_HURT_SOUND_EVENT.value(),"Provoker hurts");
        builder.add(ModRegistry.PROVOKER_DEATH_SOUND_EVENT.value(),"Provoker dies");
        builder.add(ModRegistry.PROVOKER_CELEBRATE_SOUND_EVENT.value(),"Provoker celebrates");
        builder.add(ModRegistry.BASHER_AMBIENT_SOUND_EVENT.value(),"Basher murmurs");
        builder.add(ModRegistry.BASHER_HURT_SOUND_EVENT.value(),"Basher hurts");
        builder.add(ModRegistry.BASHER_DEATH_SOUND_EVENT.value(),"Basher dies");
        builder.add(ModRegistry.BASHER_CELEBRATE_SOUND_EVENT.value(),"Basher celebrates");
        builder.add(ModRegistry.FIRECALLER_AMBIENT_SOUND_EVENT.value(),"Firecaller murmurs");
        builder.add(ModRegistry.FIRECALLER_HURT_SOUND_EVENT.value(),"Firecaller hurts");
        builder.add(ModRegistry.FIRECALLER_DEATH_SOUND_EVENT.value(),"Firecaller dies");
        builder.add(ModRegistry.FIRECALLER_CAST_SOUND_EVENT.value(),"Firecaller casts");
        builder.add(ModRegistry.SORCERER_CAST_SOUND_EVENT.value(),"Sorcerer casts");
        builder.add(ModRegistry.SORCERER_COMPLETE_CAST_SOUND_EVENT.value(),"Sorcerer casts");
        builder.add(ModRegistry.SORCERER_HURT_SOUND_EVENT.value(),"Sorcerer hurts");
        builder.add(ModRegistry.SORCERER_DEATH_SOUND_EVENT.value(),"Sorcerer dies");
        builder.add(ModRegistry.SORCERER_AMBIENT_SOUND_EVENT.value(),"Sorcerer murmurs");
        builder.add(ModRegistry.SORCERER_CELEBRATE_SOUND_EVENT.value(),"Sorcerer celebrates");
    }
}
