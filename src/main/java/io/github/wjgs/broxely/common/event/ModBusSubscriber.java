package io.github.wjgs.broxely.common.event;

import io.github.wjgs.broxely.Broxely;
import io.github.wjgs.broxely.common.block.altar.AltarContainer;
import io.github.wjgs.broxely.common.entity.BroxelyEntityTypes;
import io.github.wjgs.broxely.common.init.BroxelyBlocks;
import io.github.wjgs.broxely.common.init.BroxelyItems;
import io.github.wjgs.broxely.common.block.altar.AltarTE;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid=Broxely.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
public class ModBusSubscriber {

    @SubscribeEvent
    public static void onBlockRegistry(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(BroxelyBlocks.getBlocks());
    }

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(BroxelyItems.getItems());
    }

    @SubscribeEvent
    public static void onEntityRegistry(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().register(BroxelyEntityTypes.FIREPROOF_ITEM);
    }

    @SubscribeEvent
    public static void onTileEntityRegistry(RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry().register(TileEntityType.Builder.create(AltarTE::new, BroxelyBlocks.ALTAR).build(null).setRegistryName(Broxely.MODID, "altar"));
    }

    @SubscribeEvent
    public static void onContainerRegistry(RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().register(IForgeContainerType.create(((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new AltarContainer(windowId, Minecraft.getInstance().world, pos, Minecraft.getInstance().player, inv);
        })).setRegistryName("altar"));
    }
}
