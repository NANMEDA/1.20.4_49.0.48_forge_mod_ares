package com.menu.register;

import block.entity.register.PowerStationBurnEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.server.command.ModIdArgument;
import net.minecraft.resources.ResourceLocation;

public class MicrowaveOvenScreen extends AbstractContainerScreen<MicrowaveOvenMenu> {
	public static final String MODID = "maring";
	public static final ResourceLocation GUI = new ResourceLocation(MODID,"textures/gui/container/microwave_oven.png");
	
	public MicrowaveOvenScreen(MicrowaveOvenMenu pMenu, Inventory pInventory, Component pComponent) {
		super(pMenu, pInventory, pComponent);
	}

	@Override
	protected void renderBg(GuiGraphics pGraphics, float pPartialTick, int pMousex, int pMousey) {
		pGraphics.blit(GUI,this.leftPos,this.topPos,0,0,this.imageWidth,this.imageHeight);
		
	}
	
	@Override
    protected void init() {
        super.init();

        // Initialize and add the button
        //this.button = new Button(this.leftPos + 57, this.topPos + 8, 60, 20, Component.literal("Click Me"), this::onButtonClick);
        //this.addRenderableWidget(this.button);
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