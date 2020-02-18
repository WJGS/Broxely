package io.github.wjgs.broxely;

import io.github.wjgs.broxely.common.init.BroxelyItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(Broxely.MODID)
public class Broxely {

    public static final String MODID = "broxely";

    public static final Logger LOGGER = LogManager.getLogger();

    public static final ItemGroup TAB = new ItemGroup("main_tab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(BroxelyItems.altar);
        }
    };

    public Broxely() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(FMLClientSetupEvent event) {
        LOGGER.info("The exception below is ok.");
    }
}
