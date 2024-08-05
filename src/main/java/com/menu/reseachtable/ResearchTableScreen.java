package com.menu.reseachtable;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.network.PacketDistributor;
import network.NetworkHandler;
import network.client.CResearchTableUpdate;
import network.client.CRocketStart;
import block.entity.neutral.researchtable.ResearchTableEntity;

public class ResearchTableScreen extends AbstractContainerScreen<ResearchTableMenu> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation("maring", "textures/gui/container/researchtable_menu.png");
    private static final ResourceLocation BACKGROUND_M = new ResourceLocation("maring", "textures/gui/container/researchtable_menu_m.png");
    private static final int SCROLLBAR_WIDTH = 6;
    private static final int SCROLLBAR_HEIGHT = 14;
    private static final int SCROLLBAR_X_OFFSET = 240;
    private static final int SCROLLBAR_Y_OFFSET = 18;

    private int scrollOffset = 0;
    private boolean isScrolling = false;
    private boolean useAlternateBackground = false; // Flag to switch backgrounds
    private List<ColorBox> colorBoxes = new ArrayList<>();

    public ResearchTableScreen(ResearchTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        if (useAlternateBackground) {
        	this.imageWidth = 176;
        	this.renderTooltip(guiGraphics, mouseX, mouseY);
            renderAlternateBackground(guiGraphics, partialTicks, mouseX, mouseY);
        } else {
        	this.imageWidth = 256;
            renderMainBackground(guiGraphics, partialTicks, mouseX, mouseY);
        }
    }

    private void renderMainBackground(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, BACKGROUND);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(BACKGROUND, x, y, 0, 0, this.imageWidth, this.imageHeight);

        ResearchTableEntity blockEntity = (ResearchTableEntity) this.getMenu().getBlockEntity();
        int lines = blockEntity.getLines();
        // Draw labels
        Font font = this.font;
        int eachWidth = this.imageWidth / (lines + 1);
        for (int i = 1; i < lines + 1; i++) {
            String Name = blockEntity.getTechName(i - 1);
            int labelWidth = font.width(Name);
            int labelX = x + eachWidth * i - labelWidth / 2; // Center the label
            Name = Component.translatable(Name).getString();
            guiGraphics.drawString(font, Name, labelX, y + 10, 0xFFFFFF);
        }

        // Define the clipping area
        int clipStartY = y + 23;  // start of clipping area (adjust as needed)
        int clipEndY = (this.height + this.imageHeight) / 2 - 28;  // end of clipping area
        guiGraphics.enableScissor(x, clipStartY, x + this.imageWidth, clipEndY);

        // Clear previous color boxes
        colorBoxes.clear();

        // Draw status texts and color boxes with scroll offset
        for (int k = 1; k < lines + 1; k++) {
            String[] state = blockEntity.getLineState(k - 1);
            
            int now = blockEntity.getTechLevel(k - 1);
            renderColorBox(guiGraphics, x + eachWidth * k, y + 15, eachWidth, now, state.length - now, k - 1);
            renderTechLine(guiGraphics, font, state, x, y, clipStartY, clipEndY, eachWidth * k);
        }

        guiGraphics.disableScissor();
        
        // Draw scrollbar
        guiGraphics.fill(x + SCROLLBAR_X_OFFSET, y + SCROLLBAR_Y_OFFSET + scrollOffset, x + SCROLLBAR_X_OFFSET + SCROLLBAR_WIDTH, y + SCROLLBAR_Y_OFFSET + scrollOffset + SCROLLBAR_HEIGHT, 0xFFAAAAAA);
    }

    private void renderAlternateBackground(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, BACKGROUND_M);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(BACKGROUND_M, x, y, 0, 0, this.imageWidth, this.imageHeight);

        // Render the button to switch back
        int buttonX = x + (this.imageWidth / 2) - 40;
        int buttonY = y + this.imageHeight - 30 - this.inventoryLabelY;
        guiGraphics.fill(buttonX, buttonY, buttonX + 80, buttonY + 20, 0xFFAAAAAA);
        guiGraphics.drawString(this.font, "<--", buttonX + 30, buttonY + 6, 0xFFFFFF);
    }

    private void renderTechLine(GuiGraphics guiGraphics, Font font, String[] state, int x, int y, int clipStartY, int clipEndY, int width) {
        int statusY = y + 30 - scrollOffset;
        int labelWidth;
        int labelX;
        for (String tech : state) {
        	tech = Component.translatable(tech).getString();
            labelWidth = font.width(tech);
            labelX = x + width - labelWidth / 2; // Center the label
            if (statusY >= clipStartY && statusY <= clipEndY - 10) { // Ensure the text is within clipping area
                guiGraphics.drawString(font, tech, labelX, statusY, 0xFFFFFF);
            }
            statusY += 30;
        }
    }

    private void renderColorBox(GuiGraphics guiGraphics, int x, int startY, int width, int greenCount, int nongreenCount, int col) {
        int boxHeight = 24; // Height of each box
        int boxWidth = width - 6; // Width of each box, adjusted
        int y = startY - scrollOffset;

        // Render green boxes
        for (int i = 0; i < greenCount; i++) {
            int boxX = x - boxWidth / 2;
            int boxY = y + boxHeight / 2 - 4;
            guiGraphics.fill(boxX, boxY, x + boxWidth / 2, y + 3 * boxHeight / 2 - 4, 0x8800FF00); // Dim green color
            colorBoxes.add(new ColorBox(i, col, boxX, boxY, boxWidth, boxHeight, 0x8800FF00));
            y += 30;
        }

        // Render yellow box
        if (nongreenCount >= 1) {
            int boxX = x - boxWidth / 2;
            int boxY = y + boxHeight / 2 - 4;
            guiGraphics.fill(boxX, boxY, x + boxWidth / 2, y + 3 * boxHeight / 2 - 4, 0x88FFFF00); // Dim yellow color
            colorBoxes.add(new ColorBox(greenCount, col, boxX, boxY, boxWidth, boxHeight, 0x88FFFF00));
            y += 30;
        }

        // Render red boxes
        for (int i = 0; i < nongreenCount - 1; i++) {
            int boxX = x - boxWidth / 2;
            int boxY = y + boxHeight / 2 - 4;
            guiGraphics.fill(boxX, boxY, x + boxWidth / 2, y + 3 * boxHeight / 2 - 4, 0x88FF0000); // Dim red color
            colorBoxes.add(new ColorBox( greenCount + 1 + i,col, boxX, boxY, boxWidth, boxHeight, 0x88FF0000));
            y += 30;
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // Don't render labels if using alternate background
        if (useAlternateBackground) {
            guiGraphics.drawString(this.font, this.title.getString(), this.titleLabelX, this.titleLabelY, 0xFFFFFF);
            guiGraphics.drawString(this.font, this.playerInventoryTitle.getString(), this.inventoryLabelX, this.inventoryLabelY, 0xFFFFFF);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        if (!useAlternateBackground) {
            this.renderTooltip(guiGraphics, mouseX, mouseY);
        }
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderBg(guiGraphics, partialTicks, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOverScrollBar(mouseX, mouseY)) {
            this.isScrolling = true;
        } else {
            if (useAlternateBackground) {
                if (isMouseOverBackButton(mouseX, mouseY)) {
                    ResearchTableEntity blockEntity = (ResearchTableEntity) this.getMenu().getBlockEntity();
                    blockEntity.atTech = -1;
                    blockEntity.atLevel = -1;
                    NetworkHandler.INSTANCE.send(new CResearchTableUpdate(blockEntity.getBlockPos(),blockEntity.atTech,blockEntity.atLevel,blockEntity.tech1,blockEntity.tech2,blockEntity.tech3),
                            PacketDistributor.SERVER.noArg());
                    useAlternateBackground = false;
                    return true;
                }
            } else {
                for (ColorBox box : colorBoxes) {
                    if (box.isMouseOver(mouseX, mouseY)) {
                        onColorBoxClick(box.row, box.col,box);
                        return true;
                    }
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.isScrolling = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.isScrolling) {
            int x = (this.width - this.imageWidth) / 2;
            int y = (this.height - this.imageHeight) / 2;
            this.scrollOffset = Math.min(Math.max((int)mouseY - y - SCROLLBAR_Y_OFFSET, 0), this.imageHeight - SCROLLBAR_HEIGHT - SCROLLBAR_Y_OFFSET);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    private boolean isMouseOverScrollBar(double mouseX, double mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        int scrollBarX = x + SCROLLBAR_X_OFFSET;
        int scrollBarY = y + SCROLLBAR_Y_OFFSET + this.scrollOffset;
        return mouseX >= scrollBarX && mouseX <= scrollBarX + SCROLLBAR_WIDTH && mouseY >= scrollBarY && mouseY <= scrollBarY + SCROLLBAR_HEIGHT;
    }

    private boolean isMouseOverBackButton(double mouseX, double mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        int buttonX = x + (this.imageWidth / 2) - 40;
        int buttonY = y + this.imageHeight - 30 - this.inventoryLabelY;
        return mouseX >= buttonX && mouseX <= buttonX + 80 && mouseY <= buttonY + 20 && mouseY >= buttonY;
    }

    private void onColorBoxClick(int row, int col,ColorBox box) {
        
        ResearchTableEntity blockEntity = (ResearchTableEntity) this.getMenu().getBlockEntity();
        if(box.color == 0x88FFFF00) {
        	blockEntity.setTech(col,row+1);
            NetworkHandler.INSTANCE.send(new CResearchTableUpdate(blockEntity.getBlockPos(),blockEntity.atTech,blockEntity.atLevel,blockEntity.tech1,blockEntity.tech2,blockEntity.tech3),
                    PacketDistributor.SERVER.noArg());
        }else if(box.color == 0x8800FF00) {
            blockEntity.atTech = col;
            blockEntity.atLevel = row;
            useAlternateBackground = true;
            //System.out.println("screen" + col+ "   " + row);
            NetworkHandler.INSTANCE.send(new CResearchTableUpdate(blockEntity.getBlockPos(),col,row,blockEntity.tech1,blockEntity.tech2,blockEntity.tech3),
                    PacketDistributor.SERVER.noArg());
        }

     
        // Handle the click event for the color box with the given row and column
    }

    private static class ColorBox {
        int row, col, x, y, width, height, color;

        ColorBox(int row, int col, int x, int y, int width, int height, int color) {
            this.row = row;
            this.col = col;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
        }

        boolean isMouseOver(double mouseX, double mouseY) {
            return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        }
    }

    @Override
    protected void renderSlot(GuiGraphics guiGraphics, Slot slot) {
        if (useAlternateBackground) {
            super.renderSlot(guiGraphics, slot);
        }
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (useAlternateBackground) {
            super.renderTooltip(guiGraphics, mouseX, mouseY);
        }
    }
}
