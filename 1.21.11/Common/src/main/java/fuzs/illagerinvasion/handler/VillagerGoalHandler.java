package fuzs.illagerinvasion.handler;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import fuzs.illagerinvasion.init.ModEntityTypes;
import fuzs.illagerinvasion.world.entity.monster.*;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.sensing.VillagerHostilesSensor;
import net.minecraft.world.entity.npc.villager.AbstractVillager;

import java.util.List;
import java.util.function.BiConsumer;

public class VillagerGoalHandler {
    private static final List<VillagerEnemy<?>> VILLAGER_ENEMIES = ImmutableList.<VillagerEnemy<?>>builder()
            .add(new VillagerEnemy<>(Alchemist.class, ModEntityTypes.ALCHEMIST_ENTITY_TYPE, 12.0F))
            .add(new VillagerEnemy<>(Archivist.class, ModEntityTypes.ARCHIVIST_ENTITY_TYPE, 12.0F))
            .add(new VillagerEnemy<>(Basher.class, ModEntityTypes.BASHER_ENTITY_TYPE, 8.0F))
            .add(new VillagerEnemy<>(Firecaller.class, ModEntityTypes.FIRECALLER_ENTITY_TYPE, 12.0F))
            .add(new VillagerEnemy<>(Inquisitor.class, ModEntityTypes.INQUISITOR_ENTITY_TYPE, 15.0F))
            .add(new VillagerEnemy<>(Invoker.class, ModEntityTypes.INVOKER_ENTITY_TYPE, 15.0F))
            .add(new VillagerEnemy<>(Marauder.class, ModEntityTypes.MARAUDER_ENTITY_TYPE, 12.0F))
            .add(new VillagerEnemy<>(Necromancer.class, ModEntityTypes.NECROMANCER_ENTITY_TYPE, 15.0F))
            .add(new VillagerEnemy<>(Provoker.class, ModEntityTypes.PROVOKER_ENTITY_TYPE, 12.0F))
            .add(new VillagerEnemy<>(Sorcerer.class, ModEntityTypes.SORCERER_ENTITY_TYPE, 12.0F))
            .add(new VillagerEnemy<>(Surrendered.class, ModEntityTypes.SURRENDERED_ENTITY_TYPE, 8.0F))
            .build();

    public static void forEach(BiConsumer<Holder<? extends EntityType<?>>, Float> consumer) {
        for (VillagerEnemy<?> villagerEnemy : VILLAGER_ENEMIES) {
            consumer.accept(villagerEnemy.type(), villagerEnemy.acceptableDistance());
        }
    }

    public static EventResult onEntityLoad(Entity entity, ServerLevel serverLevel, boolean isNewlySpawned) {
        // do not do this for generic abstract villager, villagers that use the brain system instead of the goals seem to try to run both and flee much slower than they should
        if (entity.getType() == EntityType.WANDERING_TRADER) {
            for (VillagerEnemy<?> villagerEnemy : VILLAGER_ENEMIES) {
                villagerEnemy.addGoal((AbstractVillager) entity);
            }
        }

        return EventResult.PASS;
    }

    public static void registerAcceptableDistanceFromHostiles() {
        ImmutableMap.Builder<EntityType<?>, Float> builder = ImmutableMap.<EntityType<?>, Float>builder()
                .putAll(VillagerHostilesSensor.ACCEPTABLE_DISTANCE_FROM_HOSTILES);
        for (VillagerEnemy<?> villagerEnemy : VILLAGER_ENEMIES) {
            villagerEnemy.addAcceptableDistance(builder::put);
        }

        VillagerHostilesSensor.ACCEPTABLE_DISTANCE_FROM_HOSTILES = builder.build();
    }

    private record VillagerEnemy<T extends LivingEntity>(Class<T> clazz,
                                                         Holder<EntityType<T>> type,
                                                         float acceptableDistance) {

        public void addGoal(AbstractVillager abstractVillager) {
            abstractVillager.goalSelector.addGoal(1, this.makeGoal(abstractVillager));
        }

        private Goal makeGoal(AbstractVillager abstractVillager) {
            return new AvoidEntityGoal<>(abstractVillager, this.clazz, this.acceptableDistance, 0.5, 0.5);
        }

        public void addAcceptableDistance(BiConsumer<EntityType<?>, Float> consumer) {
            consumer.accept(this.type.value(), this.acceptableDistance);
        }
    }
}
