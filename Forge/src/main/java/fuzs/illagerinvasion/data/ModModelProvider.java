package fuzs.illagerinvasion.data;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractModelProvider;
import net.minecraftforge.data.event.GatherDataEvent;

public class ModModelProvider extends AbstractModelProvider {

    public ModModelProvider(GatherDataEvent evt, String modId) {
        super(evt, modId);
    }

    @Override
    protected void registerStatesAndModels() {
        this.simpleBlock(ModRegistry.IMBUING_TABLE_BLOCK.get(), this.models().cube(this.name(ModRegistry.IMBUING_TABLE_BLOCK.get()), this.modLoc("block/imbuing_table_bottom"), this.modLoc("block/imbuing_table_top"), this.modLoc("block/imbuing_table_side1"), this.modLoc("block/imbuing_table_side1"), this.modLoc("block/imbuing_table_side"), this.modLoc("block/imbuing_table_side1")).texture("particle", this.modLoc("block/imbuing_table_side")));
        this.spawnEgg(ModRegistry.ALCHEMIST_SPAWN_EGG_ITEM.get());
        this.spawnEgg(ModRegistry.ARCHIVIST_SPAWN_EGG_ITEM.get());
        this.spawnEgg(ModRegistry.BASHER_SPAWN_EGG_ITEM.get());
        this.spawnEgg(ModRegistry.FIRECALLER_SPAWN_EGG_ITEM.get());
        this.basicItem(ModRegistry.HALLOWED_GEM_ITEM.get());
        this.handheldItem(ModRegistry.PLATINUM_INFUSED_HATCHET_ITEM.get());
        this.basicItem(ModRegistry.ILLUSIONARY_DUST_ITEM.get());
        this.simpleBlockItem(ModRegistry.IMBUING_TABLE_BLOCK.get(), this.existingBlockModel(ModRegistry.IMBUING_TABLE_BLOCK.get()));
        this.spawnEgg(ModRegistry.INQUISITOR_SPAWN_EGG_ITEM.get());
        this.spawnEgg(ModRegistry.MARAUDER_SPAWN_EGG_ITEM.get());
        this.spawnEgg(ModRegistry.NECROMANCER_SPAWN_EGG_ITEM.get());
        this.basicItem(ModRegistry.PLATINUM_CHUNK_ITEM.get());
        this.basicItem(ModRegistry.PLATINUM_SHEET_ITEM.get());
        this.basicItem(ModRegistry.PRIMAL_ESSENCE_ITEM.get());
        this.spawnEgg(ModRegistry.PROVOKER_SPAWN_EGG_ITEM.get());
        this.spawnEgg(ModRegistry.SORCERER_SPAWN_EGG_ITEM.get());
        this.spawnEgg(ModRegistry.SURRENDERED_SPAWN_EGG_ITEM.get());
        this.basicItem(ModRegistry.UNUSUAL_DUST_ITEM.get());
        this.basicItem(ModRegistry.LOST_CANDLE_ITEM.get());
    }
}
