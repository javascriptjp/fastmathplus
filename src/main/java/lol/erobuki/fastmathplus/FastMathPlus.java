package lol.erobuki.fastmathplus;


import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;

@Mod(modid = FastMathPlus.MODID, name = FastMathPlus.MODNAME, version = FastMathPlus.MODVERSION, clientSideOnly = true)
public class FastMathPlus {
    public static final String MODID = "fastmathplus";
    public static final String MODNAME = "FastMathPlus";
    public static final String MODVERSION = "1.0-beta";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event) {
        LOGGER.info("Start " + MODID);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Loading " + MODID);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre event) {

    }

    @SubscribeEvent
    public void onModelBake(ModelBakeEvent event) {
        final long start = System.nanoTime();
        event.getModelManager().getModel(new ModelResourceLocation("minecraft:missingno"));
        final long end = System.nanoTime();
        LOGGER.info("Texture pre-loading took " + (end - start) / 1000000 + "ms");
    }
}
