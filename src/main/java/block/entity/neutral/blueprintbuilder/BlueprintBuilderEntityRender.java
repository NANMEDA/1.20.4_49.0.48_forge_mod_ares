package block.entity.neutral.blueprintbuilder;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

/**
 * 没写
 * */
public class BlueprintBuilderEntityRender implements BlockEntityRenderer<BlueprintBuilderEntity> {
	
	public BlueprintBuilderEntityRender(BlockEntityRendererProvider.Context context) {
	}
	
	@SuppressWarnings("resource")
	@Override
	public void render(BlueprintBuilderEntity entity, float partialTick, PoseStack pPoseStack, MultiBufferSource pBuffer,
			int packedLight, int packedOverlay) {
			entity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(ItemHandler->{
				/*
				ItemStack output = ItemHandler.getStackInSlot(0);
				var itemrender = Minecraft.getInstance().getItemRenderer();
				if(!output.isEmpty()) {
					pPoseStack.pushPose();
					Float scale = 1.0f/0.6f;
					pPoseStack.scale(1.0f/scale, 1.0f/scale, 1.0f/scale);
					pPoseStack.translate(0.8f*scale/1.6f, 0.6f*scale/1.6f+0.15f/1.6f, 0.8f*scale/1.6f);
					pPoseStack.mulPose(Axis.XN.rotationDegrees(-90f));
					itemrender.renderStatic(output, ItemDisplayContext.FIXED, packedLight, packedOverlay, pPoseStack, pBuffer, Minecraft.getInstance().level, 0);
					pPoseStack.popPose();
				}
				output = ItemHandler.getStackInSlot(1);
				if(!output.isEmpty()) {
					pPoseStack.pushPose();
					Float scale = 1.0f/0.25f;
					pPoseStack.scale(1.0f/scale, 1.0f/scale, 1.0f/scale);
					pPoseStack.translate(0.8f*scale/1.6f, (1.6f+0.2f)*scale/1.6f, 0.8f*scale/1.6f);
					itemrender.renderStatic(output, ItemDisplayContext.FIXED, packedLight, packedOverlay, pPoseStack, pBuffer, Minecraft.getInstance().level, 0);
					pPoseStack.popPose();
				}
				if(entity.crystal>15) {
					Float scale = 1.0f/0.25f;
					
					pPoseStack.pushPose();
					pPoseStack.scale(1.0f/scale, 1.0f/scale, 1.0f/scale);
					pPoseStack.translate(0.8f*scale/1.6f, (1.6f+1.2f)*scale/1.6f, 0.8f*scale/1.6f);
					pPoseStack.mulPose(Axis.XN.rotationDegrees(180f));
					itemrender.renderStatic(new ItemStack(Items.QUARTZ,1), ItemDisplayContext.FIXED, packedLight, packedOverlay, pPoseStack, pBuffer, Minecraft.getInstance().level, 0);
					pPoseStack.popPose();
					if(entity.crystal>60) {
						pPoseStack.pushPose();
						pPoseStack.scale(1.0f/scale, 1.0f/scale, 1.0f/scale);
						pPoseStack.translate(0.8f*scale/1.6f, (1.6f+1.2f)*scale/1.6f, 0.8f*scale/1.6f);
						pPoseStack.mulPose(Axis.YN.rotationDegrees(90f));
						pPoseStack.mulPose(Axis.XN.rotationDegrees(180f));
						itemrender.renderStatic(new ItemStack(Items.QUARTZ,1), ItemDisplayContext.FIXED, packedLight, packedOverlay, pPoseStack, pBuffer, Minecraft.getInstance().level, 0);
						pPoseStack.popPose();
					}
				}*/
		});
	}

}
