package fuzs.illagerinvasion.data;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.EntityTypeTags;
import net.minecraftforge.data.event.GatherDataEvent;

public class ModEntityTypeTagProvider extends AbstractTagProvider.EntityTypes {

    public ModEntityTypeTagProvider(GatherDataEvent evt, String modId) {
        super(evt, modId);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(EntityTypeTags.RAIDERS).add(ModRegistry.BASHER_ENTITY_TYPE.get(), ModRegistry.PROVOKER_ENTITY_TYPE.get(), ModRegistry.NECROMANCER_ENTITY_TYPE.get(), ModRegistry.SORCERER_ENTITY_TYPE.get(), ModRegistry.ARCHIVIST_ENTITY_TYPE.get(), ModRegistry.MARAUDER_ENTITY_TYPE.get(), ModRegistry.INQUISITOR_ENTITY_TYPE.get(), ModRegistry.ALCHEMIST_ENTITY_TYPE.get());
        this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(ModRegistry.INVOKER_ENTITY_TYPE.get());
    }
}
