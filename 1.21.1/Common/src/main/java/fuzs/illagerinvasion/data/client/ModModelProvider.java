package fuzs.illagerinvasion.data.client;

import fuzs.illagerinvasion.init.ModItems;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.client.data.v2.AbstractModelProvider;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
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
        this.skipBlock(ModRegistry.MAGIC_FIRE_BLOCK.value());
    }

    @Override
    public void addItemModels(ItemModelGenerators builder) {
        builder.generateFlatItem(ModItems.HALLOWED_GEM_ITEM.value(), ModelTemplates.FLAT_ITEM);
        builder.generateFlatItem(ModItems.ILLUSIONARY_DUST_ITEM.value(), ModelTemplates.FLAT_ITEM);
        builder.generateFlatItem(ModItems.PLATINUM_CHUNK_ITEM.value(), ModelTemplates.FLAT_ITEM);
        builder.generateFlatItem(ModItems.PLATINUM_SHEET_ITEM.value(), ModelTemplates.FLAT_ITEM);
        builder.generateFlatItem(ModItems.PRIMAL_ESSENCE_ITEM.value(), ModelTemplates.FLAT_ITEM);
        builder.generateFlatItem(ModItems.UNUSUAL_DUST_ITEM.value(), ModelTemplates.FLAT_ITEM);
        builder.generateFlatItem(ModItems.LOST_CANDLE_ITEM.value(), ModelTemplates.FLAT_ITEM);
        builder.generateFlatItem(ModItems.PLATINUM_INFUSED_HATCHET_ITEM.value(), ModelTemplates.FLAT_HANDHELD_ITEM);
        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(ModItems.MAGICAL_FIRE_CHARGE_ITEM.value()), TextureMapping.layer0(
                ResourceLocationHelper.withDefaultNamespace("entity/enderdragon/dragon_fireball")), builder.output);
        builder.generateFlatItem(ModItems.ALCHEMIST_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
        builder.generateFlatItem(ModItems.ARCHIVIST_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
        builder.generateFlatItem(ModItems.BASHER_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
        builder.generateFlatItem(ModItems.FIRECALLER_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
        builder.generateFlatItem(ModItems.INQUISITOR_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
        builder.generateFlatItem(ModItems.MARAUDER_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
        builder.generateFlatItem(ModItems.INVOKER_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
        builder.generateFlatItem(ModItems.NECROMANCER_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
        builder.generateFlatItem(ModItems.PROVOKER_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
        builder.generateFlatItem(ModItems.SORCERER_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
        builder.generateFlatItem(ModItems.SURRENDERED_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
        builder.generateFlatItem(ModItems.ILLUSIONER_SPAWN_EGG_ITEM.value(), SPAWN_EGG);
        this.skipItem(ModItems.HORN_OF_SIGHT_ITEM.value());
    }
}
