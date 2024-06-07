package block.entity.consumer.advancedmetalmanufactor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class AdvancedMetalManufactorEntityRender implements BlockEntityRenderer<AdvancedMetalManufactorEntity> {
	
	public AdvancedMetalManufactorEntityRender(BlockEntityRendererProvider.Context context) {
	}
	
	@SuppressWarnings("resource")
	@Override
	public void render(AdvancedMetalManufactorEntity entity, float partialTick, PoseStack pPoseStack, MultiBufferSource pBuffer,
			int packedLight, int packedOverlay) {
			entity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(ItemHandler->{
				ItemStack output = ItemHandler.getStackInSlot(3);
				var itemrender = Minecraft.getInstance().getItemRenderer();
				if(!output.isEmpty()) {
					pPoseStack.pushPose();
					Float scale = 1.0f/0.6f;
					pPoseStack.scale(1.0f/scale, 1.0f/scale, 1.0f/scale);
					pPoseStack.translate(0.8f*scale/1.6f, 1.2f*scale/1.6f+0.15f/1.6f, 0.8f*scale/1.6f);
					pPoseStack.mulPose(Axis.XN.rotationDegrees(-90f));
					itemrender.renderStatic(output, ItemDisplayContext.FIXED, packedLight, packedOverlay, pPoseStack, pBuffer, Minecraft.getInstance().level, 0);
					pPoseStack.popPose();
				}
		});
	}
}
