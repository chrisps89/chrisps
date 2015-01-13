package com.chrisps.testing.GUI;

import org.lwjgl.opengl.GL11;

import com.chrisps.testing.inventory.ContainerPyrolysisChamber;
import com.chrisps.testing.lib.Constants;
import com.chrisps.testing.tileEntities.TileEntityPyrolysisChamber;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiPyrolysisChamber extends GuiContainer{

	public GuiPyrolysisChamber(InventoryPlayer playerInv, TileEntityPyrolysisChamber tePyrolysisChamber) {
		super((new ContainerPyrolysisChamber(playerInv, tePyrolysisChamber)));
		this.tilePyrolysisChamber = tePyrolysisChamber;
	}

	private int x, y, z;
	
	public TileEntityPyrolysisChamber tilePyrolysisChamber;

	private EntityPlayer player;

	private World world;
	
	private int xSize = 176;
	private int ySize = 214;
	
	private ResourceLocation GuiLoc = new ResourceLocation(Constants.MODID.toLowerCase() + ":" + "textures/gui/GuiPyrolysisChamber.png");
	
	@Override
    public boolean doesGuiPauseGame() {
        return false;
    }
	
    protected void drawGuiContainerForegroundLayer(int int1, int int2){
    	
        String s = this.tilePyrolysisChamber.hasCustomInventoryName() ? this.tilePyrolysisChamber.getInventoryName() : I18n.format(this.tilePyrolysisChamber.getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, -18, 000000);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 165, 000000);
    }

    protected void drawGuiContainerBackgroundLayer(float float1, int int2, int int3) {
    	
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GuiLoc);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        if (this.tilePyrolysisChamber.isBurning()) {
        	
            int i1 = this.tilePyrolysisChamber.getBurnTimeRemainingScaled(13);
            this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 1);
            i1 = this.tilePyrolysisChamber.getCookProgressScaled(24);
            this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
        }
    }
}
