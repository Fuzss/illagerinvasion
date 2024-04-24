package fuzs.illagerinvasion.neoforge.data.client;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.neoforge.api.data.v2.client.AbstractParticleDescriptionProvider;
import fuzs.puzzleslib.neoforge.api.data.v2.core.ForgeDataProviderContext;

public class ModParticleDescriptionProvider extends AbstractParticleDescriptionProvider {

    public ModParticleDescriptionProvider(ForgeDataProviderContext context) {
        super(context);
    }

    @Override
    public void addParticleDescriptions() {
        this.add(ModRegistry.MAGIC_FLAME_PARTICLE_TYPE.value());
        this.add(ModRegistry.NECROMANCER_BUFF_PARTICLE_TYPE.value());
    }
}
