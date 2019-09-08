package io.github.wjgs.broxely.common.event;

import io.github.wjgs.broxely.Broxely;
import io.github.wjgs.broxely.common.entity.EntityWoodPlanks;
import io.github.wjgs.broxely.common.event.handle.DroppedItemHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Broxely.MODID)
public class EventBusSubscriber {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        World world = event.getWorld();
        Entity entity = event.getEntity();
        if (!world.isRemote) {
            if (entity instanceof ItemEntity && !(entity instanceof EntityWoodPlanks)) {
                if (((ItemEntity) entity).getItem().getItem() == Items.SPRUCE_PLANKS) {
                    event.setCanceled(true);
                    DroppedItemHandler.onPlayerDroppedSprucePlanks((ItemEntity) entity, world);
                }
            }
        }
    }
}
