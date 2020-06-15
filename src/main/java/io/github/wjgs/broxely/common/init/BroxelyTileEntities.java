package io.github.wjgs.broxely.common.init;

import io.github.wjgs.broxely.common.block.altar.AltarTE;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;


public class BroxelyTileEntities {

    @ObjectHolder("broxely:altar")
    public static TileEntityType<AltarTE> altarTE;
}
