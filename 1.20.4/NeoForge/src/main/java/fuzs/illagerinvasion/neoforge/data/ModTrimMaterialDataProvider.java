package fuzs.illagerinvasion.neoforge.data;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.neoforge.api.data.v2.AbstractBuiltInDataProvider;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.item.armortrim.TrimMaterial;

public class ModTrimMaterialDataProvider extends AbstractBuiltInDataProvider.TrimMaterials {

    public ModTrimMaterialDataProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    protected void addBootstrap(BootstapContext<TrimMaterial> bootstapContext) {
        this.add(ModRegistry.PLATINUM_TRIM_MATERIAL, ModRegistry.PLATINUM_SHEET_ITEM.value(), 0x527D7C, 0.2F);
    }
}
