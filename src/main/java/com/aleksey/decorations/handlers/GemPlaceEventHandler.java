package com.aleksey.decorations.handlers;

import com.aleksey.decorations.DecorationsMod;
import com.aleksey.decorations.core.BlockList;
import com.dunk.tfc.Items.ItemGem;
import com.aleksey.decorations.tileentities.TileEntityGem;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class GemPlaceEventHandler
{
    @SubscribeEvent
    public void onPlayerRightClick(PlayerInteractEvent event)
    {
        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
        {
            EntityPlayer player = event.entityPlayer;
            World world = event.world;
            int x = event.x;
            int y = event.y;
            int z = event.z;
            int side = event.face;
            ItemStack itemstack = player.getCurrentEquippedItem();

            if (itemstack != null && itemstack.getItem() instanceof ItemGem && DecorationsMod.isGemsEnabled)
            {
                Block block = world.getBlock(x, y, z);
                if (!block.isSideSolid(world, x, y, z, ForgeDirection.VALID_DIRECTIONS[side]))
                    return;

                int gemX = x;
                int gemY = y;
                int gemZ = z;

                switch (side)
                {
                    case 0:
                        gemY--;
                        break;
                    case 1:
                        gemY++;
                        break;
                    case 2:
                        gemZ--;
                        break;
                    case 3:
                        gemZ++;
                        break;
                    case 4:
                        gemX--;
                        break;
                    case 5:
                        gemX++;
                        break;
                }

                if (!world.isAirBlock(gemX, gemY, gemZ))
                    return;

                world.setBlock(gemX, gemY, gemZ, BlockList.Gems[itemstack.getItemDamage()], side, 0x2);

                if (world.isRemote)
                    world.markBlockForUpdate(gemX, gemY, gemZ);

                TileEntityGem tileEntity = (TileEntityGem) world.getTileEntity(gemX, gemY, gemZ);

                if (tileEntity != null)
                    tileEntity.setInventorySlotContents(0, new ItemStack(itemstack.getItem(), 1, itemstack.getItemDamage()));

                ItemStack newItemStack = itemstack.stackSize <= 1
                        ? null
                        : new ItemStack(itemstack.getItem(), itemstack.stackSize - 1, itemstack.getItemDamage());

                player.inventory.setInventorySlotContents(player.inventory.currentItem, newItemStack);
                player.onUpdate();

            }
        }
    }
}