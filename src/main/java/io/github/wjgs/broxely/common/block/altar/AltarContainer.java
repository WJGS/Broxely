package io.github.wjgs.broxely.common.block.altar;

import io.github.wjgs.broxely.common.init.BroxelyBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;

import static io.github.wjgs.broxely.common.init.BroxelyContainers.ALTAR_CONTAINER;

public class AltarContainer extends Container {

    private final TileEntity tileEntity;
    private final PlayerEntity player;
    private final IItemHandler inventory;

    public AltarContainer(int id, World world, BlockPos pos, PlayerEntity player, PlayerInventory inv) {
        super(ALTAR_CONTAINER, id);
        tileEntity = world.getTileEntity(pos);

        this.player = player;
        this.inventory = new InvWrapper(inv);

        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            addSlot(new SlotItemHandler(h, 0, 82, 8)); // North
            addSlot(new SlotItemHandler(h, 1, 136, 36)); // East
            addSlot(new SlotItemHandler(h, 2, 82, 64)); // South
            addSlot(new SlotItemHandler(h, 3, 28, 36)); // West

            addSlot(new SlotItemHandler(h, 4, 82, 36) {
                @Override
                public boolean isItemValid(@Nonnull ItemStack stack) {
                    return false;
                }
            });
        });
        layoutPlayerInventory();

    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), player, BroxelyBlocks.ALTAR);
    }

    private int addSlotRow(IItemHandler handler, int index, int y) {
        int x = 10;
        for (int i = 0; i < 9; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += 18;
            index++;
        }
        return index;
    }

    private void addSlotBox(IItemHandler handler) {
        int index = 9;
        int y = 87;
        for (int i = 0; i < 3; i++) {
            index = addSlotRow(handler, index, y);
            y += 18;
        }
    }

    private void layoutPlayerInventory() {
        addSlotBox(inventory);

        addSlotRow(inventory, 0, 145);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack clickedStack = slot.getStack();
            stack = clickedStack.copy();

            if (index < 5) {  // Altar click
                if (!this.mergeItemStack(clickedStack, 5, 41, true))
                    return ItemStack.EMPTY;
            }
            else {  // Inventory click
                if (!this.mergeItemStack(clickedStack, 0, 4, false))
                    return ItemStack.EMPTY;
            }

            if (clickedStack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return stack;
    }
}
