package fuzs.illagerinvasion.data.tags;

import fuzs.illagerinvasion.init.ModEntityTypes;
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
        this.add(EntityTypeTags.RAIDERS).add(ModEntityTypes.BASHER_ENTITY_TYPE.value(),
                ModEntityTypes.PROVOKER_ENTITY_TYPE.value(), ModEntityTypes.NECROMANCER_ENTITY_TYPE.value(),
                ModEntityTypes.SORCERER_ENTITY_TYPE.value(), ModEntityTypes.ARCHIVIST_ENTITY_TYPE.value(),
                ModEntityTypes.MARAUDER_ENTITY_TYPE.value(), ModEntityTypes.INQUISITOR_ENTITY_TYPE.value(),
                ModEntityTypes.ALCHEMIST_ENTITY_TYPE.value()
        );
        this.add(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(ModEntityTypes.INVOKER_ENTITY_TYPE.value());
        this.add("numismatic-overhaul:the_bourgeoisie").add(ModEntityTypes.BASHER_ENTITY_TYPE.value(),
                ModEntityTypes.PROVOKER_ENTITY_TYPE.value(), ModEntityTypes.NECROMANCER_ENTITY_TYPE.value(),
                ModEntityTypes.SORCERER_ENTITY_TYPE.value(), ModEntityTypes.ARCHIVIST_ENTITY_TYPE.value(),
                ModEntityTypes.MARAUDER_ENTITY_TYPE.value(), ModEntityTypes.INQUISITOR_ENTITY_TYPE.value(),
                ModEntityTypes.ALCHEMIST_ENTITY_TYPE.value()
        );
    }
}
