package fuzs.illagerinvasion.mixin.accessor;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.sensing.VillagerHostilesSensor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(VillagerHostilesSensor.class)
public interface VillagerHostilesSensorAccessor {

    @Accessor("ACCEPTABLE_DISTANCE_FROM_HOSTILES")
    @Mutable
    static void illagerinvasion$setAcceptableDistanceFromHostiles(ImmutableMap<EntityType<?>, Float> acceptableDistanceFromHostiles) {
        throw new RuntimeException();
    }

    @Accessor("ACCEPTABLE_DISTANCE_FROM_HOSTILES")
    static ImmutableMap<EntityType<?>, Float> illagerinvasion$getAcceptableDistanceFromHostiles() {
        throw new RuntimeException();
    }
}
