package fuzs.illagerinvasion.data;

import fuzs.illagerinvasion.init.ModRegistry;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModParticleDescriptionProvider extends AbstractParticleDescriptionProvider {

    public ModParticleDescriptionProvider(PackOutput packOutput, ExistingFileHelper fileHelper) {
        super(packOutput, fileHelper);
    }

    @Override
    protected void addParticleDescriptions() {
        this.add(ModRegistry.MAGIC_FLAME_PARTICLE_TYPE.get());
        this.add(ModRegistry.NECROMANCER_BUFF_PARTICLE_TYPE.get());
    }
}
