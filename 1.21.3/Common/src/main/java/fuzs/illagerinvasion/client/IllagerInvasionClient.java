package fuzs.illagerinvasion.client;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.gui.screens.inventory.ImbuingScreen;
import fuzs.illagerinvasion.client.handler.EnchantmentTooltipHandler;
import fuzs.illagerinvasion.client.init.ClientModRegistry;
import fuzs.illagerinvasion.client.model.*;
import fuzs.illagerinvasion.client.render.entity.*;
import fuzs.illagerinvasion.init.ModEntityTypes;
import fuzs.illagerinvasion.init.ModItems;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.*;
import fuzs.puzzleslib.api.client.event.v1.gui.ItemTooltipCallback;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.HeartParticle;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class IllagerInvasionClient implements ClientModConstructor {

    @Override
    public void onConstructMod() {
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        ItemTooltipCallback.EVENT.register(EnchantmentTooltipHandler::onItemTooltip);
    }

    @Override
    public void onRegisterMenuScreens(MenuScreensContext context) {
        context.registerMenuScreen(ModRegistry.IMBUING_MENU_TYPE.value(), ImbuingScreen::new);
    }

    @Override
    public void onRegisterEntityRenderers(EntityRenderersContext context) {
        context.registerEntityRenderer(ModEntityTypes.PROVOKER_ENTITY_TYPE.value(), ProvokerRender::new);
        context.registerEntityRenderer(ModEntityTypes.INVOKER_ENTITY_TYPE.value(), InvokerRender::new);
        context.registerEntityRenderer(ModEntityTypes.SURRENDERED_ENTITY_TYPE.value(), SurrenderedRender::new);
        context.registerEntityRenderer(ModEntityTypes.NECROMANCER_ENTITY_TYPE.value(), NecromancerRender::new);
        context.registerEntityRenderer(ModEntityTypes.SKULL_BOLT_ENTITY_TYPE.value(), SkullBoltRender::new);
        context.registerEntityRenderer(ModEntityTypes.BASHER_ENTITY_TYPE.value(), BasherRender::new);
        context.registerEntityRenderer(ModEntityTypes.SORCERER_ENTITY_TYPE.value(), SorcererRender::new);
        context.registerEntityRenderer(ModEntityTypes.ARCHIVIST_ENTITY_TYPE.value(), ArchivistRender::new);
        context.registerEntityRenderer(ModEntityTypes.INQUISITOR_ENTITY_TYPE.value(), InquisitorRender::new);
        context.registerEntityRenderer(ModEntityTypes.MARAUDER_ENTITY_TYPE.value(), MarauderRender::new);
        context.registerEntityRenderer(ModEntityTypes.ALCHEMIST_ENTITY_TYPE.value(), AlchemistRender::new);
        context.registerEntityRenderer(ModEntityTypes.FIRECALLER_ENTITY_TYPE.value(), FirecallerRender::new);
        context.registerEntityRenderer(ModEntityTypes.INVOKER_FANGS_ENTITY_TYPE.value(), InvokerFangsRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.HATCHET_ENTITY_TYPE.value(), HatchetRender::new);
        context.registerEntityRenderer(ModEntityTypes.FLYING_MAGMA_ENTITY_TYPE.value(), MagmaEntityRender::new);
    }

    @Override
    public void onRegisterParticleProviders(ParticleProvidersContext context) {
        context.registerParticleProvider(ModRegistry.MAGIC_FLAME_PARTICLE_TYPE.value(), FlameParticle.Provider::new);
        context.registerParticleProvider(ModRegistry.NECROMANCER_BUFF_PARTICLE_TYPE.value(), HeartParticle.Provider::new);
    }

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        context.registerLayerDefinition(ClientModRegistry.CAPED_ILLAGER, () -> InvokerEntityModel.getTexturedModelData(CubeDeformation.NONE));
        context.registerLayerDefinition(ClientModRegistry.INVOKER_SHIELD, () -> InvokerEntityModel.getTexturedModelData(LayerDefinitions.INNER_ARMOR_DEFORMATION));
        context.registerLayerDefinition(ClientModRegistry.NECROMANCER_SHIELD, () -> ClientModRegistry.createIllagerBodyLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION));
        context.registerLayerDefinition(ClientModRegistry.INVOKER_FANGS, InvokerFangsModel::getTexturedModelData);
        context.registerLayerDefinition(ClientModRegistry.ARMORED_ILLAGER, ArmoredIllagerEntityModel::getTexturedModelData);
        context.registerLayerDefinition(ClientModRegistry.HAT_ILLAGER, HatIllagerEntityModel::getTexturedModelData);
        context.registerLayerDefinition(ClientModRegistry.STAFF_ILLAGER, IllagerWithStaffEntityModel::getTexturedModelData);
        context.registerLayerDefinition(ClientModRegistry.BRIM_HAT_ILLAGER, BrimmedHatIllagerEntityModel::getTexturedModelData);
    }

    @Override
    public void onRegisterBlockRenderTypes(RenderTypesContext<Block> context) {
        context.registerRenderType(RenderType.cutout(), ModRegistry.MAGIC_FIRE_BLOCK.value());
    }

    @Override
    public void onRegisterItemModelProperties(ItemModelPropertiesContext context) {
        context.registerItemProperty(IllagerInvasion.id("tooting"), (ItemStack itemStack, ClientLevel clientLevel, LivingEntity livingEntity, int i) -> {
            return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F;
        }, ModItems.HORN_OF_SIGHT_ITEM.value());
    }
}
