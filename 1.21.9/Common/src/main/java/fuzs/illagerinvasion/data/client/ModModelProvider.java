package fuzs.illagerinvasion.data.client;

import fuzs.illagerinvasion.init.ModItems;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.client.data.v2.AbstractModelProvider;
import fuzs.puzzleslib.api.client.data.v2.models.ItemModelGenerationHelper;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class ModModelProvider extends AbstractModelProvider {

    public ModModelProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addBlockModels(BlockModelGenerators blockModelGenerators) {
        this.createImbuingTable(ModRegistry.IMBUING_TABLE_BLOCK.value(), blockModelGenerators);
        this.createSimpleFire(ModRegistry.MAGIC_FIRE_BLOCK.value(), blockModelGenerators);
    }

    public final void createImbuingTable(Block block, BlockModelGenerators blockModelGenerators) {
        TextureMapping textureMapping = TextureMapping.craftingTable(block, block)
                .put(TextureSlot.DOWN, TextureMapping.getBlockTexture(block, "_bottom"));
        ResourceLocation resourceLocation = ModelTemplates.CUBE.create(block,
                textureMapping,
                blockModelGenerators.modelOutput);
        blockModelGenerators.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block,
                BlockModelGenerators.plainVariant(resourceLocation)));
    }

    public final void createSimpleFire(Block block, BlockModelGenerators blockModelGenerators) {
        MultiVariant multiVariant = blockModelGenerators.createFloorFireModels(block);
        MultiVariant multiVariant2 = blockModelGenerators.createSideFireModels(block);
        blockModelGenerators.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
                .with(multiVariant)
                .with(multiVariant2)
                .with(multiVariant2.with(BlockModelGenerators.Y_ROT_90))
                .with(multiVariant2.with(BlockModelGenerators.Y_ROT_180))
                .with(multiVariant2.with(BlockModelGenerators.Y_ROT_270)));
    }

    @Override
    public void addItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(ModItems.HALLOWED_GEM_ITEM.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.ILLUSIONARY_DUST_ITEM.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.PLATINUM_CHUNK_ITEM.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.PLATINUM_SHEET_ITEM.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.PRIMAL_ESSENCE_ITEM.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.UNUSUAL_DUST_ITEM.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.LOST_CANDLE_ITEM.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.PLATINUM_INFUSED_HATCHET_ITEM.value(),
                ModelTemplates.FLAT_HANDHELD_ITEM);
        ItemModelGenerationHelper.generateFlatItem(ModItems.MAGICAL_FIRE_CHARGE_ITEM.value(),
                ModAtlasProvider.DRAGON_FIREBALL_LOCATION.texture(),
                ModelTemplates.FLAT_ITEM,
                itemModelGenerators);
        itemModelGenerators.generateFlatItem(ModItems.ALCHEMIST_SPAWN_EGG_ITEM.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.ARCHIVIST_SPAWN_EGG_ITEM.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.BASHER_SPAWN_EGG_ITEM.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.FIRECALLER_SPAWN_EGG_ITEM.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.INQUISITOR_SPAWN_EGG_ITEM.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.MARAUDER_SPAWN_EGG_ITEM.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.INVOKER_SPAWN_EGG_ITEM.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.NECROMANCER_SPAWN_EGG_ITEM.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.PROVOKER_SPAWN_EGG_ITEM.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.SORCERER_SPAWN_EGG_ITEM.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.SURRENDERED_SPAWN_EGG_ITEM.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.ILLUSIONER_SPAWN_EGG_ITEM.value(), ModelTemplates.FLAT_ITEM);
        ItemModelGenerationHelper.generateHorn(ModItems.HORN_OF_SIGHT_ITEM.value(), itemModelGenerators);
    }
}
