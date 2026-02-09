package fuzs.illagerinvasion.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import fuzs.illagerinvasion.util.WoodlandMansionPieceHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.structures.WoodlandMansionPieces;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.List;

@Mixin(TemplateStructurePiece.class)
abstract class TemplateStructurePieceMixin extends StructurePiece {
    @Shadow
    protected StructurePlaceSettings placeSettings;
    @Shadow
    protected BlockPos templatePosition;

    protected TemplateStructurePieceMixin(StructurePieceType type, int genDepth, BoundingBox boundingBox) {
        super(type, genDepth, boundingBox);
    }

    @ModifyExpressionValue(method = "postProcess",
                           at = @At(value = "INVOKE",
                                    target = "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate;filterBlocks(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructurePlaceSettings;Lnet/minecraft/world/level/block/Block;)Ljava/util/List;"),
                           slice = @Slice(to = @At(value = "FIELD",
                                                   target = "Lnet/minecraft/world/level/block/state/properties/StructureMode;DATA:Lnet/minecraft/world/level/block/state/properties/StructureMode;")))
    public List<StructureTemplate.StructureBlockInfo> postProcess(List<StructureTemplate.StructureBlockInfo> structureBlockInfos, @Local(
            argsOnly = true) WorldGenLevel level, @Local(argsOnly = true) BlockPos pos) {
        if (WoodlandMansionPieces.WoodlandMansionPiece.class.isInstance(this)) {
            return WoodlandMansionPieceHelper.finalizeBlockInfos(structureBlockInfos,
                    level,
                    pos,
                    this.makeTemplateLocation(),
                    this.templatePosition,
                    this.placeSettings);
        } else {
            return structureBlockInfos;
        }
    }

    @Shadow
    protected abstract Identifier makeTemplateLocation();
}
