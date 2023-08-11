package fuzs.illagerinvasion;

import fuzs.illagerinvasion.data.*;
import fuzs.illagerinvasion.init.ForgeModRegistry;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.ModContainerHelper;
import fuzs.puzzleslib.impl.core.context.BuildCreativeModeTabContentsContextForgeImpl;
import fuzs.puzzleslib.impl.core.context.CreativeModeTabContextForgeImpl;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod(IllagerInvasion.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class IllagerInvasionForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ForgeModRegistry.touch();
        ModConstructor.construct(IllagerInvasion.MOD_ID, IllagerInvasion::new);


        ModContainerHelper.findModEventBus(IllagerInvasion.MOD_ID).orElseThrow().addGenericListener(Fluid.class, (final RegistryEvent<Fluid> $) -> {
            IllagerInvasion constructor = new IllagerInvasion();
            constructor.onRegisterCreativeModeTabs(new CreativeModeTabContextForgeImpl());
            constructor.onBuildCreativeModeTabContents(new BuildCreativeModeTabContentsContextForgeImpl());
        });
    }

    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent evt) {
        evt.getGenerator().addProvider(new ModBiomeTagProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(new ModBlockLootProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(new ModBlockTagProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(new ModChestLootProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(new ModEnchantmentTagProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(new ModEntityInjectLootProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(new ModEntityTypeLootProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(new ModEntityTypeTagProvider(evt, IllagerInvasion.MOD_ID));
//        evt.getGenerator().addProvider(new ModInstrumentTagProvider(evt, IllagerInvasion.MOD_ID));
//        evt.getGenerator().addProvider(new ModItemTagProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(new ModLanguageProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(new ModModelProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(new ModParticleDescriptionProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(new ModRecipeProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(new ModSoundDefinitionProvider(evt, IllagerInvasion.MOD_ID));
//        evt.getGenerator().addProvider(new ModSpriteSourceProvider(evt, IllagerInvasion.MOD_ID));
//        evt.getGenerator().addProvider(new ModTrimMaterialDataProvider(evt, IllagerInvasion.MOD_ID));
    }
}
