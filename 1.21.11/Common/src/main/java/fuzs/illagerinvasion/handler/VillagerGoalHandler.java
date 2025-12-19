package fuzs.illagerinvasion.handler;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import fuzs.illagerinvasion.init.ModEntityTypes;
import fuzs.illagerinvasion.world.entity.monster.*;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.sensing.VillagerHostilesSensor;
import net.minecraft.world.entity.npc.AbstractVillager;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class VillagerGoalHandler {
    private static final List<AvoidVillagerEnemy<?>> VILLAGER_ENEMIES = ImmutableList.<AvoidVillagerEnemy<?>>builder()
            .add(new AvoidVillagerEnemy<>(Alchemist.class, ModEntityTypes.ALCHEMIST_ENTITY_TYPE::value, 12.0F))
            .add(new AvoidVillagerEnemy<>(Archivist.class, ModEntityTypes.ARCHIVIST_ENTITY_TYPE::value, 12.0F))
            .add(new AvoidVillagerEnemy<>(Basher.class, ModEntityTypes.BASHER_ENTITY_TYPE::value, 8.0F))
            .add(new AvoidVillagerEnemy<>(Firecaller.class, ModEntityTypes.FIRECALLER_ENTITY_TYPE::value, 12.0F))
            .add(new AvoidVillagerEnemy<>(Inquisitor.class, ModEntityTypes.INQUISITOR_ENTITY_TYPE::value, 15.0F))
            .add(new AvoidVillagerEnemy<>(Invoker.class, ModEntityTypes.INVOKER_ENTITY_TYPE::value, 15.0F))
            .add(new AvoidVillagerEnemy<>(Marauder.class, ModEntityTypes.MARAUDER_ENTITY_TYPE::value, 12.0F))
            .add(new AvoidVillagerEnemy<>(Necromancer.class, ModEntityTypes.NECROMANCER_ENTITY_TYPE::value, 15.0F))
            .add(new AvoidVillagerEnemy<>(Provoker.class, ModEntityTypes.PROVOKER_ENTITY_TYPE::value, 12.0F))
            .add(new AvoidVillagerEnemy<>(Sorcerer.class, ModEntityTypes.SORCERER_ENTITY_TYPE::value, 12.0F))
            .add(new AvoidVillagerEnemy<>(Surrendered.class, ModEntityTypes.SURRENDERED_ENTITY_TYPE::value, 8.0F))
            .build();

    public static EventResult onEntityLoad(Entity entity, ServerLevel serverLevel, boolean isNewlySpawned) {
        // do not do this for generic abstract villager, villagers that use the brain system instead of the goals seem to try to run both and flee much slower than they should
        if (entity.getType() == EntityType.WANDERING_TRADER) {
            for (AvoidVillagerEnemy<?> villagerEnemy : VILLAGER_ENEMIES) {
                villagerEnemy.addGoal((AbstractVillager) entity);
            }
        }

        return EventResult.PASS;
    }

    public static void init() {
        Map<EntityType<?>, Float> map = new IdentityHashMap<>(VillagerHostilesSensor.ACCEPTABLE_DISTANCE_FROM_HOSTILES);
        for (AvoidVillagerEnemy<?> villagerEnemy : VILLAGER_ENEMIES) {
            villagerEnemy.addAcceptableDistance(map::put);
        }

        VillagerHostilesSensor.ACCEPTABLE_DISTANCE_FROM_HOSTILES = ImmutableMap.copyOf(map);
    }

    private record AvoidVillagerEnemy<T extends LivingEntity>(Class<T> clazz,
                                                              Supplier<EntityType<T>> type,
                                                              float acceptableDistance) {

        public void addGoal(AbstractVillager abstractVillager) {
            abstractVillager.goalSelector.addGoal(1, this.makeGoal(abstractVillager));
        }

        private Goal makeGoal(AbstractVillager abstractVillager) {
            return new AvoidEntityGoal<>(abstractVillager, this.clazz, this.acceptableDistance, 0.5, 0.5);
        }

        public void addAcceptableDistance(BiConsumer<EntityType<?>, Float> consumer) {
            consumer.accept(this.type.get(), this.acceptableDistance);
        }
    }
}
