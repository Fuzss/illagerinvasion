package fuzs.illagerinvasion.config;

import fuzs.puzzleslib.api.config.v3.Config;
import fuzs.puzzleslib.api.config.v3.ConfigCore;

import java.util.function.Supplier;

public class ServerConfig implements ConfigCore {
    @Config
    public final IllagerConfig basher = new IllagerConfig(RaidWavesConfigHelper.BASHER_RAID_WAVES, RaidWavesConfigHelper::getBasherRaidWaves);
    @Config
    public final IllagerConfig provoker = new IllagerConfig(RaidWavesConfigHelper.PROVOKER_RAID_WAVES, RaidWavesConfigHelper::getProvokerRaidWaves);
    @Config
    public final IllagerConfig necromancer = new IllagerConfig(RaidWavesConfigHelper.NECROMANCER_RAID_WAVES, RaidWavesConfigHelper::getNecromancerRaidWaves);
    @Config
    public final IllagerConfig sorcerer = new IllagerConfig(RaidWavesConfigHelper.SORCERER_RAID_WAVES, RaidWavesConfigHelper::getSorcererRaidWaves);
    @Config
    public final IllagerConfig illusioner = new IllagerConfig(RaidWavesConfigHelper.ILLUSIONER_RAID_WAVES, RaidWavesConfigHelper::getIllusionerRaidWaves);
    @Config
    public final IllagerConfig archivist = new IllagerConfig(RaidWavesConfigHelper.ARCHIVIST_RAID_WAVES, RaidWavesConfigHelper::getArchivistRaidWaves);
    @Config
    public final IllagerConfig marauder = new IllagerConfig(RaidWavesConfigHelper.MARAUDER_RAID_WAVES, RaidWavesConfigHelper::getMarauderRaidWaves);
    @Config
    public final IllagerConfig inquisitor = new IllagerConfig(RaidWavesConfigHelper.INQUISITOR_RAID_WAVES, RaidWavesConfigHelper::getInquisitorRaidWaves);
    @Config
    public final IllagerConfig alchemist = new IllagerConfig(RaidWavesConfigHelper.ALCHEMIST_RAID_WAVES, RaidWavesConfigHelper::getAlchemistRaidWaves);

    public static class IllagerConfig implements ConfigCore {
        private final int[] raidWaves;
        private final Supplier<int[]> raidWavesSupplier;
        @Config(description = "Does this illager take part in village raids.")
        public boolean participateInRaids = true;

        public IllagerConfig(int[] raidWaves, Supplier<int[]> raidWavesSupplier) {
            this.raidWaves = raidWaves;
            this.raidWavesSupplier = raidWavesSupplier;
        }

        @Override
        public void afterConfigReload() {
            int[] source = this.participateInRaids ? this.raidWavesSupplier.get() : RaidWavesConfigHelper.getEmptyRaidWaves();
            System.arraycopy(source, 0, this.raidWaves, 0, this.raidWaves.length);
        }
    }
}
