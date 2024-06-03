package com.menu.microwaveoven;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;


@OnlyIn(Dist.CLIENT)
public class MicrowaveOvenMenuButton extends AbstractButton {

    private final ResourceLocation resourceLocation;
    private final MicrowaveOvenMenu menu;
    private final MicrowaveOvenScreen screen;
    private static final Component textComponent = Component.translatable("button.microwaveoven");
    
    private int leftPos;
    private int imageHeight;
    private int imageWidth;

    public MicrowaveOvenMenuButton(MicrowaveOvenScreen screen, MicrowaveOvenMenu menu, int imgh, int imgw, int lpos, ResourceLocation rl) {
    	super(lpos + 73,((screen.height - imgh) / 2) + 15,30,63,textComponent);
        this.menu = menu;
        this.screen = screen;
        this.resourceLocation = rl;
        leftPos = lpos;
        imageHeight = imgh;
        imageWidth = imgw;
    }


    public void renderButton(PoseStack p_94282_, int p_94283_, int p_94284_, float p_94285_){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.resourceLocation);

        int i = this.leftPos;
        int j = (screen.height - this.imageHeight) / 2;
        int k = 60;

        RenderSystem.enableDepthTest();
    }

    @Override
    public void onPress() {
        //menu.sandItems();
    }


	@Override
	protected void updateWidgetNarration(NarrationElementOutput p_259858_) {
		// TODO 自动生成的方法存根
		
	}
}
