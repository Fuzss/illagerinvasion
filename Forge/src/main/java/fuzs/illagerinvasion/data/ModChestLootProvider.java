package fuzs.illagerinvasion.data;

import fuzs.puzzleslib.api.data.v1.AbstractLootProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class ModChestLootProvider extends AbstractLootProvider.Simple {

    public ModChestLootProvider(PackOutput packOutput) {
        super(packOutput, LootContextParamSets.CHEST);
    }

    @Override
    public void generate() {

    }
}
