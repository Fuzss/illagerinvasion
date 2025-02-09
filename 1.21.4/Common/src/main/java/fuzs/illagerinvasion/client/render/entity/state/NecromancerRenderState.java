package fuzs.illagerinvasion.client.render.entity.state;

public class NecromancerRenderState extends SpellcasterIllagerRenderState implements PowerableRenderState {
    public boolean isPowered;

    @Override
    public boolean isPowered() {
        return this.isPowered;
    }
}
