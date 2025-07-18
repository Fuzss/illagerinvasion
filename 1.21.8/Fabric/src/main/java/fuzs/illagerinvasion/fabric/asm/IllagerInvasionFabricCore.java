package fuzs.illagerinvasion.fabric.asm;

import com.chocohead.mm.api.ClassTinkerers;
import fuzs.illagerinvasion.config.RaidWavesConfigHelper;
import fuzs.illagerinvasion.init.ModEntityTypes;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.world.entity.EntityType;

public class IllagerInvasionFabricCore implements Runnable {

    @Override
    public void run() {
        MappingResolver mappingResolver = FabricLoader.getInstance().getMappingResolver();
        // net/minecraft/world/entity/raid/Raid$RaiderType
        String enumType = mappingResolver.mapClassName("intermediary", "net.minecraft.class_3765$class_3766");
        // net/minecraft/world/entity/EntityType
        String entityTypeType = "L" + mappingResolver.mapClassName("intermediary", "net.minecraft.class_1299") + ";";
        ClassTinkerers.enumBuilder(enumType, entityTypeType, int[].class)
                .addEnum("ILLAGERINVASION_BASHER", () -> new Object[]{
                        ModEntityTypes.BASHER_ENTITY_TYPE.value(), RaidWavesConfigHelper.BASHER_RAID_WAVES
                })
                .addEnum("ILLAGERINVASION_PROVOKER", () -> new Object[]{
                        ModEntityTypes.PROVOKER_ENTITY_TYPE.value(), RaidWavesConfigHelper.PROVOKER_RAID_WAVES
                })
                .addEnum("ILLAGERINVASION_NECROMANCER", () -> new Object[]{
                        ModEntityTypes.NECROMANCER_ENTITY_TYPE.value(), RaidWavesConfigHelper.NECROMANCER_RAID_WAVES
                })
                .addEnum("ILLAGERINVASION_SORCERER", () -> new Object[]{
                        ModEntityTypes.SORCERER_ENTITY_TYPE.value(), RaidWavesConfigHelper.SORCERER_RAID_WAVES
                })
                .addEnum("ILLAGERINVASION_ILLUSIONER", () -> new Object[]{
                        EntityType.ILLUSIONER, RaidWavesConfigHelper.ILLUSIONER_RAID_WAVES
                })
                .addEnum("ILLAGERINVASION_ARCHIVIST", () -> new Object[]{
                        ModEntityTypes.ARCHIVIST_ENTITY_TYPE.value(), RaidWavesConfigHelper.ARCHIVIST_RAID_WAVES
                })
                .addEnum("ILLAGERINVASION_MARAUDER", () -> new Object[]{
                        ModEntityTypes.MARAUDER_ENTITY_TYPE.value(), RaidWavesConfigHelper.MARAUDER_RAID_WAVES
                })
                .addEnum("ILLAGERINVASION_INQUISITOR", () -> new Object[]{
                        ModEntityTypes.INQUISITOR_ENTITY_TYPE.value(), RaidWavesConfigHelper.INQUISITOR_RAID_WAVES
                })
                .addEnum("ILLAGERINVASION_ALCHEMIST", () -> new Object[]{
                        ModEntityTypes.ALCHEMIST_ENTITY_TYPE.value(), RaidWavesConfigHelper.ALCHEMIST_RAID_WAVES
                })
                .addEnum("ILLAGERINVASION_INVOKER", () -> new Object[]{
                        ModEntityTypes.INVOKER_ENTITY_TYPE.value(), RaidWavesConfigHelper.INVOKER_RAID_WAVES
                })
                .build();
    }
}
