package fuzs.illagerinvasion.fabric;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;

public class IllagerInvasionFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(IllagerInvasion.MOD_ID, IllagerInvasion::new);
    }
}
