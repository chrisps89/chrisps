package com.chrisps.testing.inventory;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.MathHelper;

public class SlotPyrolysisChamber extends Slot {

    private EntityPlayer thePlayer;
    private int int1;

    public SlotPyrolysisChamber(EntityPlayer player, IInventory Iinv, int var3, int var4, int var5) {
        super(Iinv, var3, var4, var5);
        this.thePlayer = player;
    }
    
    public boolean isItemValid(ItemStack itemstack)
    {
        return false;
    }
    
    public ItemStack decrStackSize(int decr)
    {
        if (this.getHasStack())
        {
            this.int1 += Math.min(decr, this.getStack().stackSize);
        }

        return super.decrStackSize(decr);
    }
    
    protected void onCrafting(ItemStack itemstack1)
    {
    	itemstack1.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.int1);

        if (!this.thePlayer.worldObj.isRemote)
        {
            int i = this.int1;
            float f = FurnaceRecipes.smelting().func_151398_b(itemstack1);
            int j;

            if (f == 0.0F)
            {
                i = 0;
            }
            else if (f < 1.0F)
            {
                j = MathHelper.floor_float((float)i * f);

                if (j < MathHelper.ceiling_float_int((float)i * f) && (float)Math.random() < (float)i * f - (float)j)
                {
                    ++j;
                }

                i = j;
            }

            while (i > 0)
            {
                j = EntityXPOrb.getXPSplit(i);
                i -= j;
                this.thePlayer.worldObj.spawnEntityInWorld(new EntityXPOrb(this.thePlayer.worldObj, this.thePlayer.posX, this.thePlayer.posY + 0.5D, this.thePlayer.posZ + 0.5D, j));
            }
        }
        this.int1 = 0;

        FMLCommonHandler.instance().firePlayerSmeltedEvent(thePlayer, itemstack1);

       /** if (itemstack1.getItem() == Items.iron_ingot)
        {
            this.thePlayer.addStat(AchievementList.acquireIron, 1);
        }

        if (itemstack1.getItem() == Items.cooked_fished)
        {
            this.thePlayer.addStat(AchievementList.cookFish, 1);
        }**/
    }
    
    public void onPickupFromSlot(EntityPlayer player, ItemStack itemstack)
    {
        this.onCrafting(itemstack);
        super.onPickupFromSlot(player, itemstack);
    }

}
