package fuzs.illagerinvasion.data.tags;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.AbstractTagProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public class ModEnchantmentTagProvider extends AbstractTagProvider.Intrinsic<Enchantment> {

    public ModEnchantmentTagProvider(DataProviderContext context) {
        super(Registries.ENCHANTMENT, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        // list taken from Quark's ancient tomes, thanks!
        this.tag(ModRegistry.IMBUING_ENCHANTMENT_TAG).add(Enchantments.ALL_DAMAGE_PROTECTION, Enchantments.BLAST_PROTECTION, Enchantments.PROJECTILE_PROTECTION, Enchantments.FIRE_PROTECTION, Enchantments.FALL_PROTECTION, Enchantments.THORNS, Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS, Enchantments.KNOCKBACK, Enchantments.FIRE_ASPECT, Enchantments.MOB_LOOTING, Enchantments.SWEEPING_EDGE, Enchantments.BLOCK_EFFICIENCY, Enchantments.UNBREAKING, Enchantments.BLOCK_FORTUNE, Enchantments.POWER_ARROWS, Enchantments.PUNCH_ARROWS, Enchantments.FISHING_LUCK, Enchantments.FISHING_SPEED, Enchantments.LOYALTY, Enchantments.RIPTIDE, Enchantments.IMPALING, Enchantments.PIERCING);
    }
}
