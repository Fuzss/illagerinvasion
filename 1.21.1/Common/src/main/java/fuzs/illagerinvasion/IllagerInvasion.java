package fuzs.illagerinvasion;

import fuzs.extensibleenums.api.v2.BuiltInEnumFactories;
import fuzs.illagerinvasion.config.RaidWavesConfigHelper;
import fuzs.illagerinvasion.config.ServerConfig;
import fuzs.illagerinvasion.handler.PlatinumTrimHandler;
import fuzs.illagerinvasion.handler.VillagerGoalHandler;
import fuzs.illagerinvasion.init.ModEntityTypes;
import fuzs.illagerinvasion.init.ModItems;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.context.CreativeModeTabContext;
import fuzs.puzzleslib.api.core.v1.context.EntityAttributesCreateContext;
import fuzs.puzzleslib.api.core.v1.context.SpawnPlacementsContext;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.api.event.v1.entity.ServerEntityLevelEvents;
import fuzs.puzzleslib.api.event.v1.entity.living.LivingExperienceDropCallback;
import fuzs.puzzleslib.api.event.v1.entity.player.BreakSpeedCallback;
import fuzs.puzzleslib.api.event.v1.level.BlockEvents;
import fuzs.puzzleslib.api.event.v1.server.LootTableLoadEvents;
import fuzs.puzzleslib.api.event.v1.server.RegisterPotionBrewingMixesCallback;
import fuzs.puzzleslib.api.item.v2.CreativeModeTabConfigurator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.IntPredicate;

public class IllagerInvasion implements ModConstructor {
    public static final String MOD_ID = "illagerinvasion";
    public static final String MOD_NAME = "Illager Invasion";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final ConfigHolder CONFIG = ConfigHolder.builder(MOD_ID).server(ServerConfig.class);

    @Override
    public void onConstructMod() {
        ModRegistry.touch();
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        BreakSpeedCallback.EVENT.register(PlatinumTrimHandler::onBreakSpeed);
        LivingExperienceDropCallback.EVENT.register(PlatinumTrimHandler::onLivingExperienceDrop);
        BlockEvents.FARMLAND_TRAMPLE.register(PlatinumTrimHandler::onFarmlandTrample);
        ServerEntityLevelEvents.LOAD.register(VillagerGoalHandler::onEntityLoad);
        LootTableLoadEvents.MODIFY.register((ResourceLocation resourceLocation, Consumer<LootPool> addLootPool, IntPredicate removeLootPool) -> {
            injectLootPool(resourceLocation, addLootPool, EntityType.ILLUSIONER.getDefaultLootTable(), ModRegistry.ILLUSIONER_INJECT_LOOT_TABLE);
            injectLootPool(resourceLocation, addLootPool, EntityType.PILLAGER.getDefaultLootTable(), ModRegistry.PILLAGER_INJECT_LOOT_TABLE);
            injectLootPool(resourceLocation, addLootPool, EntityType.RAVAGER.getDefaultLootTable(), ModRegistry.RAVAGER_INJECT_LOOT_TABLE);
        });
        RegisterPotionBrewingMixesCallback.EVENT.register(IllagerInvasion::registerPotionRecipes);
    }

    private static void injectLootPool(ResourceLocation resourceLocation, Consumer<LootPool> addPool, ResourceKey<LootTable> builtInLootTable, ResourceKey<LootTable> injectedLootTable) {
        if (resourceLocation.equals(builtInLootTable.location())) {
            addPool.accept(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(NestedLootTable.lootTableReference(injectedLootTable)).build());
        }
    }

    @Override
    public void onCommonSetup() {
        registerRaiderTypes();
        VillagerGoalHandler.init();
    }

    private static void registerRaiderTypes() {
        BuiltInEnumFactories.INSTANCE.createRaiderType(id("basher"), ModEntityTypes.BASHER_ENTITY_TYPE.value(), RaidWavesConfigHelper.BASHER_RAID_WAVES);
        BuiltInEnumFactories.INSTANCE.createRaiderType(id("provoker"), ModEntityTypes.PROVOKER_ENTITY_TYPE.value(), RaidWavesConfigHelper.PROVOKER_RAID_WAVES);
        BuiltInEnumFactories.INSTANCE.createRaiderType(id("necromancer"), ModEntityTypes.NECROMANCER_ENTITY_TYPE.value(), RaidWavesConfigHelper.NECROMANCER_RAID_WAVES);
        BuiltInEnumFactories.INSTANCE.createRaiderType(id("sorcerer"), ModEntityTypes.SORCERER_ENTITY_TYPE.value(), RaidWavesConfigHelper.SORCERER_RAID_WAVES);
        BuiltInEnumFactories.INSTANCE.createRaiderType(id("illusioner"), EntityType.ILLUSIONER, RaidWavesConfigHelper.ILLUSIONER_RAID_WAVES);
        BuiltInEnumFactories.INSTANCE.createRaiderType(id("archivist"), ModEntityTypes.ARCHIVIST_ENTITY_TYPE.value(), RaidWavesConfigHelper.ARCHIVIST_RAID_WAVES);
        BuiltInEnumFactories.INSTANCE.createRaiderType(id("marauder"), ModEntityTypes.MARAUDER_ENTITY_TYPE.value(), RaidWavesConfigHelper.MARAUDER_RAID_WAVES);
        BuiltInEnumFactories.INSTANCE.createRaiderType(id("inquisitor"), ModEntityTypes.INQUISITOR_ENTITY_TYPE.value(), RaidWavesConfigHelper.INQUISITOR_RAID_WAVES);
        BuiltInEnumFactories.INSTANCE.createRaiderType(id("alchemist"), ModEntityTypes.ALCHEMIST_ENTITY_TYPE.value(), RaidWavesConfigHelper.ALCHEMIST_RAID_WAVES);
    }

    private static void registerPotionRecipes(RegisterPotionBrewingMixesCallback.Builder builder) {
        builder.registerPotionRecipe(Potions.AWKWARD, Items.GOAT_HORN, ModRegistry.BERSERKING_POTION);
        builder.registerPotionRecipe(ModRegistry.BERSERKING_POTION, Items.REDSTONE, ModRegistry.LONG_BERSERKING_POTION);
        builder.registerPotionRecipe(ModRegistry.BERSERKING_POTION, Items.GLOWSTONE_DUST, ModRegistry.STRONG_BERSERKING_POTION);
    }

    @Override
    public void onEntityAttributeCreation(EntityAttributesCreateContext context) {
        context.registerEntityAttributes(
                ModEntityTypes.ALCHEMIST_ENTITY_TYPE.value(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 24.0).add(Attributes.MOVEMENT_SPEED, 0.38));
        context.registerEntityAttributes(
                ModEntityTypes.ARCHIVIST_ENTITY_TYPE.value(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 24.0D).add(Attributes.MOVEMENT_SPEED, 0.36D));
        context.registerEntityAttributes(ModEntityTypes.BASHER_ENTITY_TYPE.value(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 32.0D).add(Attributes.MOVEMENT_SPEED, 0.31D).add(Attributes.ATTACK_DAMAGE, 3.0D).add(Attributes.ATTACK_KNOCKBACK, 0.2D));
        context.registerEntityAttributes(
                ModEntityTypes.FIRECALLER_ENTITY_TYPE.value(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 32.0).add(Attributes.MOVEMENT_SPEED, 0.38));
        context.registerEntityAttributes(
                ModEntityTypes.INQUISITOR_ENTITY_TYPE.value(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 80.0).add(Attributes.MOVEMENT_SPEED, 0.33).add(Attributes.ATTACK_DAMAGE, 10.0).add(Attributes.ATTACK_KNOCKBACK, 1.6).add(Attributes.KNOCKBACK_RESISTANCE, 0.8));
        context.registerEntityAttributes(ModEntityTypes.INVOKER_ENTITY_TYPE.value(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 250.0D).add(Attributes.MOVEMENT_SPEED, 0.36D).add(Attributes.KNOCKBACK_RESISTANCE, 0.3D).add(Attributes.ATTACK_DAMAGE, 8.0D));
        context.registerEntityAttributes(ModEntityTypes.MARAUDER_ENTITY_TYPE.value(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 24.0D).add(Attributes.MOVEMENT_SPEED, 0.30D));
        context.registerEntityAttributes(ModEntityTypes.PROVOKER_ENTITY_TYPE.value(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 24.0D).add(Attributes.MOVEMENT_SPEED, 0.38D));
        context.registerEntityAttributes(ModEntityTypes.SORCERER_ENTITY_TYPE.value(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 32.0D).add(Attributes.MOVEMENT_SPEED, 0.38D));
        context.registerEntityAttributes(
                ModEntityTypes.SURRENDERED_ENTITY_TYPE.value(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 14.0D).add(Attributes.ATTACK_DAMAGE, 5.0D));
        context.registerEntityAttributes(
                ModEntityTypes.NECROMANCER_ENTITY_TYPE.value(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 32.0).add(Attributes.MOVEMENT_SPEED, 0.38));
    }

    @Override
    public void onRegisterSpawnPlacements(SpawnPlacementsContext context) {
        context.registerSpawnPlacement(ModEntityTypes.ALCHEMIST_ENTITY_TYPE.value(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.ARCHIVIST_ENTITY_TYPE.value(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.BASHER_ENTITY_TYPE.value(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.FIRECALLER_ENTITY_TYPE.value(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.INQUISITOR_ENTITY_TYPE.value(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.INVOKER_ENTITY_TYPE.value(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.MARAUDER_ENTITY_TYPE.value(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.PROVOKER_ENTITY_TYPE.value(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.SORCERER_ENTITY_TYPE.value(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.SURRENDERED_ENTITY_TYPE.value(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.NECROMANCER_ENTITY_TYPE.value(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::checkPatrollingMonsterSpawnRules);
    }

    @Override
    public void onRegisterCreativeModeTabs(CreativeModeTabContext context) {
        context.registerCreativeModeTab(CreativeModeTabConfigurator.from(MOD_ID).icon(() -> new ItemStack(ModItems.HORN_OF_SIGHT_ITEM.value())).displayItems((itemDisplayParameters, output) -> {
            output.accept(ModItems.IMBUING_TABLE_ITEM.value());
            output.accept(ModItems.UNUSUAL_DUST_ITEM.value());
            output.accept(ModItems.ILLUSIONARY_DUST_ITEM.value());
            output.accept(ModItems.HALLOWED_GEM_ITEM.value());
            output.accept(ModItems.PRIMAL_ESSENCE_ITEM.value());
            output.accept(ModItems.PLATINUM_CHUNK_ITEM.value());
            output.accept(ModItems.PLATINUM_SHEET_ITEM.value());
            output.accept(ModItems.PLATINUM_INFUSED_HATCHET_ITEM.value());
            output.accept(ModItems.HORN_OF_SIGHT_ITEM.value());
            output.accept(ModItems.LOST_CANDLE_ITEM.value());
            output.accept(ModItems.MAGICAL_FIRE_CHARGE_ITEM.value());
            output.accept(ModItems.PROVOKER_SPAWN_EGG_ITEM.value());
            output.accept(ModItems.BASHER_SPAWN_EGG_ITEM.value());
            output.accept(ModItems.SORCERER_SPAWN_EGG_ITEM.value());
            output.accept(ModItems.ARCHIVIST_SPAWN_EGG_ITEM.value());
            output.accept(ModItems.INQUISITOR_SPAWN_EGG_ITEM.value());
            output.accept(ModItems.MARAUDER_SPAWN_EGG_ITEM.value());
            output.accept(ModItems.INVOKER_SPAWN_EGG_ITEM.value());
            output.accept(ModItems.ALCHEMIST_SPAWN_EGG_ITEM.value());
            output.accept(ModItems.FIRECALLER_SPAWN_EGG_ITEM.value());
            output.accept(ModItems.NECROMANCER_SPAWN_EGG_ITEM.value());
            output.accept(ModItems.SURRENDERED_SPAWN_EGG_ITEM.value());
            output.accept(ModItems.ILLUSIONER_SPAWN_EGG_ITEM.value());
        }));
    }

    public static ResourceLocation id(String path) {
        return ResourceLocationHelper.fromNamespaceAndPath(MOD_ID, path);
    }
}
