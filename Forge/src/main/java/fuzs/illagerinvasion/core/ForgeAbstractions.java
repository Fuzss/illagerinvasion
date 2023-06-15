package fuzs.illagerinvasion.core;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;

public class ForgeAbstractions implements CommonAbstractions {

    @Override
    public Object createRaiderType(String internalName, EntityType<? extends Raider> entityType, int[] spawnsPerWaveBeforeBonus) {
        return Raid.RaiderType.create(internalName, entityType, spawnsPerWaveBeforeBonus);
    }

    @Override
    public Object createIllagerSpell(String internalName, double spellColorRed, double spellColorGreen, double spellColorBlue) {
        return null;
    }
}
