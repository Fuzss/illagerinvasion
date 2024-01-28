package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.world.entity.monster.Archivist;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
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

public class ArchivistRender extends IllagerRenderer<Archivist> {
    private static final ResourceLocation ARCHIVIST_LOCATION = IllagerInvasion.id("textures/entity/archivist.png");
    private static final Material BOOK_LOCATION = new Material(InventoryMenu.BLOCK_ATLAS, new ResourceLocation("entity/enchanting_table_book"));

    private final BookModel book;

    public ArchivistRender(EntityRendererProvider.Context context) {
        super(context, new IllagerModel<>(context.bakeLayer(ModelLayers.EVOKER)), 0.5f);
        this.book = new BookModel(context.bakeLayer(ModelLayers.BOOK));
        this.addLayer(new ItemInHandLayer<>(this) {
            @Override
            public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, Archivist Archivist, float f, float g, float h, float j, float k, float l) {
                if (Archivist.isCastingSpell()) {
                    super.render(matrixStack, vertexConsumerProvider, i, Archivist, f, g, h, j, k, l);
                }
                matrixStack.pushPose();
                matrixStack.translate(0.0D, 0.362D, -0.5f);
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(270.0f));
                if (Archivist.isCastingSpell()) {
                    matrixStack.translate(-0.4D, 0.0, 0.0f);
                    ArchivistRender.this.book.setupAnim(0.0f, ((float) 10 + Mth.cos((float) Archivist.tickCount) * 0.55f), 0.0f, 1.05f);
                    matrixStack.mulPose(Vector3f.ZN.rotationDegrees(30.0f));
                } else {
                    ArchivistRender.this.book.setupAnim(0.0F, 0.0F, 0.0F, 0.0f);
                }
                VertexConsumer vertexConsumer = BOOK_LOCATION.buffer(vertexConsumerProvider, RenderType::entitySolid);
                ArchivistRender.this.book.renderToBuffer(matrixStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
                matrixStack.popPose();
            }
        });
        this.model.getHat().visible = true;
    }

    @Override
    public ResourceLocation getTextureLocation(Archivist entity) {
        return ARCHIVIST_LOCATION;
    }
}
