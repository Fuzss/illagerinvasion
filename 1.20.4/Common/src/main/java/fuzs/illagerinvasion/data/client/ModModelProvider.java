package fuzs.illagerinvasion.data.client;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.client.data.v2.AbstractModelProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class ModModelProvider extends AbstractModelProvider {

    public ModModelProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addBlockModels(BlockModelGenerators builder) {
        Block imbuingTableBlock = ModRegistry.IMBUING_TABLE_BLOCK.value();
        TextureMapping textureMapping = TextureMapping.craftingTable(imbuingTableBlock, imbuingTableBlock).put(TextureSlot.DOWN, TextureMapping.getBlockTexture(imbuingTableBlock, "_bottom"));
        builder.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(imbuingTableBlock, ModelTemplates.CUBE.create(imbuingTableBlock, textureMapping, builder.modelOutput)));
    }

    @Override
    public void addItemModels(ItemModelGenerators builder) {
        builder.generateFlatItem(ModRegistry.HALLOWED_GEM_ITEM.value(), ModelTemplates.FLAT_ITEM);
        builder.generateFlatItem(ModRegistry.ILLUSIONARY_DUST_ITEM.value(), ModelTemplates.FLAT_ITEM);
        builder.generateFlatItem(ModRegistry.PLATINUM_CHUNK_ITEM.value(), ModelTemplates.FLAT_ITEM);
        builder.generateFlatItem(ModRegistry.PLATINUM_SHEET_ITEM.value(), ModelTemplates.FLAT_ITEM);
        builder.generateFlatItem(ModRegistry.PRIMAL_ESSENCE_ITEM.value(), ModelTemplates.FLAT_ITEM);
        builder.generateFlatItem(ModRegistry.UNUSUAL_DUST_ITEM.value(), ModelTemplates.FLAT_ITEM);
        builder.generateFlatItem(ModRegistry.LOST_CANDLE_ITEM.value(), ModelTemplates.FLAT_ITEM);
        builder.generateFlatItem(ModRegistry.PLATINUM_INFUSED_HATCHET_ITEM.value(), ModelTemplates.FLAT_HANDHELD_ITEM);
        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(ModRegistry.MAGICAL_FIRE_CHARGE_ITEM.value()), TextureMapping.layer0(new ResourceLocation("entity/enderdragon/dragon_fireball")), builder.output);
        builder.generateFlatItem(ModRegistry.ALCHEMIST_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
        builder.generateFlatItem(ModRegistry.ARCHIVIST_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
        builder.generateFlatItem(ModRegistry.BASHER_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
        builder.generateFlatItem(ModRegistry.FIRECALLER_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
        builder.generateFlatItem(ModRegistry.INQUISITOR_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
        builder.generateFlatItem(ModRegistry.MARAUDER_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
        builder.generateFlatItem(ModRegistry.INVOKER_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
        builder.generateFlatItem(ModRegistry.NECROMANCER_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
        builder.generateFlatItem(ModRegistry.PROVOKER_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
        builder.generateFlatItem(ModRegistry.SORCERER_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
        builder.generateFlatItem(ModRegistry.SURRENDERED_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
        builder.generateFlatItem(ModRegistry.ILLUSIONER_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
    }

    @Override
    protected boolean throwForMissingBlocks() {
        return false;
    }
}
