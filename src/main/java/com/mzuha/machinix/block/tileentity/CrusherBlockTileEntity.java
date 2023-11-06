package com.mzuha.machinix.block.tileentity;

import com.mzuha.machinix.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CrusherBlockTileEntity extends TileEntity implements ITickableTileEntity {

    public static final int CRUSHER_INPUT_SLOT = 0;
    public static final int CRUSHER_OUTPUT_SLOT = 1;
    public static final int TICKS_FOR_OPERATION = 100;
    private int progress = 0;

    private final ItemStackHandler itemStackHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemStackHandler);

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(2) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return true;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (!isItemValid(slot, stack)) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    public CrusherBlockTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public CrusherBlockTileEntity() {
        this(ModTileEntities.CRUSHER_ENTITY.get());
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        itemStackHandler.deserializeNBT(nbt.getCompound("crusher.inv"));
        progress = nbt.getInt("crusher.progress");
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("crusher.inv", itemStackHandler.serializeNBT());
        compound.putInt("crusher.progress", progress);
        return super.write(compound);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void tick() {
        if (world.isRemote) return;
        if (isOutputSlotEmptyOrNotFull()) {
            if (hasRecipe()) {
                progress++;
                if (hasRecipeFinished()) {
                    craftItem();
                    markDirty();
                    resetProgress();
                }
            } else {
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    public int getProgress() {
        return progress;
    }

    private void craftItem() {
        itemStackHandler.extractItem(CRUSHER_INPUT_SLOT, 1, false);
        itemStackHandler.insertItem(CRUSHER_OUTPUT_SLOT, new ItemStack(ModBlocks.CRUSHER.get(), 2), false);
    }

    private void resetProgress() {
        progress = 0;
    }

    private boolean hasRecipeFinished() {
        return progress == TICKS_FOR_OPERATION;
    }

    private boolean hasRecipe() {
        return itemStackHandler.getStackInSlot(CRUSHER_INPUT_SLOT).getItem() == ModBlocks.CRUSHER.get().asItem();
    }

    private boolean isOutputSlotEmptyOrNotFull() {
        return itemStackHandler.getStackInSlot(CRUSHER_OUTPUT_SLOT).isEmpty()
            || itemStackHandler.getStackInSlot(CRUSHER_OUTPUT_SLOT).getCount() + 2 <= itemStackHandler.getSlotLimit(CRUSHER_OUTPUT_SLOT);
    }
}
