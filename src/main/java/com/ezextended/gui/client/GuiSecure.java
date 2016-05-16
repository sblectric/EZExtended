package com.ezextended.gui.client;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.ezextended.blocks.EEBlocks;
import com.ezextended.gui.server.ContainerSecure;
import com.ezextended.network.EENetwork;
import com.ezextended.ref.RefStrings;
import com.ezextended.tiles.TileEntitySecure;
import com.ezextended.tiles.TileEntitySecure.SecurePlayer;
import com.ezextended.util.EZUtils;
import com.ezextended.util.JointList;

/** Secure box GUI */
@SideOnly(Side.CLIENT)
public class GuiSecure extends GuiContainer {
	
	public static final ResourceLocation secureGuiTextures = new ResourceLocation(RefStrings.MODID, "textures/gui/container/secureBox.png");
	private TileEntitySecure tileSecure;
	private static final int nButtons = 7;
	private static final int buttonWidth = 80;
	private GuiButton[] addedPlayers = new GuiButton[nButtons];
	private GuiButton[] availablePlayers = new GuiButton[nButtons];
	private List<EntityPlayer> nearbyPlayers;

	public GuiSecure(InventoryPlayer invPlayer, TileEntitySecure tile, BlockPos pos) {
		super(new ContainerSecure(invPlayer, tile));
		this.tileSecure = tile;
		this.tileSecure.setPos(pos);
		this.ySize = 230;
	}
	
	@Override
	public void initGui() {
		super.initGui();
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        
        // get the buttons ready
        for(int i = 0; i < nButtons; i++) {
        	(addedPlayers[i] = new GuiButton(i, k + 4, l + 30 + i * 15, buttonWidth, 14, "")).visible = false;
        	(availablePlayers[i] = new GuiButton(i + nButtons, k + this.xSize - buttonWidth - 4, l + 30 + i * 15, buttonWidth, 14, "")).visible = false;
        }
		buttonList = new JointList<GuiButton>().join(addedPlayers).join(availablePlayers);
		
		// get players that are nearby
		nearbyPlayers = EZUtils.getNearbyPlayers(tileSecure.getWorld(), tileSecure.getPos(), 32, nButtons);
	}
	
	@Override
	protected void actionPerformed(GuiButton parButton) {
		// remove whitelist entry
		if(parButton.id < nButtons) {
			this.mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, parButton.id);
		} else { // add a whitelist entry
			EENetwork.sendSecurePlayerMsg(tileSecure.getWorld(), tileSecure.getPos(), new SecurePlayer(nearbyPlayers.get(parButton.id - nButtons)), true);
		}
	}
	
	// update the button status
	@Override
	public void updateScreen() {
		super.updateScreen();
		
		// added players
		for(int i = 0; i < nButtons; i++) {
			// added players
			if(i < tileSecure.getAllowedPlayerCount()) {
				addedPlayers[i].visible = true;
				addedPlayers[i].displayString = tileSecure.getAllowedPlayers().get(i).name;
			} else {
				addedPlayers[i].visible = false;
				addedPlayers[i].displayString = "";
			}
			
			// available players
			if(i < nearbyPlayers.size()) {
				availablePlayers[i].visible = true;
				availablePlayers[i].displayString = nearbyPlayers.get(i).getName();
				availablePlayers[i].enabled = !nameExists(availablePlayers[i].displayString);
			} else {
				availablePlayers[i].visible = false;
				availablePlayers[i].displayString = "";
			}
		}
		
	}
	
	/** Does the name exist in the added players list? */
	private boolean nameExists(String name) {
		for(int i = 0; i < nButtons; i++) {
			if(addedPlayers[i].displayString.equals(name)) return true;
		}
		return false;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		// show the titles for each section
		String string = EEBlocks.secureBox.getLocalizedName();
		this.fontRendererObj.drawString(string, this.xSize / 2 - this.fontRendererObj.getStringWidth(string) / 2, 6, 0x404040);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 94, 0x404040);
		
		// now show subtitles
		this.fontRendererObj.drawString("Allowed Players", 4, 18, 0x606060);
		this.fontRendererObj.drawString("Nearby Players", this.xSize - buttonWidth - 4, 18, 0x606060);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		 GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.mc.getTextureManager().bindTexture(secureGuiTextures);
	        int k = (this.width - this.xSize) / 2;
	        int l = (this.height - this.ySize) / 2;
	        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}

}