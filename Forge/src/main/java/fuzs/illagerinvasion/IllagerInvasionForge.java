package fuzs.illagerinvasion;

import fuzs.illagerinvasion.data.*;
import fuzs.illagerinvasion.init.ForgeModRegistry;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.minecraftforge.data.event.GatherDataEvent;
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

    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent evt) {
        evt.getGenerator().addProvider(true, new ModBiomeTagProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModBlockLootProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModBlockTagProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModChestLootProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModEnchantmentTagProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModEntityInjectLootProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModEntityTypeTagProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModInstrumentTagProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModLanguageProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModModelProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModParticleDescriptionProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModRecipeProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModSoundDefinitionProvider(evt, IllagerInvasion.MOD_ID));
    }
}
