package fuzs.illagerinvasion.data.client;

import fuzs.illagerinvasion.init.ModItems;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.client.data.v2.AbstractModelProvider;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class ModModelProvider extends AbstractModelProvider {

    public ModModelProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addBlockModels(BlockModelGenerators blockModelGenerators) {
        Block imbuingTableBlock = ModRegistry.IMBUING_TABLE_BLOCK.value();
        TextureMapping textureMapping = TextureMapping.craftingTable(imbuingTableBlock, imbuingTableBlock)
                .put(TextureSlot.DOWN, TextureMapping.getBlockTexture(imbuingTableBlock, "_bottom"));
        blockModelGenerators.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(imbuingTableBlock,
                ModelTemplates.CUBE.create(imbuingTableBlock, textureMapping, blockModelGenerators.modelOutput)));
        this.createSimpleFire(ModRegistry.MAGIC_FIRE_BLOCK.value(), blockModelGenerators);
    }

    public final void createSimpleFire(Block block, BlockModelGenerators blockModelGenerators) {
        List<ResourceLocation> list = blockModelGenerators.createFloorFireModels(block);
        List<ResourceLocation> list2 = blockModelGenerators.createSideFireModels(block);
        blockModelGenerators.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
                .with(BlockModelGenerators.wrapModels(list, variant -> variant))
                .with(BlockModelGenerators.wrapModels(list2, variant -> variant))
                .with(BlockModelGenerators.wrapModels(list2,
                        variant -> variant.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)))
                .with(BlockModelGenerators.wrapModels(list2,
                        variant -> variant.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)))
                .with(BlockModelGenerators.wrapModels(list2,
                        variant -> variant.with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))));
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
                ResourceLocationHelper.withDefaultNamespace("entity/enderdragon/dragon_fireball"),
                ModelTemplates.FLAT_ITEM,
                itemModelGenerators);
        ItemModelGenerationHelper.generateSpawnEgg(ModItems.ALCHEMIST_SPAWN_EGG_ITEM.value(), itemModelGenerators);
        ItemModelGenerationHelper.generateSpawnEgg(ModItems.ARCHIVIST_SPAWN_EGG_ITEM.value(), itemModelGenerators);
        ItemModelGenerationHelper.generateSpawnEgg(ModItems.BASHER_SPAWN_EGG_ITEM.value(), itemModelGenerators);
        ItemModelGenerationHelper.generateSpawnEgg(ModItems.FIRECALLER_SPAWN_EGG_ITEM.value(), itemModelGenerators);
        ItemModelGenerationHelper.generateSpawnEgg(ModItems.INQUISITOR_SPAWN_EGG_ITEM.value(), itemModelGenerators);
        ItemModelGenerationHelper.generateSpawnEgg(ModItems.MARAUDER_SPAWN_EGG_ITEM.value(), itemModelGenerators);
        ItemModelGenerationHelper.generateSpawnEgg(ModItems.INVOKER_SPAWN_EGG_ITEM.value(), itemModelGenerators);
        ItemModelGenerationHelper.generateSpawnEgg(ModItems.NECROMANCER_SPAWN_EGG_ITEM.value(), itemModelGenerators);
        ItemModelGenerationHelper.generateSpawnEgg(ModItems.PROVOKER_SPAWN_EGG_ITEM.value(), itemModelGenerators);
        ItemModelGenerationHelper.generateSpawnEgg(ModItems.SORCERER_SPAWN_EGG_ITEM.value(), itemModelGenerators);
        ItemModelGenerationHelper.generateSpawnEgg(ModItems.SURRENDERED_SPAWN_EGG_ITEM.value(), itemModelGenerators);
        ItemModelGenerationHelper.generateSpawnEgg(ModItems.ILLUSIONER_SPAWN_EGG_ITEM.value(), itemModelGenerators);
        ItemModelGenerationHelper.generateHorn(ModItems.HORN_OF_SIGHT_ITEM.value(), itemModelGenerators);
    }
}
