package fuzs.illagerinvasion.fabric.init;

import fuzs.illagerinvasion.config.RaidWavesConfigHelper;
import fuzs.illagerinvasion.fabric.mixin.accessor.Raid$RaiderTypeFabricAccessor;
import fuzs.illagerinvasion.init.ModEntityTypes;
import fuzs.illagerinvasion.init.ModEnumConstants;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;

public class FabricModEnumConstants {

    public static void bootstrap() {
        registerRaiderType(ModEnumConstants.BASHER_RAIDER_TYPE,
                ModEntityTypes.BASHER_ENTITY_TYPE,
                RaidWavesConfigHelper.BASHER_RAID_WAVES);
        registerRaiderType(ModEnumConstants.PROVOKER_RAIDER_TYPE,
                ModEntityTypes.PROVOKER_ENTITY_TYPE,
                RaidWavesConfigHelper.PROVOKER_RAID_WAVES);
        registerRaiderType(ModEnumConstants.NECROMANCER_RAIDER_TYPE,
                ModEntityTypes.NECROMANCER_ENTITY_TYPE,
                RaidWavesConfigHelper.NECROMANCER_RAID_WAVES);
        registerRaiderType(ModEnumConstants.SORCERER_RAIDER_TYPE,
                ModEntityTypes.SORCERER_ENTITY_TYPE,
                RaidWavesConfigHelper.SORCERER_RAID_WAVES);
        registerRaiderType(ModEnumConstants.ILLUSIONER_RAIDER_TYPE,
                EntityType.ILLUSIONER,
                RaidWavesConfigHelper.ILLUSIONER_RAID_WAVES);
        registerRaiderType(ModEnumConstants.ARCHIVIST_RAIDER_TYPE,
                ModEntityTypes.ARCHIVIST_ENTITY_TYPE,
                RaidWavesConfigHelper.ARCHIVIST_RAID_WAVES);
        registerRaiderType(ModEnumConstants.MARAUDER_RAIDER_TYPE,
                ModEntityTypes.MARAUDER_ENTITY_TYPE,
                RaidWavesConfigHelper.MARAUDER_RAID_WAVES);
        registerRaiderType(ModEnumConstants.INQUISITOR_RAIDER_TYPE,
                ModEntityTypes.INQUISITOR_ENTITY_TYPE,
                RaidWavesConfigHelper.INQUISITOR_RAID_WAVES);
        registerRaiderType(ModEnumConstants.ALCHEMIST_RAIDER_TYPE,
                ModEntityTypes.ALCHEMIST_ENTITY_TYPE,
                RaidWavesConfigHelper.ALCHEMIST_RAID_WAVES);
        registerRaiderType(ModEnumConstants.INVOKER_RAIDER_TYPE,
                ModEntityTypes.INVOKER_ENTITY_TYPE,
                RaidWavesConfigHelper.INVOKER_RAID_WAVES);
        Raid$RaiderTypeFabricAccessor.illagerinvasion$setValues(Raid.RaiderType.values());
    }

    static void registerRaiderType(Raid.RaiderType raiderType, Holder<? extends EntityType<? extends Raider>> holder, int[] spawnsPerWaveBeforeBonus) {
        registerRaiderType(raiderType, holder.value(), spawnsPerWaveBeforeBonus);
    }

    static void registerRaiderType(Raid.RaiderType raiderType, EntityType<? extends Raider> entityType, int[] spawnsPerWaveBeforeBonus) {
        Raid$RaiderTypeFabricAccessor.class.cast(raiderType).illagerinvasion$setEntityType(entityType);
        Raid$RaiderTypeFabricAccessor.class.cast(raiderType)
                .illagerinvasion$setSpawnsPerWaveBeforeBonus(spawnsPerWaveBeforeBonus);
    }
}
