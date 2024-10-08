package com.aleksey.decorations.handlers;

import net.minecraftforge.event.world.WorldEvent;

import com.aleksey.decorations.core.Recipes;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ChunkEventHandler
{
    @SubscribeEvent
    public void onLoadWorld(WorldEvent.Load event)
    {
        if(!event.world.isRemote && event.world.provider.dimensionId == 0 && Recipes.areAnvilRecipesRegistered())
        {
            Recipes.registerAnvilRecipes(event.world);
        }
    }
}