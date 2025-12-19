package fuzs.illagerinvasion.client;

import fuzs.illagerinvasion.client.gui.screens.inventory.ImbuingScreen;
import fuzs.illagerinvasion.client.model.*;
import fuzs.illagerinvasion.client.model.geom.ModModelLayers;
import fuzs.illagerinvasion.client.render.entity.*;
import fuzs.illagerinvasion.init.ModEntityTypes;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.*;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.HeartParticle;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.entity.WitherSkullRenderer;
import net.minecraft.world.level.block.Block;

public class IllagerInvasionClient implements ClientModConstructor {

    @Override
    public void onRegisterMenuScreens(MenuScreensContext context) {
        context.registerMenuScreen(ModRegistry.IMBUING_MENU_TYPE.value(), ImbuingScreen::new);
    }

    @Override
    public void onRegisterEntityRenderers(EntityRenderersContext context) {
        context.registerEntityRenderer(ModEntityTypes.PROVOKER_ENTITY_TYPE.value(), ProvokerRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.INVOKER_ENTITY_TYPE.value(), InvokerRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.SURRENDERED_ENTITY_TYPE.value(), SurrenderedRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.NECROMANCER_ENTITY_TYPE.value(), NecromancerRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.SKULL_BOLT_ENTITY_TYPE.value(), SkullBoltRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.BASHER_ENTITY_TYPE.value(), BasherRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.SORCERER_ENTITY_TYPE.value(), SorcererRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.ARCHIVIST_ENTITY_TYPE.value(), ArchivistRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.INQUISITOR_ENTITY_TYPE.value(), InquisitorRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.MARAUDER_ENTITY_TYPE.value(), MarauderRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.ALCHEMIST_ENTITY_TYPE.value(), AlchemistRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.FIRECALLER_ENTITY_TYPE.value(), FirecallerRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.INVOKER_FANGS_ENTITY_TYPE.value(),
                fuzs.illagerinvasion.client.render.entity.InvokerFangsRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.HATCHET_ENTITY_TYPE.value(), ThrownHatchetRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.FLYING_MAGMA_ENTITY_TYPE.value(), FlyingMagmaRenderer::new);
    }

    @Override
    public void onRegisterParticleProviders(ParticleProvidersContext context) {
        context.registerParticleProvider(ModRegistry.MAGIC_FLAME_PARTICLE_TYPE.value(), FlameParticle.Provider::new);
        context.registerParticleProvider(ModRegistry.NECROMANCER_BUFF_PARTICLE_TYPE.value(),
                HeartParticle.Provider::new);
    }

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        context.registerLayerDefinition(ModModelLayers.ARCHIVIST, () -> {
            return IllagerModel.createBodyLayer().apply(CustomIllagerModel.SCALE_TRANSFORMER);
        });
        context.registerLayerDefinition(ModModelLayers.ARCHIVIST_BOOK, BookModel::createBodyLayer);
        MeshTransformer surrenderedMeshTransformer = MeshTransformer.scaling(0.85F);
        context.registerLayerDefinition(ModModelLayers.SURRENDERED,
                () -> SkeletonModel.createBodyLayer().apply(surrenderedMeshTransformer));
        context.registerArmorDefinition(ModModelLayers.SURRENDERED_ARMOR,
                HumanoidModel.createArmorMeshSet(LayerDefinitions.INNER_ARMOR_DEFORMATION,
                                LayerDefinitions.OUTER_ARMOR_DEFORMATION)
                        .map((MeshDefinition meshDefinition) -> LayerDefinition.create(meshDefinition, 64, 32)));
        context.registerLayerDefinition(ModModelLayers.PROVOKER, () -> {
            return IllagerModel.createBodyLayer().apply(CustomIllagerModel.SCALE_TRANSFORMER);
        });
        context.registerLayerDefinition(ModModelLayers.INVOKER,
                () -> InvokerModel.createBodyLayer(CubeDeformation.NONE).apply(CustomIllagerModel.SCALE_TRANSFORMER));
        context.registerLayerDefinition(ModModelLayers.INVOKER_ARMOR,
                () -> InvokerModel.createBodyLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION)
                        .apply(CustomIllagerModel.SCALE_TRANSFORMER));
        context.registerLayerDefinition(ModModelLayers.NECROMANCER,
                () -> NecromancerRenderer.createBodyLayer(CubeDeformation.NONE)
                        .apply(CustomIllagerModel.SCALE_TRANSFORMER));
        context.registerLayerDefinition(ModModelLayers.NECROMANCER_ARMOR,
                () -> NecromancerRenderer.createBodyLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION)
                        .apply(CustomIllagerModel.SCALE_TRANSFORMER));
        context.registerLayerDefinition(ModModelLayers.INVOKER_FANGS, InvokerFangsModel::createBodyLayer);
        context.registerLayerDefinition(ModModelLayers.INQUISITOR,
                () -> InquisitorModel.createBodyLayer().apply(MeshTransformer.scaling(1.1F)));
        context.registerLayerDefinition(ModModelLayers.SORCERER,
                () -> SorcererRenderer.createBodyLayer().apply(CustomIllagerModel.SCALE_TRANSFORMER));
        context.registerLayerDefinition(ModModelLayers.FIRECALLER,
                () -> FirecallerModel.createBodyLayer().apply(CustomIllagerModel.SCALE_TRANSFORMER));
        context.registerLayerDefinition(ModModelLayers.ALCHEMIST,
                () -> AlchemistRenderer.createBodyLayer().apply(CustomIllagerModel.SCALE_TRANSFORMER));
        context.registerLayerDefinition(ModModelLayers.BASHER, () -> {
            return IllagerModel.createBodyLayer().apply(CustomIllagerModel.SCALE_TRANSFORMER);
        });
        context.registerLayerDefinition(ModModelLayers.MARAUDER, () -> {
            return IllagerModel.createBodyLayer().apply(CustomIllagerModel.SCALE_TRANSFORMER);
        });
        context.registerLayerDefinition(ModModelLayers.SKULL_BOLT, WitherSkullRenderer::createSkullLayer);
    }

    @Override
    public void onRegisterBlockRenderTypes(RenderTypesContext<Block> context) {
        context.registerChunkRenderType(ModRegistry.MAGIC_FIRE_BLOCK.value(), ChunkSectionLayer.CUTOUT);
    }
}
