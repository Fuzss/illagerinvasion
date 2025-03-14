package fuzs.illagerinvasion.fabric.mixin.accessor;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Raid.RaiderType.class)
public interface Raid$RaiderTypeFabricAccessor {

    @Accessor("VALUES")
    @Mutable
    static void illagerinvasion$setValues(Raid.RaiderType[] values) {
        throw new RuntimeException();
    }

    @Accessor("entityType")
    @Mutable
    void illagerinvasion$setEntityType(EntityType<? extends Raider> entityType);

    @Accessor("spawnsPerWaveBeforeBonus")
    @Mutable
    void illagerinvasion$setSpawnsPerWaveBeforeBonus(int[] spawnsPerWaveBeforeBonus);
}
