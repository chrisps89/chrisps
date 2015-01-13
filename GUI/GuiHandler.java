package com.chrisps.testing.GUI;

import com.chrisps.testing.ModBlocks.PyrolysisChamber.GUIs;
import com.chrisps.testing.inventory.ContainerPyrolysisChamber;
import com.chrisps.testing.tileEntities.TileEntityPyrolysisChamber;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		
		TileEntityPyrolysisChamber tile = (TileEntityPyrolysisChamber) world.getTileEntity(x, y, z);
		
		if (tile instanceof TileEntityPyrolysisChamber){
			return new ContainerPyrolysisChamber(player.inventory, (TileEntityPyrolysisChamber) tile);
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		
		TileEntityPyrolysisChamber tile = (TileEntityPyrolysisChamber) world.getTileEntity(x, y, z);
		
		if(ID == GUIs.PYROLYSISCHAMBER.ordinal()) return new GuiPyrolysisChamber(player.inventory, (TileEntityPyrolysisChamber) tile);
		
		return null;
	}

}
