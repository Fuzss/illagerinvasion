package fuzs.illagerinvasion.client.render.entity.state;

public class InvokerRenderState extends SpellcasterIllagerRenderState implements PowerableRenderState {
    public boolean isPowered;
    public float floatAnimationSpeed;

    @Override
    public boolean isPowered() {
        return this.isPowered;
    }
}
