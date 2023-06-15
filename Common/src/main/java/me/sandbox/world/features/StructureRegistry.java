package me.sandbox.world.features;

import fuzs.illagerinvasion.IllagerInvasion;
import me.sandbox.config.IllagerExpansionConfig;
import fuzs.illagerinvasion.mixin.ConfiguredStructureFeaturesAccessor;
import fuzs.illagerinvasion.mixin.StructureFeatureAccessor;
import me.sandbox.world.features.structurefeatures.*;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.StructureSets;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public class StructureRegistry<C extends FeatureConfiguration> {
    public static Holder<ConfiguredStructureFeature<?,?>> CONFIGURED_LABYRINTH;
    public static Holder<StructureSet> LABYRINTHS;
    public static StructureFeature<?> ILLUSIONER_TOWER = new IllusionerTowerFeature();
    public static StructureFeature<?> SORCERER_HUT = new SorcererHutFeature();
    public static StructureFeature<?> ILLAGER_FORT = new IllagerFortFeature(JigsawConfiguration.CODEC);
    public static StructureFeature<JigsawConfiguration> LABYRINTH = new LabyrinthFeature(JigsawConfiguration.CODEC);
    static {

    }

    public static final ResourceKey<ConfiguredStructureFeature<?, ?>> LABYRINTH_KEY = StructureRegistry.of("labyrinth");

    public static final ResourceKey<StructureSet> LABYRINTH_STRUCTURE_SET_KEY = StructureRegistry.setof("labyrinth");

    public static void registerStructureFeatures() {
        StructureFeatureAccessor.callRegister(IllagerInvasion.MOD_ID + ":illusioner_tower", ILLUSIONER_TOWER, GenerationStep.Decoration.SURFACE_STRUCTURES);
        StructureFeatureAccessor.callRegister(IllagerInvasion.MOD_ID + ":illager_fort", ILLAGER_FORT, GenerationStep.Decoration.SURFACE_STRUCTURES);
        StructureFeatureAccessor.callRegister(IllagerInvasion.MOD_ID + ":sorcerer_hut", SORCERER_HUT, GenerationStep.Decoration.SURFACE_STRUCTURES);
        StructureFeatureAccessor.callRegister(IllagerInvasion.MOD_ID + ":labyrinth", LABYRINTH, GenerationStep.Decoration.SURFACE_STRUCTURES);

        LabyrinthGenerator.init();
    }
    public static void registerConfiguredStructureFeatures() {
        CONFIGURED_LABYRINTH = ConfiguredStructureFeaturesAccessor.callRegister(StructureRegistry.LABYRINTH_KEY, StructureRegistry.LABYRINTH.configured(new JigsawConfiguration(LabyrinthGenerator.STRUCTURE_POOLS, 7), BiomeTags.HAS_WOODLAND_MANSION));
    }
    public static void registerStructureSets() {
        LABYRINTHS = StructureSets.register(StructureRegistry.LABYRINTH_STRUCTURE_SET_KEY, StructureRegistry.CONFIGURED_LABYRINTH, new RandomSpreadStructurePlacement(48, 40, RandomSpreadType.LINEAR, 1687452161));
    }
    private static ResourceKey<ConfiguredStructureFeature<?, ?>> of(String id) {
        return ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation(IllagerInvasion.MOD_ID, id));
    }
    private static ResourceKey<StructureSet> setof(String id) {
        return ResourceKey.create(Registry.STRUCTURE_SET_REGISTRY, new ResourceLocation(IllagerInvasion.MOD_ID, id));
    }
}
