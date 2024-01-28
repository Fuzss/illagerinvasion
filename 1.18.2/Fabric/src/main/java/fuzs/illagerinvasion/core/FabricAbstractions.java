package fuzs.illagerinvasion.core;

import fuzs.extensibleenums.core.EnumFactories;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raider;

public class FabricAbstractions implements CommonAbstractions {

    @Override
    public Object registerRaiderType(String internalName, EntityType<? extends Raider> entityType, int[] spawnsPerWaveBeforeBonus) {
        return EnumFactories.createRaiderType(internalName, entityType, spawnsPerWaveBeforeBonus);
    }
}
