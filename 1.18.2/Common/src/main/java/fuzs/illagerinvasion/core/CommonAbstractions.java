package fuzs.illagerinvasion.core;

import fuzs.illagerinvasion.helper.IllagerSpellEnumHelper;
import fuzs.puzzleslib.api.core.v1.ServiceProviderHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raider;

public interface CommonAbstractions {
    CommonAbstractions INSTANCE = ServiceProviderHelper.load(CommonAbstractions.class);

    Object registerRaiderType(String internalName, EntityType<? extends Raider> entityType, int[] spawnsPerWaveBeforeBonus);

    default Object registerIllagerSpell(String internalName, double spellColorRed, double spellColorGreen, double spellColorBlue) {
        return IllagerSpellEnumHelper.registerIllagerSpell(internalName, spellColorRed, spellColorGreen, spellColorBlue);
    }
}
