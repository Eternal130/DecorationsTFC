package com.aleksey.decorations.core;

import com.aleksey.decorations.DecorationsMod;
import com.aleksey.decorations.core.data.LanternInfo;
import com.aleksey.decorations.items.*;
import com.dunk.tfc.api.TFCItems;
import cpw.mods.fml.common.registry.ExistingSubstitutionException;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemList
{
    public static Item[] lanternCores;
    public static Item liquidDye;
    public static Item brush;
    
    public static void setup() throws ExistingSubstitutionException
    {
        
        if(DecorationsMod.isLanternsEnabled)
        {
            lanternCores = new Item[Constants.Lanterns.length];
            
            for(int i = 0; i < lanternCores.length; i++)
            {
                LanternInfo info = Constants.Lanterns[i];
                lanternCores[i] = new ItemLanternCore(info).setUnlocalizedName("LanternCore" + "." + info.LanternName);
            }
        }

        liquidDye = new ItemLiquidDye().setUnlocalizedName("LiquidDye");
        brush = new ItemBrush().setUnlocalizedName("Brush");

        registerItems();
    }
    
    private static void registerItems() throws ExistingSubstitutionException
    {
        if(DecorationsMod.isLanternsEnabled)
        {
            for (Item lanternCore : lanternCores) {
                GameRegistry.registerItem(lanternCore, lanternCore.getUnlocalizedName());
            }
        }

        GameRegistry.registerItem(liquidDye, liquidDye.getUnlocalizedName());
        GameRegistry.registerItem(brush, brush.getUnlocalizedName());
    }

}
