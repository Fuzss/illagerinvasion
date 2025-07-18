package fuzs.illagerinvasion.world.entity.projectile;

import fuzs.illagerinvasion.init.ModEntityTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class FlyingMagma extends AbstractHurtingProjectile {

    public FlyingMagma(EntityType<? extends FlyingMagma> entityType, Level level) {
        super(entityType, level);
    }

    public FlyingMagma(Level level, LivingEntity owner, double directionX, double directionY, double directionZ) {
        super(ModEntityTypes.FLYING_MAGMA_ENTITY_TYPE.value(),
                owner,
                new Vec3(directionX, directionY, directionZ),
                level);
    }

    @Override
    public void tick() {
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    3,
                    0.3,
                    0.3,
                    0.3,
                    0.05);
        }
        super.tick();
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level() instanceof ServerLevel serverLevel) {
            Entity target = entityHitResult.getEntity();
            Entity owner = this.getOwner();
            DamageSource damageSource = this.damageSources().indirectMagic(this, owner);
            if (target.hurtServer(serverLevel, damageSource, 12.0F) && owner instanceof LivingEntity) {
                EnchantmentHelper.doPostAttackEffects(serverLevel, target, damageSource);
            }
            serverLevel.sendParticles(ParticleTypes.LAVA,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    15,
                    0.4,
                    0.4,
                    0.4,
                    0.15D);
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.explode(this.getOwner(),
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    1,
                    true,
                    Level.ExplosionInteraction.MOB);
            serverLevel.sendParticles(ParticleTypes.LAVA,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    15,
                    0.4,
                    0.4,
                    0.4,
                    0.15);
            this.discard();
        }
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }
}

