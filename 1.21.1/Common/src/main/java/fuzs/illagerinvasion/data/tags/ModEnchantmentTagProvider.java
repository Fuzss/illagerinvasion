package fuzs.illagerinvasion.data.tags;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public class ModEnchantmentTagProvider extends AbstractTagProvider<Enchantment> {

    public ModEnchantmentTagProvider(DataProviderContext context) {
        super(Registries.ENCHANTMENT, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        // list taken from Quark's ancient tomes, thanks!
        this.add(ModRegistry.IMBUING_ENCHANTMENT_TAG).add(Enchantments.PROTECTION, Enchantments.BLAST_PROTECTION,
                Enchantments.PROJECTILE_PROTECTION, Enchantments.FIRE_PROTECTION, Enchantments.FEATHER_FALLING,
                Enchantments.THORNS, Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS,
                Enchantments.KNOCKBACK, Enchantments.FIRE_ASPECT, Enchantments.LOOTING, Enchantments.SWEEPING_EDGE,
                Enchantments.EFFICIENCY, Enchantments.UNBREAKING, Enchantments.FORTUNE, Enchantments.POWER,
                Enchantments.PUNCH, Enchantments.LUCK_OF_THE_SEA, Enchantments.LURE, Enchantments.LOYALTY,
                Enchantments.RIPTIDE, Enchantments.IMPALING, Enchantments.PIERCING, Enchantments.DENSITY,
                Enchantments.BREACH, Enchantments.WIND_BURST
        );
    }
}
