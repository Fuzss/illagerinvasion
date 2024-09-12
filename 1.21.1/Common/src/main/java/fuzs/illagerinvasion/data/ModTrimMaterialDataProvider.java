package fuzs.illagerinvasion.data;

import fuzs.illagerinvasion.init.ModItems;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.AbstractRegistriesDatapackGenerator;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.armortrim.TrimMaterial;

public class ModTrimMaterialDataProvider extends AbstractRegistriesDatapackGenerator.TrimMaterials {

    public ModTrimMaterialDataProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    protected void addBootstrap(BootstrapContext<TrimMaterial> bootstapContext) {
        this.add(ModRegistry.PLATINUM_TRIM_MATERIAL, ModItems.PLATINUM_SHEET_ITEM.value(), 0x527D7C, 0.2F);
    }
}
