package io.github.wjgs.broxely.common.utils;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;


/**
 * An interface for just one slot of IItemHandler.
 */
public class OneSlotItemHandler implements IItemHandler {
    private boolean allowInsertion = true;
    private boolean allowExtraction = true;

    private final int slot;
    private final IItemHandler mainHandler;

    /**
     * @param slot The slot OneSlotItemHandler is linked to.
     * @param mainHandler Handler that will be notified about item insertion/extraction.
     */
    public OneSlotItemHandler(int slot, IItemHandler mainHandler) {
        this.slot = slot;
        this.mainHandler = mainHandler;
    }

    public void disableInsertion() { allowInsertion = false; }

    public void disableExtraction() { allowExtraction = false; }

    /**
     * Makes no sense since it always returns 1.
     */
    @Override
    @Deprecated
    public int getSlots() {
        return 1;
    }

    /**
     * Makes no sense, use getStack().
     */
    @Nonnull
    @Override
    @Deprecated
    public ItemStack getStackInSlot(int slot) {
        return mainHandler.getStackInSlot(this.slot);
    }

    @Nonnull
    public ItemStack getStack() {
        return mainHandler.getStackInSlot(this.slot);
    }

    /**
     * Inserts item into the slot specified in constructor if allowed. Otherwise, returns stack.
     * @param slot Any number, it is left for compatibility.
     * @param stack ItemStack inserted.
     * @param simulate If true, the insertion is only simulated.
     * @return The remaining ItemStack that was not inserted.
     */
    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (!allowInsertion) return stack;

        return mainHandler.insertItem(this.slot, stack, simulate);
    }

    /**
     * Extracts items from the slot specified in constructor if allowed. Otherwise, returns ItemStack.EMPTY.
     * @param slot Any number, it is left for compatibility.
     * @param amount The amount of items extracted.
     * @param simulate If true, the extraction is only simulated.
     * @return ItemStack extracted from the slot.
     */
    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (!allowExtraction) return ItemStack.EMPTY;

        return mainHandler.extractItem(this.slot, amount, simulate);
    }

    /**
     * Makes no sense since it always returns 1.
     */
    @Override
    public int getSlotLimit(int slot) {
        return mainHandler.getSlotLimit(this.slot);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return mainHandler.isItemValid(this.slot, stack);
    }
}
