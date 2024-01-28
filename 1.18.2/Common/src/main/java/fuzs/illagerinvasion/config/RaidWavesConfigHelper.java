package fuzs.illagerinvasion.config;

public class RaidWavesConfigHelper {
    public static final int[] BASHER_RAID_WAVES = getBasherRaidWaves();
    public static final int[] PROVOKER_RAID_WAVES = getProvokerRaidWaves();
    public static final int[] NECROMANCER_RAID_WAVES = getNecromancerRaidWaves();
    public static final int[] SORCERER_RAID_WAVES = getSorcererRaidWaves();
    public static final int[] ILLUSIONER_RAID_WAVES = getIllusionerRaidWaves();
    public static final int[] ARCHIVIST_RAID_WAVES = getArchivistRaidWaves();
    public static final int[] MARAUDER_RAID_WAVES = getMarauderRaidWaves();
    public static final int[] INQUISITOR_RAID_WAVES = getInquisitorRaidWaves();
    public static final int[] ALCHEMIST_RAID_WAVES = getAlchemistRaidWaves();

    public static int[] getEmptyRaidWaves() {
        return new int[]{0, 0, 0, 0, 0, 0, 0, 0};
    }

    public static int[] getBasherRaidWaves() {
        return new int[]{1, 1, 2, 1, 2, 2, 3, 3};
    }

    public static int[] getProvokerRaidWaves() {
        return new int[]{0, 1, 1, 0, 1, 1, 2, 2};
    }

    public static int[] getNecromancerRaidWaves() {
        return new int[]{0, 0, 0, 0, 1, 1, 1, 1};
    }

    public static int[] getSorcererRaidWaves() {
        return new int[]{0, 0, 0, 0, 0, 1, 1, 1};
    }

    public static int[] getIllusionerRaidWaves() {
        return new int[]{0, 0, 0, 0, 0, 1, 0, 1};
    }

    public static int[] getArchivistRaidWaves() {
        return new int[]{0, 1, 0, 1, 1, 1, 2, 3};
    }

    public static int[] getMarauderRaidWaves() {
        return new int[]{0, 1, 1, 1, 2, 2, 3, 3};
    }

    public static int[] getInquisitorRaidWaves() {
        return new int[]{0, 0, 0, 0, 1, 0, 1, 1};
    }

    public static int[] getAlchemistRaidWaves() {
        return new int[]{0, 0, 0, 1, 2, 1, 2, 2};
    }
}
