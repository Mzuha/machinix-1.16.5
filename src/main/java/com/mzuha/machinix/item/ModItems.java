package com.mzuha.machinix.item;

import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.mzuha.machinix.MachinixMod.MOD_ID;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
        ForgeRegistries.ITEMS, MOD_ID
    );

    public static final RegistryObject<Item> URANIUM = ITEMS.register(
        "uranium",
        () -> new Item(
            new Item.Properties().group(ModItemGroup.MOD_ITEM_GROUP)
        )
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
