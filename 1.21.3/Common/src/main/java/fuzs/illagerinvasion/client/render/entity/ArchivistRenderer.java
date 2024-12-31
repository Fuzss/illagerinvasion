package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.init.ModelLayerLocations;
import fuzs.illagerinvasion.client.render.entity.state.SpellcasterIllagerRenderState;
import fuzs.illagerinvasion.world.entity.monster.Archivist;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;

public class ArchivistRenderer extends IllagerRenderer<Archivist, SpellcasterIllagerRenderState> {
    private static final ResourceLocation TEXTURE_LOCATION = IllagerInvasion.id("textures/entity/archivist.png");
    private static final Material BOOK_LOCATION = new Material(InventoryMenu.BLOCK_ATLAS,
            ResourceLocationHelper.withDefaultNamespace("entity/enchanting_table_book"));

    public ArchivistRenderer(EntityRendererProvider.Context context) {
        super(context, new IllagerModel<>(context.bakeLayer(ModelLayerLocations.ARCHIVIST)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemRenderer()) {
            private final BookModel book = new BookModel(context.bakeLayer(ModelLayerLocations.ARCHIVIST_BOOK));

            @Override
            public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, SpellcasterIllagerRenderState renderState, float yRot, float xRot) {
                if (renderState.isCastingSpell) {
                    super.render(poseStack, bufferSource, packedLight, renderState, yRot, xRot);
                }
                poseStack.pushPose();
                poseStack.translate(0.0F, 0.362F, -0.5F);
                poseStack.mulPose(Axis.YP.rotationDegrees(270.0F));
                if (renderState.isCastingSpell) {
                    poseStack.translate(-0.4F, 0.0F, 0.0F);
                    this.book.setupAnim(0.0F, 10.0F + Mth.cos(renderState.ageInTicks) * 0.55F, 0.0F, 1.05F);
                    poseStack.mulPose(Axis.ZN.rotationDegrees(30.0F));
                } else {
                    this.book.setupAnim(0.0F, 0.0F, 0.0F, 0.0f);
                }
                VertexConsumer vertexConsumer = BOOK_LOCATION.buffer(bufferSource, RenderType::entitySolid);
                this.book.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
                poseStack.popPose();
            }
        });
        this.model.getHat().visible = true;
    }

    @Override
    public SpellcasterIllagerRenderState createRenderState() {
        return new SpellcasterIllagerRenderState();
    }

    @Override
    public void extractRenderState(Archivist entity, SpellcasterIllagerRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.isCastingSpell = entity.isCastingSpell();
    }

    @Override
    public ResourceLocation getTextureLocation(SpellcasterIllagerRenderState renderState) {
        return TEXTURE_LOCATION;
    }
}
