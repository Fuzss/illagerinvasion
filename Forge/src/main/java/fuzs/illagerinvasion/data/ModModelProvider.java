package fuzs.illagerinvasion.data;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractModelProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
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
        this.handheldItem(ModRegistry.HATCHET_ITEM.get());
        ItemModelBuilder hornOfSightUsing = this.basicItem(this.modLoc("horn_of_sight_using"), ModRegistry.HORN_OF_SIGHT_ITEM.get()).transforms().transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(0, -90, 0).translation(-3, 1, 2.5F).scale(0.55F, 0.55F, 0.55F).end().transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(0, -90, 0).translation(-3, 1, 2.5F).scale(0.55F, 0.55F, 0.55F).end().transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(0, -90, 0).translation(2, 1.0F, -4.0F).scale(0.68F, 0.68F, 0.68F).end().transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(0, 90, 0).translation(2, 1.0F, -4).scale(0.68F, 0.68F, 0.68F).end().end();
        this.basicItem(ModRegistry.HORN_OF_SIGHT_ITEM.get()).transforms().transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(0, 0, 0).translation(0, 2, 0).scale(0.55F, 0.55F, 0.55F).end().transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(0, 0, 0).translation(0, 2, 0).scale(0.55F, 0.55F, 0.55F).end().transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(0, -90, 0).translation(1.13F, 0, 1.13F).scale(0.68F, 0.68F, 0.68F).end().transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(0, -90, 0).translation(1.13F, 3.2F, 1.13F).scale(0.68F, 0.68F, 0.68F).end().end().override().model(hornOfSightUsing).predicate(this.modLoc("using"), 1.0F).end();
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
