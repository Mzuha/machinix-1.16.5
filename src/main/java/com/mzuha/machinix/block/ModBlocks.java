package com.mzuha.machinix.block;

import com.mzuha.machinix.block.custom.MaceratorBlock;
import com.mzuha.machinix.item.ModItemGroup;
import com.mzuha.machinix.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static com.mzuha.machinix.MachinixMod.MOD_ID;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
        ForgeRegistries.BLOCKS, MOD_ID
    );

    public static final RegistryObject<Block> MACERATOR_BLOCK = registerBlock(
        "macerator_block",
        () -> new MaceratorBlock(
            AbstractBlock.Properties.create(Material.IRON)
        )
    );

    public static final RegistryObject<Block> URANIUM_ORE = registerBlock(
        "uranium_ore",
        () -> new Block(
            AbstractBlock.Properties.create(Material.ROCK)
        )
    );

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> blockToReturn = BLOCKS.register(name, block);
        registerBlockItem(name, blockToReturn);
        return blockToReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, Supplier<T> block) {
        ModItems.ITEMS.register(
            name, () -> new BlockItem(
                block.get(), new Item.Properties().group(ModItemGroup.MOD_ITEM_GROUP)
            )
        );
    }
}
