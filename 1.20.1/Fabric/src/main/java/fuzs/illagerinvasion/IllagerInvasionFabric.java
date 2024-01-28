package fuzs.illagerinvasion;

import fuzs.illagerinvasion.init.FabricModRegistry;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;

public class IllagerInvasionFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        FabricModRegistry.touch();
        ModConstructor.construct(IllagerInvasion.MOD_ID, IllagerInvasion::new);
    }
}
