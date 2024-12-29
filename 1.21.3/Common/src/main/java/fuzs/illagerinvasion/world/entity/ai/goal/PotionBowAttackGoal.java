package fuzs.illagerinvasion.world.entity.ai.goal;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class PotionBowAttackGoal<T extends Monster> extends Goal {
    private final Mob mob;
    private final RangedAttackMob owner;
    private final double mobSpeed;
    private final int minIntervalTicks;
    private final int maxIntervalTicks;
    private final float maxShootRange;
    private final float squaredMaxShootRange;
    private final T actor;
    private final double speed;
    private final float squaredRange;
    @Nullable
    private LivingEntity target;
    private int updateCountdownTicks;
    private int seenTargetTicks;
    private int attackInterval;
    private int cooldown;
    private int targetSeeingTicker;
    private boolean movingToLeft;
    private boolean backward;
    private int combatTicks;

    public PotionBowAttackGoal(final T actor, final double speed, final int attackInterval, final float range) {
        this.updateCountdownTicks = -1;
        this.cooldown = -1;
        this.combatTicks = -1;
        this.actor = actor;
        this.speed = speed;
        this.attackInterval = attackInterval;
        this.squaredRange = range * range;
        this.owner = (RangedAttackMob) actor;
        this.mob = actor;
        this.mobSpeed = speed;
        this.minIntervalTicks = attackInterval;
        this.maxIntervalTicks = attackInterval;
        this.maxShootRange = range;
        this.squaredMaxShootRange = this.maxShootRange * this.maxShootRange;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public void setAttackInterval(final int attackInterval) {
        this.attackInterval = attackInterval;
    }

    @Override
    public boolean canUse() {
        return this.actor.getTarget() != null && this.isHoldingBow();
    }

    protected boolean isHoldingBow() {
        return this.actor.isHolding(Items.BOW) || this.actor.isHolding(Items.LINGERING_POTION);
    }

    @Override
    public boolean canContinueToUse() {
        return (this.canUse() || !this.actor.getNavigation().isDone()) && this.isHoldingBow();
    }

    @Override
    public void start() {
        super.start();
        this.actor.setAggressive(true);
    }

    @Override
    public void stop() {
        super.stop();
        this.actor.setAggressive(false);
        this.targetSeeingTicker = 0;
        this.cooldown = -1;
        this.actor.stopUsingItem();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        final LivingEntity livingEntity = this.actor.getTarget();
        if (livingEntity == null) {
            return;
        }
        if (this.actor.getItemBySlot(EquipmentSlot.MAINHAND).is(Items.LINGERING_POTION)) {
            final double d = this.mob.distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            final boolean bl = this.mob.getSensing().hasLineOfSight(livingEntity);
            this.seenTargetTicks = bl ? ++this.seenTargetTicks : 0;
            if (d > this.squaredMaxShootRange || this.seenTargetTicks < 5) {
                this.mob.getNavigation().moveTo(livingEntity, this.mobSpeed);
            } else {
                this.mob.getNavigation().stop();
            }
            this.mob.getLookControl().setLookAt(livingEntity, 30.0f, 30.0f);
            final int updateCountdownTicks = this.updateCountdownTicks - 1;
            this.updateCountdownTicks = updateCountdownTicks;
            if (updateCountdownTicks == 0) {
                if (!bl) {
                    return;
                }
                final float f = (float) Math.sqrt(d) / this.maxShootRange;
                final float g = Mth.clamp(f, 0.1f, 1.0f);
                this.owner.performRangedAttack(livingEntity, g);
                this.updateCountdownTicks = Mth.floor(f * (this.maxIntervalTicks - this.minIntervalTicks) + this.minIntervalTicks);
            } else if (this.updateCountdownTicks < 0) {
                this.updateCountdownTicks = Mth.floor(Mth.lerp(Math.sqrt(d) / this.maxShootRange, this.minIntervalTicks, this.maxIntervalTicks));
            }
        }
        if (this.actor.getItemBySlot(EquipmentSlot.MAINHAND).is(Items.BOW)) {
            final double d = this.actor.distanceToSqr(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            final boolean bl = this.actor.getSensing().hasLineOfSight(livingEntity);
            final boolean bl2 = this.targetSeeingTicker > 0;
            if (bl != bl2) {
                this.targetSeeingTicker = 0;
            }
            if (bl) {
                ++this.targetSeeingTicker;
            } else {
                --this.targetSeeingTicker;
            }
            if (d > this.squaredRange || this.targetSeeingTicker < 20) {
                this.actor.getNavigation().moveTo(livingEntity, this.speed);
                this.combatTicks = -1;
            } else {
                this.actor.getNavigation().stop();
                ++this.combatTicks;
            }
            if (this.combatTicks >= 20) {
                if (this.actor.getRandom().nextFloat() < 0.3) {
                    this.movingToLeft = false;
                }
                if (this.actor.getRandom().nextFloat() < 0.3) {
                    this.backward = !this.backward;
                }
                this.combatTicks = 0;
            }
            if (this.combatTicks > -1) {
                if (d > this.squaredRange * 0.75f) {
                    this.backward = false;
                } else if (d < this.squaredRange * 0.25f) {
                    this.backward = true;
                }
                this.actor.getMoveControl().strafe(this.backward ? -0.5f : 0.5f, this.movingToLeft ? 0.5f : -0.5f);
                this.actor.lookAt(livingEntity, 30.0f, 30.0f);
            } else {
                this.actor.getLookControl().setLookAt(livingEntity, 30.0f, 30.0f);
            }
            if (this.actor.isUsingItem()) {
                if (!bl && this.targetSeeingTicker < -60) {
                    this.actor.stopUsingItem();
                } else {
                    final int i;
                    if (bl && (i = this.actor.getTicksUsingItem()) >= 20) {
                        this.actor.stopUsingItem();
                        ((RangedAttackMob) this.actor).performRangedAttack(livingEntity, BowItem.getPowerForTime(i));
                        this.cooldown = this.attackInterval;
                    }
                }
            } else {
                final int cooldown = this.cooldown - 1;
                this.cooldown = cooldown;
                if (cooldown <= 0 && this.targetSeeingTicker >= -60 && this.actor.getItemBySlot(EquipmentSlot.MAINHAND).is(Items.BOW)) {
                    this.actor.startUsingItem(ProjectileUtil.getWeaponHoldingHand(this.actor, Items.BOW));
                }
            }
        }
    }
}
