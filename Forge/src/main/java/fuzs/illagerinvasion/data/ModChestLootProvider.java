package fuzs.illagerinvasion.data;

import fuzs.puzzleslib.api.data.v1.AbstractLootProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.data.event.GatherDataEvent;

public class ModChestLootProvider extends AbstractLootProvider.Simple {

    public ModChestLootProvider(GatherDataEvent evt, String modId) {
        super(LootContextParamSets.CHEST, evt, modId);
    }

    @Override
    public void generate() {

    }
}
