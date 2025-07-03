package fuzs.illagerinvasion.config;

import fuzs.puzzleslib.api.config.v3.Config;
import fuzs.puzzleslib.api.config.v3.ConfigCore;

import java.util.function.Supplier;

public class ServerConfig implements ConfigCore {
    @Config(
            category = "general", description = {
            "Will platinum armor trims provide special effects to the player depending on the piece of armor they are applied to.",
            "Helmet: More experience from killing mobs.",
            "Chestplate: Faster block breaking speed when using an incorrect or no tool.",
            "Leggings: Slower hunger drain when performaing actions.",
            "Boots: Farmland can no longer be trampled."
    }
    )
    public boolean platinumTrimEffects = true;
    @Config(category = "invoker", description = "Show a yellow boss bar when fighting an invoker.")
    public boolean invokerBossBar = true;
    @Config
    public final IllagerConfig basher = new IllagerConfig(RaidWavesConfigHelper.BASHER_RAID_WAVES,
            RaidWavesConfigHelper::getBasherRaidWaves);
    @Config
    public final IllagerConfig provoker = new IllagerConfig(RaidWavesConfigHelper.PROVOKER_RAID_WAVES,
            RaidWavesConfigHelper::getProvokerRaidWaves);
    @Config
    public final IllagerConfig necromancer = new IllagerConfig(RaidWavesConfigHelper.NECROMANCER_RAID_WAVES,
            RaidWavesConfigHelper::getNecromancerRaidWaves);
    @Config
    public final IllagerConfig sorcerer = new IllagerConfig(RaidWavesConfigHelper.SORCERER_RAID_WAVES,
            RaidWavesConfigHelper::getSorcererRaidWaves);
    @Config
    public final IllagerConfig illusioner = new IllagerConfig(RaidWavesConfigHelper.ILLUSIONER_RAID_WAVES,
            RaidWavesConfigHelper::getIllusionerRaidWaves);
    @Config
    public final IllagerConfig archivist = new IllagerConfig(RaidWavesConfigHelper.ARCHIVIST_RAID_WAVES,
            RaidWavesConfigHelper::getArchivistRaidWaves);
    @Config
    public final IllagerConfig marauder = new IllagerConfig(RaidWavesConfigHelper.MARAUDER_RAID_WAVES,
            RaidWavesConfigHelper::getMarauderRaidWaves);
    @Config
    public final IllagerConfig inquisitor = new IllagerConfig(RaidWavesConfigHelper.INQUISITOR_RAID_WAVES,
            RaidWavesConfigHelper::getInquisitorRaidWaves);
    @Config
    public final IllagerConfig alchemist = new IllagerConfig(RaidWavesConfigHelper.ALCHEMIST_RAID_WAVES,
            RaidWavesConfigHelper::getAlchemistRaidWaves);
    @Config
    public final IllagerConfig invoker = new IllagerConfig(RaidWavesConfigHelper.INVOKER_RAID_WAVES,
            RaidWavesConfigHelper::getInvokerRaidWaves);

    public ServerConfig() {
        this.invoker.participateInRaids = false;
    }

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
            int[] source =
                    this.participateInRaids ? this.raidWavesSupplier.get() : RaidWavesConfigHelper.getEmptyRaidWaves();
            System.arraycopy(source, 0, this.raidWaves, 0, this.raidWaves.length);
        }
    }
}
