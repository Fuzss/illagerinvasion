package fuzs.illagerinvasion.neoforge;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.data.ModRecipeProvider;
import fuzs.illagerinvasion.data.client.ModLanguageProvider;
import fuzs.illagerinvasion.data.client.ModModelProvider;
import fuzs.illagerinvasion.data.loot.ModBlockLootProvider;
import fuzs.illagerinvasion.data.loot.ModChestLootProvider;
import fuzs.illagerinvasion.data.loot.ModEntityInjectLootProvider;
import fuzs.illagerinvasion.data.loot.ModEntityTypeLootProvider;
import fuzs.illagerinvasion.data.tags.*;
import fuzs.illagerinvasion.neoforge.data.ModSoundDefinitionProvider;
import fuzs.illagerinvasion.neoforge.data.ModTrimMaterialDataProvider;
import fuzs.illagerinvasion.neoforge.data.client.ModParticleDescriptionProvider;
import fuzs.illagerinvasion.neoforge.data.client.ModSpriteSourceProvider;
import fuzs.illagerinvasion.neoforge.init.NeoForgeModRegistry;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;

@Mod(IllagerInvasion.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class IllagerInvasionNeoForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        NeoForgeModRegistry.touch();
        ModConstructor.construct(IllagerInvasion.MOD_ID, IllagerInvasion::new);
        DataProviderHelper.registerDataProviders(IllagerInvasion.MOD_ID, ModBiomeTagProvider::new, ModBlockTagProvider::new, ModEnchantmentTagProvider::new, ModEntityTypeTagProvider::new, ModInstrumentTagProvider::new, ModItemTagProvider::new);
        DataProviderHelper.registerDataProviders(IllagerInvasion.MOD_ID, ModBlockLootProvider::new, ModChestLootProvider::new, ModEntityInjectLootProvider::new, ModEntityTypeLootProvider::new);
        DataProviderHelper.registerDataProviders(IllagerInvasion.MOD_ID, ModRecipeProvider::new, ModSoundDefinitionProvider::new, ModTrimMaterialDataProvider::new);
        DataProviderHelper.registerDataProviders(IllagerInvasion.MOD_ID, ModLanguageProvider::new, ModModelProvider::new, ModParticleDescriptionProvider::new, ModSpriteSourceProvider::new);
    }
}
