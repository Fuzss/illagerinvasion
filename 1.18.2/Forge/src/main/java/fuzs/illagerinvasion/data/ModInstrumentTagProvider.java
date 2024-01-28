//package fuzs.illagerinvasion.data;
//
//import fuzs.illagerinvasion.init.ModRegistry;
//import fuzs.puzzleslib.api.data.v1.AbstractTagProvider;
//import net.minecraft.core.HolderLookup;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.world.item.Instrument;
//import net.minecraftforge.data.event.GatherDataEvent;
//
//public class ModInstrumentTagProvider extends AbstractTagProvider.Intrinsic<Instrument> {
//
//    public ModInstrumentTagProvider(GatherDataEvent evt, String modId) {
//        super(Registries.INSTRUMENT, evt, modId);
//    }
//
//    @Override
//    protected void addTags(HolderLookup.Provider provider) {
//        this.tag(ModRegistry.HORN_OF_SIGHT_INSTRUMENT_TAG).add(ModRegistry.REVEAL_INSTRUMENT.get());
//    }
//}
