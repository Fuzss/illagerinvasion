package fuzs.illagerinvasion.util;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;

public class TeleportUtil {

    public static boolean tryRandomTeleport(LivingEntity entity) {
        for (int i = 0; i < 64; ++i) {
            if (randomTeleport(entity)) {
                return true;
            }
        }
        return false;
    }

    private static boolean randomTeleport(LivingEntity entity) {
        RandomSource random = entity.getRandom();
        int randX = -10 + random.nextInt(21);
        int randY = -6 + random.nextInt(13);
        int randZ = -10 + random.nextInt(21);
        double x = entity.getX() + randX;
        double y = entity.getY() + randY;
        double z = entity.getZ() + randZ;
        while ((randX <= 9 && randX >= -9 && randZ <= 9 && randZ >= -9)) {
            randX = -10 + random.nextInt(21);
            randZ = -10 + random.nextInt(21);
            x = entity.getX() + randX;
            z = entity.getZ() + randZ;
        }

        return entity.randomTeleport(x, y, z, true);
    }
}