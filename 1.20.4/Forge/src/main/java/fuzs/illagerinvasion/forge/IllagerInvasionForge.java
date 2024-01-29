package fuzs.illagerinvasion.forge;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.forge.init.ForgeModRegistry;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod(IllagerInvasion.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class IllagerInvasionForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ForgeModRegistry.touch();
        ModConstructor.construct(IllagerInvasion.MOD_ID, IllagerInvasion::new);
    }
}
