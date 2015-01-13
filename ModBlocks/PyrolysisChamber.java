package com.chrisps.testing.ModBlocks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.chrisps.testing.testingBaseClass;
import com.chrisps.testing.lib.Constants;
import com.chrisps.testing.tileEntities.TileEntityPyrolysisChamber;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class PyrolysisChamber extends Block implements ITileEntityProvider{
	
	public static String name = "pyrolysisChamber";
	public static String nameActive = "pyrolysisChamberActive";
	private String privateName = "pyrolysisChamber";
	private String privateNameActive = "pyrolysisChamberActive";
	
	public IIcon Bottom;
	public IIcon Top;
	public IIcon Front;
	public IIcon Back;
	public IIcon Left;
	public IIcon Right;
	
	private final Random random = new Random();
	
	private static boolean litCheck;
	
	public boolean isActive;
	
	protected PyrolysisChamber(boolean active) {
		super(Material.rock);
		if(!active) {
			setBlockName(Constants.MODID + "_" + name);
			setCreativeTab(CreativeTabs.tabBlock);
			GameRegistry.registerBlock(this, privateName);
			setHardness(3.0F);
			isActive = active;
		} else if (active) {
			
			setBlockName(Constants.MODID + "_" + nameActive);
			GameRegistry.registerBlock(this, privateNameActive);
			setHardness(3.0F);
			isActive = active;
		}

	}
	
	public enum GUIs {
	    PYROLYSISCHAMBER
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta){
		return new TileEntityPyrolysisChamber();
	}
	
	@Override
	public boolean hasTileEntity(int metadata){
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase entLivBase, ItemStack itemStack) {
		int facing = MathHelper
				.floor_double(entLivBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (facing == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2); // South - Front is
																// Front.
		}

		if (facing == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2); // West - Front is
																// Left.
		}

		if (facing == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2); // North - Back is
																// Front.
		}

		if (facing == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2); // East - Front is
																// Right
		}

	}

	
	public void registerBlockIcons(IIconRegister icon) {
		Bottom = icon.registerIcon(Constants.MODID + ":" + "pyrolysisChamber0");
		Top = icon.registerIcon(Constants.MODID + ":" + "pyrolysisChamber1");
		if (!isActive) {
			Front = icon.registerIcon(Constants.MODID + ":" + "pyrolysisChamber2");
		} else if (isActive) {
			Front = icon.registerIcon(Constants.MODID + ":" + "pyrolysisChamber2active");
		}
		Back = icon.registerIcon(Constants.MODID + ":" + "pyrolysisChamber3");
		Left = icon.registerIcon(Constants.MODID + ":" + "pyrolysisChamber4");
		Right = icon.registerIcon(Constants.MODID + ":" + "pyrolysisChamber5");
	}
	
    public static void updatePyrolysisChamber(boolean active, World world, int x, int y, int z) {
    	
        int l = world.getBlockMetadata(x, y, z);
        
        TileEntityPyrolysisChamber tileentity = (TileEntityPyrolysisChamber) world.getTileEntity(x, y, z);
        
       // litCheck = true;

        if (active) {
        	
            world.setBlock(x, y, z, ModBlocks.pyrolysisChamberActive);
        } else {
        	
            world.setBlock(x, y, z, ModBlocks.pyrolysisChamber);
        }

       // litCheck = false;
        
        world.setBlockMetadataWithNotify(x, y, z, l, 2);

        if (tileentity != null)
        {
            tileentity.validate();
            world.setTileEntity(x, y, z, tileentity);
        }
    }
	
	@Override
	public IIcon getIcon(int side, int meta) {

		switch (meta) {
		case 2:
			if (side == 2) {
				return Front;
			} else if (side == 3) {
				return Back;
			} else if (side == 4) {
				return Right;
			} else if (side == 5) {
				return Left;
			}
		case 5:
			if (side == 2) {
				return Right;
			} else if (side == 3) {
				return Left;
			} else if (side == 4) {
				return Back;
			} else if (side == 5) {
				return Front;
			}
		case 3:
			if (side == 2) {
				return Back;
			} else if (side == 3) {
				return Front;
			} else if (side == 4) {
				return Left;
			} else if (side == 5) {
				return Right;
			}
		case 4:
			if (side == 2) {
				return Left;
			} else if (side == 3) {
				return Right;
			} else if (side == 4) {
				return Front;
			} else if (side == 5) {
				return Back;
			}
		case 0:
			if (side == 0) {
				return Bottom;
			} else if (side == 1) {
				return Top;
			} else if (side == 2) {
				return Back;
			} else if (side == 3) {
				return Front;
			} else if (side == 4) {
				return Left;
			} else if (side == 5) {
				return Right;
			}
		}
		return null;
	}
	
	@Override
	public int damageDropped(int meta){
		return meta;
	}
	
	
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ) {
            if (world.getTileEntity(x, y, z) != null){
                player.openGui(testingBaseClass.instance, GUIs.PYROLYSISCHAMBER.ordinal(), world, x, y, z);
            return true;
        }
        return true;
    }
    
    public void breakBlock(World world, int x, int y, int z, Block block, int var6) {
    	
    	if(!isActive) {
    	TileEntityPyrolysisChamber tileEntityPyrolysisChamber = (TileEntityPyrolysisChamber)world.getTileEntity(x, y, z);
    	
        if (tileEntityPyrolysisChamber != null) {
            for (int i = 0; i < tileEntityPyrolysisChamber.getSizeInventory(); ++i)
            {
                ItemStack itemstack = tileEntityPyrolysisChamber.getStackInSlot(i);

                if (itemstack != null)
                {
                    float f = this.random.nextFloat() * 0.4F + 0.1F;
                    float f1 = this.random.nextFloat() * 0.4F + 0.1F;
                    float f2 = this.random.nextFloat() * 0.4F + 0.1F;

                    while (itemstack.stackSize > 0) {
                        int j1 = this.random.nextInt(21) + 10;

                        if (j1 > itemstack.stackSize)
                        {
                            j1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= j1;
                        
                        EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                        if (itemstack.hasTagCompound())
                        {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }

                        float f3 = 0.05F;
                        
                        entityitem.motionX = (double)((float)this.random.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)this.random.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)this.random.nextGaussian() * f3);
                        
                        world.spawnEntityInWorld(entityitem);
                    }
                }
            }
        }

            world.func_147453_f(x, y, z, block);
            }

    super.breakBlock(world, x, y, z, this, var6);
}
    
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(ModBlocks.pyrolysisChamber);
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z)
    {
        return Item.getItemFromBlock(ModBlocks.pyrolysisChamber);
    }

}
