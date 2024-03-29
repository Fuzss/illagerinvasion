package fuzs.illagerinvasion.fabric;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.fabric.init.FabricModRegistry;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;

public class IllagerInvasionFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        FabricModRegistry.touch();
        ModConstructor.construct(IllagerInvasion.MOD_ID, IllagerInvasion::new);
    }
}
