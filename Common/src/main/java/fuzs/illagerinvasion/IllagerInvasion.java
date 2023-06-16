package fuzs.illagerinvasion;

import fuzs.illagerinvasion.core.CommonAbstractions;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.context.CreativeModeTabContext;
import fuzs.puzzleslib.api.core.v1.context.EntityAttributesCreateContext;
import fuzs.puzzleslib.api.core.v1.context.ModLifecycleContext;
import fuzs.puzzleslib.api.event.v1.server.LootTableLoadEvents;
import fuzs.puzzleslib.api.init.v2.PotionBrewingRegistry;
import fuzs.puzzleslib.api.item.v2.CreativeModeTabConfigurator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.IntPredicate;

public class IllagerInvasion implements ModConstructor {
    public static final String MOD_ID = "illagerinvasion";
    public static final String MOD_NAME = "Illager Invasion";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @Override
    public void onConstructMod() {
        ModRegistry.touch();
        registerHandlers();
    }

    private static void registerHandlers() {
        LootTableLoadEvents.MODIFY.register((LootDataManager lootManager, ResourceLocation identifier, Consumer<LootPool> addPool, IntPredicate removePool) -> {
            injectLootPool(identifier, addPool, EntityType.ILLUSIONER.getDefaultLootTable(), ModRegistry.ILLUSIONER_INJECT_LOOT_TABLE);
            injectLootPool(identifier, addPool, EntityType.PILLAGER.getDefaultLootTable(), ModRegistry.PILLAGER_INJECT_LOOT_TABLE);
            injectLootPool(identifier, addPool, EntityType.RAVAGER.getDefaultLootTable(), ModRegistry.RAVAGER_INJECT_LOOT_TABLE);
        });
    }

    private static void injectLootPool(ResourceLocation identifier, Consumer<LootPool> addPool, ResourceLocation builtInLootTable, ResourceLocation injectedLootTable) {
        if (identifier.equals(builtInLootTable)) {
            addPool.accept(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootTableReference.lootTableReference(injectedLootTable)).build());
        }
    }

    @Override
    public void onCommonSetup(ModLifecycleContext context) {
        registerRaiderTypes();
        registerPotionRecipes();
    }

    private static void registerRaiderTypes() {
        CommonAbstractions.INSTANCE.registerRaiderType("BASHER", ModRegistry.BASHER_ENTITY_TYPE.get(), new int[]{1, 1, 2, 1, 2, 2, 3, 3});
        CommonAbstractions.INSTANCE.registerRaiderType("PROVOKER", ModRegistry.PROVOKER_ENTITY_TYPE.get(), new int[]{0, 1, 1, 0, 1, 1, 2, 2});
        CommonAbstractions.INSTANCE.registerRaiderType("NECROMANCER", ModRegistry.NECROMANCER_ENTITY_TYPE.get(), new int[]{0, 0, 0, 0, 1, 1, 1, 1});
        CommonAbstractions.INSTANCE.registerRaiderType("SORCERER", ModRegistry.SORCERER_ENTITY_TYPE.get(), new int[]{0, 0, 0, 0, 0, 1, 1, 1});
        CommonAbstractions.INSTANCE.registerRaiderType("ILLUSIONER", EntityType.ILLUSIONER, new int[]{0, 0, 0, 0, 0, 1, 0, 1});
        CommonAbstractions.INSTANCE.registerRaiderType("ARCHIVIST", ModRegistry.ARCHIVIST_ENTITY_TYPE.get(), new int[]{0, 1, 0, 1, 1, 1, 2, 3});
        CommonAbstractions.INSTANCE.registerRaiderType("MARAUDER", ModRegistry.MARAUDER_ENTITY_TYPE.get(), new int[]{0, 1, 1, 1, 2, 2, 3, 3});
        CommonAbstractions.INSTANCE.registerRaiderType("INQUISITOR", ModRegistry.INQUISITOR_ENTITY_TYPE.get(), new int[]{0, 0, 0, 0, 1, 0, 1, 1});
        CommonAbstractions.INSTANCE.registerRaiderType("ALCHEMIST", ModRegistry.ALCHEMIST_ENTITY_TYPE.get(), new int[]{0, 0, 0, 1, 2, 1, 2, 2});
    }

    private static void registerPotionRecipes() {
        PotionBrewingRegistry.INSTANCE.registerPotionRecipe(Potions.AWKWARD, Items.GOAT_HORN, ModRegistry.BERSERKING_POTION.get());
        PotionBrewingRegistry.INSTANCE.registerPotionRecipe(ModRegistry.BERSERKING_POTION.get(), Items.REDSTONE, ModRegistry.LONG_BERSERKING_POTION.get());
        PotionBrewingRegistry.INSTANCE.registerPotionRecipe(ModRegistry.BERSERKING_POTION.get(), Items.GLOWSTONE_DUST, ModRegistry.STRONG_BERSERKING_POTION.get());
    }

    @Override
    public void onEntityAttributeCreation(EntityAttributesCreateContext context) {
        context.registerEntityAttributes(ModRegistry.ALCHEMIST_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 23.0).add(Attributes.MOVEMENT_SPEED, 0.38));
        context.registerEntityAttributes(ModRegistry.ARCHIVIST_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 22.0D).add(Attributes.MOVEMENT_SPEED, 0.36D));
        context.registerEntityAttributes(ModRegistry.BASHER_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 28.0D).add(Attributes.MOVEMENT_SPEED, 0.31D).add(Attributes.ATTACK_DAMAGE, 3.0D).add(Attributes.ATTACK_KNOCKBACK, 0.2D));
        context.registerEntityAttributes(ModRegistry.FIRECALLER_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 32.0).add(Attributes.MOVEMENT_SPEED, 0.38));
        context.registerEntityAttributes(ModRegistry.INQUISITOR_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 80.0).add(Attributes.MOVEMENT_SPEED, 0.33).add(Attributes.ATTACK_DAMAGE, 10.0).add(Attributes.ATTACK_KNOCKBACK, 1.6).add(Attributes.KNOCKBACK_RESISTANCE, 0.8));
        context.registerEntityAttributes(ModRegistry.INVOKER_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 250.0D).add(Attributes.MOVEMENT_SPEED, 0.36D).add(Attributes.KNOCKBACK_RESISTANCE, 0.3D).add(Attributes.ATTACK_DAMAGE, 8.0D));
        context.registerEntityAttributes(ModRegistry.MARAUDER_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 21.0D).add(Attributes.MOVEMENT_SPEED, 0.30D));
        context.registerEntityAttributes(ModRegistry.PROVOKER_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 23.0D).add(Attributes.MOVEMENT_SPEED, 0.38D));
        context.registerEntityAttributes(ModRegistry.SORCERER_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 26.0D).add(Attributes.MOVEMENT_SPEED, 0.38D));
        context.registerEntityAttributes(ModRegistry.SURRENDERED_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 18.0D).add(Attributes.ATTACK_DAMAGE, 5.0D));
        context.registerEntityAttributes(ModRegistry.NECROMANCER_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 32.0).add(Attributes.MOVEMENT_SPEED, 0.38));
    }

    @Override
    public void onRegisterCreativeModeTabs(CreativeModeTabContext context) {
        context.registerCreativeModeTab(CreativeModeTabConfigurator.from(MOD_ID).icon(() -> new ItemStack(ModRegistry.HORN_OF_SIGHT_ITEM.get())).displayItems((itemDisplayParameters, output) -> {
            output.accept(ModRegistry.IMBUIING_TABLE_ITEM.get());
            output.accept(ModRegistry.UNUSUAL_DUST_ITEM.get());
            output.accept(ModRegistry.ILLUSIONARY_DUST_ITEM.get());
            output.accept(ModRegistry.LOST_CANDLE_ITEM.get());
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
            output.accept(ModRegistry.NECROMANCER_SPAWN_EGG_ITEM.get());
        }));
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
