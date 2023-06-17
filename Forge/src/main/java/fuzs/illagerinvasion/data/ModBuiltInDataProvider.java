package fuzs.illagerinvasion.data;

import fuzs.illagerinvasion.init.ModRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.data.event.GatherDataEvent;

public class ModBuiltInDataProvider extends AbstractBuiltInDataProvider {

    public ModBuiltInDataProvider(GatherDataEvent evt, String modId) {
        super(evt, modId);
    }

    @Override
    protected void addRegistrySets() {
        this.add(Registries.TRIM_MATERIAL, ModRegistry::bootstrapTrimMaterials);
    }
}
