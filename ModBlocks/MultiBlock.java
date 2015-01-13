package com.chrisps.testing.ModBlocks;

import java.util.List;

import com.chrisps.testing.items.ItemBlockMultiBlock;
import com.chrisps.testing.lib.Constants;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class MultiBlock extends Block {
	
	public final String name = "multiBlock";
	public final int maxMeta = 4;
	
	private IIcon[] icons;

	protected MultiBlock() {
		super(Material.rock);
		setBlockName(Constants.MODID + "_" + name);
		setCreativeTab(CreativeTabs.tabBlock);
		icons = new IIcon[maxMeta];
		GameRegistry.registerBlock(this, ItemBlockMultiBlock.class, name);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister){
		for (int i = 0; i < icons.length; i++){
			iconRegister.registerIcon(Constants.MODID + ":" + name + i);
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta){

        switch(meta) {
        
        	case 0: return icons[0];
        	case 1: switch(side) {

        			case 0: return icons[1];
        			case 1: return icons[2];
        			default: return icons[3];
        	}
        	
        	case 2: return icons[1];
        	case 3: return icons[2];
        	case 4: return icons[3];
        	default: return icons[0];		
        }
	}
	
	@Override
	public int damageDropped(int meta){
		return meta;
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list){
		
		for(int i = 0; i < maxMeta; i++){
			
			list.add(new ItemStack(item, 1, i));
		}
	}
}
