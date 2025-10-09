package fuzs.illagerinvasion.mixin;

import fuzs.illagerinvasion.util.FireworksShootingHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Illusioner.class)
abstract class IllusionerMixin extends SpellcasterIllager {
    @Shadow
    private int clientSideIllusionTicks;
    @Shadow
    @Final
    private Vec3[][] clientSideIllusionOffsets;

    protected IllusionerMixin(EntityType<? extends SpellcasterIllager> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "aiStep", at = @At("HEAD"), cancellable = true)
    public void aiStep(CallbackInfo callback) {
        super.aiStep();
        if (this.level().isClientSide() && this.isInvisible()) {
            if (this.clientSideIllusionTicks > 0) {
                --this.clientSideIllusionTicks;
            }
        }

        callback.cancel();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (this.level().isClientSide() && DATA_SHARED_FLAGS_ID.equals(key) && this.isInvisible()) {
            this.clientSideIllusionTicks = 3;
            for (int i = 0; i < 4; ++i) {
                this.clientSideIllusionOffsets[0][i] = Vec3.ZERO;
                this.clientSideIllusionOffsets[1][i] = new Vec3(
                        (double) (-6.0F + (float) this.random.nextInt(13)) * 0.5,
                        Math.max(0, this.random.nextInt(6) - 4),
                        (double) (-6.0F + (float) this.random.nextInt(13)) * 0.5);
            }

            for (int i = 0; i < 16; ++i) {
                this.level()
                        .addParticle(ParticleTypes.CLOUD,
                                this.getRandomX(0.5),
                                this.getRandomY(),
                                this.getZ(0.5),
                                0.0,
                                0.0,
                                0.0);
            }

            this.level()
                    .playLocalSound(this.getX(),
                            this.getY(),
                            this.getZ(),
                            SoundEvents.ILLUSIONER_MIRROR_MOVE,
                            this.getSoundSource(),
                            1.0F,
                            1.0F,
                            false);
        }
    }

    @Override
    protected void actuallyHurt(ServerLevel serverLevel, DamageSource damageSource, float damageAmount) {
        super.actuallyHurt(serverLevel, damageSource, damageAmount);
        if (damageSource.getDirectEntity() != null) {
            this.removeEffect(MobEffects.INVISIBILITY);
        }
    }

    @Override
    public boolean isInvulnerableTo(ServerLevel serverLevel, DamageSource damageSource) {
        return damageSource.is(DamageTypes.FIREWORKS) || super.isInvulnerableTo(serverLevel, damageSource);
    }

    @Inject(method = "performRangedAttack", at = @At("HEAD"), cancellable = true)
    public void performRangedAttack(LivingEntity target, float velocity, CallbackInfo callback) {
        if (this.getRandom().nextInt(3) == 0
                && this.level().getNearestPlayer(this.getX(), this.getY(), this.getZ(), 4.0, true) == null) {
            if (FireworksShootingHelper.performShooting(this, target)) callback.cancel();
        }
    }
}
