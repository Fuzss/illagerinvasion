package fuzs.illagerinvasion.init;

import fuzs.illagerinvasion.world.entity.monster.*;
import fuzs.illagerinvasion.world.entity.projectile.FlyingMagma;
import fuzs.illagerinvasion.world.entity.projectile.SkullBolt;
import fuzs.illagerinvasion.world.entity.projectile.ThrownHatchet;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntityTypes {
    public static final Holder.Reference<EntityType<Provoker>> PROVOKER_ENTITY_TYPE = ModRegistry.REGISTRIES.registerEntityType(
            "provoker",
            () -> EntityType.Builder.of(Provoker::new, MobCategory.MONSTER).sized(0.5F, 1.92F).notInPeaceful());
    public static final Holder.Reference<EntityType<Invoker>> INVOKER_ENTITY_TYPE = ModRegistry.REGISTRIES.registerEntityType(
            "invoker",
            () -> EntityType.Builder.of(Invoker::new, MobCategory.MONSTER)
                    .sized(0.5F, 1.92F)
                    .fireImmune()
                    .notInPeaceful());
    public static final Holder.Reference<EntityType<Necromancer>> NECROMANCER_ENTITY_TYPE = ModRegistry.REGISTRIES.registerEntityType(
            "necromancer",
            () -> EntityType.Builder.of(Necromancer::new, MobCategory.MONSTER).sized(0.5F, 1.92F).notInPeaceful());
    public static final Holder.Reference<EntityType<Basher>> BASHER_ENTITY_TYPE = ModRegistry.REGISTRIES.registerEntityType(
            "basher",
            () -> EntityType.Builder.of(Basher::new, MobCategory.MONSTER).sized(0.5F, 1.92F).notInPeaceful());
    public static final Holder.Reference<EntityType<Sorcerer>> SORCERER_ENTITY_TYPE = ModRegistry.REGISTRIES.registerEntityType(
            "sorcerer",
            () -> EntityType.Builder.of(Sorcerer::new, MobCategory.MONSTER).sized(0.5F, 1.92F).notInPeaceful());
    public static final Holder.Reference<EntityType<Archivist>> ARCHIVIST_ENTITY_TYPE = ModRegistry.REGISTRIES.registerEntityType(
            "archivist",
            () -> EntityType.Builder.of(Archivist::new, MobCategory.MONSTER).sized(0.5F, 1.92F).notInPeaceful());
    public static final Holder.Reference<EntityType<Inquisitor>> INQUISITOR_ENTITY_TYPE = ModRegistry.REGISTRIES.registerEntityType(
            "inquisitor",
            () -> EntityType.Builder.of(Inquisitor::new, MobCategory.MONSTER).sized(0.5F, 2.48F).notInPeaceful());
    public static final Holder.Reference<EntityType<Marauder>> MARAUDER_ENTITY_TYPE = ModRegistry.REGISTRIES.registerEntityType(
            "marauder",
            () -> EntityType.Builder.of(Marauder::new, MobCategory.MONSTER)
                    .sized(0.5F, 1.92F)
                    .canSpawnFarFromPlayer()
                    .notInPeaceful());
    public static final Holder.Reference<EntityType<Alchemist>> ALCHEMIST_ENTITY_TYPE = ModRegistry.REGISTRIES.registerEntityType(
            "alchemist",
            () -> EntityType.Builder.of(Alchemist::new, MobCategory.MONSTER).sized(0.5F, 1.92F).notInPeaceful());
    public static final Holder.Reference<EntityType<Firecaller>> FIRECALLER_ENTITY_TYPE = ModRegistry.REGISTRIES.registerEntityType(
            "firecaller",
            () -> EntityType.Builder.of(Firecaller::new, MobCategory.MONSTER)
                    .sized(0.5F, 1.92F)
                    .fireImmune()
                    .notInPeaceful());
    public static final Holder.Reference<EntityType<Surrendered>> SURRENDERED_ENTITY_TYPE = ModRegistry.REGISTRIES.registerEntityType(
            "surrendered",
            () -> EntityType.Builder.of(Surrendered::new, MobCategory.MONSTER)
                    .sized(0.5F, 1.42F)
                    .eyeHeight(1.1F)
                    .fireImmune()
                    .notInPeaceful());
    public static final Holder.Reference<EntityType<SkullBolt>> SKULL_BOLT_ENTITY_TYPE = ModRegistry.REGISTRIES.registerEntityType(
            "skull_bolt",
            () -> EntityType.Builder.<SkullBolt>of(SkullBolt::new, MobCategory.MISC)
                    .sized(0.3F, 0.3F)
                    .clientTrackingRange(4)
                    .updateInterval(10));
    public static final Holder.Reference<EntityType<ThrownHatchet>> HATCHET_ENTITY_TYPE = ModRegistry.REGISTRIES.registerEntityType(
            "hatchet",
            () -> EntityType.Builder.<ThrownHatchet>of(ThrownHatchet::new, MobCategory.MISC)
                    .sized(0.35F, 0.35F)
                    .clientTrackingRange(4)
                    .updateInterval(10));
    public static final Holder.Reference<EntityType<InvokerFangs>> INVOKER_FANGS_ENTITY_TYPE = ModRegistry.REGISTRIES.registerEntityType(
            "invoker_fangs",
            () -> EntityType.Builder.<InvokerFangs>of(InvokerFangs::new, MobCategory.MISC).sized(0.65F, 1.05F));
    public static final Holder.Reference<EntityType<FlyingMagma>> FLYING_MAGMA_ENTITY_TYPE = ModRegistry.REGISTRIES.registerEntityType(
            "flying_magma",
            () -> EntityType.Builder.<FlyingMagma>of(FlyingMagma::new, MobCategory.MISC).sized(0.95F, 1.05F));

    public static void bootstrap() {
        // NO-OP
    }
}
