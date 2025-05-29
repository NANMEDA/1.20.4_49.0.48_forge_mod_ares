package com.main.maring.block.entity.consumer.canfoodmaker;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

/**
 * 显示罐头
 * @author NANMEDA
 * */
public class CanfoodMakerEntityRender implements BlockEntityRenderer<CanfoodMakerEntity> {
	
	public CanfoodMakerEntityRender(BlockEntityRendererProvider.Context context) {
	}
	
	@SuppressWarnings("resource")
	@Override
	public void render(CanfoodMakerEntity entity, float partialTick, PoseStack pPoseStack, MultiBufferSource pBuffer,
			int packedLight, int packedOverlay) {
			entity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(ItemHandler->{
				ItemStack output = ItemHandler.getStackInSlot(4);
				Direction direction = entity.getBlockState().getValue(BlockStateProperties.FACING);
				if(!output.isEmpty()) {
					var itemrender = Minecraft.getInstance().getItemRenderer();
					pPoseStack.pushPose();
					Float scale = 1.0f/0.6f;
					pPoseStack.scale(1.0f/scale, 1.0f/scale, 1.0f/scale);
					switch (direction) {
					case NORTH: 
						pPoseStack.translate(0.5f*scale/1.6f, 0.6f*scale/1.6f+0.3f/1.6f, 0.4f*scale/1.6f);
						//pPoseStack.mulPose(Axis.XN.rotationDegrees(-90f));
						break;
					case SOUTH: 
						pPoseStack.translate(1.1f*scale/1.6f, 0.6f*scale/1.6f+0.3f/1.6f, 1.2f*scale/1.6f);
						pPoseStack.mulPose(Axis.YN.rotationDegrees(180f));
						break;
					case WEST: 
						pPoseStack.translate(0.4f*scale/1.6f, 0.6f*scale/1.6f+0.3f/1.6f, 1.1f*scale/1.6f);
						pPoseStack.mulPose(Axis.YN.rotationDegrees(-90f));
						break;
					case EAST: 
						pPoseStack.translate(1.2f*scale/1.6f, 0.6f*scale/1.6f+0.3f/1.6f, 0.5f*scale/1.6f);
						pPoseStack.mulPose(Axis.YN.rotationDegrees(90f));
						break;
						
					default:
						pPoseStack.translate(scale, scale, scale);//5*0.2=1看scale
						break;
					}
					
					itemrender.renderStatic(output, ItemDisplayContext.FIXED, packedLight, packedOverlay, pPoseStack, pBuffer, Minecraft.getInstance().level, 0);
					pPoseStack.popPose();
				}
		});
	}

}
