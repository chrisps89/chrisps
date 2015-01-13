package com.chrisps.testing;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

import com.chrisps.testing.GUI.GuiHandler;
import com.chrisps.testing.ModBlocks.ModBlocks;
import com.chrisps.testing.lib.Constants;
import com.chrisps.testing.proxy.CommonProxy;

@Mod(modid = Constants.MODID, name = Constants.MODNAME, version = Constants.VERSION)

public class testingBaseClass {
	
	@Mod.Instance(Constants.MODID)
	public static testingBaseClass instance;
	
	@SidedProxy(clientSide = Constants.CLIENT_PROXY, serverSide = Constants.COMMON_PROXY)
	public static CommonProxy proxy;
	
	@Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
		ModBlocks.init();
    }
 
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    	
    	proxy.registerTileEntities();
    	proxy.registerNetworkItems();
    }
 
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	
    }
}
