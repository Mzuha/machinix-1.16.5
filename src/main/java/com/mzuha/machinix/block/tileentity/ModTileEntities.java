package com.mzuha.machinix.block.tileentity;

import com.mzuha.machinix.block.ModBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.mzuha.machinix.MachinixMod.MOD_ID;

public class ModTileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(
        ForgeRegistries.TILE_ENTITIES, MOD_ID
    );

    public static final RegistryObject<TileEntityType<CrusherBlockTileEntity>> CRUSHER_ENTITY = TILE_ENTITIES.register(
        "crusher_tile_entity", () -> TileEntityType.Builder.create(
            CrusherBlockTileEntity::new, ModBlocks.CRUSHER.get()
        ).build(null)
    );

    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }
}
