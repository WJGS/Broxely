package io.github.wjgs.broxely.common.init;

import io.github.wjgs.broxely.Broxely;
import io.github.wjgs.broxely.common.init.BroxelyBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Broxely.MODID)
public class BroxelyItems {

    public static final Item fireInfusedPlanks = new BlockItem(BroxelyBlocks.fireInfusedPlanks, new Item.Properties().group(Broxely.TAB))
            .setRegistryName(Broxely.MODID, "fire_infused_planks");

    public static final Item altar = new BlockItem(BroxelyBlocks.altar, new Item.Properties().group(Broxely.TAB))
            .setRegistryName(Broxely.MODID, "altar");

    private static final Item[] m_items = {fireInfusedPlanks, altar};

    public static Item[] getItems() {
        return m_items;
    }
}
