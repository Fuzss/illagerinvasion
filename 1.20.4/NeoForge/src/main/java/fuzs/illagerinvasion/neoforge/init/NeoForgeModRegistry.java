package fuzs.illagerinvasion.neoforge.init;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.world.entity.projectile.Hatchet;
import fuzs.illagerinvasion.world.entity.projectile.SkullBolt;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class NeoForgeModRegistry {
    static final RegistryManager REGISTRY = RegistryManager.from(IllagerInvasion.MOD_ID);
    public static final Holder.Reference<EntityType<SkullBolt>> SKULL_BOLT_ENTITY_TYPE = REGISTRY.registerEntityType("skull_bolt", () -> EntityType.Builder.<SkullBolt>of(SkullBolt::new, MobCategory.MISC).sized(0.3f, 0.3f).setTrackingRange(4).setUpdateInterval(10));
    public static final Holder.Reference<EntityType<Hatchet>> HATCHET_ENTITY_TYPE = REGISTRY.registerEntityType("hatchet", () -> EntityType.Builder.<Hatchet>of(Hatchet::new, MobCategory.MISC).sized(0.35f, 0.35f).setTrackingRange(4).setUpdateInterval(10));

    public static void touch() {

    }
}
