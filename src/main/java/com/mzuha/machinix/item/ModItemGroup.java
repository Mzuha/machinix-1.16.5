package com.mzuha.machinix.item;

import com.mzuha.machinix.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup {
    public static final ItemGroup MOD_ITEM_GROUP = new ItemGroup("machinixModGroup") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.URANIUM_ORE.get());
        }
    };
}
