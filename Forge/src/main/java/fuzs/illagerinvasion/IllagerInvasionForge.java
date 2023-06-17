package fuzs.illagerinvasion;

import fuzs.illagerinvasion.data.*;
import fuzs.illagerinvasion.handler.PlatinumTrimHandler;
import fuzs.illagerinvasion.init.ForgeModRegistry;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
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
        registerHandlers();
    }

    private static void registerHandlers() {
        MinecraftForge.EVENT_BUS.addListener((final PlayerEvent.BreakSpeed evt) -> {
            if (evt.getEntity().getInventory().getDestroySpeed(evt.getState()) == 1.0F) {
                if (PlatinumTrimHandler.hasPlatinumTrim(evt.getEntity(), EquipmentSlot.CHEST)) {
                    evt.setNewSpeed(evt.getNewSpeed() * 1.5F);
                }
            }
        });
    }

    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent evt) {
        evt.getGenerator().addProvider(true, new ModBiomeTagProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModBlockLootProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModBlockTagProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModBuiltInDataProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModChestLootProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModEnchantmentTagProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModEntityInjectLootProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModEntityTypeLootProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModEntityTypeTagProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModInstrumentTagProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModItemTagProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModLanguageProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModModelProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModParticleDescriptionProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModRecipeProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModSoundDefinitionProvider(evt, IllagerInvasion.MOD_ID));
        evt.getGenerator().addProvider(true, new ModSpriteSourceProvider(evt, IllagerInvasion.MOD_ID));
    }
}
