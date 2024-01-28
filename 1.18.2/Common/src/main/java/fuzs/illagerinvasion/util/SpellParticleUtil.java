package fuzs.illagerinvasion.util;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class SpellParticleUtil {

    public static void sendSpellParticles(LivingEntity entity, ServerLevel level, ParticleOptions particleTypes, int count, double speed) {
        float g = entity.yBodyRot * ((float) Math.PI / 180) + Mth.cos((float) entity.tickCount * 0.6662f) * 0.25f;
        float h = Mth.cos(g);
        float i = Mth.sin(g);
        level.sendParticles(particleTypes, entity.getX() + (double) h * 0.6, entity.getY() + 1.8, entity.getZ() + (double) i, count, 0.0D, 0.0D, 0.0D, speed);
        level.sendParticles(particleTypes, entity.getX() - (double) h * 0.6, entity.getY() + 1.8, entity.getZ() - (double) i, count, 0.0D, 0.0D, 0.0D, speed);
    }
}
