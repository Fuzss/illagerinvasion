package fuzs.illagerinvasion.data.tags;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.AbstractTagProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.EntityTypeTags;

public class ModEntityTypeTagProvider extends AbstractTagProvider.EntityTypes {

    public ModEntityTypeTagProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(EntityTypeTags.RAIDERS).add(ModRegistry.BASHER_ENTITY_TYPE.value(), ModRegistry.PROVOKER_ENTITY_TYPE.value(), ModRegistry.NECROMANCER_ENTITY_TYPE.value(), ModRegistry.SORCERER_ENTITY_TYPE.value(), ModRegistry.ARCHIVIST_ENTITY_TYPE.value(), ModRegistry.MARAUDER_ENTITY_TYPE.value(), ModRegistry.INQUISITOR_ENTITY_TYPE.value(), ModRegistry.ALCHEMIST_ENTITY_TYPE.value());
        this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(ModRegistry.INVOKER_ENTITY_TYPE.value());
        this.tag("numismatic-overhaul:the_bourgeoisie").add(ModRegistry.BASHER_ENTITY_TYPE.value(), ModRegistry.PROVOKER_ENTITY_TYPE.value(), ModRegistry.NECROMANCER_ENTITY_TYPE.value(), ModRegistry.SORCERER_ENTITY_TYPE.value(), ModRegistry.ARCHIVIST_ENTITY_TYPE.value(), ModRegistry.MARAUDER_ENTITY_TYPE.value(), ModRegistry.INQUISITOR_ENTITY_TYPE.value(), ModRegistry.ALCHEMIST_ENTITY_TYPE.value());
    }
}
