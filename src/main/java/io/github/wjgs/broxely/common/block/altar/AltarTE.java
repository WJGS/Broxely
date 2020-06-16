package io.github.wjgs.broxely.common.block.altar;

import io.github.wjgs.broxely.common.init.BroxelyTileEntities;
import io.github.wjgs.broxely.common.utils.OneSlotItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class AltarTE extends TileEntity implements INamedContainerProvider {

    private final LazyOptional<IItemHandler> guiHandler = LazyOptional.of(this::createGuiHandler);
    private final LazyOptional<IItemHandler> northHandler = LazyOptional.of(() -> createSideAutomationHandler(0));
    private final LazyOptional<IItemHandler> eastHandler = LazyOptional.of(() -> createSideAutomationHandler(1));
    private final LazyOptional<IItemHandler> southHandler = LazyOptional.of(() -> createSideAutomationHandler(2));
    private final LazyOptional<IItemHandler> westHandler = LazyOptional.of(() -> createSideAutomationHandler(3));
    private final LazyOptional<IItemHandler> outputHandler = LazyOptional.of(this::createOutputAutomationHandler);

    public AltarTE() {
        super(BroxelyTileEntities.altarTE);
    }

    private IItemHandler createGuiHandler() {
        return new ItemStackHandler(5) {
            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (slot == 4) return ItemStack.EMPTY;
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    private IItemHandler createSideAutomationHandler(int slot) {
        return new OneSlotItemHandler(slot,
                guiHandler.orElseThrow(() -> new IllegalStateException("Somehow, Lava Infusion Altar is not initialized properly.")));
    }

    private IItemHandler createOutputAutomationHandler() {
        OneSlotItemHandler handler = new OneSlotItemHandler(4,
                guiHandler.orElseThrow(() -> new IllegalStateException("Somehow, Lava Infusion Altar is not initialized properly.")));

        handler.disableInsertion();
        return handler;
    }

    @Override
    public void read(CompoundNBT compound) {
        CompoundNBT tag = compound.getCompound("inv");
        guiHandler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(tag));
        super.read(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        guiHandler.ifPresent(h -> {
            CompoundNBT tag = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            tag.put("inv", compound);
        });
        return super.write(compound);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == null) {
                return guiHandler.cast();
            }
            switch (side) {
                case DOWN:
                case UP:
                    return outputHandler.cast();
                case NORTH:
                    return northHandler.cast();
                case SOUTH:
                    return southHandler.cast();
                case WEST:
                    return westHandler.cast();
                case EAST:
                    return eastHandler.cast();
            }

        }
        return super.getCapability(cap, side);
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("Lava Infusion Altar");
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity player) {
        return new AltarContainer(i, world, pos, player, playerInventory);
    }


}
