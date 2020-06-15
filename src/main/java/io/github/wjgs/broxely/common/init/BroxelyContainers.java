package io.github.wjgs.broxely.common.init;

import io.github.wjgs.broxely.common.block.altar.AltarContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.ObjectHolder;

public class BroxelyContainers {

    @ObjectHolder("broxely:altar")
    public static ContainerType<AltarContainer> ALTAR_CONTAINER;
}
