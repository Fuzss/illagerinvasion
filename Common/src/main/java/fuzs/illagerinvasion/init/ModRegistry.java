package fuzs.illagerinvasion.init;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.core.CommonAbstractions;
import fuzs.illagerinvasion.world.inventory.ImbuingMenu;
import fuzs.illagerinvasion.world.level.block.ImbuingTableBlock;
import fuzs.illagerinvasion.world.level.block.MagicFireBlock;
import fuzs.puzzleslib.api.init.v2.RegistryManager;
import fuzs.puzzleslib.api.init.v2.RegistryReference;
import me.sandbox.entity.monster.EntityRegistry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class ModRegistry {
    static final RegistryManager REGISTRY = RegistryManager.instant(IllagerInvasion.MOD_ID);
    public static final RegistryReference<Block> IMBUING_TABLE_BLOCK = REGISTRY.registerBlock("imbuing_table", () -> new ImbuingTableBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));
    public static final RegistryReference<Block> MAGIC_FIRE_BLOCK = REGISTRY.registerBlock("magic_fire", () -> new MagicFireBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_FIRE).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryReference<Potion> BERSERKING_POTION = REGISTRY.registerPotion("berserking", () -> new Potion(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 1), new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 1)));
    public static final RegistryReference<Potion> LONG_BERSERKING_POTION = REGISTRY.registerPotion("long_berserking", () -> new Potion("berserking", new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 0), new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0)));
    public static final RegistryReference<Potion> STRONG_BERSERKING_POTION = REGISTRY.registerPotion("strong_berserking", () -> new Potion("berserking", new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, 2), new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 2)));
    public static final RegistryReference<MenuType<ImbuingMenu>> IMBUING_MENU_TYPE = REGISTRY.registerMenuType("imbuing", () -> ImbuingMenu::new);
    public static final RegistryReference<SimpleParticleType> MAGIC_FLAME_PARTICLE_TYPE = REGISTRY.register(Registries.PARTICLE_TYPE, "magic_flame", () -> new SimpleParticleType(false));
    public static final RegistryReference<SoundEvent> SURRENDERED_AMBIENT_SOUND_EVENT = REGISTRY.registerSoundEvent("surrendered_ambient");
    public static final RegistryReference<SoundEvent> SURRENDERED_HURT_SOUND_EVENT = REGISTRY.registerSoundEvent("surrendered_hurt");
    public static final RegistryReference<SoundEvent> SURRENDERED_CHARGE_SOUND_EVENT = REGISTRY.registerSoundEvent("surrendered_charge");
    public static final RegistryReference<SoundEvent> SURRENDERED_DEATH_SOUND_EVENT = REGISTRY.registerSoundEvent("surrendered_death");
    public static final RegistryReference<SoundEvent> ARCHIVIST_AMBIENT_SOUND_EVENT = REGISTRY.registerSoundEvent("archivist_ambient");
    public static final RegistryReference<SoundEvent> ARCHIVIST_HURT_SOUND_EVENT = REGISTRY.registerSoundEvent("archivist_hurt");
    public static final RegistryReference<SoundEvent> ARCHIVIST_DEATH_SOUND_EVENT = REGISTRY.registerSoundEvent("archivist_death");
    public static final RegistryReference<SoundEvent> SORCERER_CAST_SOUND_EVENT = REGISTRY.registerSoundEvent("sorcerer_cast");
    public static final RegistryReference<SoundEvent> SORCERER_COMPLETE_CAST_SOUND_EVENT = REGISTRY.registerSoundEvent("sorcerer_complete_cast");
    public static final RegistryReference<SoundEvent> HORN_OF_SIGHT_SOUND_EVENT = REGISTRY.registerSoundEvent("horn_of_sight");
    public static final RegistryReference<SoundEvent> INVOKER_FANGS_SOUND_EVENT = REGISTRY.registerSoundEvent("invoker_fangs");
    public static final RegistryReference<SoundEvent> INVOKER_HURT_SOUND_EVENT = REGISTRY.registerSoundEvent("invoker_hurt");
    public static final RegistryReference<SoundEvent> INVOKER_DEATH_SOUND_EVENT = REGISTRY.registerSoundEvent("invoker_death");
    public static final RegistryReference<SoundEvent> INVOKER_AMBIENT_SOUND_EVENT = REGISTRY.registerSoundEvent("invoker_ambient");
    public static final RegistryReference<SoundEvent> INVOKER_COMPLETE_CAST_SOUND_EVENT = REGISTRY.registerSoundEvent("invoker_completecast");
    public static final RegistryReference<SoundEvent> INVOKER_TELEPORT_CAST_SOUND_EVENT = REGISTRY.registerSoundEvent("invoker_teleport_cast");
    public static final RegistryReference<SoundEvent> INVOKER_FANGS_CAST_SOUND_EVENT = REGISTRY.registerSoundEvent("invoker_fangs_cast");
    public static final RegistryReference<SoundEvent> INVOKER_BIG_CAST_SOUND_EVENT = REGISTRY.registerSoundEvent("invoker_big_cast");
    public static final RegistryReference<SoundEvent> INVOKER_SUMMON_CAST_SOUND_EVENT = REGISTRY.registerSoundEvent("invoker_summon_cast");
    public static final RegistryReference<SoundEvent> INVOKER_SHIELD_BREAK_SOUND_EVENT = REGISTRY.registerSoundEvent("invoker_shield_break");
    public static final RegistryReference<SoundEvent> ILLAGER_BRUTE_AMBIENT_SOUND_EVENT = REGISTRY.registerSoundEvent("illager_brute_ambient");
    public static final RegistryReference<SoundEvent> ILLAGER_BRUTE_HURT_SOUND_EVENT = REGISTRY.registerSoundEvent("illager_brute_hurt");
    public static final RegistryReference<SoundEvent> ILLAGER_BRUTE_DEATH_SOUND_EVENT = REGISTRY.registerSoundEvent("illager_brute_death");
    public static final RegistryReference<SoundEvent> PROVOKER_AMBIENT_SOUND_EVENT = REGISTRY.registerSoundEvent("provoker_idle");
    public static final RegistryReference<SoundEvent> PROVOKER_HURT_SOUND_EVENT = REGISTRY.registerSoundEvent("provoker_hurt");
    public static final RegistryReference<SoundEvent> PROVOKER_DEATH_SOUND_EVENT = REGISTRY.registerSoundEvent("provoker_death");
    public static final RegistryReference<SoundEvent> PROVOKER_CELEBRATE_SOUND_EVENT = REGISTRY.registerSoundEvent("provoker_celebrate");
    public static final RegistryReference<SoundEvent> BASHER_AMBIENT_SOUND_EVENT = REGISTRY.registerSoundEvent("basher_idle");
    public static final RegistryReference<SoundEvent> BASHER_HURT_SOUND_EVENT = REGISTRY.registerSoundEvent("basher_hurt");
    public static final RegistryReference<SoundEvent> BASHER_DEATH_SOUND_EVENT = REGISTRY.registerSoundEvent("basher_death");
    public static final RegistryReference<SoundEvent> BASHER_CELEBRATE_SOUND_EVENT = REGISTRY.registerSoundEvent("basher_celebrate");
    public static final RegistryReference<SoundEvent> FIRECALLER_AMBIENT_SOUND_EVENT = REGISTRY.registerSoundEvent("firecaller_idle");
    public static final RegistryReference<SoundEvent> FIRECALLER_HURT_SOUND_EVENT = REGISTRY.registerSoundEvent("firecaller_hurt");
    public static final RegistryReference<SoundEvent> FIRECALLER_DEATH_SOUND_EVENT = REGISTRY.registerSoundEvent("firecaller_death");
    public static final RegistryReference<SoundEvent> FIRECALLER_CAST_SOUND_EVENT = REGISTRY.registerSoundEvent("firecaller_cast");
    public static final RegistryReference<SoundEvent> SORCERER_HURT_SOUND_EVENT = REGISTRY.registerSoundEvent("sorcerer_hurt");
    public static final RegistryReference<SoundEvent> SORCERER_DEATH_SOUND_EVENT = REGISTRY.registerSoundEvent("sorcerer_death");
    public static final RegistryReference<SoundEvent> SORCERER_AMBIENT_SOUND_EVENT = REGISTRY.registerSoundEvent("sorcerer_idle");
    public static final RegistryReference<SoundEvent> SORCERER_CELEBRATE_SOUND_EVENT = REGISTRY.registerSoundEvent("sorcerer_celebrate");

    public static final TagKey<Enchantment> IMBUING_ENCHANTMENT_TAG = REGISTRY.registerEnchantmentTag("imbuing");

    public static final Object ENCHANT_ILLAGER_SPELL = CommonAbstractions.INSTANCE.createIllagerSpell("ENCHANT", 0.8, 0.8, 0.2);
    public static final Object CONJURE_FLAMES_ILLAGER_SPELL = CommonAbstractions.INSTANCE.createIllagerSpell("CONJURE_FLAMES", 1.8, 0.0, 1.8);    
    public static final Object CONJURE_TELEPORT_ILLAGER_SPELL = CommonAbstractions.INSTANCE.createIllagerSpell("CONJURE_TELEPORT", 1.5, 1.5, 0.8);
    public static final Object NECRORAISE_ILLAGER_SPELL = CommonAbstractions.INSTANCE.createIllagerSpell("NECRORAISE", 0.3, 0.8, 0.05);
    public static final Object CONJURE_SKULLBOLT_ILLAGER_SPELL = CommonAbstractions.INSTANCE.createIllagerSpell("CONJURE_SKULLBOLT", 0.5, 0.05, 0.05);
    public static final Object PROVOKE_ILLAGER_SPELL = CommonAbstractions.INSTANCE.createIllagerSpell("PROVOKE", 1.0, 0.8, 0.75);

    public static void touch() {
        CommonAbstractions.INSTANCE.createRaiderType("BASHER", EntityRegistry.BASHER, new int[]{1, 1, 2, 1, 2, 2, 3, 3});
        CommonAbstractions.INSTANCE.createRaiderType("PROVOKER", EntityRegistry.PROVOKER, new int[]{0, 1, 1, 0, 1, 1, 2, 2});
        CommonAbstractions.INSTANCE.createRaiderType("SORCERER", EntityRegistry.SORCERER, new int[]{0, 0, 0, 0, 0, 1, 1, 1});
        CommonAbstractions.INSTANCE.createRaiderType("ILLUSIONER", EntityType.ILLUSIONER, new int[]{0, 0, 0, 0, 0, 1, 0, 1});
        CommonAbstractions.INSTANCE.createRaiderType("ARCHIVIST", EntityRegistry.ARCHIVIST, new int[]{0, 1, 0, 1, 1, 1, 2, 3});
        CommonAbstractions.INSTANCE.createRaiderType("MARAUDER", EntityRegistry.MARAUDER, new int[]{0, 1, 1, 1, 2, 2, 3, 3});
        CommonAbstractions.INSTANCE.createRaiderType("INQUISITOR", EntityRegistry.INQUISITOR, new int[]{0, 0, 0, 0, 1, 0, 1, 1});
        CommonAbstractions.INSTANCE.createRaiderType("ALCHEMIST", EntityRegistry.ALCHEMIST, new int[]{0, 0, 0, 1, 2, 1, 2, 2});
    }
}
