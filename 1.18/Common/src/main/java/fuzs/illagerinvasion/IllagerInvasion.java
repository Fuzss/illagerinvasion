package fuzs.illagerinvasion;

import fuzs.illagerinvasion.config.RaidWavesConfigHelper;
import fuzs.illagerinvasion.config.ServerConfig;
import fuzs.illagerinvasion.core.CommonAbstractions;
import fuzs.illagerinvasion.handler.VillagerGoalHandler;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.context.CreativeModeTabContext;
import fuzs.puzzleslib.api.core.v1.context.EntityAttributesCreateContext;
import fuzs.puzzleslib.api.core.v1.context.SpawnPlacementsContext;
import fuzs.puzzleslib.api.event.v1.entity.ServerEntityLevelEvents;
import fuzs.puzzleslib.api.event.v1.server.LootTableLoadEvents;
import fuzs.puzzleslib.api.init.v2.PotionBrewingRegistry;
import fuzs.puzzleslib.api.item.v2.CreativeModeTabConfigurator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
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
        registerHandlers();
        VillagerGoalHandler.init();
    }

    private static void registerHandlers() {
//        PlayerEvents.BREAK_SPEED.register(PlatinumTrimHandler::onBreakSpeed);
//        PlayerTickEvents.START.register(PlatinumTrimHandler::onStartPlayerTick);
//        PlayerTickEvents.END.register(PlatinumTrimHandler::onEndPlayerTick);
//        LivingExperienceDropCallback.EVENT.register(PlatinumTrimHandler::onLivingExperienceDrop);
//        BlockEvents.FARMLAND_TRAMPLE.register(PlatinumTrimHandler::onFarmlandTrample);
        ServerEntityLevelEvents.LOAD.register(VillagerGoalHandler::onEntityJoinServerLevel);
        LootTableLoadEvents.MODIFY.register((LootTables lootManager, ResourceLocation identifier, Consumer<LootPool> addPool, IntPredicate removePool) -> {
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
    public void onCommonSetup() {
        registerRaiderTypes();
        registerPotionRecipes();
    }

    private static void registerRaiderTypes() {
        String s = MOD_ID.toUpperCase(Locale.ROOT) + "$";
        CommonAbstractions.INSTANCE.registerRaiderType(s + "BASHER", ModRegistry.BASHER_ENTITY_TYPE.get(), RaidWavesConfigHelper.BASHER_RAID_WAVES);
        CommonAbstractions.INSTANCE.registerRaiderType(s + "PROVOKER", ModRegistry.PROVOKER_ENTITY_TYPE.get(), RaidWavesConfigHelper.PROVOKER_RAID_WAVES);
        CommonAbstractions.INSTANCE.registerRaiderType(s + "NECROMANCER", ModRegistry.NECROMANCER_ENTITY_TYPE.get(), RaidWavesConfigHelper.NECROMANCER_RAID_WAVES);
        CommonAbstractions.INSTANCE.registerRaiderType(s + "SORCERER", ModRegistry.SORCERER_ENTITY_TYPE.get(), RaidWavesConfigHelper.SORCERER_RAID_WAVES);
        CommonAbstractions.INSTANCE.registerRaiderType(s + "ILLUSIONER", EntityType.ILLUSIONER, RaidWavesConfigHelper.ILLUSIONER_RAID_WAVES);
        CommonAbstractions.INSTANCE.registerRaiderType(s + "ARCHIVIST", ModRegistry.ARCHIVIST_ENTITY_TYPE.get(), RaidWavesConfigHelper.ARCHIVIST_RAID_WAVES);
        CommonAbstractions.INSTANCE.registerRaiderType(s + "MARAUDER", ModRegistry.MARAUDER_ENTITY_TYPE.get(), RaidWavesConfigHelper.MARAUDER_RAID_WAVES);
        CommonAbstractions.INSTANCE.registerRaiderType(s + "INQUISITOR", ModRegistry.INQUISITOR_ENTITY_TYPE.get(), RaidWavesConfigHelper.INQUISITOR_RAID_WAVES);
        CommonAbstractions.INSTANCE.registerRaiderType(s + "ALCHEMIST", ModRegistry.ALCHEMIST_ENTITY_TYPE.get(), RaidWavesConfigHelper.ALCHEMIST_RAID_WAVES);
    }

    private static void registerPotionRecipes() {
//        PotionBrewingRegistry.INSTANCE.registerPotionRecipe(Potions.AWKWARD, Items.GOAT_HORN, ModRegistry.BERSERKING_POTION.get());
        PotionBrewingRegistry.INSTANCE.registerPotionRecipe(ModRegistry.BERSERKING_POTION.get(), Items.REDSTONE, ModRegistry.LONG_BERSERKING_POTION.get());
        PotionBrewingRegistry.INSTANCE.registerPotionRecipe(ModRegistry.BERSERKING_POTION.get(), Items.GLOWSTONE_DUST, ModRegistry.STRONG_BERSERKING_POTION.get());
    }

    @Override
    public void onEntityAttributeCreation(EntityAttributesCreateContext context) {
        context.registerEntityAttributes(ModRegistry.ALCHEMIST_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 24.0).add(Attributes.MOVEMENT_SPEED, 0.38));
        context.registerEntityAttributes(ModRegistry.ARCHIVIST_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 24.0D).add(Attributes.MOVEMENT_SPEED, 0.36D));
        context.registerEntityAttributes(ModRegistry.BASHER_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 32.0D).add(Attributes.MOVEMENT_SPEED, 0.31D).add(Attributes.ATTACK_DAMAGE, 3.0D).add(Attributes.ATTACK_KNOCKBACK, 0.2D));
        context.registerEntityAttributes(ModRegistry.FIRECALLER_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 32.0).add(Attributes.MOVEMENT_SPEED, 0.38));
        context.registerEntityAttributes(ModRegistry.INQUISITOR_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 80.0).add(Attributes.MOVEMENT_SPEED, 0.33).add(Attributes.ATTACK_DAMAGE, 10.0).add(Attributes.ATTACK_KNOCKBACK, 1.6).add(Attributes.KNOCKBACK_RESISTANCE, 0.8));
        context.registerEntityAttributes(ModRegistry.INVOKER_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 250.0D).add(Attributes.MOVEMENT_SPEED, 0.36D).add(Attributes.KNOCKBACK_RESISTANCE, 0.3D).add(Attributes.ATTACK_DAMAGE, 8.0D));
        context.registerEntityAttributes(ModRegistry.MARAUDER_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 24.0D).add(Attributes.MOVEMENT_SPEED, 0.30D));
        context.registerEntityAttributes(ModRegistry.PROVOKER_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 24.0D).add(Attributes.MOVEMENT_SPEED, 0.38D));
        context.registerEntityAttributes(ModRegistry.SORCERER_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 32.0D).add(Attributes.MOVEMENT_SPEED, 0.38D));
        context.registerEntityAttributes(ModRegistry.SURRENDERED_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 14.0D).add(Attributes.ATTACK_DAMAGE, 5.0D));
        context.registerEntityAttributes(ModRegistry.NECROMANCER_ENTITY_TYPE.get(), Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 32.0).add(Attributes.MOVEMENT_SPEED, 0.38));
    }

    @Override
    public void onRegisterSpawnPlacements(SpawnPlacementsContext context) {
        context.registerSpawnPlacement(ModRegistry.ALCHEMIST_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModRegistry.ARCHIVIST_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModRegistry.BASHER_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModRegistry.FIRECALLER_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModRegistry.INQUISITOR_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModRegistry.INVOKER_ENTITY_TYPE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        context.registerSpawnPlacement(ModRegistry.MARAUDER_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModRegistry.PROVOKER_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModRegistry.SORCERER_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModRegistry.SURRENDERED_ENTITY_TYPE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        context.registerSpawnPlacement(ModRegistry.NECROMANCER_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::checkPatrollingMonsterSpawnRules);
    }

    @Override
    public void onRegisterCreativeModeTabs(CreativeModeTabContext context) {
        context.registerCreativeModeTab(CreativeModeTabConfigurator.from(MOD_ID).icon(() -> new ItemStack(ModRegistry.LOST_CANDLE_ITEM.get())).displayItems((output) -> {
            output.accept(ModRegistry.IMBUIING_TABLE_ITEM.get());
            output.accept(ModRegistry.UNUSUAL_DUST_ITEM.get());
            output.accept(ModRegistry.ILLUSIONARY_DUST_ITEM.get());
            output.accept(ModRegistry.HALLOWED_GEM_ITEM.get());
            output.accept(ModRegistry.PRIMAL_ESSENCE_ITEM.get());
            output.accept(ModRegistry.PLATINUM_CHUNK_ITEM.get());
            output.accept(ModRegistry.PLATINUM_SHEET_ITEM.get());
            output.accept(ModRegistry.PLATINUM_INFUSED_HATCHET_ITEM.get());
//            output.accept(ModRegistry.HORN_OF_SIGHT_ITEM.get());
            output.accept(ModRegistry.LOST_CANDLE_ITEM.get());
            output.accept(ModRegistry.MAGICAL_FIRE_CHARGE_ITEM.get());
            output.accept(ModRegistry.PROVOKER_SPAWN_EGG_ITEM.get());
            output.accept(ModRegistry.BASHER_SPAWN_EGG_ITEM.get());
            output.accept(ModRegistry.SORCERER_SPAWN_EGG_ITEM.get());
            output.accept(ModRegistry.ARCHIVIST_SPAWN_EGG_ITEM.get());
            output.accept(ModRegistry.INQUISITOR_SPAWN_EGG_ITEM.get());
            output.accept(ModRegistry.MARAUDER_SPAWN_EGG_ITEM.get());
            output.accept(ModRegistry.INVOKER_SPAWN_EGG_ITEM.get());
            output.accept(ModRegistry.ALCHEMIST_SPAWN_EGG_ITEM.get());
            output.accept(ModRegistry.FIRECALLER_SPAWN_EGG_ITEM.get());
            output.accept(ModRegistry.NECROMANCER_SPAWN_EGG_ITEM.get());
            output.accept(ModRegistry.SURRENDERED_SPAWN_EGG_ITEM.get());
            output.accept(ModRegistry.ILLUSIONER_SPAWN_EGG_ITEM.get());
        }));
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
