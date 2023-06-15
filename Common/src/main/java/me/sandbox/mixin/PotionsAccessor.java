package me.sandbox.mixin;


import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Potions.class)
public interface PotionsAccessor {
    @Invoker
    static Potion callRegister(String name, Potion potion) {
        throw new UnsupportedOperationException();
    }
}
