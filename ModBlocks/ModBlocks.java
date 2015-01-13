package com.chrisps.testing.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {
	
	public static Block testingBlock;
	public static Block multiBlock;
	public static Block pyrolysisChamber;
	public static Block pyrolysisChamberActive;
	
	public static void init() {
		
		testingBlock = new TestingBlock();
		multiBlock = new MultiBlock();
		pyrolysisChamber = new PyrolysisChamber(false);
		pyrolysisChamberActive = new PyrolysisChamber(true);
		
	}

}
