package fuzs.illagerinvasion.init;

import fuzs.illagerinvasion.world.entity.projectile.Hatchet;
import fuzs.illagerinvasion.world.entity.projectile.SkullBolt;
import fuzs.puzzleslib.api.init.v2.RegistryReference;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import static fuzs.illagerinvasion.init.ModRegistry.REGISTRY;

public class FabricModRegistry {
    public static final RegistryReference<EntityType<SkullBolt>> SKULL_BOLT_ENTITY_TYPE = REGISTRY.register(Registries.ENTITY_TYPE, "skull_bolt", () -> FabricEntityTypeBuilder.<SkullBolt>create(MobCategory.MISC, SkullBolt::new).dimensions(EntityDimensions.scalable(0.3f, 0.3f)).trackRangeBlocks(4).trackedUpdateRate(10).build());
    public static final RegistryReference<EntityType<Hatchet>> HATCHET_ENTITY_TYPE = REGISTRY.register(Registries.ENTITY_TYPE, "hatchet", () -> FabricEntityTypeBuilder.<Hatchet>create(MobCategory.MISC, Hatchet::new).dimensions(EntityDimensions.scalable(0.35f, 0.35f)).trackRangeBlocks(4).trackedUpdateRate(10).build());

    public static void touch() {

    }
}
