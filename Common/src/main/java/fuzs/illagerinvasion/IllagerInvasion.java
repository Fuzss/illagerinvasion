package fuzs.illagerinvasion;

import fuzs.illagerinvasion.config.ServerConfig;
import fuzs.illagerinvasion.core.CommonAbstractions;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.context.CreativeModeTabContext;
import fuzs.puzzleslib.api.core.v1.context.ModLifecycleContext;
import fuzs.puzzleslib.api.init.v2.PotionBrewingRegistry;
import fuzs.puzzleslib.api.item.v2.CreativeModeTabConfigurator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IllagerInvasion implements ModConstructor {
    public static final String MOD_ID = "illagerinvasion";
    public static final String MOD_NAME = "Illager Invasion";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final ConfigHolder CONFIG = ConfigHolder.builder(MOD_ID).server(ServerConfig.class);

    @Override
    public void onConstructMod() {
        ModRegistry.touch();
    }

    @Override
    public void onCommonSetup(ModLifecycleContext context) {
        registerRaiderTypes();
        registerPotionRecipes();
    }

    private static void registerRaiderTypes() {
        CommonAbstractions.INSTANCE.registerRaiderType("BASHER", ModRegistry.BASHER_ENTITY_TYPE.get(), new int[]{1, 1, 2, 1, 2, 2, 3, 3});
        CommonAbstractions.INSTANCE.registerRaiderType("PROVOKER", ModRegistry.PROVOKER_ENTITY_TYPE.get(), new int[]{0, 1, 1, 0, 1, 1, 2, 2});
        CommonAbstractions.INSTANCE.registerRaiderType("SORCERER", ModRegistry.SORCERER_ENTITY_TYPE.get(), new int[]{0, 0, 0, 0, 0, 1, 1, 1});
        CommonAbstractions.INSTANCE.registerRaiderType("ILLUSIONER", EntityType.ILLUSIONER, new int[]{0, 0, 0, 0, 0, 1, 0, 1});
        CommonAbstractions.INSTANCE.registerRaiderType("ARCHIVIST", ModRegistry.ARCHIVIST_ENTITY_TYPE.get(), new int[]{0, 1, 0, 1, 1, 1, 2, 3});
        CommonAbstractions.INSTANCE.registerRaiderType("MARAUDER", ModRegistry.MARAUDER_ENTITY_TYPE.get(), new int[]{0, 1, 1, 1, 2, 2, 3, 3});
        CommonAbstractions.INSTANCE.registerRaiderType("INQUISITOR", ModRegistry.INQUISITOR_ENTITY_TYPE.get(), new int[]{0, 0, 0, 0, 1, 0, 1, 1});
        CommonAbstractions.INSTANCE.registerRaiderType("ALCHEMIST", ModRegistry.ALCHEMIST_ENTITY_TYPE.get(), new int[]{0, 0, 0, 1, 2, 1, 2, 2});
    }

    private static void registerPotionRecipes() {
        PotionBrewingRegistry.INSTANCE.registerPotionRecipe(Potions.AWKWARD, ModRegistry.RAVAGER_HORN_ITEM.get(), ModRegistry.BERSERKING_POTION.get());
        PotionBrewingRegistry.INSTANCE.registerPotionRecipe(ModRegistry.BERSERKING_POTION.get(), Items.REDSTONE, ModRegistry.LONG_BERSERKING_POTION.get());
        PotionBrewingRegistry.INSTANCE.registerPotionRecipe(ModRegistry.BERSERKING_POTION.get(), Items.GLOWSTONE_DUST, ModRegistry.STRONG_BERSERKING_POTION.get());
    }

    @Override
    public void onRegisterCreativeModeTabs(CreativeModeTabContext context) {
        context.registerCreativeModeTab(CreativeModeTabConfigurator.from(MOD_ID).icon(() -> new ItemStack(ModRegistry.HORN_OF_SIGHT_ITEM.get())).displayItems((itemDisplayParameters, output) -> {
            output.accept(ModRegistry.IMBUIING_TABLE_ITEM.get());
            output.accept(ModRegistry.UNUSUAL_DUST_ITEM.get());
            output.accept(ModRegistry.ILLUSIONARY_DUST_ITEM.get());
            output.accept(ModRegistry.RAVAGER_HORN_ITEM.get());
            output.accept(ModRegistry.GILDED_HORN_ITEM.get());
            output.accept(ModRegistry.HORN_OF_SIGHT_ITEM.get());
            output.accept(ModRegistry.HALLOWED_GEM_ITEM.get());
            output.accept(ModRegistry.PRIMAL_ESSENCE_ITEM.get());
            output.accept(ModRegistry.PLATINUM_CHUNK_ITEM.get());
            output.accept(ModRegistry.PLATINUM_SHEET_ITEM.get());
            output.accept(ModRegistry.HATCHET_ITEM.get());
            output.accept(ModRegistry.PLATINUM_INFUSED_NETHERITE_SHOVEL_ITEM.get());
            output.accept(ModRegistry.PLATINUM_INFUSED_NETHERITE_SWORD_ITEM.get());
            output.accept(ModRegistry.PLATINUM_INFUSED_NETHERITE_HOE_ITEM.get());
            output.accept(ModRegistry.PLATINUM_INFUSED_NETHERITE_AXE_ITEM.get());
            output.accept(ModRegistry.PLATINUM_INFUSED_NETHERITE_PICKAXE_ITEM.get());
            output.accept(ModRegistry.PLATINUM_INFUSED_NETHERITE_BOOTS_ITEM.get());
            output.accept(ModRegistry.PLATINUM_INFUSED_NETHERITE_LEGGINGS_ITEM.get());
            output.accept(ModRegistry.PLATINUM_INFUSED_NETHERITE_CHESTPLATE_ITEM.get());
            output.accept(ModRegistry.PLATINUM_INFUSED_NETHERITE_HELMET_ITEM.get());
            output.accept(ModRegistry.PROVOKER_SPAWN_EGG_ITEM.get());
            output.accept(ModRegistry.BASHER_SPAWN_EGG_ITEM.get());
            output.accept(ModRegistry.SORCERER_SPAWN_EGG_ITEM.get());
            output.accept(ModRegistry.ARCHIVIST_SPAWN_EGG_ITEM.get());
            output.accept(ModRegistry.INQUISITOR_SPAWN_EGG_ITEM.get());
            output.accept(ModRegistry.MARAUDER_SPAWN_EGG_ITEM.get());
            output.accept(ModRegistry.ALCHEMIST_SPAWN_EGG_ITEM.get());
            output.accept(ModRegistry.FIRECALLER_SPAWN_EGG_ITEM.get());
            output.accept(ModRegistry.SURRENDERED_SPAWN_EGG_ITEM.get());
        }));
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
