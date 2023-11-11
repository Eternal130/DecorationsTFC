package com.aleksey.decorations;

import net.minecraftforge.common.MinecraftForge;

import com.aleksey.decorations.core.BlockList;
import com.aleksey.decorations.core.FluidList;
import com.aleksey.decorations.core.ItemList;
import com.aleksey.decorations.core.Recipes;
import com.aleksey.decorations.core.player.PlayerTracker;
import com.aleksey.decorations.handlers.ChunkEventHandler;
import com.aleksey.decorations.handlers.CraftingHandler;
import com.aleksey.decorations.handlers.network.InitClientWorldPacket;
import com.dunk.tfc.TerraFirmaCraft;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.ExistingSubstitutionException;

@Mod(modid="DecorationsTFC", name="Decorations", version="1.0.21", dependencies="required-after:terrafirmacraftplus")
public class DecorationsMod
{
    @Instance("DecorationsTFC")
    public static DecorationsMod instance;

    @SidedProxy(clientSide = "com.aleksey.decorations.ClientProxy", serverSide = "com.aleksey.decorations.CommonProxy")
    public static CommonProxy proxy;
    
    public static final boolean isLanternsEnabled = !Loader.isModLoaded("LanternsTFC");
    public static boolean isGemsEnabled;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) throws ExistingSubstitutionException
    {
        DecorationConfig.loadConfig(event);
        
        System.out.println("DecorationsTFC Lanterns Enabled = " + isLanternsEnabled);
        
        proxy.registerTickHandler();
        proxy.registerTileEntities();
        
        FluidList.register();
        
        BlockList.loadBlocks();
        BlockList.registerBlocks();
                
        ItemList.setup();
        
        //proxy.registerGuiHandler();
    }
  
    @EventHandler
    public void initialize(FMLInitializationEvent event)
    {
        TerraFirmaCraft.PACKET_PIPELINE.registerPacket(InitClientWorldPacket.class);
        
        FMLCommonHandler.instance().bus().register(new PlayerTracker());
        
        //Register Crafting Handler
        FMLCommonHandler.instance().bus().register(new CraftingHandler());
        
        // Register the Chunk Load/Save Handler
        MinecraftForge.EVENT_BUS.register(new ChunkEventHandler());
        
        proxy.registerRenderInformation();
        
        FluidList.registerFluidContainers();
        
        Recipes.registerRecipes();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    }
}