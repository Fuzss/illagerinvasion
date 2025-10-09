package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.model.CustomIllagerModel;
import fuzs.illagerinvasion.client.model.geom.ModModelLayers;
import fuzs.illagerinvasion.client.render.entity.state.ArchivistRenderState;
import fuzs.illagerinvasion.world.entity.monster.Archivist;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ArchivistRenderer extends IllagerRenderer<Archivist, ArchivistRenderState> {
    private static final ResourceLocation TEXTURE_LOCATION = IllagerInvasion.id("textures/entity/archivist.png");
    private static final Material BOOK_LOCATION = new Material(TextureAtlas.LOCATION_BLOCKS,
            ResourceLocationHelper.withDefaultNamespace("entity/enchanting_table_book"));

    private final MaterialSet materials;

    public ArchivistRenderer(EntityRendererProvider.Context context) {
        super(context, new CustomIllagerModel<>(context.bakeLayer(ModModelLayers.ARCHIVIST)), 0.5F);
        this.materials = context.getMaterials();
        this.addLayer(new ItemInHandLayer<>(this) {
            private final BookModel book = new BookModel(context.bakeLayer(ModModelLayers.ARCHIVIST_BOOK));

            @Override
            public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, ArchivistRenderState renderState, float yRot, float xRot) {
                if (renderState.isCastingSpell) {
                    super.submit(poseStack, nodeCollector, packedLight, renderState, yRot, xRot);
                }

                poseStack.pushPose();
                poseStack.translate(0.0F, 0.362F, -0.5F);
                poseStack.mulPose(Axis.YP.rotationDegrees(270.0F));
                if (renderState.isCastingSpell) {
                    poseStack.translate(-0.4F, 0.0F, 0.0F);
                    poseStack.mulPose(Axis.ZN.rotationDegrees(30.0F));
                }

                nodeCollector.submitModel(this.book,
                        renderState.book,
                        poseStack,
                        BOOK_LOCATION.renderType(RenderType::entitySolid),
                        renderState.lightCoords,
                        OverlayTexture.NO_OVERLAY,
                        -1,
                        ArchivistRenderer.this.materials.get(BOOK_LOCATION),
                        renderState.outlineColor,
                        null);
                poseStack.popPose();
            }
        });
        this.model.getHat().visible = true;
    }

    @Override
    public ArchivistRenderState createRenderState() {
        return new ArchivistRenderState();
    }

    @Override
    public void extractRenderState(Archivist entity, ArchivistRenderState renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);
        renderState.isCastingSpell = entity.isCastingSpell();
        if (renderState.isCastingSpell) {
            renderState.book = new BookModel.State(0.0F, 10.0F + Mth.cos(renderState.ageInTicks) * 0.55F, 0.0F, 1.05F);
        } else {
            renderState.book = new BookModel.State(0.0F, 0.0F, 0.0F, 0.0F);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(ArchivistRenderState renderState) {
        return TEXTURE_LOCATION;
    }
}
