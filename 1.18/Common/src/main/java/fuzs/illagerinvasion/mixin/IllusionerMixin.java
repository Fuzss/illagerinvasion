package fuzs.illagerinvasion.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Illusioner.class)
abstract class IllusionerMixin extends SpellcasterIllager implements CrossbowAttackMob {
    @Unique
    private static final String ILLAGER_INVASION$TAG_FIREWORKS = "{Flight:3,Explosions:[{Type:1,Flicker:0,Trail:0,Colors:[I;2437522],FadeColors:[I;2437522]},{Type:1,Flicker:0,Trail:0,Colors:[I;8073150],FadeColors:[I;8073150]},{Type:1,Flicker:0,Trail:0,Colors:[I;3887386],FadeColors:[I;3887386]}]}";

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
        if (this.level.isClientSide && this.isInvisible()) {
            if (this.clientSideIllusionTicks > 0) {
                --this.clientSideIllusionTicks;
            }
        }
        callback.cancel();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (this.level.isClientSide && DATA_SHARED_FLAGS_ID.equals(key) && this.isInvisible()) {
            this.clientSideIllusionTicks = 3;

            int j;
            for (j = 0; j < 4; ++j) {
                this.clientSideIllusionOffsets[0][j] = Vec3.ZERO;
                this.clientSideIllusionOffsets[1][j] = new Vec3((double) (-6.0F + (float) this.random.nextInt(13)) * 0.5, Math.max(0, this.random.nextInt(6) - 4), (double) (-6.0F + (float) this.random.nextInt(13)) * 0.5);
            }

            for (j = 0; j < 16; ++j) {
                this.level.addParticle(ParticleTypes.CLOUD, this.getRandomX(0.5), this.getRandomY(), this.getZ(0.5), 0.0, 0.0, 0.0);
            }

            this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ILLUSIONER_MIRROR_MOVE, this.getSoundSource(), 1.0F, 1.0F, false);
        }
    }

    @Override
    protected void actuallyHurt(DamageSource damageSource, float damageAmount) {
        super.actuallyHurt(damageSource, damageAmount);
        if (damageSource.getDirectEntity() != null) {
            this.removeEffect(MobEffects.INVISIBILITY);
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return super.isInvulnerableTo(source) || "fireworks".equals(source.getMsgId());
    }

    @Override
    public void setChargingCrossbow(boolean chargingCrossbow) {

    }

    @Override
    public void shootCrossbowProjectile(LivingEntity target, ItemStack crossbowStack, Projectile projectile, float projectileAngle) {
        this.shootCrossbowProjectile(this, target, projectile, projectileAngle, 1.6F);
    }

    @Override
    public void onCrossbowAttackPerformed() {

    }

    @Inject(method = "performRangedAttack", at = @At("HEAD"), cancellable = true)
    public void performRangedAttack(LivingEntity target, float velocity, CallbackInfo callback) {
        if (this.getRandom().nextInt(3) == 0 && this.level.getNearestPlayer(this.getX(), this.getY(), this.getZ(), 4.0, true) == null) {
            ItemStack ammoStack = new ItemStack(Items.FIREWORK_ROCKET);
            try {
                ammoStack.addTagElement("Fireworks", TagParser.parseTag(ILLAGER_INVASION$TAG_FIREWORKS));
            } catch (CommandSyntaxException ignored) {
                return;
            }
            Projectile projectile = new FireworkRocketEntity(this.level, ammoStack, this, this.getX(), this.getEyeY() - 0.15F, this.getZ(), true);
            this.shootCrossbowProjectile(this.getTarget(), ItemStack.EMPTY, projectile, 0.0F);
            this.playSound(SoundEvents.CROSSBOW_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            this.level.addFreshEntity(projectile);
            callback.cancel();
        }
    }
}
