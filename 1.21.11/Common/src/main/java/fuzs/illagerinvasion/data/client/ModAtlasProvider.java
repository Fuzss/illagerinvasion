package fuzs.illagerinvasion.data.client;

import fuzs.puzzleslib.api.client.data.v2.AbstractAtlasProvider;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;

public class ModAtlasProvider extends AbstractAtlasProvider {
    static final Material DRAGON_FIREBALL_LOCATION = new Material(TextureAtlas.LOCATION_BLOCKS,
            ResourceLocationHelper.withDefaultNamespace("entity/enderdragon/dragon_fireball"));

    public ModAtlasProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addAtlases() {
        this.addMaterial(DRAGON_FIREBALL_LOCATION);
    }
}
