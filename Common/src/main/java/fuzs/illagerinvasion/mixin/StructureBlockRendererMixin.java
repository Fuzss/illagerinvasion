package fuzs.illagerinvasion.mixin;

import net.minecraft.client.renderer.blockentity.StructureBlockRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(StructureBlockRenderer.class)
public abstract class StructureBlockRendererMixin {
    @Overwrite
    public int getViewDistance() {
        return 256;
    }
}
