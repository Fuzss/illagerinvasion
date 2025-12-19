package fuzs.illagerinvasion.neoforge.data;

import fuzs.illagerinvasion.handler.VillagerGoalHandler;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.illagerinvasion.world.item.enchantment.ImbuingEnchantmentLevel;
import fuzs.neoforgedatapackextensions.neoforge.api.v1.NeoForgeDataMapToken;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.AcceptableVillagerDistance;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

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
        Builder<AcceptableVillagerDistance, EntityType<?>> acceptableVillagerDistanceBuilder = this.builder(
                NeoForgeDataMaps.ACCEPTABLE_VILLAGER_DISTANCES);
        VillagerGoalHandler.forEach((Holder<? extends EntityType<?>> holder, Float acceptableVillagerDistance) -> {
            acceptableVillagerDistanceBuilder.add((Holder<EntityType<?>>) holder,
                    new AcceptableVillagerDistance(acceptableVillagerDistance),
                    false);
        });
        HolderGetter<Enchantment> enchantmentLookup = registries.lookupOrThrow(Registries.ENCHANTMENT);
        Builder<ImbuingEnchantmentLevel, Enchantment> imbuingLevelsBuilder = this.builder(NeoForgeDataMapToken.unwrap(
                ModRegistry.IMBUING_LEVELS_DATA_MAP_TYPE));
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.PROTECTION, 10);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.BLAST_PROTECTION, 8);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.PROJECTILE_PROTECTION, 8);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.FIRE_PROTECTION, 8);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.FEATHER_FALLING, 8);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.THORNS, 5);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.SHARPNESS, 10);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.SMITE, 10);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.BANE_OF_ARTHROPODS, 10);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.KNOCKBACK, 5);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.FIRE_ASPECT, 5);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.LOOTING, 10);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.SWEEPING_EDGE, 5);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.EFFICIENCY, 10);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.UNBREAKING, 10);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.FORTUNE, 10);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.POWER, 10);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.PUNCH, 3);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.LUCK_OF_THE_SEA, 10);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.LURE, 10);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.LOYALTY, 5);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.RIPTIDE, 5);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.IMPALING, 10);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.PIERCING, 10);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.DENSITY, 5);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.BREACH, 5);
        register(enchantmentLookup, imbuingLevelsBuilder, Enchantments.WIND_BURST, 5);

    }

    static void register(HolderGetter<Enchantment> enchantments, Builder<ImbuingEnchantmentLevel, Enchantment> builder, ResourceKey<Enchantment> resourceKey, int enchantmentLevel) {
        builder.add(enchantments.getOrThrow(resourceKey), new ImbuingEnchantmentLevel(enchantmentLevel), false);
    }
}
