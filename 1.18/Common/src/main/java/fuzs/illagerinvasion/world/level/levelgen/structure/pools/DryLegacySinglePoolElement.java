package fuzs.illagerinvasion.world.level.levelgen.structure.pools;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class DryLegacySinglePoolElement extends LegacySinglePoolElement {
    public static final Codec<LegacySinglePoolElement> CODEC = RecordCodecBuilder.create((p_210429_) -> {
        return p_210429_.group(templateCodec(), processorsCodec(), projectionCodec()).apply(p_210429_, DryLegacySinglePoolElement::new);
    });

    protected DryLegacySinglePoolElement(Either<ResourceLocation, StructureTemplate> either, Holder<StructureProcessorList> holder, StructureTemplatePool.Projection projection) {
        super(either, holder, projection);
    }

    @Override
    protected StructurePlaceSettings getSettings(Rotation rotation, BoundingBox boundingBox, boolean bl) {
        return super.getSettings(rotation, boundingBox, bl).setKeepLiquids(false);
    }

    @Override
    public StructurePoolElementType<?> getType() {
        return StructurePoolElementType.SINGLE;
    }

    @Override
    public String toString() {
        return "DryLegacySingle[" + this.template + "]";
    }
}
