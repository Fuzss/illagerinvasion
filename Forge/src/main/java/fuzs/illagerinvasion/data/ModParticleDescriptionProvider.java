package fuzs.illagerinvasion.data;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractParticleDescriptionProvider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

public class ModParticleDescriptionProvider extends AbstractParticleDescriptionProvider {

    public ModParticleDescriptionProvider(GatherDataEvent evt, String modId) {
        super(evt, modId);
    }

    @Override
    protected void addParticleDescriptions() {
        this.add(ModRegistry.MAGIC_FLAME_PARTICLE_TYPE.get());
        this.add(ModRegistry.NECROMANCER_BUFF_PARTICLE_TYPE.get());
    }
}
