package fuzs.illagerinvasion.data.loot;

import fuzs.illagerinvasion.init.ModEntityTypes;
import fuzs.illagerinvasion.init.ModItems;
import fuzs.puzzleslib.api.data.v2.AbstractLootProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.RaiderPredicate;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetOminousBottleAmplifierFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class ModEntityTypeLootProvider extends AbstractLootProvider.EntityTypes {

    public ModEntityTypeLootProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addLootTables() {
        this.add(ModEntityTypes.ALCHEMIST_ENTITY_TYPE.value(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.EMERALD)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries(),
                                                UniformGenerator.between(0.0F, 1.0F)))
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer())))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.GUNPOWDER)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries(),
                                                UniformGenerator.between(0.0F, 1.0F)))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.GLASS_BOTTLE)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries(),
                                                UniformGenerator.between(0.0F, 1.0F))))));
        this.add(ModEntityTypes.ARCHIVIST_ENTITY_TYPE.value(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.EMERALD)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries(),
                                                UniformGenerator.between(0.0F, 1.0F)))
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer())))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.BOOK)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries(),
                                                UniformGenerator.between(0.0F, 1.0F)))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.PAPER)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries(),
                                                UniformGenerator.between(0.0F, 1.0F))))));
        this.add(ModEntityTypes.BASHER_ENTITY_TYPE.value(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.EMERALD)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries(),
                                                UniformGenerator.between(0.0F, 1.0F)))
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer())))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 7.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries(),
                                                UniformGenerator.between(0.0F, 2.0F))))));
        this.add(ModEntityTypes.FIRECALLER_ENTITY_TYPE.value(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.EMERALD)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries(),
                                                UniformGenerator.between(0.0F, 1.0F)))
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer())))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.FIRE_CHARGE)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries(),
                                                UniformGenerator.between(0.0F, 1.0F))))));
        this.add(ModEntityTypes.INQUISITOR_ENTITY_TYPE.value(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.EMERALD)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries(),
                                                UniformGenerator.between(0.0F, 1.0F)))
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer())))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.IRON_INGOT)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries(),
                                                UniformGenerator.between(0.0F, 2.0F)))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.LEATHER)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries(),
                                                UniformGenerator.between(0.0F, 2.0F)))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.PLATINUM_CHUNK_ITEM.value())
                                        .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(
                                                this.registries(),
                                                0.5F,
                                                0.0625F))
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer()))));
        this.add(ModEntityTypes.INVOKER_ENTITY_TYPE.value(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.EMERALD)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries(),
                                                UniformGenerator.between(0.0F, 2.0F)))
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer())))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.PRIMAL_ESSENCE_ITEM.value())
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer()))));
        this.add(ModEntityTypes.MARAUDER_ENTITY_TYPE.value(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.EMERALD)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries(),
                                                UniformGenerator.between(0.0F, 1.0F)))
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer())))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.OMINOUS_BOTTLE)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                        .apply(SetOminousBottleAmplifierFunction.setAmplifier(UniformGenerator.between(
                                                0.0F,
                                                4.0F))))
                                .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                                        EntityPredicate.Builder.entity()
                                                .subPredicate(RaiderPredicate.CAPTAIN_WITHOUT_RAID)))));
        this.add(ModEntityTypes.NECROMANCER_ENTITY_TYPE.value(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.EMERALD)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries(),
                                                UniformGenerator.between(0.0F, 1.0F)))
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer())))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Blocks.SKELETON_SKULL)
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                        .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(
                                                this.registries(),
                                                0.025F,
                                                0.01F))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Blocks.ZOMBIE_HEAD)
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                        .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(
                                                this.registries(),
                                                0.025F,
                                                0.01F)))));
        this.add(ModEntityTypes.PROVOKER_ENTITY_TYPE.value(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.EMERALD)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries(),
                                                UniformGenerator.between(0.0F, 1.0F)))
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer())))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.ARROW)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries(),
                                                UniformGenerator.between(0.0F, 1.0F))))));
        this.add(ModEntityTypes.SORCERER_ENTITY_TYPE.value(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.EMERALD)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries(),
                                                UniformGenerator.between(0.0F, 1.0F)))
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer())))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.BOOK)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries(),
                                                UniformGenerator.between(0.0F, 1.0F)))))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.UNUSUAL_DUST_ITEM.value())
                                        .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(
                                                this.registries(),
                                                0.4F,
                                                0.2F))
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer()))));
        this.add(ModEntityTypes.SURRENDERED_ENTITY_TYPE.value(), LootTable.lootTable());
    }
}
