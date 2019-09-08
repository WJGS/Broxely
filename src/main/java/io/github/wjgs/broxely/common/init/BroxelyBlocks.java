package io.github.wjgs.broxely.common.init;

import io.github.wjgs.broxely.Broxely;
import io.github.wjgs.broxely.common.block.BlockAltar;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Broxely.MODID)
public class BroxelyBlocks {

    public static final BlockAltar altar = new BlockAltar();

    public static final Block fireInfusedPlanks = new Block(Block.Properties.create(Material.WOOD)
    .harvestTool(ToolType.AXE).hardnessAndResistance((float)5).lightValue(8)).setRegistryName("fire_infused_planks");

    private static final Block[] m_blocks = {altar, fireInfusedPlanks};

    public static Block[] getBlocks() {
        return m_blocks;
    }
}
