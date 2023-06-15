package me.sandbox.item;

import me.sandbox.IllagerExpansion;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroup {

    public static final CreativeModeTab SandBoxMisc = FabricItemGroupBuilder.build(new ResourceLocation(IllagerExpansion.MOD_ID, "sandboxmisc"),
            () -> new ItemStack(ItemRegistry.RAVAGER_HORN));

    public static final CreativeModeTab SandBoxMobs = FabricItemGroupBuilder.build(new ResourceLocation(IllagerExpansion.MOD_ID, "sandboxmobs"),
            () -> new ItemStack(ItemRegistry.PROVOKER_SPAWN_EGG));
}
