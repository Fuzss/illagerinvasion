package fuzs.illagerinvasion.data;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractBuiltInDataProvider;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraftforge.data.event.GatherDataEvent;

public class ModTrimMaterialDataProvider extends AbstractBuiltInDataProvider.TrimMaterials {

    public ModTrimMaterialDataProvider(GatherDataEvent evt, String modId) {
        super(evt, modId);
    }

    @Override
    protected void addBootstrap(BootstapContext<TrimMaterial> bootstapContext) {
        this.add(ModRegistry.PLATINUM_TRIM_MATERIAL, ModRegistry.PLATINUM_SHEET_ITEM.get(), 0x527D7C, 0.2F);
    }
}
