package fuzs.illagerinvasion.data;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.handler.PlatinumTrimHandler;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.illagerinvasion.world.inventory.ImbuingMenu;
import fuzs.puzzleslib.api.data.v1.AbstractLanguageProvider;
import fuzs.puzzleslib.api.init.v2.RegistryReference;
import net.minecraft.Util;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.data.event.GatherDataEvent;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(GatherDataEvent evt, String modId) {
        super(evt, modId);
    }

    @Override
    protected void addTranslations() {
        this.addCreativeModeTab(IllagerInvasion.MOD_NAME);
        this.add("container.imbue", "Imbue");
        this.add(ImbuingMenu.InvalidImbuingState.TOO_MANY_ENCHANTMENTS.getTranslationKey(), "Book has too many enchantment!");
        this.add(ImbuingMenu.InvalidImbuingState.NOT_AT_MAX_LEVEL.getTranslationKey(), "Book enchantment level is too low!");
        this.add(ImbuingMenu.InvalidImbuingState.AT_WRONG_LEVEL.getTranslationKey(), "Item enchantment level is wrong!");
        this.add(ImbuingMenu.InvalidImbuingState.INVALID_ENCHANTMENT.getTranslationKey(), "Book enchantment cannot be imbued!");
        this.add(ImbuingMenu.InvalidImbuingState.INVALID_ITEM.getTranslationKey(), "Item is not compatible with this enchantment!");
        this.add(ModRegistry.LOST_CANDLE_ITEM.get().getDescriptionId() + ".foundNearby", "%s found nearby");
        this.add("instrument", ModRegistry.REVEAL_INSTRUMENT, "Reveal");
        this.add("trim_material", ModRegistry.PLATINUM_TRIM_MATERIAL, "Platinum Material");
        this.add(PlatinumTrimHandler.INSIGHT_TRANSLATION_KEY, "Insight Effect");
        this.add(PlatinumTrimHandler.AGILITY_TRANSLATION_KEY, "Agility Effect");
        this.add(PlatinumTrimHandler.ENDURANCE_TRANSLATION_KEY, "Endurance Effect");
        this.add(PlatinumTrimHandler.FEATHERWEIGHT_TRANSLATION_KEY, "Featherweight Effect");
        this.add(ModRegistry.UNUSUAL_DUST_ITEM.get(), "Unusual Dust");
        this.add(ModRegistry.MAGICAL_FIRE_CHARGE_ITEM.get(), "Magical Fire Charge");
        this.add(ModRegistry.ILLUSIONARY_DUST_ITEM.get(), "Illusionary Dust");
        this.add(ModRegistry.LOST_CANDLE_ITEM.get(), "Lost Candle");
        this.add(ModRegistry.HORN_OF_SIGHT_ITEM.get(), "Horn of Sight");
        this.add(ModRegistry.HALLOWED_GEM_ITEM.get(), "Hallowed Gem");
        this.add(ModRegistry.PLATINUM_INFUSED_HATCHET_ITEM.get(), "Platinum Infused Hatchet");
        this.add(ModRegistry.PLATINUM_CHUNK_ITEM.get(), "Platinum Chunk");
        this.add(ModRegistry.PLATINUM_SHEET_ITEM.get(), "Platinum Sheet");
        this.add(ModRegistry.PRIMAL_ESSENCE_ITEM.get(), "Primal Essence");
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
        this.add(ModRegistry.ILLUSIONER_SPAWN_EGG_ITEM.get(), "Illusioner Spawn Egg");
        this.add(ModRegistry.NECROMANCER_SPAWN_EGG_ITEM.get(), "Necromancer Egg");
        this.add(ModRegistry.BASHER_SPAWN_EGG_ITEM.get(), "Basher Spawn Egg");
        this.add(ModRegistry.SORCERER_SPAWN_EGG_ITEM.get(), "Sorcerer Spawn Egg");
        this.add(ModRegistry.ARCHIVIST_SPAWN_EGG_ITEM.get(), "Archivist Spawn Egg");
        this.add(ModRegistry.INQUISITOR_SPAWN_EGG_ITEM.get(), "Inquisitor Spawn Egg");
        this.add(ModRegistry.MARAUDER_SPAWN_EGG_ITEM.get(), "Marauder Spawn Egg");
        this.add(ModRegistry.INVOKER_SPAWN_EGG_ITEM.get(), "Invoker Spawn Egg");
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
        this.add(ModRegistry.HORN_OF_SIGHT_SOUND_EVENT.get(),"Horn of Sight blows");
        this.add(ModRegistry.LOST_CANDLE_DIAMOND_SOUND_EVENT.get(),"Diamonds ring");
        this.add(ModRegistry.LOST_CANDLE_IRON_SOUND_EVENT.get(),"Iron rings");
        this.add(ModRegistry.LOST_CANDLE_COAL_SOUND_EVENT.get(),"Coal rings");
        this.add(ModRegistry.LOST_CANDLE_COPPER_SOUND_EVENT.get(),"Copper rings");
        this.add(ModRegistry.LOST_CANDLE_GOLD_SOUND_EVENT.get(),"Gold rings");
        this.add(ModRegistry.SURRENDERED_AMBIENT_SOUND_EVENT.get(),"Surrendered clanks");
        this.add(ModRegistry.SURRENDERED_HURT_SOUND_EVENT.get(),"Surrendered hurts");
        this.add(ModRegistry.SURRENDERED_CHARGE_SOUND_EVENT.get(),"Surrendered charges");
        this.add(ModRegistry.SURRENDERED_DEATH_SOUND_EVENT.get(),"Surrendered dies");
        this.add(ModRegistry.NECROMANCER_SUMMON_SOUND_EVENT.get(),"Necromancer summons");
        this.add(ModRegistry.ARCHIVIST_AMBIENT_SOUND_EVENT.get(),"Archivist murmurs");
        this.add(ModRegistry.ARCHIVIST_HURT_SOUND_EVENT.get(),"Archivist hurts");
        this.add(ModRegistry.ARCHIVIST_DEATH_SOUND_EVENT.get(),"Archivist dies");
        this.add(ModRegistry.INVOKER_FANGS_SOUND_EVENT.get(),"Fangs attack");
        this.add(ModRegistry.INVOKER_HURT_SOUND_EVENT.get(),"Invoker hurts");
        this.add(ModRegistry.INVOKER_DEATH_SOUND_EVENT.get(),"Invoker dies");
        this.add(ModRegistry.INVOKER_AMBIENT_SOUND_EVENT.get(),"Invoker murmurs");
        this.add(ModRegistry.INVOKER_COMPLETE_CAST_SOUND_EVENT.get(),"Invoker casts");
        this.add(ModRegistry.INVOKER_TELEPORT_CAST_SOUND_EVENT.get(),"Invoker casts");
        this.add(ModRegistry.INVOKER_FANGS_CAST_SOUND_EVENT.get(),"Invoker casts");
        this.add(ModRegistry.INVOKER_BIG_CAST_SOUND_EVENT.get(),"Invoker casts");
        this.add(ModRegistry.INVOKER_SUMMON_CAST_SOUND_EVENT.get(),"Invoker casts");
        this.add(ModRegistry.INVOKER_SHIELD_BREAK_SOUND_EVENT.get(),"Shield breaks");
        this.add(ModRegistry.INVOKER_SHIELD_CREATE_SOUND_EVENT.get(),"Shield summoned");
        this.add(ModRegistry.ILLAGER_BRUTE_AMBIENT_SOUND_EVENT.get(),"Illager Brute grunts");
        this.add(ModRegistry.ILLAGER_BRUTE_HURT_SOUND_EVENT.get(),"Illager Brute hurts");
        this.add(ModRegistry.ILLAGER_BRUTE_DEATH_SOUND_EVENT.get(),"Illager Brute dies");
        this.add(ModRegistry.PROVOKER_AMBIENT_SOUND_EVENT.get(),"Provoker murmurs");
        this.add(ModRegistry.PROVOKER_HURT_SOUND_EVENT.get(),"Provoker hurts");
        this.add(ModRegistry.PROVOKER_DEATH_SOUND_EVENT.get(),"Provoker dies");
        this.add(ModRegistry.PROVOKER_CELEBRATE_SOUND_EVENT.get(),"Provoker celebrates");
        this.add(ModRegistry.BASHER_AMBIENT_SOUND_EVENT.get(),"Basher murmurs");
        this.add(ModRegistry.BASHER_HURT_SOUND_EVENT.get(),"Basher hurts");
        this.add(ModRegistry.BASHER_DEATH_SOUND_EVENT.get(),"Basher dies");
        this.add(ModRegistry.BASHER_CELEBRATE_SOUND_EVENT.get(),"Basher celebrates");
        this.add(ModRegistry.FIRECALLER_AMBIENT_SOUND_EVENT.get(),"Firecaller murmurs");
        this.add(ModRegistry.FIRECALLER_HURT_SOUND_EVENT.get(),"Firecaller hurts");
        this.add(ModRegistry.FIRECALLER_DEATH_SOUND_EVENT.get(),"Firecaller dies");
        this.add(ModRegistry.FIRECALLER_CAST_SOUND_EVENT.get(),"Firecaller casts");
        this.add(ModRegistry.SORCERER_CAST_SOUND_EVENT.get(),"Sorcerer casts");
        this.add(ModRegistry.SORCERER_COMPLETE_CAST_SOUND_EVENT.get(),"Sorcerer casts");
        this.add(ModRegistry.SORCERER_HURT_SOUND_EVENT.get(),"Sorcerer hurts");
        this.add(ModRegistry.SORCERER_DEATH_SOUND_EVENT.get(),"Sorcerer dies");
        this.add(ModRegistry.SORCERER_AMBIENT_SOUND_EVENT.get(),"Sorcerer murmurs");
        this.add(ModRegistry.SORCERER_CELEBRATE_SOUND_EVENT.get(),"Sorcerer celebrates");
    }

    public void add(String registry, RegistryReference<?> registryReference, String value) {
        this.add(registry, registryReference.getResourceLocation(), value);
    }

    public void add(String registry, ResourceKey<?> resourceKey, String value) {
        this.add(registry, resourceKey.location(), value);
    }

    public void add(String registry, ResourceLocation resourceLocation, String value) {
        this.add(Util.makeDescriptionId(registry, resourceLocation), value);
    }
}
