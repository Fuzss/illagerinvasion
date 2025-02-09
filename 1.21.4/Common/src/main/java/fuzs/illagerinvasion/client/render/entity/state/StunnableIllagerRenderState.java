package fuzs.illagerinvasion.client.render.entity.state;

import net.minecraft.client.renderer.entity.state.IllagerRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;

public class StunnableIllagerRenderState extends IllagerRenderState {
    public boolean isStunned;

    public ItemStack getOffHandItem() {
        return this.mainArm != HumanoidArm.RIGHT ? this.rightHandItem : this.leftHandItem;
    }
}
