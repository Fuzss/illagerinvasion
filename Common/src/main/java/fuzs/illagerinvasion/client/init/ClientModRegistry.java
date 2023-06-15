package fuzs.illagerinvasion.client.init;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.puzzleslib.api.client.init.v1.ModelLayerFactory;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class ClientModRegistry {
    static final ModelLayerFactory FACTORY = ModelLayerFactory.from(IllagerInvasion.MOD_ID);
    public static final ModelLayerLocation CAPED_ILLAGER = FACTORY.register("caped_illager");
    public static final ModelLayerLocation INVOKER_SHIELD = FACTORY.register("invoker_shield");
    public static final ModelLayerLocation INVOKER_FANGS = FACTORY.register("invoker_fangs");
    public static final ModelLayerLocation HAT_ILLAGER = FACTORY.register("hat_illager");
    public static final ModelLayerLocation ARMORED_ILLAGER = FACTORY.register("armored_illager");
    public static final ModelLayerLocation STAFF_ILLAGER = FACTORY.register("staff_illager");
    public static final ModelLayerLocation BRIM_HAT_ILLAGER = FACTORY.register("brim_hat_illager");
}
