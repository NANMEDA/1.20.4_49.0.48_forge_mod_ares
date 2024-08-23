package menu.show;

import org.joml.Matrix4f;

import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexSorting;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemDisplayContext;

public class ShowBlockScreen extends AbstractContainerScreen<ShowBlockMenu> {
    private float rotationX;
    private float rotationY;
    private boolean isDragging;
    private int previousMouseX;
    private int previousMouseY;

    public ShowBlockScreen(ShowBlockMenu pMenu, Inventory pInventory, Component pComponent) {
        super(pMenu, pInventory, pComponent);
        this.rotationX = 0;
        this.rotationY = 0;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        // No background rendering required for this example
    }

    private void render3DModel(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        PoseStack poseStack = new PoseStack();
        
        poseStack.pushPose();
        poseStack.translate(this.width / 2, this.height / 2, 50f);
        poseStack.scale(100f, 100f, 100f);
        poseStack.mulPose(Axis.XP.rotationDegrees(180 + rotationY));
        poseStack.mulPose(Axis.YP.rotationDegrees(rotationX - 180));
        
        RenderSystem.applyModelViewMatrix();
        // Increment rotation based on mouse dragging
        if (this.isDragging) {
            rotationX += (mouseX - this.previousMouseX) * 0.5F;
            rotationY += (mouseY - this.previousMouseY);
            if (rotationX > 360f) rotationX -= 360f;
            if (rotationY > 360f) rotationY -= 360f;
            if (rotationX < -360f) rotationX += 360f;
            if (rotationY < -360f) rotationY += 360f;
        }

        
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();

        Minecraft.getInstance().getItemRenderer().renderStatic(this.getMenu().getItemStack(), ItemDisplayContext.FIXED, 0xF000F0, OverlayTexture.NO_OVERLAY, poseStack,bufferSource, Minecraft.getInstance().level, 0);
        bufferSource.endBatch();
        poseStack.popPose();
        
        RenderSystem.applyModelViewMatrix();
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // No labels rendering required for this example
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseY, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
        render3DModel(guiGraphics, partialTick, mouseX, mouseY);
        this.previousMouseX = mouseX;
        this.previousMouseY = mouseY;
    }

    // Handle mouse dragging
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.isDragging = true;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.isDragging = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }
}
