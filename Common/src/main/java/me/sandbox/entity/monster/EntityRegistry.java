package me.sandbox.entity.monster;

import me.sandbox.IllagerExpansion;
import me.sandbox.entity.projectile.Hatchet;
import me.sandbox.entity.projectile.FlyingMagma;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.entity.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class EntityRegistry {


    //Register Entities
    public static final EntityType<Provoker> PROVOKER = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(IllagerExpansion.MOD_ID,"provoker"),
            FabricEntityTypeBuilder.create(MobCategory.MONSTER, Provoker::new).dimensions(EntityDimensions.fixed(0.5f, 1.92f)).build()
    );
    public static final EntityType<Invoker> INVOKER = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(IllagerExpansion.MOD_ID,"invoker"),
            FabricEntityTypeBuilder.create(MobCategory.MONSTER, Invoker::new).dimensions(EntityDimensions.fixed(0.5f, 1.92f)).build()
    );
    public static final EntityType<Basher> BASHER = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(IllagerExpansion.MOD_ID,"basher"),
            FabricEntityTypeBuilder.create(MobCategory.MONSTER, Basher::new).dimensions(EntityDimensions.fixed(0.5f, 1.92f)).build()
    );
    public static final EntityType<Sorcerer> SORCERER = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(IllagerExpansion.MOD_ID,"sorcerer"),
            FabricEntityTypeBuilder.create(MobCategory.MONSTER, Sorcerer::new).dimensions(EntityDimensions.fixed(0.5f, 1.92f)).build()
    );
    public static final EntityType<Archivist> ARCHIVIST = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(IllagerExpansion.MOD_ID,"archivist"),
            FabricEntityTypeBuilder.create(MobCategory.MONSTER, Archivist::new).dimensions(EntityDimensions.fixed(0.5f, 1.92f)).build()
    );
    public static final EntityType<Inquisitor> INQUISITOR = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(IllagerExpansion.MOD_ID,"inquisitor"),
            FabricEntityTypeBuilder.create(MobCategory.MONSTER, Inquisitor::new).dimensions(EntityDimensions.fixed(0.5f, 2.48f)).build()
    );
    public static final EntityType<Marauder> MARAUDER = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(IllagerExpansion.MOD_ID,"marauder"),
            FabricEntityTypeBuilder.create(MobCategory.MONSTER, Marauder::new).dimensions(EntityDimensions.fixed(0.5f, 1.92f)).build()
    );
    public static final EntityType<Alchemist> ALCHEMIST = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(IllagerExpansion.MOD_ID,"alchemist"),
            FabricEntityTypeBuilder.create(MobCategory.MONSTER, Alchemist::new).dimensions(EntityDimensions.fixed(0.5f, 1.92f)).build()
    );
    public static final EntityType<Firecaller> FIRECALLER = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(IllagerExpansion.MOD_ID,"firecaller"),
            FabricEntityTypeBuilder.create(MobCategory.MONSTER, Firecaller::new).dimensions(EntityDimensions.fixed(0.5f, 1.92f)).build()
    );
    public static final EntityType<Surrendered> SURRENDERED = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(IllagerExpansion.MOD_ID,"surrendered"),
            FabricEntityTypeBuilder.create(MobCategory.MONSTER, Surrendered::new).fireImmune().dimensions(EntityDimensions.fixed(0.5f, 1.42f)).build()
    );
    public static final EntityType<Hatchet> HATCHET = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(IllagerExpansion.MOD_ID, "hatchet"),
            FabricEntityTypeBuilder.<Hatchet>create(MobCategory.MISC, (Hatchet::new)).dimensions(EntityDimensions.fixed(0.35f,0.35f)).trackRangeBlocks(4).trackedUpdateRate(10).build()
    );
    public static final EntityType<InvokerFangs> INVOKER_FANGS = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(IllagerExpansion.MOD_ID,"invoker_fangs"),
            FabricEntityTypeBuilder.<InvokerFangs>create(MobCategory.MISC, InvokerFangs::new).dimensions(EntityDimensions.fixed(0.65f, 1.05f)).build()
    );
    public static final EntityType<FlyingMagma> MAGMA = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(IllagerExpansion.MOD_ID,"magma"),
            FabricEntityTypeBuilder.<FlyingMagma>create(MobCategory.MISC, FlyingMagma::new).dimensions(EntityDimensions.fixed(0.95f, 1.05f)).build()
    );

    public static void registerEntities() {
    }

}
