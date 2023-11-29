package com.aleksey.decorations.handlers.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

import com.aleksey.decorations.core.Recipes;
import com.dunk.tfc.Handlers.Network.AbstractPacket;

public class InitClientWorldPacket extends AbstractPacket
{
    public InitClientWorldPacket()
    {
        
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        if(Recipes.areAnvilRecipesRegistered())
            Recipes.registerAnvilRecipes(player.worldObj);
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {
    }
}
