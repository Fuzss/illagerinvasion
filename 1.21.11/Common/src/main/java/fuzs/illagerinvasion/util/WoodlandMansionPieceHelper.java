package fuzs.illagerinvasion.util;

import com.google.common.collect.ImmutableMap;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.init.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WoodlandMansionPieceHelper {
    public static final String ARCHIVIST_DATA_MARKER = IllagerInvasion.id("archivist").toString();
    public static final String BASHER_DATA_MARKER = IllagerInvasion.id("basher").toString();
    public static final String INVOKER_DATA_MARKER = IllagerInvasion.id("invoker").toString();
    public static final String PROVOKER_DATA_MARKER = IllagerInvasion.id("provoker").toString();
    private static final Map<String, Holder<? extends EntityType<? extends Mob>>> DATA_MARKER_TYPES = ImmutableMap.<String, Holder<? extends EntityType<? extends Mob>>>builder()
            .put(ARCHIVIST_DATA_MARKER, ModEntityTypes.ARCHIVIST_ENTITY_TYPE)
            .put(BASHER_DATA_MARKER, ModEntityTypes.BASHER_ENTITY_TYPE)
            .put(INVOKER_DATA_MARKER, ModEntityTypes.INVOKER_ENTITY_TYPE)
            .put(PROVOKER_DATA_MARKER, ModEntityTypes.PROVOKER_ENTITY_TYPE)
            .build();

    /**
     * @see net.minecraft.world.level.levelgen.structure.structures.WoodlandMansionPieces.WoodlandMansionPiece#handleDataMarker(String,
     *         BlockPos, ServerLevelAccessor, RandomSource, BoundingBox)
     */
    public static boolean createIllagerType(String dataMarker, BlockPos blockPos, ServerLevelAccessor level, RandomSource randomSource) {
        EntityType<? extends Mob> entityType = parseEntityType(dataMarker, randomSource);
        if (entityType != null) {
            Mob mob = entityType.create(level.getLevel(), EntitySpawnReason.STRUCTURE);
            if (mob != null) {
                mob.setPersistenceRequired();
                mob.snapTo(blockPos, 0.0F, 0.0F);
                mob.finalizeSpawn(level,
                        level.getCurrentDifficultyAt(mob.blockPosition()),
                        EntitySpawnReason.STRUCTURE,
                        null);
                level.addFreshEntityWithPassengers(mob);
                level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
                return true;
            }
        }

        return false;
    }

    private static @Nullable EntityType<? extends Mob> parseEntityType(String dataMarker, RandomSource randomSource) {
        Holder<? extends EntityType<? extends Mob>> holder = DATA_MARKER_TYPES.get(dataMarker);
        return holder != null ? holder.value() : null;
    }

    /**
     * @see StructureTemplate#processBlockInfos(ServerLevelAccessor, BlockPos, BlockPos, StructurePlaceSettings,
     *         List)
     */
    public static List<StructureTemplate.StructureBlockInfo> finalizeBlockInfos(List<StructureTemplate.StructureBlockInfo> structureBlockInfos, WorldGenLevel level, BlockPos pos, Identifier identifier, BlockPos templatePosition, StructurePlaceSettings placeSettings) {
        return level.registryAccess()
                .lookupOrThrow(Registries.PROCESSOR_LIST)
                .get(identifier)
                .map((Holder.Reference<StructureProcessorList> holder) -> {
                    List<StructureTemplate.StructureBlockInfo> modifiedStructureBlockInfos = new ArrayList<>(
                            structureBlockInfos);
                    for (StructureProcessor structureProcessor : holder.value().list()) {
                        modifiedStructureBlockInfos = structureProcessor.finalizeProcessing(level,
                                templatePosition,
                                pos,
                                structureBlockInfos,
                                modifiedStructureBlockInfos,
                                placeSettings);
                    }

                    return modifiedStructureBlockInfos;
                })
                .orElse(structureBlockInfos);
    }
}
