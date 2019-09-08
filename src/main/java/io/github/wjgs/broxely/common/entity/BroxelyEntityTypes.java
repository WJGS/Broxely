package io.github.wjgs.broxely.common.entity;

import io.github.wjgs.broxely.Broxely;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

public class BroxelyEntityTypes {

    public static final EntityType<?> FIREPROOF_ITEM = EntityType.Builder.create(EntityWoodPlanks::new, EntityClassification.MISC)
            .setUpdateInterval(20)
        .build(Broxely.MODID + ":fireproof_item").setRegistryName(Broxely.MODID, "fireproof_item");
        }
