package fuzs.illagerinvasion.core;

import fuzs.extensibleenums.api.extensibleenums.v1.BuiltInEnumFactories;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raider;

public class FabricAbstractions implements CommonAbstractions {

    @Override
    public Object registerRaiderType(String internalName, EntityType<? extends Raider> entityType, int[] spawnsPerWaveBeforeBonus) {
        return BuiltInEnumFactories.createRaiderType(internalName, entityType, spawnsPerWaveBeforeBonus);
    }

    @Override
    public Object registerIllagerSpell(String internalName, double spellColorRed, double spellColorGreen, double spellColorBlue) {
        return BuiltInEnumFactories.createIllagerSpell(internalName, spellColorRed, spellColorGreen, spellColorBlue);
    }
}
