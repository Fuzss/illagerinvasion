package fuzs.illagerinvasion.init;

import fuzs.illagerinvasion.world.item.*;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ModItems {
    public static final Holder.Reference<Item> IMBUING_TABLE_ITEM = ModRegistry.REGISTRIES.registerBlockItem(ModRegistry.IMBUING_TABLE_BLOCK);
    public static final Holder.Reference<Item> UNUSUAL_DUST_ITEM = ModRegistry.REGISTRIES.registerItem("unusual_dust");
    public static final Holder.Reference<Item> MAGICAL_FIRE_CHARGE_ITEM = ModRegistry.REGISTRIES.registerItem(
            "magical_fire_charge",
            MagicalFireChargeItem::new,
            Item.Properties::new);
    public static final Holder.Reference<Item> ILLUSIONARY_DUST_ITEM = ModRegistry.REGISTRIES.registerItem(
            "illusionary_dust",
            IllusionaryDustItem::new,
            Item.Properties::new);
    public static final Holder.Reference<Item> LOST_CANDLE_ITEM = ModRegistry.REGISTRIES.registerItem("lost_candle",
            LostCandleItem::new,
            Item.Properties::new);
    public static final Holder.Reference<Item> HORN_OF_SIGHT_ITEM = ModRegistry.REGISTRIES.registerItem("horn_of_sight",
            (Item.Properties properties) -> new HornOfSightItem(ModRegistry.HORN_OF_SIGHT_INSTRUMENT_TAG, properties),
            () -> new Item.Properties().stacksTo(1));
    public static final Holder.Reference<Item> HALLOWED_GEM_ITEM = ModRegistry.REGISTRIES.registerItem("hallowed_gem",
            () -> new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final Holder.Reference<Item> PRIMAL_ESSENCE_ITEM = ModRegistry.REGISTRIES.registerItem(
            "primal_essence",
            () -> new Item.Properties().rarity(Rarity.RARE));
    public static final Holder.Reference<Item> PLATINUM_CHUNK_ITEM = ModRegistry.REGISTRIES.registerItem(
            "platinum_chunk");
    public static final Holder.Reference<Item> PLATINUM_SHEET_ITEM = ModRegistry.REGISTRIES.registerItem(
            "platinum_sheet");
    public static final Holder.Reference<Item> PLATINUM_INFUSED_HATCHET_ITEM = ModRegistry.REGISTRIES.registerItem(
            "platinum_infused_hatchet",
            HatchetItem::new,
            () -> new Item.Properties().rarity(Rarity.EPIC)
                    .durability(327)
                    .attributes(HatchetItem.createAttributes())
                    .component(DataComponents.TOOL, HatchetItem.createToolProperties())
                    .enchantable(1)
                    .repairable(ModItems.PLATINUM_SHEET_ITEM.value()));
    public static final Holder.Reference<Item> PROVOKER_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES.registerSpawnEggItem(
            ModEntityTypes.PROVOKER_ENTITY_TYPE,
            9541270,
            9399876);
    public static final Holder.Reference<Item> BASHER_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES.registerSpawnEggItem(
            ModEntityTypes.BASHER_ENTITY_TYPE,
            9541270,
            5985087);
    public static final Holder.Reference<Item> SORCERER_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES.registerSpawnEggItem(
            ModEntityTypes.SORCERER_ENTITY_TYPE,
            9541270,
            10899592);
    public static final Holder.Reference<Item> ARCHIVIST_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES.registerSpawnEggItem(
            ModEntityTypes.ARCHIVIST_ENTITY_TYPE,
            9541270,
            13251893);
    public static final Holder.Reference<Item> INQUISITOR_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES.registerSpawnEggItem(
            ModEntityTypes.INQUISITOR_ENTITY_TYPE,
            9541270,
            4934471);
    public static final Holder.Reference<Item> MARAUDER_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES.registerSpawnEggItem(
            ModEntityTypes.MARAUDER_ENTITY_TYPE,
            9541270,
            5441030);
    public static final Holder.Reference<Item> INVOKER_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES.registerSpawnEggItem(
            ModEntityTypes.INVOKER_ENTITY_TYPE,
            9541270,
            13552007);
    public static final Holder.Reference<Item> ALCHEMIST_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES.registerSpawnEggItem(
            ModEntityTypes.ALCHEMIST_ENTITY_TYPE,
            9541270,
            7550099);
    public static final Holder.Reference<Item> FIRECALLER_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES.registerSpawnEggItem(
            ModEntityTypes.FIRECALLER_ENTITY_TYPE,
            9541270,
            14185784);
    public static final Holder.Reference<Item> NECROMANCER_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES.registerSpawnEggItem(
            ModEntityTypes.NECROMANCER_ENTITY_TYPE,
            9541270,
            9585210);
    public static final Holder.Reference<Item> SURRENDERED_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES.registerSpawnEggItem(
            ModEntityTypes.SURRENDERED_ENTITY_TYPE,
            11260369,
            11858160);
    public static final Holder.Reference<Item> ILLUSIONER_SPAWN_EGG_ITEM = ModRegistry.REGISTRIES.registerSpawnEggItem((Holder<? extends EntityType<? extends Mob>>) (Holder<?>) EntityType.ILLUSIONER.builtInRegistryHolder(),
            1267859,
            9541270);

    public static void bootstrap() {
        // NO-OP
    }
}
