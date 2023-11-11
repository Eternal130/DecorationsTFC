package com.aleksey.decorations;

import com.aleksey.decorations.handlers.ServerTickHandler;
import com.aleksey.decorations.tileentities.TileEntityGem;
import com.aleksey.decorations.tileentities.TileEntityMudBrickRaw;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
    public void registerRenderInformation()
    {
    }
    
    public void registerTileEntities()
    {
        registerServerTileEntities();
        registerCommonTileEntities();
    }
    
    protected void registerServerTileEntities()
    {
        GameRegistry.registerTileEntity(TileEntityGem.class, "Gem");
    }
    
    protected void registerCommonTileEntities()
    {
        GameRegistry.registerTileEntity(TileEntityMudBrickRaw.class, "MudBrickRow");
    }
    
    public void registerTickHandler()
    {
        FMLCommonHandler.instance().bus().register(new ServerTickHandler());
    }
    
    public boolean isRemote()
    {
        return false;
    }
}
