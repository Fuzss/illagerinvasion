package me.sandbox.mixin;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.StructureSets;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin (StructureSets.class)
public interface StructureSetsAccessor {
    @Invoker
    static Holder<StructureSet> callRegister(ResourceKey<StructureSet> key, StructureSet structureSet) {
        throw new UnsupportedOperationException();
    }
}
