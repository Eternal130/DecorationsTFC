package com.aleksey.decorations.core;

import net.minecraft.block.Block;

import com.aleksey.decorations.DecorationsMod;
import com.aleksey.decorations.blocks.BlockAlabaster;
import com.aleksey.decorations.blocks.BlockCustomGem;
import com.aleksey.decorations.blocks.BlockCustomLantern;
import com.aleksey.decorations.blocks.BlockMudBrickRaw;
import com.aleksey.decorations.blocks.BlockMudBricks;
import com.aleksey.decorations.core.data.GemInfo;
import com.aleksey.decorations.core.data.LanternInfo;
import com.aleksey.decorations.items.itemblocks.ItemAlabaster;
import com.aleksey.decorations.items.itemblocks.ItemLantern;
import com.aleksey.decorations.items.itemblocks.ItemMudBrickRaw;
import com.aleksey.decorations.items.itemblocks.ItemMudBricks;
import com.dunk.tfc.api.Constant.Global;

import cpw.mods.fml.common.registry.GameRegistry;

public class BlockList
{
    public static int LanternRenderId;
    public static int MudBrickRawRenderId;
    
    public static Block[] Lanterns;
    public static Block[] Gems;
    public static Block Alabaster;
    public static Block[] MudBrickRaws;
    public static Block MudBricks;
    public static Block MudBricks2;
    
    public static void registerBlocks()
    {
        if(DecorationsMod.isLanternsEnabled)
        {
            for (Block lantern : Lanterns)
                GameRegistry.registerBlock(lantern, ItemLantern.class, lantern.getUnlocalizedName().substring(5));
        }
        
        if(DecorationsMod.isGemsEnabled)
        {
            for (Block gem : Gems) GameRegistry.registerBlock(gem, gem.getUnlocalizedName().substring(5));
        }
        
        GameRegistry.registerBlock(Alabaster, ItemAlabaster.class, Alabaster.getUnlocalizedName().substring(5));

        for (Block mudBrickRaw : MudBrickRaws)
            GameRegistry.registerBlock(mudBrickRaw, ItemMudBrickRaw.class, mudBrickRaw.getUnlocalizedName().substring(5));
        
        GameRegistry.registerBlock(MudBricks, ItemMudBricks.class, MudBricks.getUnlocalizedName().substring(5));
        GameRegistry.registerBlock(MudBricks2, ItemMudBricks.class, MudBricks2.getUnlocalizedName().substring(5));
    }
    
    public static void loadBlocks()
    {
        if(DecorationsMod.isLanternsEnabled)
        {
            //Lanterns
            Lanterns = new Block[Constants.Lanterns.length];
            
            for(int i = 0; i < Constants.Lanterns.length; i++)
            {
                LanternInfo info = Constants.Lanterns[i];
                String name = "Lantern." + info.LanternName;
                
                Lanterns[i] = new BlockCustomLantern(info).setBlockName(name);
            }
        }
        
        if(DecorationsMod.isGemsEnabled)
        {
            //Gems
            Gems = new Block[Constants.Gems.length];
            
            for(int i = 0; i < Gems.length; i++)
            {
                GemInfo info = Constants.Gems[i];
                String name = "Gem." + info.GemName; 
                
                Gems[i] = new BlockCustomGem(info).setBlockName(name).setHardness(0.25f);
            }
        }
        
        //Gypsum
        Alabaster = new BlockAlabaster().setBlockName("Alabaster");
        
        //Mud Bricks
        MudBrickRaws = new Block[Global.STONE_ALL.length];
        
        for(int i = 0; i < MudBrickRaws.length; i++)
            MudBrickRaws[i] = new BlockMudBrickRaw(i).setBlockName("MudBrickRaw." + Global.STONE_ALL[i].replaceAll(" ", ""));
        
        MudBricks = new BlockMudBricks(0).setBlockName("MudBricks");
        MudBricks2 = new BlockMudBricks(16).setBlockName("MudBricks2");
    }
}
