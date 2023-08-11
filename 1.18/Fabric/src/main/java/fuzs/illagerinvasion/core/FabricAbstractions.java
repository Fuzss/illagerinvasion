package fuzs.illagerinvasion.core;

import fuzs.extensibleenums.core.EnumFactories;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raider;

public class FabricAbstractions implements CommonAbstractions {

    @Override
    public Object registerRaiderType(String internalName, EntityType<? extends Raider> entityType, int[] spawnsPerWaveBeforeBonus) {
        return EnumFactories.createRaiderType(internalName, entityType, spawnsPerWaveBeforeBonus);
    }

    @Override
    public Object registerIllagerSpell(String internalName, double spellColorRed, double spellColorGreen, double spellColorBlue) {
        try {
            return Class.forName("net.minecraft.world.entity.monster.SpellcasterIllager$IllagerSpell").getEnumConstants()[0];
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
//        return EnumFactories.createIllagerSpell(internalName, spellColorRed, spellColorGreen, spellColorBlue);
    }
}
