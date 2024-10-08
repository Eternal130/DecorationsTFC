package com.aleksey.decorations.render.blocks;

import com.aleksey.decorations.core.data.Bound;
import com.dunk.tfc.api.TFCBlocks;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class RenderLantern implements ISimpleBlockRenderingHandler
{
    //http://greyminecraftcoder.blogspot.com/2013/07/rendering-non-standard-blocks.html
    
    private static final double VOXEL_SIZE_SCALED = 0.0625;// 1/16

    private static final Bound[] ShellBounds =
        {
            new Bound(5, 4, 5, 5, 9, 5),//back-left
            new Bound(5, 4, 10, 5, 9, 10),//forward-left
            new Bound(10, 4, 10, 10, 9, 10),//forward-right
            new Bound(10, 4, 5, 10, 9, 5),//back-right
            
            new Bound(4, 3, 4, 11, 3, 11),//bottom-large
            new Bound(6, 2, 6, 9, 2, 9),//bottom-small
    
            new Bound(4, 10, 4, 11, 10, 11),//top-large
            new Bound(6, 11, 6, 9, 11, 9),//top-small
        };
    
    private static final Bound[] CandleWickBounds = { new Bound(7, 4, 7, 8, 5, 8) };
    
    private static final Bound[] GlassBounds =
        {
            new Bound(6, 4, 5, 9, 9, 5),//forward
            new Bound(5, 4, 6, 5, 9, 9),//left
            new Bound(6, 4, 9, 9, 9, 9),//back
            new Bound(9, 4, 6, 9, 9, 9),//right
        };
    
    private static final Bound[] HandleBottomShortBounds = { new Bound(7, 1, 7, 8, 1, 8) };
    private static final Bound[] HandleBottomLongBounds = { new Bound(7, 1, 7, 8, 1, 8), new Bound(6, 0, 6, 9, 0, 9) };

    private static final Bound[] HandleTopShortBounds = { new Bound(7, 12, 7, 8, 12, 8) };
    private static final Bound[] HandleTopLongBounds = { new Bound(7, 12, 7, 8, 14, 8), new Bound(6, 15, 6, 9, 15, 9) };
    
    private static final Bound HandleBaseConnectorBound = new Bound(7, 12, 7, 8, 14, 8);
    private static final Bound[] HandleLeftBounds = { HandleBaseConnectorBound, new Bound(1, 13, 7, 6, 14, 8), new Bound(0, 12, 6, 0, 15, 9) };
    private static final Bound[] HandleRightBounds = { HandleBaseConnectorBound, new Bound(9, 13, 7, 14, 14, 8), new Bound(15, 12, 6, 15, 15, 9) };
    private static final Bound[] HandleForwardBounds = { HandleBaseConnectorBound, new Bound(7, 13, 9, 8, 14, 14), new Bound(6, 12, 15, 9, 15, 15) };
    private static final Bound[] HandleBackBounds = { HandleBaseConnectorBound, new Bound(7, 13, 1, 8, 14, 6), new Bound(6, 12, 0, 9, 15, 0) };

    private static final Bound[] FenceHandleLeftBounds = { HandleBaseConnectorBound, new Bound(-5, 13, 7, 6, 14, 8), new Bound(-6, 12, 6, -6, 15, 9) };
    private static final Bound[] FenceHandleRightBounds = { HandleBaseConnectorBound, new Bound(9, 13, 7, 20, 14, 8), new Bound(21, 12, 6, 21, 15, 9) };
    private static final Bound[] FenceHandleForwardBounds = { HandleBaseConnectorBound, new Bound(7, 13, 9, 8, 14, 20), new Bound(6, 12, 21, 9, 15, 21) };
    private static final Bound[] FenceHandleBackBounds = { HandleBaseConnectorBound, new Bound(7, 13, -5, 8, 14, 6), new Bound(6, 12, -6, 9, 15, -6) };

    private static final Bound[] WallHandleLeftBounds = { HandleBaseConnectorBound, new Bound(0, 9, 7, 1, 14, 8), new Bound(2, 13, 7, 6, 14, 8), new Bound(-4, 8, 6, -4, 11, 9), new Bound(-3, 9, 7, -1, 10, 8) };
    private static final Bound[] WallHandleRightBounds = { HandleBaseConnectorBound, new Bound(14, 9, 7, 15, 14, 8), new Bound(9, 13, 7, 13, 14, 8), new Bound(19, 8, 6, 19, 11, 9), new Bound(16, 9, 7, 18, 10, 8) };
    private static final Bound[] WallHandleForwardBounds = { HandleBaseConnectorBound, new Bound(7, 9, 14, 8, 14, 15), new Bound(7, 13, 9, 8, 14, 13), new Bound(6, 8, 19, 9, 11, 19), new Bound(7, 9, 16, 8, 10, 18) };
    private static final Bound[] WallHandleBackBounds = { HandleBaseConnectorBound, new Bound(7, 9, 0, 8, 14, 1), new Bound(7, 13, 2, 8, 14, 6), new Bound(6, 8, -4, 9, 11, -4), new Bound(7, 9, -3, 8, 10, -1) };

    private static final Bound[] WallLowHandleLeftBounds = { HandleBaseConnectorBound, new Bound(0, 9, 7, 1, 14, 8), new Bound(2, 13, 7, 6, 14, 8), new Bound(-5, 8, 6, -5, 11, 9), new Bound(-4, 9, 7, -1, 10, 8) };
    private static final Bound[] WallLowHandleRightBounds = { HandleBaseConnectorBound, new Bound(14, 9, 7, 15, 14, 8), new Bound(9, 13, 7, 13, 14, 8), new Bound(20, 8, 6, 20, 11, 9), new Bound(16, 9, 7, 19, 10, 8) };
    private static final Bound[] WallLowHandleForwardBounds = { HandleBaseConnectorBound, new Bound(7, 9, 14, 8, 14, 15), new Bound(7, 13, 9, 8, 14, 13), new Bound(6, 8, 20, 9, 11, 20), new Bound(7, 9, 16, 8, 10, 19) };
    private static final Bound[] WallLowHandleBackBounds = { HandleBaseConnectorBound, new Bound(7, 9, 0, 8, 14, 1), new Bound(7, 13, 2, 8, 14, 6), new Bound(6, 8, -5, 9, 11, -5), new Bound(7, 9, -4, 8, 10, -1) };

    private Bound[] shellBoundsScaled;
    private Bound[] candleWickBoundsScaled;
    private Bound[] glassBoundsScaled;
    private Bound[] handleBottomShortBoundsScaled;
    private Bound[] handleBottomLongBoundsScaled;
    private Bound[] handleTopShortBoundsScaled;
    private Bound[] handleTopLongBoundsScaled;
    private Bound[] handleLeftBoundsScaled;
    private Bound[] handleRightBoundsScaled;
    private Bound[] handleForwardBoundsScaled;
    private Bound[] handleBackBoundsScaled;
    private Bound[] fenceHandleLeftBoundsScaled;
    private Bound[] fenceHandleRightBoundsScaled;
    private Bound[] fenceHandleForwardBoundsScaled;
    private Bound[] fenceHandleBackBoundsScaled;
    private Bound[] wallHandleLeftBoundsScaled;
    private Bound[] wallHandleRightBoundsScaled;
    private Bound[] wallHandleForwardBoundsScaled;
    private Bound[] wallHandleBackBoundsScaled;
    private Bound[] wallLowHandleLeftBoundsScaled;
    private Bound[] wallLowHandleRightBoundsScaled;
    private Bound[] wallLowHandleForwardBoundsScaled;
    private Bound[] wallLowHandleBackBoundsScaled;
    
    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        getBoundsScaled();
        
        renderInvBlock(block, metadata, renderer, shellBoundsScaled);
        
        renderer.overrideBlockTexture = Block.getBlockFromName("planks").getIcon(0, 0);
        
        renderInvBlock(block, metadata, renderer, handleTopShortBoundsScaled);
        renderInvBlock(block, metadata, renderer, handleBottomShortBoundsScaled);
        
        renderer.clearOverrideBlockTexture();
    }
    
    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        getBoundsScaled();
        
        renderWorldBlock(x, y, z, block, renderer, shellBoundsScaled);
        renderWorldBlock(x, y, z, block, renderer, candleWickBoundsScaled);

        renderer.overrideBlockTexture = Block.getBlockFromName("glass").getIcon(0, 0);

        renderWorldBlock(x, y, z, block, renderer, glassBoundsScaled);

        renderer.clearOverrideBlockTexture();

        IBlockAccess blockAccess = renderer.blockAccess;
        
        renderer.overrideBlockTexture = Block.getBlockFromName("planks").getIcon(0, 0);
      
        if(blockAccess.isSideSolid(x, y - 1, z, ForgeDirection.UP, false) || isFenceOrWall(blockAccess.getBlock(x, y - 1, z)))
        {
            renderWorldBlock(x, y, z, block, renderer, handleTopShortBoundsScaled);
            renderWorldBlock(x, y, z, block, renderer, handleBottomLongBoundsScaled);
        }
        else if(blockAccess.isSideSolid(x, y + 1, z, ForgeDirection.DOWN, false) || isFenceOrWall(blockAccess.getBlock(x, y + 1, z)))
        {
            renderWorldBlock(x, y, z, block, renderer, handleTopLongBoundsScaled);
            renderWorldBlock(x, y, z, block, renderer, handleBottomShortBoundsScaled);
        }
        else
        {
            Block leftBlock = blockAccess.getBlock(x - 1, y, z);
            Block rightBlock = blockAccess.getBlock(x + 1, y, z);
            Block forwardBlock = blockAccess.getBlock(x, y, z + 1);
            Block backBlock = blockAccess.getBlock(x, y, z - 1);
            
            if(leftBlock.isBlockNormalCube())
                renderWorldBlock(x, y, z, block, renderer, handleLeftBoundsScaled);
            else if(rightBlock.isBlockNormalCube())
                renderWorldBlock(x, y, z, block, renderer, handleRightBoundsScaled);
            else if(forwardBlock.isBlockNormalCube())
                renderWorldBlock(x, y, z, block, renderer, handleForwardBoundsScaled);
            else if(backBlock.isBlockNormalCube())
                renderWorldBlock(x, y, z, block, renderer, handleBackBoundsScaled);
            
            if(isFence(leftBlock))
                renderWorldBlock(x, y, z, block, renderer, fenceHandleLeftBoundsScaled);
            else if(isFence(rightBlock))
                renderWorldBlock(x, y, z, block, renderer, fenceHandleRightBoundsScaled);
            else if(isFence(forwardBlock))
                renderWorldBlock(x, y, z, block, renderer, fenceHandleForwardBoundsScaled);
            else if(isFence(backBlock))
                renderWorldBlock(x, y, z, block, renderer, fenceHandleBackBoundsScaled);

            if(isWall(leftBlock))
            {
                if(isWallLow(x - 1, y, z, leftBlock, renderer))
                    renderWorldBlock(x, y, z, block, renderer, wallLowHandleLeftBoundsScaled);
                else
                    renderWorldBlock(x, y, z, block, renderer, wallHandleLeftBoundsScaled);
            }
            else if(isWall(rightBlock))
            {
                if(isWallLow(x + 1, y, z, rightBlock, renderer))
                    renderWorldBlock(x, y, z, block, renderer, wallLowHandleRightBoundsScaled);
                else
                    renderWorldBlock(x, y, z, block, renderer, wallHandleRightBoundsScaled);
            }
            else if(isWall(forwardBlock))
            {
                if(isWallLow(x, y, z + 1, forwardBlock, renderer))
                    renderWorldBlock(x, y, z, block, renderer, wallLowHandleForwardBoundsScaled);
                else
                    renderWorldBlock(x, y, z, block, renderer, wallHandleForwardBoundsScaled);
            }
            else if(isWall(backBlock))
            {
                if(isWallLow(x, y, z - 1, backBlock, renderer))
                    renderWorldBlock(x, y, z, block, renderer, wallLowHandleBackBoundsScaled);
                else
                    renderWorldBlock(x, y, z, block, renderer, wallHandleBackBoundsScaled);
            }
            
            else
                renderWorldBlock(x, y, z, block, renderer, handleTopShortBoundsScaled);
            
            renderWorldBlock(x, y, z, block, renderer, handleBottomShortBoundsScaled);
        }

        renderer.clearOverrideBlockTexture();

        return true;
    }
    
    private static boolean isWallLow(int x, int y, int z, Block block, RenderBlocks renderer)
    {
        BlockWall blockWall = (BlockWall)block;
        IBlockAccess blockAccess = renderer.blockAccess;
        boolean flag = blockWall.canConnectWallTo(blockAccess, x - 1, y, z);
        boolean flag1 = blockWall.canConnectWallTo(blockAccess, x + 1, y, z);
        boolean flag2 = blockWall.canConnectWallTo(blockAccess, x, y, z - 1);
        boolean flag3 = blockWall.canConnectWallTo(blockAccess, x, y, z + 1);
        boolean flag4 = flag2 && flag3 && !flag && !flag1;
        boolean flag5 = !flag2 && !flag3 && flag && flag1;
        boolean flag6 = blockAccess.isAirBlock(x, y + 1, z);

        return (flag4 || flag5) && flag6;
    }
    
    @Override
    public boolean shouldRender3DInInventory(int modelId)
    {
        return true;
    }
    
    @Override
    public int getRenderId()
    {
        return 0;
    }
    
    private void getBoundsScaled()
    {
        if(shellBoundsScaled != null)
            return;
        
        shellBoundsScaled = convertToScaled(ShellBounds);
        candleWickBoundsScaled = convertToScaled(CandleWickBounds);
        glassBoundsScaled = convertToScaled(GlassBounds);
        handleBottomShortBoundsScaled = convertToScaled(HandleBottomShortBounds);
        handleBottomLongBoundsScaled = convertToScaled(HandleBottomLongBounds);
        handleTopShortBoundsScaled = convertToScaled(HandleTopShortBounds);
        handleTopLongBoundsScaled = convertToScaled(HandleTopLongBounds);
        handleLeftBoundsScaled = convertToScaled(HandleLeftBounds);
        handleRightBoundsScaled = convertToScaled(HandleRightBounds);
        handleForwardBoundsScaled = convertToScaled(HandleForwardBounds);
        handleBackBoundsScaled = convertToScaled(HandleBackBounds);
        fenceHandleLeftBoundsScaled = convertToScaled(FenceHandleLeftBounds);
        fenceHandleRightBoundsScaled = convertToScaled(FenceHandleRightBounds);
        fenceHandleForwardBoundsScaled = convertToScaled(FenceHandleForwardBounds);
        fenceHandleBackBoundsScaled = convertToScaled(FenceHandleBackBounds);
        wallHandleLeftBoundsScaled = convertToScaled(WallHandleLeftBounds);
        wallHandleRightBoundsScaled = convertToScaled(WallHandleRightBounds);
        wallHandleForwardBoundsScaled = convertToScaled(WallHandleForwardBounds);
        wallHandleBackBoundsScaled = convertToScaled(WallHandleBackBounds);
        wallLowHandleLeftBoundsScaled = convertToScaled(WallLowHandleLeftBounds);
        wallLowHandleRightBoundsScaled = convertToScaled(WallLowHandleRightBounds);
        wallLowHandleForwardBoundsScaled = convertToScaled(WallLowHandleForwardBounds);
        wallLowHandleBackBoundsScaled = convertToScaled(WallLowHandleBackBounds);
    }
    
    private static Bound[] convertToScaled(Bound[] inputList)
    {
        ArrayList<Bound> resultList = new ArrayList<>();

        for (Bound bound : inputList) {
            Bound[] inputs = splitBound(bound);

            for (Bound input : inputs) {
                double minX = VOXEL_SIZE_SCALED * input.MinX;
                double minY = VOXEL_SIZE_SCALED * input.MinY;
                double minZ = VOXEL_SIZE_SCALED * input.MinZ;
                double maxX = VOXEL_SIZE_SCALED * (input.MaxX + 1);
                double maxY = VOXEL_SIZE_SCALED * (input.MaxY + 1);
                double maxZ = VOXEL_SIZE_SCALED * (input.MaxZ + 1);

                resultList.add(new Bound(minX, minY, minZ, maxX, maxY, maxZ, input.ShiftX, input.ShiftY, input.ShiftZ));
            }
        }
        
        Bound[] resultArray = new Bound[resultList.size()];
        
        return resultList.toArray(resultArray);
    }
    
    private static Bound[] splitBound(Bound input)
    {
        if(input.MinX < 0 || input.MaxX > 15)
            return splitBoundByX(input);
        else if(input.MinZ < 0 || input.MaxZ > 15)
            return splitBoundByZ(input);
        else
            return new Bound[] { input };
    }
    
    private static Bound[] splitBoundByX(Bound input)
    {
        if(input.MinX < 0 && input.MaxX < 0)
            return new Bound[] { new Bound(16 + input.MinX, input.MinY, input.MinZ, 16 + input.MaxX, input.MaxY, input.MaxZ, -1, 0, 0) };

        if(input.MinX > 15 && input.MaxX > 15)
            return new Bound[] { new Bound(input.MinX - 16, input.MinY, input.MinZ, input.MaxX - 16, input.MaxY, input.MaxZ, 1, 0, 0) };
        
        Bound[] result = new Bound[2];
        
        if(input.MinX < 0)
        {
            result[0] = new Bound(16 + input.MinX, input.MinY, input.MinZ, 15, input.MaxY, input.MaxZ, -1, 0, 0);
            result[1] = new Bound(0, input.MinY, input.MinZ, input.MaxX, input.MaxY, input.MaxZ, 0, 0, 0);
        }
        else
        {
            result[0] = new Bound(input.MinX, input.MinY, input.MinZ, 15, input.MaxY, input.MaxZ, 0, 0, 0);
            result[1] = new Bound(0, input.MinY, input.MinZ, input.MaxX - 16, input.MaxY, input.MaxZ, 1, 0, 0);            
        }
        
        return result;
    }

    private static Bound[] splitBoundByZ(Bound input)
    {
        if(input.MinZ < 0 && input.MaxZ < 0)
            return new Bound[] { new Bound(input.MinX, input.MinY, 16 + input.MinZ, input.MaxX, input.MaxY, 16 + input.MaxZ, 0, 0, -1) };

        if(input.MinZ > 15 && input.MaxZ > 15)
            return new Bound[] { new Bound(input.MinX, input.MinY, input.MinZ - 16, input.MaxX, input.MaxY, input.MaxZ - 16, 0, 0, 1) };
        
        Bound[] result = new Bound[2];
        
        if(input.MinZ < 0)
        {
            result[0] = new Bound(input.MinX, input.MinY, 16 + input.MinZ, input.MaxX, input.MaxY, 15, 0, 0, -1);
            result[1] = new Bound(input.MinX, input.MinY, 0, input.MaxX, input.MaxY, input.MaxZ, 0, 0, 0);
        }
        else
        {
            result[0] = new Bound(input.MinX, input.MinY, input.MinZ, input.MaxX, input.MaxY, 15, 0, 0, 0);
            result[1] = new Bound(input.MinX, input.MinY, 0, input.MaxX, input.MaxY, input.MaxZ - 16, 0, 0, 1);            
        }
        
        return result;
    }
    
    private static void renderInvBlock(Block block, int m, RenderBlocks renderer, Bound[] bounds)
    {
        Tessellator var14 = Tessellator.instance;

        for (Bound bound : bounds) {
            renderer.setRenderBounds(bound.MinX, bound.MinY, bound.MinZ, bound.MaxX, bound.MaxY, bound.MaxZ);

            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            var14.startDrawingQuads();
            var14.setNormal(0.0F, -1.0F, 0.0F);
            renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, m));
            var14.draw();
            var14.startDrawingQuads();
            var14.setNormal(0.0F, 1.0F, 0.0F);
            renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, m));
            var14.draw();
            var14.startDrawingQuads();
            var14.setNormal(-1.0F, 0.0F, 0.0F);
            renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, m));
            var14.draw();
            var14.startDrawingQuads();
            var14.setNormal(0.0F, 0.0F, -1.0F);
            renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, m));
            var14.draw();
            var14.startDrawingQuads();
            var14.setNormal(-1.0F, 0.0F, 0.0F);
            renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, m));
            var14.draw();
            var14.startDrawingQuads();
            var14.setNormal(0.0F, 0.0F, 1.0F);
            renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, m));
            var14.draw();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        }
    }
    
    private static void renderWorldBlock(int x, int y, int z, Block block, RenderBlocks renderer, Bound[] bounds)
    {
        for (Bound bound : bounds) {
            renderer.setRenderBounds(bound.MinX, bound.MinY, bound.MinZ, bound.MaxX, bound.MaxY, bound.MaxZ);
            renderer.renderStandardBlock(block, x + bound.ShiftX, y + bound.ShiftY, z + bound.ShiftZ);
        }
    }
    
    private static boolean isFence(Block block)
    {
        return TFCBlocks.isBlockAFence(block);
    }
    
    private static boolean isWall(Block block)
    {
        return block instanceof BlockWall;
    }

    private static boolean isFenceOrWall(Block block)
    {
        return TFCBlocks.isBlockAFence(block) || block instanceof BlockWall;
    }
}