package fuzs.illagerinvasion.world.level.levelgen.structure.pools;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fuzs.illagerinvasion.init.ModRegistry;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class SingleNoLiquidPoolElement extends SinglePoolElement {
    public static final Codec<SinglePoolElement> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(templateCodec(), processorsCodec(), projectionCodec())
                .apply(instance, SingleNoLiquidPoolElement::new);
    });

    public SingleNoLiquidPoolElement(Either<ResourceLocation, StructureTemplate> either, Holder<StructureProcessorList> holder, StructureTemplatePool.Projection projection) {
        super(either, holder, projection);
    }

    @Override
    protected StructurePlaceSettings getSettings(Rotation rotation, BoundingBox boundingBox, boolean bl) {
        return super.getSettings(rotation, boundingBox, bl).setKeepLiquids(false);
    }

    @Override
    public StructurePoolElementType<?> getType() {
        return ModRegistry.SINGLE_POOL_ELEMENT_TYPE.value();
    }

    @Override
    public String toString() {
        return "SingleNoLiquid[" + this.template + "]";
    }
}
