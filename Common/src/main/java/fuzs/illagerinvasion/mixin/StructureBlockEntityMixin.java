package fuzs.illagerinvasion.mixin;

import net.minecraft.world.level.block.entity.StructureBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(StructureBlockEntity.class)
public abstract class StructureBlockEntityMixin {
    @ModifyConstant(method = "load", constant = {@Constant(intValue = 48), @Constant(intValue = -48)})
    private int getMaxSizePos(int value) {
        return value > 0 ? 255 : -256;
    }
}
