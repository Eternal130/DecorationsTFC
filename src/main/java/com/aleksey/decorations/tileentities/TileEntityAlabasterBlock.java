package com.aleksey.decorations.tileentities;

import com.dunk.tfc.TileEntities.NetworkTileEntity;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityAlabasterBlock extends NetworkTileEntity {
    public Block block;
    public int meta;
    public int blockMeta;

    public TileEntityAlabasterBlock() {
    }

    public void handleInitPacket(NBTTagCompound nbt) {
        this.block = Block.getBlockById(nbt.getInteger("block"));
        this.meta = nbt.getInteger("meta");
        this.blockMeta = nbt.getInteger("blockMeta");
    }

    public void createInitNBT(NBTTagCompound nbt) {
        nbt.setInteger("block", Block.getIdFromBlock(this.block));
        nbt.setInteger("meta", this.meta);
        nbt.setInteger("blockMeta", this.blockMeta);
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.block = Block.getBlockById(nbt.getInteger("block"));
        this.meta = nbt.getInteger("meta");
        this.blockMeta = nbt.getInteger("blockMeta");
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("block", Block.getIdFromBlock(this.block));
        nbt.setInteger("meta", this.meta);
        nbt.setInteger("blockMeta", this.blockMeta);
    }
}
