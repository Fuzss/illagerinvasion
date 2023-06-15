package me.sandbox.client;

import fuzs.illagerinvasion.init.ModRegistry;
import me.sandbox.client.particle.ParticleRegistry;
import me.sandbox.client.renders.*;
import me.sandbox.entity.monster.EntityRegistry;
import me.sandbox.gui.ModdedScreenHandler;
import me.sandbox.item.ItemRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ClientRegistry implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        //entities
        EntityRendererRegistry.register(EntityRegistry.PROVOKER, ProvokerRender::new);
        EntityRendererRegistry.register(EntityRegistry.INVOKER, InvokerRender::new);
        EntityRendererRegistry.register(EntityRegistry.SURRENDERED, SurrenderedRender::new);
        EntityRendererRegistry.register(EntityRegistry.BASHER, BasherRender::new);
        EntityRendererRegistry.register(EntityRegistry.SORCERER, SorcererRender::new);
        EntityRendererRegistry.register(EntityRegistry.ARCHIVIST, ArchivistRender::new);
        EntityRendererRegistry.register(EntityRegistry.INQUISITOR, InquisitorRender::new);
        EntityRendererRegistry.register(EntityRegistry.MARAUDER, MarauderRender::new);
        EntityRendererRegistry.register(EntityRegistry.ALCHEMIST, AlchemistRender::new);
        EntityRendererRegistry.register(EntityRegistry.FIRECALLER, FirecallerRender::new);
        EntityRendererRegistry.register(EntityRegistry.INVOKER_FANGS, InvokerFangsRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.HATCHET, HatchetRender::new);
        EntityRendererRegistry.register(EntityRegistry.MAGMA, MagmaEntityRender::new);




        //pull registry
        registerPullPredicates(ItemRegistry.HORN_OF_SIGHT);

        //Block client stuff
        BlockRenderLayerMap.INSTANCE.putBlock(ModRegistry.MAGIC_FIRE_BLOCK, RenderType.cutout());


        }
        public void registerPullPredicates(Item item) {
            ItemProperties.register(item, new ResourceLocation("pull"), (itemStack, world, livingEntity, i) -> {
                if (livingEntity == null) {
                    return 0.0F;
                } else {
                    return livingEntity.getUseItem() != itemStack ? 0.0F : (float) (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0F;
                }
            });
            ItemProperties.register(item, new ResourceLocation("pulling"),
                    (itemStack, clientWorld, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);
        }

}
