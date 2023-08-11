package fuzs.illagerinvasion.world.entity.projectile;

import fuzs.illagerinvasion.init.ModRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class SkullBolt extends AbstractHurtingProjectile {

    public SkullBolt(EntityType<? extends SkullBolt> entityType, Level world) {
        super(entityType, world);
    }

    public SkullBolt(Level world, LivingEntity owner, double directionX, double directionY, double directionZ) {
        super(ModRegistry.SKULL_BOLT_ENTITY_TYPE.get(), owner, directionX, directionY, directionZ, world);
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level.isClientSide) {
            return;
        }
        Entity entity = entityHitResult.getEntity();
        Entity entity2 = this.getOwner();
        if (entity2 instanceof LivingEntity) {
            if (((LivingEntity) entity).getMobType() == MobType.UNDEAD) {
                ((LivingEntity) entity).heal(5.0f);
                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 2));
            } else {
                entity.hurt(DamageSource.MAGIC, 7.0f);
                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
            }
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (this.level instanceof ServerLevel) {
            double x = this.getX();
            double y = this.getY() + 0.2;
            double z = this.getZ();
            ((ServerLevel) this.level).sendParticles(ParticleTypes.SMOKE, x, y, z, 25, 0.25D, 0.25D, 0.25D, 0.05D);
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
