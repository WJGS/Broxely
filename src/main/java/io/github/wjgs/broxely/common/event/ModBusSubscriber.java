package io.github.wjgs.broxely.common.event;

import io.github.wjgs.broxely.Broxely;
import io.github.wjgs.broxely.common.entity.BroxelyEntityTypes;
import io.github.wjgs.broxely.common.init.BroxelyBlocks;
import io.github.wjgs.broxely.common.init.BroxelyItems;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
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
}
