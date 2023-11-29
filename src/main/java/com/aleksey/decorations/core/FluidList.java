package com.aleksey.decorations.core;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.aleksey.decorations.DecorationsMod;
import com.dunk.tfc.Core.FluidBaseTFC;
import com.dunk.tfc.Items.ItemDyeCustom;
import com.dunk.tfc.api.TFCFluids;
import com.dunk.tfc.api.TFCItems;

public class FluidList
{
    public static final Fluid[] alcoholFluids = new Fluid[]
    {
        TFCFluids.SAKE,
        TFCFluids.APPLEJACK,
        TFCFluids.PAPAYABRANDY,
        TFCFluids.PLUMBRANDY,
        TFCFluids.BRANDY,
        TFCFluids.BERRYBRANDY,
        TFCFluids.DATEBRANDY,
        TFCFluids.FIGBRANDY,
        TFCFluids.FRUITBRANDY,
        TFCFluids.HONEYBRANDY,
        TFCFluids.LEMONBRANDY,
        TFCFluids.ORANGEBRANDY,
        TFCFluids.PEACHBRANDY,
        TFCFluids.RUM,
        TFCFluids.SHOCHU,
        TFCFluids.TEQUILA,
        TFCFluids.VODKA,
        TFCFluids.WHISKEY,
        TFCFluids.RYEWHISKEY,
        TFCFluids.CORNWHISKEY,
        TFCFluids.BARLEYWHISKEY,
        TFCFluids.RICEWHISKEY
    };
    
    public static FluidBaseTFC Plaster = new FluidBaseTFC("plaster").setBaseColor(0xD5D1C0);
    
    public static DyeFluid[] LiquidDyes;
    
    public static void register()
    {

        FluidRegistry.registerFluid(Plaster);
        
        LiquidDyes = new DyeFluid[ItemDyeCustom.DYE_COLOR_NAMES.length];
        
        for(int i = 0; i < LiquidDyes.length; i++)
        {
            int dyeIndex;
            
            if(i == 0)
                dyeIndex = LiquidDyes.length - 1;
            else if(i == LiquidDyes.length - 1)
                dyeIndex = 0;
            else
                dyeIndex = i;
            
            String fluidName = "liquid_dye." + ItemDyeCustom.DYE_COLOR_NAMES[dyeIndex];
            int color = Constants.DyeColors[dyeIndex];
            DyeFluid fluid = new DyeFluid(fluidName, dyeIndex);
            
            fluid.setBaseColor(color);
            
            FluidRegistry.registerFluid(LiquidDyes[i] = fluid);
        }
    }
    
    public static void registerFluidContainers()
    {
        if(DecorationsMod.isLanternsEnabled)
        {
            for(int i = 0; i < Constants.Lanterns.length; i++)
            {
                Item core = ItemList.LanternCores[i]; 
                ItemStack coreEmpty = new ItemStack(core, 1, 0);

                for(int k = 0; k < FluidList.alcoholFluids.length; k++)
                {
                    FluidStack fluid = new FluidStack(alcoholFluids[k], 2000);
                    ItemStack coreFilled = new ItemStack(core, 1, k + 1);

                    FluidContainerRegistry.registerFluidContainer(fluid, coreFilled, coreEmpty);
                }
            }
        }
        
        FluidContainerRegistry.registerFluidContainer(new FluidStack(Plaster, 1000), new ItemStack(ItemList.Plaster), new ItemStack(TFCItems.woodenBucketEmpty));
        
        for(int i = 0; i < LiquidDyes.length; i++)
            FluidContainerRegistry.registerFluidContainer(new FluidStack(LiquidDyes[i], 1000), new ItemStack(ItemList.LiquidDye, 1, i), new ItemStack(TFCItems.woodenBucketEmpty));
    }
}
