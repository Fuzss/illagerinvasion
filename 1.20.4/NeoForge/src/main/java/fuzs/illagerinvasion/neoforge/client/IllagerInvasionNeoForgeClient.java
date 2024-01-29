package fuzs.illagerinvasion.neoforge.client;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.IllagerInvasionClient;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = IllagerInvasion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class IllagerInvasionNeoForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientModConstructor.construct(IllagerInvasion.MOD_ID, IllagerInvasionClient::new);
    }
}
