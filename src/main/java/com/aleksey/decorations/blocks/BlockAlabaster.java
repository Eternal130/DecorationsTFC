package com.aleksey.decorations.blocks;

import com.aleksey.decorations.core.FluidList;
import com.aleksey.decorations.tileentities.TileEntityAlabasterBlock;
import com.dunk.tfc.Blocks.BlockPlasteredBlock;
import com.dunk.tfc.Core.TFCTabs;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Items.ItemDyeCustom;
import com.dunk.tfc.TileEntities.TEPlasteredBlock;
import com.dunk.tfc.api.TFCBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockAlabaster extends BlockPlasteredBlock
{
    protected String[] names;
    protected IIcon[] icons;
    public BlockAlabaster()
    {
        super(Material.rock);
        this.setCreativeTab(TFCTabs.TFC_BUILDING);
        
        this.names = new String[16];
        for(int i = 0; i < this.names.length; i++)
        {
            String dyeName = ItemDyeCustom.DYE_COLOR_NAMES[FluidList.LiquidDyes[i].TFCDyeIndex];

            this.names[i] = "Alabaster" + dyeName.substring(0, 1).toUpperCase() + dyeName.substring(1);
        }
        
        this.icons = new IIcon[16];
    }
    
    @Override
    public IIcon getIcon(int side, int meta)
    {
        return this.icons[meta];
    }
    
    @Override
    public void registerBlockIcons(IIconRegister iconRegisterer)
    {
        for(int i = 0; i < this.icons.length; i++)
            this.icons[i] = iconRegisterer.registerIcon("decorations:alabasters/" + this.names[i]);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityAlabasterBlock();
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean canHarvest) {
        if (!canHarvest && !player.capabilities.isCreativeMode) {
            return false;
        } else {
            TileEntityAlabasterBlock tileEntityAlabasterBlock = (TileEntityAlabasterBlock)world.getTileEntity(x, y, z);
            world.setBlock(x, y, z, TFCBlocks.plasteredBlock, tileEntityAlabasterBlock.blockMeta, 2);
            TEPlasteredBlock te = (TEPlasteredBlock)world.getTileEntity(x, y, z);
            te.block = tileEntityAlabasterBlock.block;
            te.meta = tileEntityAlabasterBlock.meta;
            te.markDirty();
            TFC_Core.addPlayerExhaustion(player, 0.001F);
            return false;
        }
    }
}
