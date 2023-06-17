package fuzs.illagerinvasion.data;

import com.mojang.serialization.Lifecycle;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractBuiltInDataProvider implements DataProvider {
    private RegistrySetBuilder registrySetBuilder;
    private final PackOutput output;
    private final String modId;
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;

    public AbstractBuiltInDataProvider(GatherDataEvent evt, String modId) {
        this(evt.getGenerator().getPackOutput(), modId, evt.getLookupProvider());
    }

    public AbstractBuiltInDataProvider(PackOutput output, String modId, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        this.output = output;
        this.modId = modId;
        this.lookupProvider = lookupProvider;
    }

    protected final <T> void add(ResourceKey<? extends Registry<T>> key, Lifecycle lifecycle, RegistrySetBuilder.RegistryBootstrap<T> bootstrap) {
        Objects.requireNonNull(this.registrySetBuilder, "registry set builder is null");
        this.registrySetBuilder.add(key, lifecycle, bootstrap);
    }

    protected final <T> void add(ResourceKey<? extends Registry<T>> key, RegistrySetBuilder.RegistryBootstrap<T> bootstrap) {
        Objects.requireNonNull(this.registrySetBuilder, "registry set builder is null");
        this.registrySetBuilder.add(key, bootstrap);
    }

    protected abstract void addRegistrySets();

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        this.registrySetBuilder = new RegistrySetBuilder();
        this.addRegistrySets();
        return new DatapackBuiltinEntriesProvider(this.output, this.lookupProvider, this.registrySetBuilder, Set.of(this.modId)).run(output);
    }

    @Override
    public String getName() {
        return "Registries";
    }
}
