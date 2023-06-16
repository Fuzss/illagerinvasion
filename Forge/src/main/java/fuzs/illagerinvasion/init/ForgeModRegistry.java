package fuzs.illagerinvasion.init;

import fuzs.illagerinvasion.world.entity.projectile.Hatchet;
import fuzs.illagerinvasion.world.entity.projectile.SkullBolt;
import fuzs.puzzleslib.api.init.v2.RegistryReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import static fuzs.illagerinvasion.init.ModRegistry.REGISTRY;

public class ForgeModRegistry {
    public static final RegistryReference<EntityType<SkullBolt>> SKULL_BOLT_ENTITY_TYPE = REGISTRY.registerEntityType("skull_bolt", () -> EntityType.Builder.<SkullBolt>of(SkullBolt::new, MobCategory.MISC).sized(0.3f, 0.3f).setTrackingRange(4).setUpdateInterval(10));
    public static final RegistryReference<EntityType<Hatchet>> HATCHET_ENTITY_TYPE = REGISTRY.registerEntityType("hatchet", () -> EntityType.Builder.<Hatchet>of(Hatchet::new, MobCategory.MISC).sized(0.35f, 0.35f).setTrackingRange(4).setUpdateInterval(10));

    public static void touch() {

    }
}
