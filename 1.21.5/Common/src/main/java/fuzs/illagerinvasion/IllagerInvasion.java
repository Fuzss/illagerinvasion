package fuzs.illagerinvasion;

import fuzs.illagerinvasion.config.ServerConfig;
import fuzs.illagerinvasion.handler.VillagerGoalHandler;
import fuzs.illagerinvasion.init.ModEntityTypes;
import fuzs.illagerinvasion.init.ModEnumConstants;
import fuzs.illagerinvasion.init.ModLootTables;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.context.EntityAttributesContext;
import fuzs.puzzleslib.api.core.v1.context.SpawnPlacementsContext;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.api.event.v1.entity.ServerEntityLevelEvents;
import fuzs.puzzleslib.api.event.v1.server.LootTableLoadCallback;
import fuzs.puzzleslib.api.event.v1.server.RegisterPotionBrewingMixesCallback;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.levelgen.Heightmap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IllagerInvasion implements ModConstructor {
    public static final String MOD_ID = "illagerinvasion";
    public static final String MOD_NAME = "Illager Invasion";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final ConfigHolder CONFIG = ConfigHolder.builder(MOD_ID).server(ServerConfig.class);

    @Override
    public void onConstructMod() {
        ModRegistry.bootstrap();
        registerEventHandlers();
    }

    @Override
    public void onCommonSetup() {
        ModEnumConstants.bootstrap();
        VillagerGoalHandler.init();
    }

    private static void registerEventHandlers() {
        ServerEntityLevelEvents.LOAD.register(VillagerGoalHandler::onEntityLoad);
        LootTableLoadCallback.EVENT.register(ModLootTables::onLootTableLoad);
        RegisterPotionBrewingMixesCallback.EVENT.register(IllagerInvasion::registerPotionRecipes);
    }

    private static void registerPotionRecipes(RegisterPotionBrewingMixesCallback.Builder builder) {
        builder.registerPotionRecipe(Potions.AWKWARD, Items.GOAT_HORN, ModRegistry.BERSERKING_POTION);
        builder.registerPotionRecipe(ModRegistry.BERSERKING_POTION, Items.REDSTONE, ModRegistry.LONG_BERSERKING_POTION);
        builder.registerPotionRecipe(ModRegistry.BERSERKING_POTION,
                Items.GLOWSTONE_DUST,
                ModRegistry.STRONG_BERSERKING_POTION);
    }

    @Override
    public void onRegisterEntityAttributes(EntityAttributesContext context) {
        context.registerAttributes(ModEntityTypes.ALCHEMIST_ENTITY_TYPE.value(),
                Monster.createMonsterAttributes()
                        .add(Attributes.MAX_HEALTH, 24.0)
                        .add(Attributes.MOVEMENT_SPEED, 0.38));
        context.registerAttributes(ModEntityTypes.ARCHIVIST_ENTITY_TYPE.value(),
                Monster.createMonsterAttributes()
                        .add(Attributes.MAX_HEALTH, 24.0)
                        .add(Attributes.MOVEMENT_SPEED, 0.36));
        context.registerAttributes(ModEntityTypes.BASHER_ENTITY_TYPE.value(),
                Monster.createMonsterAttributes()
                        .add(Attributes.MAX_HEALTH, 32.0)
                        .add(Attributes.MOVEMENT_SPEED, 0.31)
                        .add(Attributes.ATTACK_DAMAGE, 3.0)
                        .add(Attributes.ATTACK_KNOCKBACK, 0.2));
        context.registerAttributes(ModEntityTypes.FIRECALLER_ENTITY_TYPE.value(),
                Monster.createMonsterAttributes()
                        .add(Attributes.MAX_HEALTH, 32.0)
                        .add(Attributes.MOVEMENT_SPEED, 0.38));
        context.registerAttributes(ModEntityTypes.INQUISITOR_ENTITY_TYPE.value(),
                Monster.createMonsterAttributes()
                        .add(Attributes.MAX_HEALTH, 80.0)
                        .add(Attributes.MOVEMENT_SPEED, 0.33)
                        .add(Attributes.ATTACK_DAMAGE, 10.0)
                        .add(Attributes.ATTACK_KNOCKBACK, 1.6)
                        .add(Attributes.KNOCKBACK_RESISTANCE, 0.8));
        context.registerAttributes(ModEntityTypes.INVOKER_ENTITY_TYPE.value(),
                Monster.createMonsterAttributes()
                        .add(Attributes.MAX_HEALTH, 250.0)
                        .add(Attributes.MOVEMENT_SPEED, 0.36)
                        .add(Attributes.KNOCKBACK_RESISTANCE, 0.3)
                        .add(Attributes.ATTACK_DAMAGE, 8.0));
        context.registerAttributes(ModEntityTypes.MARAUDER_ENTITY_TYPE.value(),
                Monster.createMonsterAttributes()
                        .add(Attributes.MAX_HEALTH, 24.0)
                        .add(Attributes.MOVEMENT_SPEED, 0.30));
        context.registerAttributes(ModEntityTypes.PROVOKER_ENTITY_TYPE.value(),
                Monster.createMonsterAttributes()
                        .add(Attributes.MAX_HEALTH, 24.0)
                        .add(Attributes.MOVEMENT_SPEED, 0.38));
        context.registerAttributes(ModEntityTypes.SORCERER_ENTITY_TYPE.value(),
                Monster.createMonsterAttributes()
                        .add(Attributes.MAX_HEALTH, 32.0)
                        .add(Attributes.MOVEMENT_SPEED, 0.38));
        context.registerAttributes(ModEntityTypes.SURRENDERED_ENTITY_TYPE.value(),
                Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 14.0).add(Attributes.ATTACK_DAMAGE, 5.0));
        context.registerAttributes(ModEntityTypes.NECROMANCER_ENTITY_TYPE.value(),
                Monster.createMonsterAttributes()
                        .add(Attributes.MAX_HEALTH, 32.0)
                        .add(Attributes.MOVEMENT_SPEED, 0.38));
    }

    @Override
    public void onRegisterSpawnPlacements(SpawnPlacementsContext context) {
        context.registerSpawnPlacement(ModEntityTypes.ALCHEMIST_ENTITY_TYPE.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.ARCHIVIST_ENTITY_TYPE.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.BASHER_ENTITY_TYPE.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.FIRECALLER_ENTITY_TYPE.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.INQUISITOR_ENTITY_TYPE.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.INVOKER_ENTITY_TYPE.value(),
                SpawnPlacementTypes.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.MARAUDER_ENTITY_TYPE.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.PROVOKER_ENTITY_TYPE.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.SORCERER_ENTITY_TYPE.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PatrollingMonster::checkPatrollingMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.SURRENDERED_ENTITY_TYPE.value(),
                SpawnPlacementTypes.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.NECROMANCER_ENTITY_TYPE.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PatrollingMonster::checkPatrollingMonsterSpawnRules);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocationHelper.fromNamespaceAndPath(MOD_ID, path);
    }
}
