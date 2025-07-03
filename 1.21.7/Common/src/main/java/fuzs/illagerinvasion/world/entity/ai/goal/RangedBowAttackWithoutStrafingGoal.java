package fuzs.illagerinvasion.world.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;

public class RangedBowAttackWithoutStrafingGoal<T extends Monster & RangedAttackMob> extends RangedBowAttackGoal<T> {
    /**
     * Do not use access widener for this, NeoForge changes the generic type on the class.
     */
    private final T mob;
    private int attackIntervalBase;

    public RangedBowAttackWithoutStrafingGoal(T mob, double speedModifier, int attackIntervalMin, float attackRadius) {
        super(mob, speedModifier, attackIntervalMin, attackRadius);
        this.mob = mob;
        this.attackIntervalBase = attackIntervalMin;
    }

    @Override
    public void setMinAttackInterval(int attackCooldown) {
        super.setMinAttackInterval(attackCooldown);
        this.attackIntervalBase = attackCooldown;
    }

    @Override
    public void tick() {
        // disable strafing behavior
        this.strafingTime = Integer.MIN_VALUE;
        // make mob shoot faster the closer the target is, this was removed when strafing was introduced
        LivingEntity livingEntity = this.mob.getTarget();
        if (livingEntity != null) {
            double distanceToTargetSqr = this.mob.distanceToSqr(livingEntity);
            this.attackIntervalMin = this.attackIntervalBase -
                    (int) ((1.0 - Math.min(distanceToTargetSqr / this.attackRadiusSqr, 1.0)) * 20.0);
        }
        super.tick();
    }
}
