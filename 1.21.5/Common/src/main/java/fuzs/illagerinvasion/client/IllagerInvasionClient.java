package fuzs.illagerinvasion.client;

import fuzs.illagerinvasion.client.gui.screens.inventory.ImbuingScreen;
import fuzs.illagerinvasion.client.init.ModelLayerLocations;
import fuzs.illagerinvasion.client.model.FirecallerModel;
import fuzs.illagerinvasion.client.model.InquisitorModel;
import fuzs.illagerinvasion.client.model.InvokerFangsModel;
import fuzs.illagerinvasion.client.model.InvokerModel;
import fuzs.illagerinvasion.client.render.entity.*;
import fuzs.illagerinvasion.init.ModEntityTypes;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.*;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.HeartParticle;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.WitherSkullRenderer;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

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
        MeshTransformer illagerMeshTransformer = MeshTransformer.scaling(0.9375F);
        Supplier<LayerDefinition> illagerLayerDefinition = () -> {
            return IllagerModel.createBodyLayer().apply(illagerMeshTransformer);
        };
        context.registerLayerDefinition(ModelLayerLocations.ARCHIVIST, illagerLayerDefinition);
        context.registerLayerDefinition(ModelLayerLocations.ARCHIVIST_BOOK, BookModel::createBodyLayer);
        MeshTransformer surrenderedMeshTransformer = MeshTransformer.scaling(0.85F);
        context.registerLayerDefinition(ModelLayerLocations.SURRENDERED,
                () -> SkeletonModel.createBodyLayer().apply(surrenderedMeshTransformer));
        context.registerLayerDefinition(ModelLayerLocations.SURRENDERED_INNER_ARMOR,
                () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION),
                        64,
                        32).apply(surrenderedMeshTransformer));
        context.registerLayerDefinition(ModelLayerLocations.SURRENDERED_OUTER_ARMOR,
                () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(LayerDefinitions.OUTER_ARMOR_DEFORMATION),
                        64,
                        32).apply(surrenderedMeshTransformer));
        context.registerLayerDefinition(ModelLayerLocations.PROVOKER, illagerLayerDefinition);
        context.registerLayerDefinition(ModelLayerLocations.INVOKER,
                () -> InvokerModel.createBodyLayer(CubeDeformation.NONE).apply(illagerMeshTransformer));
        context.registerLayerDefinition(ModelLayerLocations.INVOKER_ARMOR,
                () -> InvokerModel.createBodyLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION)
                        .apply(illagerMeshTransformer));
        context.registerLayerDefinition(ModelLayerLocations.NECROMANCER,
                () -> NecromancerRenderer.createBodyLayer(CubeDeformation.NONE).apply(illagerMeshTransformer));
        context.registerLayerDefinition(ModelLayerLocations.NECROMANCER_ARMOR,
                () -> NecromancerRenderer.createBodyLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION)
                        .apply(illagerMeshTransformer));
        context.registerLayerDefinition(ModelLayerLocations.INVOKER_FANGS, InvokerFangsModel::createBodyLayer);
        context.registerLayerDefinition(ModelLayerLocations.INQUISITOR,
                () -> InquisitorModel.createBodyLayer().apply(MeshTransformer.scaling(1.1F)));
        context.registerLayerDefinition(ModelLayerLocations.SORCERER,
                () -> SorcererRenderer.createBodyLayer().apply(illagerMeshTransformer));
        context.registerLayerDefinition(ModelLayerLocations.FIRECALLER,
                () -> FirecallerModel.createBodyLayer().apply(illagerMeshTransformer));
        context.registerLayerDefinition(ModelLayerLocations.ALCHEMIST,
                () -> AlchemistRenderer.createBodyLayer().apply(illagerMeshTransformer));
        context.registerLayerDefinition(ModelLayerLocations.BASHER, illagerLayerDefinition);
        context.registerLayerDefinition(ModelLayerLocations.MARAUDER, illagerLayerDefinition);
        context.registerLayerDefinition(ModelLayerLocations.SKULL_BOLT, WitherSkullRenderer::createSkullLayer);
    }

    @Override
    public void onRegisterBlockRenderTypes(RenderTypesContext<Block> context) {
        context.registerRenderType(RenderType.cutout(), ModRegistry.MAGIC_FIRE_BLOCK.value());
    }
}
