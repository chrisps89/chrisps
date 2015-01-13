package com.chrisps.testing.inventory;

import com.chrisps.testing.tileEntities.TileEntityPyrolysisChamber;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerPyrolysisChamber extends Container{
	
    private TileEntityPyrolysisChamber tilePyrolysisChamber;
    private int lastCookTime;
    private int lastBurnTime;
    private int lastItemBurnTime;
	
	public ContainerPyrolysisChamber(InventoryPlayer playerInv, TileEntityPyrolysisChamber tePyrolysisChamber){
		this.tilePyrolysisChamber = tePyrolysisChamber;
		
		this.addSlotToContainer(new Slot(tePyrolysisChamber, 0, 56, -7));
		
        this.addSlotToContainer(new Slot(tePyrolysisChamber, 1, 56, 29));
        
        this.addSlotToContainer(new SlotPyrolysisChamber(playerInv.player, tePyrolysisChamber, 2, 116, 11));
        
        System.out.println(this.inventorySlots);
        
        int i;
        

        for (i = 0; i < 3; ++i) {
        	
            for (int j = 0; j < 9; ++j) {
            	
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 60 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
        	
            this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 118));
        }
    }
	
    public void addCraftingToCrafters(ICrafting craft) {
    	
        super.addCraftingToCrafters(craft);
        craft.sendProgressBarUpdate(this, 0, this.tilePyrolysisChamber.pyrolysisChamberCookTime);
        craft.sendProgressBarUpdate(this, 1, this.tilePyrolysisChamber.pyrolysisChamberBurnTime);
        craft.sendProgressBarUpdate(this, 2, this.tilePyrolysisChamber.currentItemBurnTime);
    }
    
    public void detectAndSendChanges() {
    	
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
        	
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            	if (this.lastCookTime != this.tilePyrolysisChamber.pyrolysisChamberCookTime) {
            	
            		icrafting.sendProgressBarUpdate(this, 0, this.tilePyrolysisChamber.pyrolysisChamberCookTime);
            	}

            	if (this.lastBurnTime != this.tilePyrolysisChamber.pyrolysisChamberBurnTime) {
            		
                icrafting.sendProgressBarUpdate(this, 1, this.tilePyrolysisChamber.pyrolysisChamberBurnTime);
            	}

            	if (this.lastItemBurnTime != this.tilePyrolysisChamber.currentItemBurnTime) {
            		
                icrafting.sendProgressBarUpdate(this, 2, this.tilePyrolysisChamber.currentItemBurnTime);
            	}
        }

        this.lastCookTime = this.tilePyrolysisChamber.pyrolysisChamberCookTime;
        this.lastBurnTime = this.tilePyrolysisChamber.pyrolysisChamberBurnTime;
        this.lastItemBurnTime = this.tilePyrolysisChamber.currentItemBurnTime;
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int int1, int int2) {
    	
        if (int1 == 0) {
        	
            this.tilePyrolysisChamber.pyrolysisChamberCookTime = int2;
        }

        if (int1 == 1) {
        	
            this.tilePyrolysisChamber.pyrolysisChamberBurnTime = int2;
        }

        if (int1 == 2) {
        	
            this.tilePyrolysisChamber.currentItemBurnTime = int2;
        }
    }

    public boolean canInteractWith(EntityPlayer entity) {
    	
        return this.tilePyrolysisChamber.isUseableByPlayer(entity);
    }
    
    public ItemStack transferStackInSlot(EntityPlayer player, int int2)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(int2);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (int2 == 2)
            {
                if (!this.mergeItemStack(itemstack1, 3, 39, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (int2 != 1 && int2 != 0)
            {
                if (FurnaceRecipes.smelting().getSmeltingResult(itemstack1) != null)
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (TileEntityFurnace.isItemFuel(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false))
                    {
                        return null;
                    }
                }
                else if (int2 >= 3 && int2 < 30)
                {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false))
                    {
                        return null;
                    }
                }
                else if (int2 >= 30 && int2 < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 3, 39, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }

}
