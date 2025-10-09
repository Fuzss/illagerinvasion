package fuzs.illagerinvasion.client.model.geom;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.puzzleslib.api.client.init.v1.ModelLayerFactory;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.ArmorModelSet;

public class ModModelLayers {
    static final ModelLayerFactory MODEL_LAYERS = ModelLayerFactory.from(IllagerInvasion.MOD_ID);
    public static final ModelLayerLocation ARCHIVIST = MODEL_LAYERS.registerModelLayer("provoker");
    public static final ModelLayerLocation ARCHIVIST_BOOK = MODEL_LAYERS.registerModelLayer("archivist");
    public static final ModelLayerLocation PROVOKER = MODEL_LAYERS.registerModelLayer("archivist", "book");
    public static final ModelLayerLocation SURRENDERED = MODEL_LAYERS.registerModelLayer("surrendered");
    public static final ArmorModelSet<ModelLayerLocation> SURRENDERED_ARMOR = MODEL_LAYERS.registerArmorSet(
            "surrendered");
    public static final ModelLayerLocation INVOKER = MODEL_LAYERS.registerModelLayer("invoker");
    public static final ModelLayerLocation INVOKER_ARMOR = MODEL_LAYERS.registerModelLayer("invoker", "armor");
    public static final ModelLayerLocation NECROMANCER = MODEL_LAYERS.registerModelLayer("necromancer");
    public static final ModelLayerLocation NECROMANCER_ARMOR = MODEL_LAYERS.registerModelLayer("necromancer", "armor");
    public static final ModelLayerLocation INVOKER_FANGS = MODEL_LAYERS.registerModelLayer("invoker_fangs");
    public static final ModelLayerLocation SORCERER = MODEL_LAYERS.registerModelLayer("sorcerer");
    public static final ModelLayerLocation INQUISITOR = MODEL_LAYERS.registerModelLayer("inquisitor");
    public static final ModelLayerLocation FIRECALLER = MODEL_LAYERS.registerModelLayer("firecaller");
    public static final ModelLayerLocation ALCHEMIST = MODEL_LAYERS.registerModelLayer("alchemist");
    public static final ModelLayerLocation BASHER = MODEL_LAYERS.registerModelLayer("basher");
    public static final ModelLayerLocation MARAUDER = MODEL_LAYERS.registerModelLayer("marauder");
    public static final ModelLayerLocation SKULL_BOLT = MODEL_LAYERS.registerModelLayer("skull_bolt");
}
