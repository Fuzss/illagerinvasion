package fuzs.illagerinvasion.data;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.AbstractRecipeProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;

public class ModRecipeProvider extends AbstractRecipeProvider {

    public ModRecipeProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModRegistry.HALLOWED_GEM_ITEM.value())
                .define('#', Items.AMETHYST_SHARD)
                .define('B', ModRegistry.UNUSUAL_DUST_ITEM.value())
                .define('R', ModRegistry.ILLUSIONARY_DUST_ITEM.value())
                .define('D', Items.DIAMOND)
                .pattern("#B#")
                .pattern("RDR")
                .pattern("#B#")
                .unlockedBy(getHasName(ModRegistry.UNUSUAL_DUST_ITEM.value(), ModRegistry.ILLUSIONARY_DUST_ITEM.value()), has(ModRegistry.UNUSUAL_DUST_ITEM.value(), ModRegistry.ILLUSIONARY_DUST_ITEM.value()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModRegistry.IMBUIING_TABLE_ITEM.value())
                .define('#', Items.COPPER_BLOCK)
                .define('P', Items.PAPER)
                .define('O', Items.DARK_OAK_LOG)
                .define('S', ModRegistry.PRIMAL_ESSENCE_ITEM.value())
                .define('E', Items.EXPERIENCE_BOTTLE)
                .pattern("#P#")
                .pattern("OSO")
                .pattern("#E#")
                .unlockedBy(getHasName(ModRegistry.PRIMAL_ESSENCE_ITEM.value()), has(ModRegistry.PRIMAL_ESSENCE_ITEM.value()))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModRegistry.PLATINUM_SHEET_ITEM.value())
                .requires(ModRegistry.PLATINUM_CHUNK_ITEM.value(), 4)
                .unlockedBy(getHasName(ModRegistry.PLATINUM_CHUNK_ITEM.value()), has(ModRegistry.PLATINUM_CHUNK_ITEM.value()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModRegistry.HORN_OF_SIGHT_ITEM.value())
                .define('#', Items.GOLD_INGOT)
                .define('H', Items.GOAT_HORN)
                .define('G', ModRegistry.HALLOWED_GEM_ITEM.value())
                .pattern(" G ")
                .pattern("#H#")
                .pattern(" # ")
                .unlockedBy(getHasName(ModRegistry.HALLOWED_GEM_ITEM.value()), has(ModRegistry.HALLOWED_GEM_ITEM.value()))
                .save(recipeOutput);
    }
}
