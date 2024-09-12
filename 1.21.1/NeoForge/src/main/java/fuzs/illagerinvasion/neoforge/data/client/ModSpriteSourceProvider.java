package fuzs.illagerinvasion.neoforge.data.client;

import fuzs.puzzleslib.neoforge.api.data.v2.client.AbstractSpriteSourceProvider;
import fuzs.puzzleslib.neoforge.api.data.v2.core.ForgeDataProviderContext;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class ModSpriteSourceProvider extends AbstractSpriteSourceProvider {

    public ModSpriteSourceProvider(ForgeDataProviderContext context) {
        super(context);
    }

    @Override
    public void addSpriteSources() {
        this.atlas(BLOCKS_ATLAS).addSource(new SingleFile(new ResourceLocation("entity/enderdragon/dragon_fireball"), Optional.empty()));
    }
}
