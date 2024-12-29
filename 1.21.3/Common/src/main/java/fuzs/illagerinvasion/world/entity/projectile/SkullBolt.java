package fuzs.illagerinvasion.world.entity.projectile;

import fuzs.illagerinvasion.init.ModEntityTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SkullBolt extends AbstractHurtingProjectile {

    public SkullBolt(EntityType<? extends SkullBolt> entityType, Level level) {
        super(entityType, level);
    }

    public SkullBolt(Level level, LivingEntity owner, double directionX, double directionY, double directionZ) {
        super(ModEntityTypes.SKULL_BOLT_ENTITY_TYPE.value(), owner, new Vec3(directionX, directionY, directionZ), level);
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level().isClientSide) {
            return;
        }
        if (entityHitResult.getEntity() instanceof LivingEntity livingEntity) {
            if (livingEntity.getType().is(EntityTypeTags.UNDEAD)) {
                livingEntity.heal(5.0f);
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 2));
            } else {
                livingEntity.hurt(this.damageSources().magic(), 7.0f);
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
            }
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (this.level() instanceof ServerLevel) {
            double x = this.getX();
            double y = this.getY() + 0.2;
            double z = this.getZ();
            ((ServerLevel) this.level()).sendParticles(ParticleTypes.SMOKE, x, y, z, 25, 0.25D, 0.25D, 0.25D, 0.05D);
        }
        this.discard();

    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }
}
