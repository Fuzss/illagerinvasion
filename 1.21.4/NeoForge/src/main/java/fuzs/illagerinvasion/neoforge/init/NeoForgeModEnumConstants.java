package fuzs.illagerinvasion.neoforge.init;

import fuzs.illagerinvasion.config.RaidWavesConfigHelper;
import fuzs.illagerinvasion.init.ModEntityTypes;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.function.Supplier;

/**
 * Accessed by {@code enumextensions.json}.
 */
public class NeoForgeModEnumConstants {
    public static final EnumProxy<Raid.RaiderType> BASHER_RAIDER_TYPE = registerRaiderType(ModEntityTypes.BASHER_ENTITY_TYPE,
            RaidWavesConfigHelper.BASHER_RAID_WAVES);
    public static final EnumProxy<Raid.RaiderType> PROVOKER_RAIDER_TYPE = registerRaiderType(ModEntityTypes.PROVOKER_ENTITY_TYPE,
            RaidWavesConfigHelper.PROVOKER_RAID_WAVES);
    public static final EnumProxy<Raid.RaiderType> NECROMANCER_RAIDER_TYPE = registerRaiderType(ModEntityTypes.NECROMANCER_ENTITY_TYPE,
            RaidWavesConfigHelper.NECROMANCER_RAID_WAVES);
    public static final EnumProxy<Raid.RaiderType> SORCERER_RAIDER_TYPE = registerRaiderType(ModEntityTypes.SORCERER_ENTITY_TYPE,
            RaidWavesConfigHelper.SORCERER_RAID_WAVES);
    public static final EnumProxy<Raid.RaiderType> ILLUSIONER_RAIDER_TYPE = registerRaiderType(() -> EntityType.ILLUSIONER,
            RaidWavesConfigHelper.ILLUSIONER_RAID_WAVES);
    public static final EnumProxy<Raid.RaiderType> ARCHIVIST_RAIDER_TYPE = registerRaiderType(ModEntityTypes.ARCHIVIST_ENTITY_TYPE,
            RaidWavesConfigHelper.ARCHIVIST_RAID_WAVES);
    public static final EnumProxy<Raid.RaiderType> MARAUDER_RAIDER_TYPE = registerRaiderType(ModEntityTypes.MARAUDER_ENTITY_TYPE,
            RaidWavesConfigHelper.MARAUDER_RAID_WAVES);
    public static final EnumProxy<Raid.RaiderType> INQUISITOR_RAIDER_TYPE = registerRaiderType(ModEntityTypes.INQUISITOR_ENTITY_TYPE,
            RaidWavesConfigHelper.INQUISITOR_RAID_WAVES);
    public static final EnumProxy<Raid.RaiderType> ALCHEMIST_RAIDER_TYPE = registerRaiderType(ModEntityTypes.ALCHEMIST_ENTITY_TYPE,
            RaidWavesConfigHelper.ALCHEMIST_RAID_WAVES);
    public static final EnumProxy<Raid.RaiderType> INVOKER_RAIDER_TYPE = registerRaiderType(ModEntityTypes.INVOKER_ENTITY_TYPE,
            RaidWavesConfigHelper.INVOKER_RAID_WAVES);

    static EnumProxy<Raid.RaiderType> registerRaiderType(Holder<? extends EntityType<? extends Raider>> holder, int[] spawnsPerWaveBeforeBonus) {
        return registerRaiderType(holder::value, spawnsPerWaveBeforeBonus);
    }

    static EnumProxy<Raid.RaiderType> registerRaiderType(Supplier<EntityType<? extends Raider>> entityTypeSupplier, int[] spawnsPerWaveBeforeBonus) {
        return new EnumProxy<>(Raid.RaiderType.class, entityTypeSupplier, spawnsPerWaveBeforeBonus);
    }
}
