package com.mzuha.machinix.block.container;

import net.minecraft.inventory.container.ContainerType;
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

    public static void register(IEventBus eventBus) {
        CONTAINERS.register(eventBus);
    }

    public static final RegistryObject<ContainerType<MaceratorContainer>> MACERATOR_CONTAINER = CONTAINERS.register(
        "macerator_container",
        () -> IForgeContainerType.create(MaceratorContainer::new)
    );
}
