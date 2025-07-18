package fuzs.illagerinvasion.fabric.client;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.IllagerInvasionClient;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class IllagerInvasionFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(IllagerInvasion.MOD_ID, IllagerInvasionClient::new);
    }
}
