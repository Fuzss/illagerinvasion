package fuzs.illagerinvasion.data;

import fuzs.illagerinvasion.init.ModItems;
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
        ShapedRecipeBuilder.shaped(this.items(), RecipeCategory.MISC, ModItems.HALLOWED_GEM_ITEM.value())
                .define('#', Items.AMETHYST_SHARD)
                .define('B', ModItems.UNUSUAL_DUST_ITEM.value())
                .define('R', ModItems.ILLUSIONARY_DUST_ITEM.value())
                .define('D', Items.DIAMOND)
                .pattern("#B#")
                .pattern("RDR")
                .pattern("#B#")
                .unlockedBy(getHasName(ModItems.UNUSUAL_DUST_ITEM.value()),
                        this.has(ModItems.UNUSUAL_DUST_ITEM.value()))
                .unlockedBy(getHasName(ModItems.ILLUSIONARY_DUST_ITEM.value()),
                        this.has(ModItems.ILLUSIONARY_DUST_ITEM.value()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(this.items(), RecipeCategory.DECORATIONS, ModItems.IMBUING_TABLE_ITEM.value())
                .define('#', Items.COPPER_BLOCK)
                .define('P', Items.PAPER)
                .define('O', Items.DARK_OAK_LOG)
                .define('S', ModItems.PRIMAL_ESSENCE_ITEM.value())
                .define('E', Items.EXPERIENCE_BOTTLE)
                .pattern("#P#")
                .pattern("OSO")
                .pattern("#E#")
                .unlockedBy(getHasName(ModItems.PRIMAL_ESSENCE_ITEM.value()),
                        this.has(ModItems.PRIMAL_ESSENCE_ITEM.value()))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(this.items(), RecipeCategory.MISC, ModItems.PLATINUM_SHEET_ITEM.value())
                .requires(ModItems.PLATINUM_CHUNK_ITEM.value(), 4)
                .unlockedBy(getHasName(ModItems.PLATINUM_CHUNK_ITEM.value()),
                        this.has(ModItems.PLATINUM_CHUNK_ITEM.value()))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(this.items(), RecipeCategory.TOOLS, ModItems.HORN_OF_SIGHT_ITEM.value())
                .define('#', Items.GOLD_INGOT)
                .define('H', Items.GOAT_HORN)
                .define('G', ModItems.HALLOWED_GEM_ITEM.value())
                .pattern(" G ")
                .pattern("#H#")
                .pattern(" # ")
                .unlockedBy(getHasName(ModItems.HALLOWED_GEM_ITEM.value()),
                        this.has(ModItems.HALLOWED_GEM_ITEM.value()))
                .save(recipeOutput);
    }
}
