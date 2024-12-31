package fuzs.illagerinvasion.data.registries;

import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.illagerinvasion.init.ModSoundEvents;
import fuzs.puzzleslib.api.data.v2.AbstractRegistriesDatapackGenerator;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Instrument;

public class ModInstrumentDataProvider extends AbstractRegistriesDatapackGenerator<Instrument> {

    public ModInstrumentDataProvider(DataProviderContext context) {
        super(Registries.INSTRUMENT, context);
    }

    @Override
    public void addBootstrap(BootstrapContext<Instrument> context) {
        registerInstrument(context,
                ModRegistry.REVEAL_INSTRUMENT,
                ModSoundEvents.HORN_OF_SIGHT_SOUND_EVENT,
                7.0F,
                64.0F);
    }

    @Deprecated(forRemoval = true)
    protected static void registerInstrument(BootstrapContext<Instrument> context, ResourceKey<Instrument> resourceKey, Holder<SoundEvent> soundEvent, float useDuration, float range) {
        context.register(resourceKey, new Instrument(soundEvent, useDuration, range, getComponent(resourceKey)));
    }

    @Deprecated(forRemoval = true)
    public static MutableComponent getComponent(ResourceKey<?> resourceKey) {
        return Component.translatable(getTranslationKey(resourceKey));
    }

    @Deprecated(forRemoval = true)
    public static String getTranslationKey(ResourceKey<?> resourceKey) {
        return Util.makeDescriptionId(resourceKey.registry().getPath(), resourceKey.location());
    }
}
