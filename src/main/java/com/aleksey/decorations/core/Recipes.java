package com.aleksey.decorations.core;

import com.aleksey.decorations.DecorationsMod;
import com.aleksey.decorations.core.data.LanternInfo;
import com.dunk.tfc.api.Constant.Global;
import com.dunk.tfc.api.Crafting.*;
import com.dunk.tfc.api.Enums.RuleEnum;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.TFCCrafting;
import com.dunk.tfc.api.TFCFluids;
import com.dunk.tfc.api.TFCItems;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.Map;

public class Recipes
{
    private static final String LANTERN_CORE_PLAN = "lanterncore";
    
    public static void registerRecipes()
    {
        if(DecorationsMod.isLanternsEnabled)
            registerLanternRecipes();
        
        if(DecorationsMod.isGemsEnabled)
        {
            if (TFCCrafting.diamondConversion)
            {
                GameRegistry.addShapelessRecipe(new ItemStack(Items.diamond, 1), new ItemStack(TFCItems.gemDiamond,1,2));
                GameRegistry.addShapelessRecipe(new ItemStack(Items.diamond, 2), new ItemStack(TFCItems.gemDiamond,1,3));
                GameRegistry.addShapelessRecipe(new ItemStack(Items.diamond, 3), new ItemStack(TFCItems.gemDiamond,1,4));
                GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.gemDiamond,1,2), new ItemStack(Items.diamond));
                GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.gemDiamond,1,3), new ItemStack(Items.diamond), new ItemStack(Items.diamond));
                GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.gemDiamond,1,4), new ItemStack(Items.diamond), new ItemStack(Items.diamond), new ItemStack(Items.diamond));
            }
            
            if (TFCCrafting.emeraldConversion)
            {
                GameRegistry.addShapelessRecipe(new ItemStack(Items.emerald, 1), new ItemStack(TFCItems.gemEmerald,1,2));
                GameRegistry.addShapelessRecipe(new ItemStack(Items.emerald, 2), new ItemStack(TFCItems.gemEmerald,1,3));
                GameRegistry.addShapelessRecipe(new ItemStack(Items.emerald, 3), new ItemStack(TFCItems.gemEmerald,1,4));
                GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.gemEmerald,1,2), new ItemStack(Items.emerald));
                GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.gemEmerald,1,3), new ItemStack(Items.emerald), new ItemStack(Items.emerald));
                GameRegistry.addShapelessRecipe(new ItemStack(TFCItems.gemEmerald,1,4), new ItemStack(Items.emerald), new ItemStack(Items.emerald), new ItemStack(Items.emerald));
            }
        }
        
        GameRegistry.addRecipe(new ItemStack(ItemList.brush), "w", "r", "t", 'w', new ItemStack(TFCItems.wool), 'r', new ItemStack(TFCItems.rope), 't', new ItemStack(TFCItems.stick));
        
        if (DecorationsMod.isMudBricksEnabled) {
            registerMudBrickRecipes();
        }

        registerBarrelRecipes();
    }
    
    private static void registerLanternRecipes()
    {
        ItemStack stick = new ItemStack(TFCItems.stick, 1);
        ItemStack yarn = new ItemStack(TFCItems.woolYarn, 1);
        ItemStack linenString = new ItemStack(TFCItems.linenString, 1);
        ItemStack silkString = new ItemStack(TFCItems.silkString, 1);
        ItemStack cottonYarn = new ItemStack(TFCItems.cottonYarn, 1);
        ItemStack glassPane = new ItemStack(Blocks.glass_pane, 1);
        
        for(int i = 0; i < Constants.Lanterns.length; i++)
        {
            ItemStack lantern = new ItemStack(BlockList.Lanterns[i], 2);
            Item lanternCore = ItemList.lanternCores[i];

            for(int k = 0; k < FluidList.alcoholFluids.length; k++)
            {
                ItemStack coreFilled = new ItemStack(lanternCore, 1, k + 1);

                GameRegistry.addRecipe(lantern, "tgy", "gsg", "tgy", 'g', glassPane, 's', coreFilled, 't', stick, 'y', yarn);
                GameRegistry.addRecipe(lantern, "ygt", "gsg", "ygt", 'g', glassPane, 's', coreFilled, 't', stick, 'y', yarn);
                GameRegistry.addRecipe(lantern, "tgy", "gsg", "tgy", 'g', glassPane, 's', coreFilled, 't', stick, 'y', linenString);
                GameRegistry.addRecipe(lantern, "ygt", "gsg", "ygt", 'g', glassPane, 's', coreFilled, 't', stick, 'y', linenString);
                GameRegistry.addRecipe(lantern, "tgy", "gsg", "tgy", 'g', glassPane, 's', coreFilled, 't', stick, 'y', silkString);
                GameRegistry.addRecipe(lantern, "ygt", "gsg", "ygt", 'g', glassPane, 's', coreFilled, 't', stick, 'y', silkString);
                GameRegistry.addRecipe(lantern, "tgy", "gsg", "tgy", 'g', glassPane, 's', coreFilled, 't', stick, 'y', cottonYarn);
                GameRegistry.addRecipe(lantern, "ygt", "gsg", "ygt", 'g', glassPane, 's', coreFilled, 't', stick, 'y', cottonYarn);
            }
        }
    }
    
    private static void registerMudBrickRecipes()
    {
        for(int i = 0; i < 16; i++)
        {
            GameRegistry.addShapelessRecipe(new ItemStack(BlockList.MudBrickRaws[i], 1, 0), new ItemStack(TFCBlocks.dirt, 1, i), new ItemStack(TFCItems.clayBall, 1, 0), new ItemStack(TFCItems.straw));
            GameRegistry.addShapelessRecipe(new ItemStack(BlockList.MudBrickRaws[i], 1, 0), new ItemStack(TFCBlocks.sand, 1, i), new ItemStack(TFCItems.clayBall, 1, 0), new ItemStack(TFCItems.straw));
        }
            
        for(int i = 0; i < Global.STONE_ALL.length - 16; i++)
        {
            GameRegistry.addShapelessRecipe(new ItemStack(BlockList.MudBrickRaws[16 + i], 1, 0), new ItemStack(TFCBlocks.dirt2, 1, i), new ItemStack(TFCItems.clayBall, 1, 0), new ItemStack(TFCItems.straw));
            GameRegistry.addShapelessRecipe(new ItemStack(BlockList.MudBrickRaws[16 + i], 1, 0), new ItemStack(TFCBlocks.sand2, 1, i), new ItemStack(TFCItems.clayBall, 1, 0), new ItemStack(TFCItems.straw));
        }

        for(int i = 0; i < 16; i++)
            GameRegistry.addRecipe(new ItemStack(BlockList.MudBricks, 4, i), "mm", "mm", 'm', new ItemStack(BlockList.MudBrickRaws[i], 1, 1));
            
        for(int i = 0; i < Global.STONE_ALL.length - 16; i++)
            GameRegistry.addRecipe(new ItemStack(BlockList.MudBricks2, 4, i), "mm", "mm", 'm', new ItemStack(BlockList.MudBrickRaws[16 + i], 1, 1));
    }
    
    private static void registerBarrelRecipes()
    {
        for(int i = 0; i < FluidList.LiquidDyes.length; i++)
        {
            DyeFluid dye = FluidList.LiquidDyes[i];
            
            BarrelManager.getInstance().addRecipe(new BarrelRecipe(new ItemStack(TFCItems.dye, 1, dye.TFCDyeIndex), new FluidStack(TFCFluids.FRESHWATER, 400), null, new FluidStack(dye, 400), 0).setMinTechLevel(0).setSealedRecipe(false).setRemovesLiquid(false).setAllowAnyStack(false));
        }
    }
    
    public static boolean areAnvilRecipesRegistered()
    {
        if(!DecorationsMod.isLanternsEnabled)
            return false;
        
        Map<String, PlanRecipe> map = AnvilManager.getInstance().getPlans();
        
        return !map.containsKey(LANTERN_CORE_PLAN);
    }
    
    public static void registerAnvilRecipes(World world)
    {
        if(!DecorationsMod.isLanternsEnabled)
            return;
        
        AnvilManager manager = AnvilManager.getInstance();
        //We need to set the world ref so that all anvil recipes can generate correctly
        AnvilManager.world = world;
        
        manager.addPlan(LANTERN_CORE_PLAN, new PlanRecipe(new RuleEnum[] { RuleEnum.HITLAST, RuleEnum.PUNCHNOTLAST, RuleEnum.HITNOTLAST }));
        
        for(int i = 0; i < Constants.Lanterns.length; i++)
        {
            LanternInfo info = Constants.Lanterns[i];
            Item sheetItem = GameRegistry.findItem("terrafirmacraftplus", info.SheetName);
            ItemStack lanternCore = new ItemStack(ItemList.lanternCores[i], 1, 0);

            manager.addRecipe(new AnvilRecipe(new ItemStack(sheetItem), null, LANTERN_CORE_PLAN, false, info.Anvil, lanternCore).addRecipeSkill(Global.SKILL_GENERAL_SMITHING));
        }
    }
}
