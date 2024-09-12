package fuzs.illagerinvasion.data.tags;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;

public class ModEntityTypeTagProvider extends AbstractTagProvider<EntityType<?>> {

    public ModEntityTypeTagProvider(DataProviderContext context) {
        super(Registries.ENTITY_TYPE, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.add(EntityTypeTags.RAIDERS).add(ModRegistry.BASHER_ENTITY_TYPE.value(),
                ModRegistry.PROVOKER_ENTITY_TYPE.value(), ModRegistry.NECROMANCER_ENTITY_TYPE.value(),
                ModRegistry.SORCERER_ENTITY_TYPE.value(), ModRegistry.ARCHIVIST_ENTITY_TYPE.value(),
                ModRegistry.MARAUDER_ENTITY_TYPE.value(), ModRegistry.INQUISITOR_ENTITY_TYPE.value(),
                ModRegistry.ALCHEMIST_ENTITY_TYPE.value()
        );
        this.add(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(ModRegistry.INVOKER_ENTITY_TYPE.value());
        this.add("numismatic-overhaul:the_bourgeoisie").add(ModRegistry.BASHER_ENTITY_TYPE.value(),
                ModRegistry.PROVOKER_ENTITY_TYPE.value(), ModRegistry.NECROMANCER_ENTITY_TYPE.value(),
                ModRegistry.SORCERER_ENTITY_TYPE.value(), ModRegistry.ARCHIVIST_ENTITY_TYPE.value(),
                ModRegistry.MARAUDER_ENTITY_TYPE.value(), ModRegistry.INQUISITOR_ENTITY_TYPE.value(),
                ModRegistry.ALCHEMIST_ENTITY_TYPE.value()
        );
    }
}
