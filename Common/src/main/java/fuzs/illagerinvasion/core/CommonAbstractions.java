package fuzs.illagerinvasion.core;

import fuzs.puzzleslib.api.core.v1.ServiceProviderHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raider;

public interface CommonAbstractions {
    CommonAbstractions INSTANCE = ServiceProviderHelper.load(CommonAbstractions.class);

    Object createRaiderType(String internalName, EntityType<? extends Raider> entityType, int[] spawnsPerWaveBeforeBonus);

    Object createIllagerSpell(String internalName, double spellColorRed, double spellColorGreen, double spellColorBlue);
}
