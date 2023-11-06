package com.mzuha.machinix.block.container;

import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.mzuha.machinix.MachinixMod.MOD_ID;

public class ModContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(
        ForgeRegistries.CONTAINERS, MOD_ID
    );

    public static final RegistryObject<ContainerType<CrusherContainer>> CRUSHER_CONTAINER = CONTAINERS.register(
        "crusher_container",
        () -> IForgeContainerType.create(
            (windowId, inv, data) -> {
                BlockPos blockPos = data.readBlockPos();
                World world = inv.player.world;
                return new CrusherContainer(
                    windowId, world, blockPos, inv, inv.player
                );
            }
        )
    );

    public static void register(IEventBus eventBus) {
        CONTAINERS.register(eventBus);
    }
}
