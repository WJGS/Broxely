package io.github.wjgs.broxely.common.event.handle;

import io.github.wjgs.broxely.common.entity.EntityWoodPlanks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;


public class DroppedItemHandler {

    public static void onPlayerDroppedSprucePlanks(ItemEntity itemEntity, World world) {

        CompoundNBT nbt = new CompoundNBT();
        itemEntity.writeAdditional(nbt);

        EntityWoodPlanks newEntity = new EntityWoodPlanks(itemEntity);
        newEntity.readAdditional(nbt);

        newEntity.setMotion(itemEntity.getMotion());

        itemEntity.remove();
        world.addEntity(newEntity);
    }
}
