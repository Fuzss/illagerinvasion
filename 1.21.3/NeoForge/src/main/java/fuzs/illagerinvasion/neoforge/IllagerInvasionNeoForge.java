package fuzs.illagerinvasion.neoforge;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.data.ModRecipeProvider;
import fuzs.illagerinvasion.data.loot.ModBlockLootProvider;
import fuzs.illagerinvasion.data.loot.ModChestLootProvider;
import fuzs.illagerinvasion.data.loot.ModEntityInjectLootProvider;
import fuzs.illagerinvasion.data.loot.ModEntityTypeLootProvider;
import fuzs.illagerinvasion.data.registries.ModInstrumentDataProvider;
import fuzs.illagerinvasion.data.registries.ModTrimMaterialDataProvider;
import fuzs.illagerinvasion.data.tags.*;
import fuzs.illagerinvasion.neoforge.data.ModDataMapProvider;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.fml.common.Mod;

@Mod(IllagerInvasion.MOD_ID)
public class IllagerInvasionNeoForge {

    public IllagerInvasionNeoForge() {
        ModConstructor.construct(IllagerInvasion.MOD_ID, IllagerInvasion::new);
        DataProviderHelper.registerDataProviders(IllagerInvasion.MOD_ID,
                ModTrimMaterialDataProvider::new,
                ModInstrumentDataProvider::new,
                ModBiomeTagProvider::new,
                ModBlockTagProvider::new,
                ModEntityTypeTagProvider::new,
                ModInstrumentTagProvider::new,
                ModItemTagProvider::new,
                ModBlockLootProvider::new,
                ModChestLootProvider::new,
                ModEntityInjectLootProvider::new,
                ModEntityTypeLootProvider::new,
                ModRecipeProvider::new,
                ModDataMapProvider::new);
    }
}
