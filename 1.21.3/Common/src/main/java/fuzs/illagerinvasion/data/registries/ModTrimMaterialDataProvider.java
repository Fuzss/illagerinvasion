package fuzs.illagerinvasion.data.registries;

import fuzs.illagerinvasion.init.ModItems;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.AbstractRegistriesDatapackGenerator;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.equipment.trim.TrimMaterial;

public class ModTrimMaterialDataProvider extends AbstractRegistriesDatapackGenerator<TrimMaterial> {

    public ModTrimMaterialDataProvider(DataProviderContext context) {
        super(Registries.TRIM_MATERIAL, context);
    }

    @Override
    public void addBootstrap(BootstrapContext<TrimMaterial> context) {
        registerTrimMaterial(context,
                ModRegistry.PLATINUM_TRIM_MATERIAL,
                ModItems.PLATINUM_SHEET_ITEM.value(),
                0x527D7C,
                0.2F
        );
    }
}
