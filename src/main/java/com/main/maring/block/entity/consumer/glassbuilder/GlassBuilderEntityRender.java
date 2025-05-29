package com.main.maring.block.entity.consumer.glassbuilder;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

/**
 * 显示玻璃
 * 显示玻璃滴落效果
 * @author NANMEDA
 * */
public class GlassBuilderEntityRender implements BlockEntityRenderer<GlassBuilderEntity> {
	
	private float drip = -1f;
	private float maxdrip_height = 1.9f;
	
	public GlassBuilderEntityRender(BlockEntityRendererProvider.Context context) {
	}
	
	@SuppressWarnings("resource")
	@Override
	public void render(GlassBuilderEntity entity, float partialTick, PoseStack pPoseStack, MultiBufferSource pBuffer,
			int packedLight, int packedOverlay) {
			entity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(ItemHandler->{
				ItemStack output = ItemHandler.getStackInSlot(0);
				var itemrender = Minecraft.getInstance().getItemRenderer();
				Direction direction = entity.getBlockState().getValue(BlockStateProperties.FACING);
				if(!output.isEmpty()) {
					pPoseStack.pushPose();
					Float scale = 1.0f/0.6f;
					pPoseStack.scale(1.0f/scale, 1.0f/scale, 1.0f/scale);
					if(direction==Direction.NORTH) {
						pPoseStack.translate(0.8f*scale/1.6f, 2.4f*scale/1.6f+0.15f/1.6f, 2.4f*scale/1.6f);
					}else if(direction==Direction.SOUTH) {
						pPoseStack.translate(0.8f*scale/1.6f, 2.4f*scale/1.6f+0.15f/1.6f, -0.8f*scale/1.6f);
					}else if(direction==Direction.EAST) {
						pPoseStack.translate(-0.8f*scale/1.6f, 2.4f*scale/1.6f+0.15f/1.6f, 0.8f*scale/1.6f);
					}else if(direction==Direction.WEST) {
						pPoseStack.translate(2.4f*scale/1.6f, 2.4f*scale/1.6f+0.15f/1.6f, 0.8f*scale/1.6f);
					}
					pPoseStack.mulPose(Axis.XN.rotationDegrees(-90f));
					itemrender.renderStatic(output, ItemDisplayContext.FIXED, packedLight, packedOverlay, pPoseStack, pBuffer, Minecraft.getInstance().level, 0);
					pPoseStack.popPose();
				}
				output = ItemHandler.getStackInSlot(1);
				if(!output.isEmpty()) {
					pPoseStack.pushPose();
					Float scale = 1.0f/0.8f;
					Float height = 0.7f*scale/1.6f+0.5f/1.6f;
					pPoseStack.scale(1.0f/scale, 1.0f/scale, 1.0f/scale);
					pPoseStack.translate(0.8f*scale/1.6f,height, 0.8f*scale/1.6f);
					itemrender.renderStatic(output, ItemDisplayContext.FIXED, packedLight, packedOverlay, pPoseStack, pBuffer, Minecraft.getInstance().level, 0);
					pPoseStack.popPose();
				}
				if(entity.process_progress>0) {
					drip += 0.05f;
					float scale = 1.0f/0.2f;
					if(drip > maxdrip_height*scale/1.6f - (0.7f*scale/1.6f+1f/1.6f)-0.5f/1.6f) {
						drip = -1f;
					}
					pPoseStack.pushPose();
					pPoseStack.scale(1.0f/scale, 1.0f/scale, 1.0f/scale);
					pPoseStack.translate(0.8f*scale/1.6f,maxdrip_height*scale/1.6f-drip, 0.8f*scale/1.6f);
					itemrender.renderStatic(new ItemStack(Items.GLASS,1), ItemDisplayContext.FIXED, packedLight, packedOverlay, pPoseStack, pBuffer, Minecraft.getInstance().level, 0);
					pPoseStack.popPose();
				}
		});
	}

}
