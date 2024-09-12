package fuzs.illagerinvasion.data.loot;

import fuzs.illagerinvasion.init.ModItems;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.AbstractLootProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class ModEntityInjectLootProvider extends AbstractLootProvider.Simple {

    public ModEntityInjectLootProvider(DataProviderContext context) {
        super(LootContextParamSets.ENTITY, context);
    }

    @Override
    public void addLootTables() {
        this.add(ModRegistry.ILLUSIONER_INJECT_LOOT_TABLE, LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.EMERALD)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries(), UniformGenerator.between(0.0F, 1.0F)))
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                )
                ).withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.ARROW)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries(), UniformGenerator.between(0.0F, 1.0F)))
                                )
                ).withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.ILLUSIONARY_DUST_ITEM.value()))
                                .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries(), 0.4F, 0.2F))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                )
        );
        this.add(ModRegistry.PILLAGER_INJECT_LOOT_TABLE, LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.PLATINUM_CHUNK_ITEM.value())
                                        .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries(), 0.5F, 0.0625F))
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                )
                ).withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.EMERALD)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries(), UniformGenerator.between(0.0F, 1.0F)))
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                )
                )
        );
        this.add(ModRegistry.RAVAGER_INJECT_LOOT_TABLE, LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.PLATINUM_CHUNK_ITEM.value())
                                        .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries(), 0.5F, 0.0625F))
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                )
                )
        );
    }
}
