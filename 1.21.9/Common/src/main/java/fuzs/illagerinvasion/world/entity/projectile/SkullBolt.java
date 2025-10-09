package fuzs.illagerinvasion.world.entity.projectile;

import fuzs.illagerinvasion.init.ModEntityTypes;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SkullBolt extends WitherSkull {

    public SkullBolt(EntityType<? extends SkullBolt> entityType, Level level) {
        super(entityType, level);
    }

    public SkullBolt(Level level, LivingEntity owner, Vec3 movement) {
        this(ModEntityTypes.SKULL_BOLT_ENTITY_TYPE.value(), level);
        this.setOwner(owner);
        this.setRot(owner.getYRot(), owner.getXRot());
        this.snapTo(owner.getX(), owner.getY(), owner.getZ(), this.getYRot(), this.getXRot());
        this.reapplyPosition();
        this.assignDirectionalMovement(movement, this.accelerationPower);
    }

    @Override
    public float getBlockExplosionResistance(Explosion explosion, BlockGetter level, BlockPos pos, BlockState blockState, FluidState fluidState, float explosionPower) {
        // this should not cause explosions
        return SharedConstants.MAXIMUM_BLOCK_EXPLOSION_RESISTANCE;
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        if (this.level() instanceof ServerLevel serverLevel) {
            if (this.getOwner() instanceof LivingEntity owner
                    && entityHitResult.getEntity() instanceof LivingEntity target) {
                if (target.getType().is(EntityTypeTags.UNDEAD)) {
                    target.heal(5.0F);
                    target.addEffect(new MobEffectInstance(MobEffects.SPEED, 100, 2));
                } else {
                    DamageSource damageSource = this.damageSources().indirectMagic(this, owner);
                    target.hurtServer(serverLevel, damageSource, 7.0F);
                    EnchantmentHelper.doPostAttackEffects(serverLevel, target, damageSource);
                    target.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 100, 1));
                }
            }
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (this.level() instanceof ServerLevel serverLevel) {
            double x = this.getX();
            double y = this.getY() + 0.2;
            double z = this.getZ();
            serverLevel.sendParticles(ParticleTypes.SMOKE, x, y, z, 25, 0.25D, 0.25D, 0.25D, 0.05D);
            this.discard();
        }
    }

    @Override
    public boolean isDangerous() {
        return false;
    }
}
