package com.aleksey.decorations.items.itemblocks;

import com.aleksey.decorations.blocks.BlockMudBricks;
import com.dunk.tfc.Items.ItemTerra;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Enums.EnumItemReach;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.api.Enums.EnumWeight;
import com.dunk.tfc.api.Interfaces.ISize;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemMudBricks extends ItemBlock implements ISize
{
    private final int _startIndex;
    
    public ItemMudBricks(Block block)
    {
        super(block);
        
        setHasSubtypes(true);
        
        _startIndex = ((BlockMudBricks)block).getStartIndex();
    }
    
    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        int index = _startIndex + itemstack.getItemDamage();
        
        return getUnlocalizedName() + "." + Global.STONE_ALL[index];
    }
    
    @Override
    public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
    {
        ItemTerra.addSizeInformation(is, arraylist);
    }
    
    @Override
    public int getItemStackLimit(ItemStack is)
    {
        return this.getSize(null).stackSize * getWeight(null).multiplier;
    }
    
    @Override
    public boolean canStack()
    {
        return true;
    }
    
    @Override
    public int getMetadata(int i)
    {
        return i;
    }
    
    @Override
    public EnumSize getSize(ItemStack is)
    {
        return EnumSize.VERYSMALL;
    }

    @Override
    public EnumWeight getWeight(ItemStack is)
    {
        return EnumWeight.HEAVY;
    }
    
    @Override
    public EnumItemReach getReach(ItemStack is)
    {
        return EnumItemReach.SHORT;
    }
}
