package com.mzuha.machinix.block.tileentity;

import com.mzuha.machinix.block.ModBlocks;
import com.mzuha.machinix.block.container.MaceratorContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MaceratorBlockTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    public static final int MACERATOR_INPUT_SLOT = 0;
    public static final int MACERATOR_OUTPUT_SLOT = 1;

    private final ItemStackHandler itemStackHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemStackHandler);
    private final IIntArray maceratorData;
    public int maxProgress = 100;
    private int progress = 0;

    public MaceratorBlockTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        this.maceratorData = new IIntArray() {
            @Override
            public int get(int index) {
                switch (index) {
                    case 0:
                        return MaceratorBlockTileEntity.this.progress;
                    case 1:
                        return MaceratorBlockTileEntity.this.maxProgress;

                    default:
                        return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0:
                        MaceratorBlockTileEntity.this.progress = value;
                    case 1:
                        MaceratorBlockTileEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    public MaceratorBlockTileEntity() {
        this(ModTileEntities.MACERATOR_TILE.get());
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(2) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                switch (slot) {
                    case 0:
                    case 1:
                        return stack.getItem() == ModBlocks.MACERATOR_BLOCK.get().asItem();
                }
                return false;
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

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        itemStackHandler.deserializeNBT(nbt.getCompound("macerator.inv"));
        progress = nbt.getInt("macerator.progress");
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("macerator.inv", itemStackHandler.serializeNBT());
        compound.putInt("macerator.progress", progress);
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

    private void craftItem() {
        itemStackHandler.extractItem(MACERATOR_INPUT_SLOT, 1, false);
        itemStackHandler.insertItem(MACERATOR_OUTPUT_SLOT, new ItemStack(ModBlocks.MACERATOR_BLOCK.get(), 2), false);
    }

    private void resetProgress() {
        progress = 0;
    }

    private boolean hasRecipeFinished() {
        return progress == maxProgress;
    }

    private boolean hasRecipe() {
        return itemStackHandler.getStackInSlot(MACERATOR_INPUT_SLOT).getItem() == ModBlocks.MACERATOR_BLOCK.get().asItem();
    }

    private boolean isOutputSlotEmptyOrNotFull() {
        return itemStackHandler.getStackInSlot(MACERATOR_OUTPUT_SLOT).isEmpty()
            || itemStackHandler.getStackInSlot(MACERATOR_OUTPUT_SLOT).getCount() + 2 <= itemStackHandler.getSlotLimit(MACERATOR_OUTPUT_SLOT);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("screen.machinix.macerator");
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new MaceratorContainer(i, playerInventory, this, this.maceratorData);
    }
}
