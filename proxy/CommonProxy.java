package com.chrisps.testing.proxy;

import net.minecraft.entity.player.EntityPlayer;

import com.chrisps.testing.testingBaseClass;
import com.chrisps.testing.GUI.GuiHandler;
import com.chrisps.testing.tileEntities.TileEntityPyrolysisChamber;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		 return ctx.getServerHandler().playerEntity;
		}
	
	public void registerTileEntities(){
		GameRegistry.registerTileEntity(TileEntityPyrolysisChamber.class, TileEntityPyrolysisChamber.name);
	}
	
	public void registerNetworkItems() {
		
		NetworkRegistry.INSTANCE.registerGuiHandler(testingBaseClass.instance, new GuiHandler());		
	}

}
