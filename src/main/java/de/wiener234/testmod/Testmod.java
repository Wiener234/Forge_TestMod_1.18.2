package de.wiener234.testmod;

import de.wiener234.testmod.block.ModBlocks;
import de.wiener234.testmod.item.ModItems;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("testmod")
public class Testmod {

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "testmod";

    public Testmod() {

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();


        ModItems.register(eventBus);
        ModBlocks.register(eventBus);


        eventBus.addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event){
        // some preinit code
        LOGGER.info("Hello from preinit");
        LOGGER.info("Dirt block >> {}", Blocks.DIRT.getRegistryName());
    }
}
