package fuzs.illagerinvasion.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerboundSetStructureBlockPacket.class)
public abstract class ServerboundSetStructureBlockPacketMixin {
    @Shadow
    @Final
    @Mutable
    private BlockPos offset;

    @Shadow
    @Final
    @Mutable
    private Vec3i size;

    /**
     * @author LatvianModder
     * @reason to fix 48 block limit
     */
    @Inject(method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", at = @At("RETURN"))
    public void readSE(FriendlyByteBuf buf, CallbackInfo ci) {
        offset = new BlockPos(
                Mth.clamp(buf.readVarInt(), -256, 255),
                Mth.clamp(buf.readVarInt(), -256, 255),
                Mth.clamp(buf.readVarInt(), -256, 255)
        );

        size = new BlockPos(
                Mth.clamp(buf.readVarInt(), 0, 255),
                Mth.clamp(buf.readVarInt(), 0, 255),
                Mth.clamp(buf.readVarInt(), 0, 255)
        );
    }

    /**
     * @author LatvianModder
     * @reason to fix 48 block limit
     */
    @Inject(method = "write", at = @At("RETURN"))
    public void writeSE(FriendlyByteBuf buf, CallbackInfo ci) {
        buf.writeVarInt(offset.getX());
        buf.writeVarInt(offset.getY());
        buf.writeVarInt(offset.getZ());
        buf.writeVarInt(size.getX());
        buf.writeVarInt(size.getY());
        buf.writeVarInt(size.getZ());
    }
}
