package fuzs.illagerinvasion.world.entity.ai.goal;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.illagerinvasion.world.entity.monster.Marauder;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;

public class HatchetAttackGoal extends RangedAttackGoal {
    public static final UniformInt COOLDOWN_RANGE = TimeUtil.rangeOfSeconds(1, 2);
    private final Monster hostile;
    private final float squaredRange;
    private final double speed;
    private int seeingTargetTicker;
    private int cooldown = -1;
    private int chargeTime = 0;

    public HatchetAttackGoal(RangedAttackMob mob, double mobSpeed, int intervalTicks, float maxShootRange) {
        super(mob, mobSpeed, intervalTicks, maxShootRange);
        this.hostile = (Marauder) mob;
        this.speed = mobSpeed;
        this.squaredRange = maxShootRange * maxShootRange;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.hostile.getMainHandItem().is(ModRegistry.PLATINUM_INFUSED_HATCHET_ITEM.get());
    }

    @Override
    public void start() {
        super.start();
        this.hostile.setAggressive(true);
        this.hostile.startUsingItem(InteractionHand.MAIN_HAND);
    }

    @Override
    public void stop() {
        super.stop();
        this.hostile.stopUsingItem();
        this.hostile.setAggressive(false);
        this.seeingTargetTicker = 0;
    }

    @Override
    public void tick() {
        boolean bl3;
        boolean bl2;
        LivingEntity target = this.hostile.getTarget();
        if (target == null) {
            this.chargeTime = 0;
            if (this.hostile instanceof Marauder) {
                ((Marauder) this.hostile).setCharging(false);
            }
            return;
        }
        boolean bl = this.hostile.getSensing().hasLineOfSight(target);
        this.hostile.getLookControl().setLookAt(target, 30.0f, 30.0f);
        boolean bl4 = bl2 = this.seeingTargetTicker > 0;
        if (bl != bl2) {
            this.seeingTargetTicker = 0;
        }
        if (bl) {
            ++this.seeingTargetTicker;
        } else {
            --this.seeingTargetTicker;
        }
        double d = this.hostile.distanceToSqr(target);
        boolean bl5 = bl3 = (d > (double) this.squaredRange || this.seeingTargetTicker < 5);
        if (bl3) {
            --this.cooldown;
            if (this.cooldown <= 0) {
                this.hostile.getNavigation().moveTo(target, this.speed);
                this.cooldown = COOLDOWN_RANGE.sample(this.hostile.getRandom());
            }
        } else {
            this.cooldown = 0;
            this.hostile.getNavigation().stop();
        }
        --this.chargeTime;
        if (this.hostile instanceof Marauder) {
            if (this.chargeTime == -40) {
                ((Marauder) this.hostile).setCharging(true);
            }
            if (this.chargeTime == -80) {
                ((Marauder) this.hostile).performRangedAttack(target, 1.0f);
                ((Marauder) this.hostile).setCharging(false);
                this.chargeTime = 0;
            }
        }
    }
}

