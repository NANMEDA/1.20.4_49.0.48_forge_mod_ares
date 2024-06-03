package com.menu.microwaveoven;


import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;

public class MicrowaveOvenScreen extends AbstractContainerScreen<MicrowaveOvenMenu> {
	public static final String MODID = "maring";
	public static final ResourceLocation GUI = new ResourceLocation(MODID,"textures/gui/container/microwave_oven.png");
	//private Button button;
	
	public MicrowaveOvenScreen(MicrowaveOvenMenu pMenu, Inventory pInventory, Component pComponent) {
		super(pMenu, pInventory, pComponent);
	}

	@Override
	protected void renderBg(GuiGraphics pGraphics, float pPartialTick, int pMousex, int pMousey) {
		pGraphics.blit(GUI,this.leftPos,this.topPos,0,0,this.imageWidth,this.imageHeight);
	}
	
	@Override
    protected void init() {
		/*
        this.button = new Button.Builder(Component.translatable("maring.gui.microwave.button"), pButton -> {
        	System.out.println("HERE");
        	onClose();
        }).pos(this.width / 2 - 40, this.height-40).size(80, 20).build();*/
        super.init();
    }
	
	@Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        //this.button.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

	
/*
    private void onButtonClick(Button button) {
        // Handle button click
        if (this.menu.getBlockEntity() instanceof PowerStationBurnEntity) {
            PowerStationBurnEntity blockEntity = (PowerStationBurnEntity) this.menu.getBlockEntity();
            blockEntity.setClicking(true);  // Make sure you have a setClicking method in your block entity
            // Send a packet to the server to synchronize the state if needed
        }
    }*/
    
}