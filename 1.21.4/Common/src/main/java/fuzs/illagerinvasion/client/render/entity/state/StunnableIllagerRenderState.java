package fuzs.illagerinvasion.client.render.entity.state;

import fuzs.illagerinvasion.world.entity.monster.Stunnable;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class StunnableIllagerRenderState extends IllagerRenderState {
    public boolean isStunned;
    public ItemStack mainHandItem = ItemStack.EMPTY;
    public ItemStack offHandItem = ItemStack.EMPTY;

    public <T extends LivingEntity & Stunnable> void extractRenderState(T entity) {
        this.isStunned = entity.isStunned();
        this.mainHandItem = entity.getMainHandItem();
        this.offHandItem = entity.getOffhandItem();
    }
}
