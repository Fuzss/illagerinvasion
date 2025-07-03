package fuzs.illagerinvasion.neoforge.data.client;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.neoforge.api.client.data.v2.AbstractParticleProvider;
import fuzs.puzzleslib.neoforge.api.data.v2.core.NeoForgeDataProviderContext;

public class ModParticleProvider extends AbstractParticleProvider {

    public ModParticleProvider(NeoForgeDataProviderContext context) {
        super(context);
    }

    @Override
    public void addParticles() {
        this.add(ModRegistry.MAGIC_FLAME_PARTICLE_TYPE.value());
        this.add(ModRegistry.NECROMANCER_BUFF_PARTICLE_TYPE.value());
    }
}
