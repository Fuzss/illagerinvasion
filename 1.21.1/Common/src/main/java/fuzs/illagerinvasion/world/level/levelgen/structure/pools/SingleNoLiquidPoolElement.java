package fuzs.illagerinvasion.world.level.levelgen.structure.pools;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fuzs.illagerinvasion.init.ModRegistry;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Optional;

public class SingleNoLiquidPoolElement extends SinglePoolElement {
    public static final MapCodec<SinglePoolElement> CODEC = RecordCodecBuilder.mapCodec(instance -> {
        return instance.group(templateCodec(), processorsCodec(), projectionCodec(), overrideLiquidSettingsCodec())
                .apply(instance, SingleNoLiquidPoolElement::new);
    });

    public SingleNoLiquidPoolElement(Either<ResourceLocation, StructureTemplate> template, Holder<StructureProcessorList> processors, StructureTemplatePool.Projection projection, Optional<LiquidSettings> overrideLiquidSettings) {
        super(template, processors, projection, overrideLiquidSettings);
    }

    @Override
    protected StructurePlaceSettings getSettings(Rotation rotation, BoundingBox boundingBox, LiquidSettings liquidSettings, boolean offset) {
        return super.getSettings(rotation, boundingBox, liquidSettings, offset).setLiquidSettings(LiquidSettings.IGNORE_WATERLOGGING);
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
