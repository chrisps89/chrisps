package com.chrisps.testing.ModBlocks;

import com.chrisps.testing.lib.Constants;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;

public class TestingBlock extends Block {
	
	public String name = "TestingBlock";
	
	private IIcon[] icons = new IIcon[6];

	protected TestingBlock() {
		super(Material.rock);
		setBlockName(Constants.MODID + "_" + name);
		setCreativeTab(CreativeTabs.tabBlock);
		GameRegistry.registerBlock(this, name);
	}
	/**Multi Texture Numbers to Sides, 0=Bottom, 1=Top, 2=Front, 3=Back, 4=Right, 5=Left **/
	@Override
	public void registerBlockIcons(IIconRegister iconRegister){
		for(int i = 0; i < icons.length; i++){
			icons[i] = iconRegister.registerIcon(Constants.MODID + ":" + name + i); 
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta){
		return icons[side];
		
	}

}
