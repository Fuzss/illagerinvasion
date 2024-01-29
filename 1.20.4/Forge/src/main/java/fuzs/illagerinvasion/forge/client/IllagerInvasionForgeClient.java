package fuzs.illagerinvasion.forge.client;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.IllagerInvasionClient;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = IllagerInvasion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class IllagerInvasionForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientModConstructor.construct(IllagerInvasion.MOD_ID, IllagerInvasionClient::new);
    }
}
