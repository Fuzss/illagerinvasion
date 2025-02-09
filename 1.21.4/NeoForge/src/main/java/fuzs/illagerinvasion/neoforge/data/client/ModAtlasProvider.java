package fuzs.illagerinvasion.neoforge.data.client;

import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.neoforge.api.client.data.v2.AbstractAtlasProvider;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;

import java.util.Optional;

public class ModAtlasProvider extends AbstractAtlasProvider {

    public ModAtlasProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addAtlases() {
        this.atlas(BLOCKS_ATLAS)
                .addSource(new SingleFile(ResourceLocationHelper.withDefaultNamespace(
                        "entity/enderdragon/dragon_fireball"), Optional.empty()));
    }
}
