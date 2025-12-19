package fuzs.illagerinvasion.neoforge.client;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.IllagerInvasionClient;
import fuzs.illagerinvasion.data.client.ModAtlasProvider;
import fuzs.illagerinvasion.data.client.ModLanguageProvider;
import fuzs.illagerinvasion.data.client.ModModelProvider;
import fuzs.illagerinvasion.data.client.ModParticleProvider;
import fuzs.illagerinvasion.neoforge.data.client.ModSoundProvider;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = IllagerInvasion.MOD_ID, dist = Dist.CLIENT)
public class IllagerInvasionNeoForgeClient {

    public IllagerInvasionNeoForgeClient() {
        ClientModConstructor.construct(IllagerInvasion.MOD_ID, IllagerInvasionClient::new);
        DataProviderHelper.registerDataProviders(IllagerInvasion.MOD_ID,
                ModLanguageProvider::new,
                ModModelProvider::new,
                ModParticleProvider::new,
                ModSoundProvider::new,
                ModAtlasProvider::new);
    }
}
