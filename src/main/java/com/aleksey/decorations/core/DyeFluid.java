package com.aleksey.decorations.core;

import com.dunk.tfc.Core.FluidBaseTFC;

public class DyeFluid extends FluidBaseTFC
{
    public int TFCDyeIndex;
    
    public DyeFluid(String fluidName, int tfcDyeIndex)
    {
        super(fluidName);
        
        TFCDyeIndex = tfcDyeIndex;
    }
}
