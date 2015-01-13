package com.chrisps.testing.tileEntities;

import com.chrisps.testing.ModBlocks.PyrolysisChamber;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPyrolysisChamber extends TileEntity implements ISidedInventory {
	
	public static String name = "TileEntityPyrolysisChamber";
	
    private static final int[] slotsTop = new int[] {0};
    private static final int[] slotsBottom = new int[] {2, 1};
    private static final int[] slotsSides = new int[] {1};
    
    private ItemStack[] pyrolysisChamberItemStacks = new ItemStack[39];
    
    public int pyrolysisChamberBurnTime;
    public int currentItemBurnTime;
    public int pyrolysisChamberCookTime;
    
    private String machineName;
    
	@Override
	public int getSizeInventory() {
		
		return this.pyrolysisChamberItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int slotInt) {
		
		return this.pyrolysisChamberItemStacks[slotInt];
	}

	@Override
	public ItemStack decrStackSize(int int1, int int2) {
		
		if (this.pyrolysisChamberItemStacks[int1] != null)
        {
            ItemStack itemstack;

            if (this.pyrolysisChamberItemStacks[int1].stackSize <= int2)
            {
                itemstack = this.pyrolysisChamberItemStacks[int1];
                this.pyrolysisChamberItemStacks[int1] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.pyrolysisChamberItemStacks[int1].splitStack(int2);

                if (this.pyrolysisChamberItemStacks[int1].stackSize == 0)
                {
                    this.pyrolysisChamberItemStacks[int1] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
	}
	
	

	@Override
	public ItemStack getStackInSlotOnClosing(int int1) {
		
		if (this.pyrolysisChamberItemStacks[int1] != null) {
			
            ItemStack itemstack = this.pyrolysisChamberItemStacks[int1];
            this.pyrolysisChamberItemStacks[int1] = null;
            return itemstack;
        } else {
        	
            return null;
        }
	}

	@Override
	public void setInventorySlotContents(int int1, ItemStack itemstack) {
		
		this.pyrolysisChamberItemStacks[int1] = itemstack;

        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
        	
            itemstack.stackSize = this.getInventoryStackLimit();
        }
		
	}

	@Override
	public String getInventoryName() {
		
		return this.hasCustomInventoryName() ? this.machineName : "Pyrolysis Chamber";
	}

	@Override
	public boolean hasCustomInventoryName() {
		
		return this.machineName != null && this.machineName.length() > 0;
	}
	
    public void setName(String name) {
    	
        this.machineName = name;
    }
    
    public void readFromNBT(NBTTagCompound nbtCompound) {
    	
        super.readFromNBT(nbtCompound);
        NBTTagList nbttaglist = nbtCompound.getTagList("Items", 10);
        this.pyrolysisChamberItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.pyrolysisChamberItemStacks.length)
            {
                this.pyrolysisChamberItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.pyrolysisChamberBurnTime = nbtCompound.getShort("BurnTime");
        this.pyrolysisChamberCookTime = nbtCompound.getShort("CookTime");
        this.currentItemBurnTime = getItemBurnTime(this.pyrolysisChamberItemStacks[1]);

        if (nbtCompound.hasKey("CustomName", 8))
        {
            this.machineName = nbtCompound.getString("CustomName");
        }
    }

    public void writeToNBT(NBTTagCompound nbtCompound) {
    	
        super.writeToNBT(nbtCompound);
        nbtCompound.setShort("BurnTime", (short)this.pyrolysisChamberBurnTime);
        nbtCompound.setShort("CookTime", (short)this.pyrolysisChamberCookTime);

        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.pyrolysisChamberItemStacks.length; ++i)
        {
            if (this.pyrolysisChamberItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.pyrolysisChamberItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbtCompound.setTag("Items", nbttaglist);

        if (this.hasCustomInventoryName())
        {
            nbtCompound.setString("CustomName", this.machineName);
        }
    }

	@Override
	public int getInventoryStackLimit() {
	
		return 64;
	}
	
	
	
    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int int1) {
    	
        return this.pyrolysisChamberCookTime * int1 / 200;
    }
    
    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int time) {
    	
        if (this.currentItemBurnTime == 0)
        {
            this.currentItemBurnTime = 200;
        }

        return this.pyrolysisChamberBurnTime * time / this.currentItemBurnTime;
    }
    
    public boolean isBurning() {
    	
        return this.pyrolysisChamberBurnTime > 0;
    }
    
    public void updateEntity() {
    	
        boolean flag = this.pyrolysisChamberBurnTime > 0;
        boolean flag1 = false;

        if (this.pyrolysisChamberBurnTime > 0)
        {
            --this.pyrolysisChamberBurnTime;
        }

        if (!this.worldObj.isRemote)
        {
            if (this.pyrolysisChamberBurnTime != 0 || this.pyrolysisChamberItemStacks[1] != null && this.pyrolysisChamberItemStacks[0] != null)
            {
                if (this.pyrolysisChamberBurnTime == 0 && this.canSmelt())
                {
                    this.currentItemBurnTime = this.pyrolysisChamberBurnTime = getItemBurnTime(this.pyrolysisChamberItemStacks[1]);

                    if (this.pyrolysisChamberBurnTime > 0)
                    {
                        flag1 = true;

                        if (this.pyrolysisChamberItemStacks[1] != null)
                        {
                            --this.pyrolysisChamberItemStacks[1].stackSize;

                            if (this.pyrolysisChamberItemStacks[1].stackSize == 0)
                            {
                                this.pyrolysisChamberItemStacks[1] = pyrolysisChamberItemStacks[1].getItem().getContainerItem(pyrolysisChamberItemStacks[1]);
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt())
                {
                    ++this.pyrolysisChamberCookTime;

                    if (this.pyrolysisChamberCookTime == 200)
                    {
                        this.pyrolysisChamberCookTime = 0;
                        this.smeltItem();
                        flag1 = true;
                    }
                }
                else
                {
                    this.pyrolysisChamberCookTime = 0;
                }
            }

            if (flag != this.pyrolysisChamberBurnTime > 0)
            {
                flag1 = true;
                
                PyrolysisChamber.updatePyrolysisChamber(this.pyrolysisChamberBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
    }
    
    private boolean canSmelt() {
    	
        if (this.pyrolysisChamberItemStacks[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.pyrolysisChamberItemStacks[0]);
            if (itemstack == null) return false;
            if (this.pyrolysisChamberItemStacks[2] == null) return true;
            if (!this.pyrolysisChamberItemStacks[2].isItemEqual(itemstack)) return false;
            int result = pyrolysisChamberItemStacks[2].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.pyrolysisChamberItemStacks[2].getMaxStackSize(); //Forge BugFix: Make it respect stack sizes properly.
        }
    }
    
    public void smeltItem() {
    	
        if (this.canSmelt())
        {
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.pyrolysisChamberItemStacks[0]);

            if (this.pyrolysisChamberItemStacks[2] == null)
            {
                this.pyrolysisChamberItemStacks[2] = itemstack.copy();
            }
            else if (this.pyrolysisChamberItemStacks[2].getItem() == itemstack.getItem())
            {
                this.pyrolysisChamberItemStacks[2].stackSize += itemstack.stackSize; // Forge BugFix: Results may have multiple items
            }

            --this.pyrolysisChamberItemStacks[0].stackSize;

            if (this.pyrolysisChamberItemStacks[0].stackSize <= 0)
            {
                this.pyrolysisChamberItemStacks[0] = null;
            }
        }
    }
    
    public static int getItemBurnTime(ItemStack itemstack) {
    	
        if (itemstack == null)
        {
            return 0;
        }
        else
        {
            Item item = itemstack.getItem();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air)
            {
                Block block = Block.getBlockFromItem(item);

                if (block == Blocks.wooden_slab)
                {
                    return 150;
                }

                if (block.getMaterial() == Material.wood)
                {
                    return 300;
                }

                if (block == Blocks.coal_block)
                {
                    return 16000;
                }
            }

            if (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equals("WOOD")) return 200;
            if (item instanceof ItemSword && ((ItemSword)item).getToolMaterialName().equals("WOOD")) return 200;
            if (item instanceof ItemHoe && ((ItemHoe)item).getToolMaterialName().equals("WOOD")) return 200;
            if (item == Items.stick) return 100;
            if (item == Items.coal) return 1600;
            if (item == Items.lava_bucket) return 20000;
            if (item == Item.getItemFromBlock(Blocks.sapling)) return 100;
            if (item == Items.blaze_rod) return 2400;
            return GameRegistry.getFuelValue(itemstack);
        }
    }
    
    public static boolean isItemFuel(ItemStack itemstack){
    	
        return getItemBurnTime(itemstack) > 0;
    }

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int int1, ItemStack itemstack) {
		
		return int1 == 2 ? false : (int1 == 1 ? isItemFuel(itemstack) : true);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int int1) {
		
		return int1 == 0 ? slotsBottom : (int1 == 1 ? slotsTop : slotsSides);
	}

	@Override
	public boolean canInsertItem(int int1, ItemStack itemstack, int int3) {
		return this.isItemValidForSlot(int1, itemstack);
	}
	
	

	@Override
	public boolean canExtractItem(int int1, ItemStack itemstack, int int3) {
		
		return int3 != 0 || int1 != 1 || itemstack.getItem() == Items.bucket;
	}
	
	
}