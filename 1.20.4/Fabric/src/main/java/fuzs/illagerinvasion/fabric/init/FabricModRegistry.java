package fuzs.illagerinvasion.fabric.init;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.world.entity.projectile.Hatchet;
import fuzs.illagerinvasion.world.entity.projectile.SkullBolt;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class FabricModRegistry {
    static final RegistryManager REGISTRY = RegistryManager.from(IllagerInvasion.MOD_ID);
    public static final Holder.Reference<EntityType<SkullBolt>> SKULL_BOLT_ENTITY_TYPE = REGISTRY.register(Registries.ENTITY_TYPE, "skull_bolt", () -> FabricEntityTypeBuilder.<SkullBolt>create(MobCategory.MISC, SkullBolt::new).dimensions(EntityDimensions.scalable(0.3f, 0.3f)).trackRangeBlocks(4).trackedUpdateRate(10).build());
    public static final Holder.Reference<EntityType<Hatchet>> HATCHET_ENTITY_TYPE = REGISTRY.register(Registries.ENTITY_TYPE, "hatchet", () -> FabricEntityTypeBuilder.<Hatchet>create(MobCategory.MISC, Hatchet::new).dimensions(EntityDimensions.scalable(0.35f, 0.35f)).trackRangeBlocks(4).trackedUpdateRate(10).build());

    public static void touch() {

    }
}
