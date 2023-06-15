package fuzs.illagerinvasion.init;

import fuzs.illagerinvasion.world.entity.projectile.Hatchet;
import fuzs.puzzleslib.api.init.v2.RegistryReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ForgeModRegistry {
    public static final RegistryReference<EntityType<Hatchet>> HATCHET_ENTITY_TYPE = ModRegistry.REGISTRY.registerEntityType("hatchet", () -> EntityType.Builder.<Hatchet>of(Hatchet::new, MobCategory.MISC).sized(0.35f, 0.35f).setTrackingRange(4).setUpdateInterval(10));

    public static void touch() {

    }
}
