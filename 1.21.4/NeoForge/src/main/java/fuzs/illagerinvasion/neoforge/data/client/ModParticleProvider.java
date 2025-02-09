package fuzs.illagerinvasion.neoforge.data.client;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.neoforge.api.client.data.v2.AbstractParticleProvider;
import net.minecraft.client.particle.ParticleDescription;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.WithConditions;

import java.util.List;

public class ModParticleProvider extends AbstractParticleProvider {

    public ModParticleProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addParticles() {
        this.add(ModRegistry.MAGIC_FLAME_PARTICLE_TYPE.value());
        this.add(ModRegistry.NECROMANCER_BUFF_PARTICLE_TYPE.value());
    }

    @Deprecated(forRemoval = true)
    @Override
    public void unconditional(ResourceLocation id, ParticleDescription value) {
        // TODO remove this, fixed in Puzzles Lib
        this.conditions.put(id, new WithConditions<>(List.of(), value));
    }
}
