package fuzs.illagerinvasion.world.item.enchantment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.neoforgedatapackextensions.api.v1.DataMapRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;

public record ImbuingEnchantmentLevel(int enchantmentLevel) {
    public static final Codec<ImbuingEnchantmentLevel> INLINE_CODEC = Codec.intRange(1, 255)
            .xmap(ImbuingEnchantmentLevel::new, ImbuingEnchantmentLevel::enchantmentLevel);
    public static final Codec<ImbuingEnchantmentLevel> CODEC = Codec.withAlternative(RecordCodecBuilder.create(instance -> instance.group(
                    Codec.intRange(1, 255).fieldOf("enchantment_level").forGetter(ImbuingEnchantmentLevel::enchantmentLevel))
            .apply(instance, ImbuingEnchantmentLevel::new)), INLINE_CODEC);

    public static boolean isSupportedByImbuing(Holder<Enchantment> enchantment) {
        return DataMapRegistry.INSTANCE.getData(ModRegistry.IMBUING_LEVELS_DATA_MAP_TYPE, enchantment) != null;
    }

    public static int getImbuingMaxEnchantmentLevel(Holder<Enchantment> enchantment) {
        ImbuingEnchantmentLevel enchantmentLevel = DataMapRegistry.INSTANCE.getData(ModRegistry.IMBUING_LEVELS_DATA_MAP_TYPE,
                enchantment);
        return enchantmentLevel != null ? enchantmentLevel.enchantmentLevel() : 0;
    }
}
