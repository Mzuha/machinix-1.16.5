package com.mzuha.machinix.datagen;

import com.mzuha.machinix.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import static com.mzuha.machinix.MachinixMod.MOD_ID;

public class ModBlockStatesProvider extends BlockStateProvider {
    public ModBlockStatesProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.CRUSHER);
    }

    private void blockWithItem(RegistryObject<Block> block) {
        simpleBlock(block.get(), cubeAll(block.get()));
        simpleBlockItem(block.get(), cubeAll(block.get()));
    }
}
