package fuzs.illagerinvasion.data;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractLootProvider;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class ModBlockLootProvider extends AbstractLootProvider.Blocks {

    public ModBlockLootProvider(GatherDataEvent evt, String modId) {
        super(evt, modId);
    }

    @Override
    public void generate() {
        this.dropSelf(ModRegistry.IMBUING_TABLE_BLOCK.get());
        this.dropNothing(ModRegistry.MAGIC_FIRE_BLOCK.get());
    }
}
