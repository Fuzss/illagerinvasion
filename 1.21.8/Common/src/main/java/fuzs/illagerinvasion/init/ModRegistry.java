package fuzs.illagerinvasion.init;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.world.inventory.ImbuingMenu;
import fuzs.illagerinvasion.world.item.enchantment.ImbuingEnchantmentLevel;
import fuzs.illagerinvasion.world.level.block.ImbuingTableBlock;
import fuzs.illagerinvasion.world.level.block.MagicFireBlock;
import fuzs.neoforgedatapackextensions.api.v1.DataMapRegistry;
import fuzs.neoforgedatapackextensions.api.v1.DataMapToken;
import fuzs.puzzleslib.api.data.v2.AbstractDatapackRegistriesProvider;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class ModRegistry {
    public static final RegistrySetBuilder REGISTRY_SET_BUILDER = new RegistrySetBuilder().add(Registries.TRIM_MATERIAL,
                    ModRegistry::boostrapTrimMaterials)
            .add(Registries.INSTRUMENT, ModRegistry::boostrapInstruments)
            .add(Registries.STRUCTURE, ModStructures::bootstrap)
            .add(Registries.STRUCTURE_SET, ModStructureSets::bootstrap)
            .add(Registries.TEMPLATE_POOL, ModTemplatePools::bootstrap);

    static final RegistryManager REGISTRIES = RegistryManager.from(IllagerInvasion.MOD_ID);
    public static final Holder.Reference<Block> IMBUING_TABLE_BLOCK = REGISTRIES.registerBlock("imbuing_table",
            ImbuingTableBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK));
    public static final Holder.Reference<Block> MAGIC_FIRE_BLOCK = REGISTRIES.registerBlock("magic_fire",
            MagicFireBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_FIRE).mapColor(MapColor.COLOR_PURPLE).randomTicks());
    public static final Holder.Reference<Potion> BERSERKING_POTION = REGISTRIES.registerPotion("berserking",
            (String name) -> new Potion(name,
                    new MobEffectInstance(MobEffects.STRENGTH, 600, 1),
                    new MobEffectInstance(MobEffects.SPEED, 600, 1)));
    public static final Holder.Reference<Potion> LONG_BERSERKING_POTION = REGISTRIES.registerPotion("long_berserking",
            () -> new Potion("berserking",
                    new MobEffectInstance(MobEffects.STRENGTH, 1200, 0),
                    new MobEffectInstance(MobEffects.SPEED, 1200, 0)));
    public static final Holder.Reference<Potion> STRONG_BERSERKING_POTION = REGISTRIES.registerPotion(
            "strong_berserking",
            () -> new Potion("berserking",
                    new MobEffectInstance(MobEffects.STRENGTH, 300, 2),
                    new MobEffectInstance(MobEffects.SPEED, 300, 2)));
    public static final Holder.Reference<MenuType<ImbuingMenu>> IMBUING_MENU_TYPE = REGISTRIES.registerMenuType(
            "imbuing",
            ImbuingMenu::new);
    public static final Holder.Reference<SimpleParticleType> MAGIC_FLAME_PARTICLE_TYPE = REGISTRIES.register(Registries.PARTICLE_TYPE,
            "magic_flame",
            () -> new SimpleParticleType(false));
    public static final Holder.Reference<SimpleParticleType> NECROMANCER_BUFF_PARTICLE_TYPE = REGISTRIES.register(
            Registries.PARTICLE_TYPE,
            "necromancer_buff",
            () -> new SimpleParticleType(false));
    public static final ResourceKey<TrimMaterial> PLATINUM_TRIM_MATERIAL = REGISTRIES.makeResourceKey(Registries.TRIM_MATERIAL,
            "platinum");
    public static final ResourceKey<Instrument> REVEAL_INSTRUMENT = REGISTRIES.makeResourceKey(Registries.INSTRUMENT,
            "reveal");
    public static final Holder.Reference<CreativeModeTab> CREATIVE_MODE_TAB = REGISTRIES.registerCreativeModeTab(
            ModItems.HORN_OF_SIGHT_ITEM);

    public static final DataMapToken<Enchantment, ImbuingEnchantmentLevel> IMBUING_LEVELS_DATA_MAP_TYPE = DataMapRegistry.INSTANCE.register(
            IllagerInvasion.id("imbuing_levels"),
            Registries.ENCHANTMENT,
            ImbuingEnchantmentLevel.CODEC,
            ImbuingEnchantmentLevel.INLINE_CODEC,
            true);

    public static void bootstrap() {
        ModTags.bootstrap();
        ModItems.bootstrap();
        ModEntityTypes.bootstrap();
        ModSoundEvents.bootstrap();
        ModLootTables.bootstrap();
    }

    public static void boostrapTrimMaterials(BootstrapContext<TrimMaterial> context) {
        AbstractDatapackRegistriesProvider.registerTrimMaterial(context,
                PLATINUM_TRIM_MATERIAL,
                ModItems.PLATINUM_SHEET_ITEM.value(),
                0x527D7C);
    }

    public static void boostrapInstruments(BootstrapContext<Instrument> context) {
        AbstractDatapackRegistriesProvider.registerInstrument(context,
                REVEAL_INSTRUMENT,
                ModSoundEvents.HORN_OF_SIGHT_SOUND_EVENT,
                7.0F,
                64.0F);
    }
}
