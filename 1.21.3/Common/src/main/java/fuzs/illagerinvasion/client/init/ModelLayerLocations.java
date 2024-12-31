package fuzs.illagerinvasion.client.init;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.puzzleslib.api.client.init.v1.ModelLayerFactory;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class ModelLayerLocations {
    static final ModelLayerFactory FACTORY = ModelLayerFactory.from(IllagerInvasion.MOD_ID);
    public static final ModelLayerLocation ARCHIVIST = FACTORY.register("provoker");
    public static final ModelLayerLocation ARCHIVIST_BOOK = FACTORY.register("archivist");
    public static final ModelLayerLocation PROVOKER = FACTORY.register("archivist", "book");
    public static final ModelLayerLocation SURRENDERED = FACTORY.register("surrendered");
    public static final ModelLayerLocation SURRENDERED_INNER_ARMOR = FACTORY.registerInnerArmor("surrendered");
    public static final ModelLayerLocation SURRENDERED_OUTER_ARMOR = FACTORY.registerOuterArmor("surrendered");
    public static final ModelLayerLocation INVOKER = FACTORY.register("invoker");
    public static final ModelLayerLocation INVOKER_ARMOR = FACTORY.register("invoker", "armor");
    public static final ModelLayerLocation NECROMANCER = FACTORY.register("necromancer");
    public static final ModelLayerLocation NECROMANCER_ARMOR = FACTORY.register("necromancer", "armor");
    public static final ModelLayerLocation INVOKER_FANGS = FACTORY.register("invoker_fangs");
    public static final ModelLayerLocation SORCERER = FACTORY.register("sorcerer");
    public static final ModelLayerLocation INQUISITOR = FACTORY.register("inquisitor");
    public static final ModelLayerLocation FIRECALLER = FACTORY.register("firecaller");
    public static final ModelLayerLocation ALCHEMIST = FACTORY.register("alchemist");
    public static final ModelLayerLocation BASHER = FACTORY.register("basher");
    public static final ModelLayerLocation MARAUDER = FACTORY.register("marauder");
    public static final ModelLayerLocation SKULL_BOLT = FACTORY.register("skull_bolt");
}
