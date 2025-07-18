package fuzs.illagerinvasion.neoforge.data;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.illagerinvasion.world.item.enchantment.ImbuingEnchantmentLevel;
import fuzs.neoforgedatapackextensions.neoforge.api.v1.NeoForgeDataMapToken;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {

    public ModDataMapProvider(DataProviderContext context) {
        this(context.getPackOutput(), context.getRegistries());
    }

    public ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider registries) {
        HolderGetter<Enchantment> enchantments = registries.lookupOrThrow(Registries.ENCHANTMENT);
        Builder<ImbuingEnchantmentLevel, Enchantment> builder = this.builder(NeoForgeDataMapToken.unwrap(ModRegistry.IMBUING_LEVELS_DATA_MAP_TYPE));
        register(enchantments, builder, Enchantments.PROTECTION, 10);
        register(enchantments, builder, Enchantments.BLAST_PROTECTION, 8);
        register(enchantments, builder, Enchantments.PROJECTILE_PROTECTION, 8);
        register(enchantments, builder, Enchantments.FIRE_PROTECTION, 8);
        register(enchantments, builder, Enchantments.FEATHER_FALLING, 8);
        register(enchantments, builder, Enchantments.THORNS, 5);
        register(enchantments, builder, Enchantments.SHARPNESS, 10);
        register(enchantments, builder, Enchantments.SMITE, 10);
        register(enchantments, builder, Enchantments.BANE_OF_ARTHROPODS, 10);
        register(enchantments, builder, Enchantments.KNOCKBACK, 5);
        register(enchantments, builder, Enchantments.FIRE_ASPECT, 5);
        register(enchantments, builder, Enchantments.LOOTING, 10);
        register(enchantments, builder, Enchantments.SWEEPING_EDGE, 5);
        register(enchantments, builder, Enchantments.EFFICIENCY, 10);
        register(enchantments, builder, Enchantments.UNBREAKING, 10);
        register(enchantments, builder, Enchantments.FORTUNE, 10);
        register(enchantments, builder, Enchantments.POWER, 10);
        register(enchantments, builder, Enchantments.PUNCH, 3);
        register(enchantments, builder, Enchantments.LUCK_OF_THE_SEA, 10);
        register(enchantments, builder, Enchantments.LURE, 10);
        register(enchantments, builder, Enchantments.LOYALTY, 5);
        register(enchantments, builder, Enchantments.RIPTIDE, 5);
        register(enchantments, builder, Enchantments.IMPALING, 10);
        register(enchantments, builder, Enchantments.PIERCING, 10);
        register(enchantments, builder, Enchantments.DENSITY, 5);
        register(enchantments, builder, Enchantments.BREACH, 5);
        register(enchantments, builder, Enchantments.WIND_BURST, 5);

    }

    static void register(HolderGetter<Enchantment> enchantments, Builder<ImbuingEnchantmentLevel, Enchantment> builder, ResourceKey<Enchantment> resourceKey, int enchantmentLevel) {
        builder.add(enchantments.getOrThrow(resourceKey), new ImbuingEnchantmentLevel(enchantmentLevel), false);
    }
}
