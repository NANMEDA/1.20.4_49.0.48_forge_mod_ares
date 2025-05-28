package block.entity.consumer.basicmetalmanufactor;

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
 * 显示基础金属零件
 * author NANMEDA
 */
public class BasicMetalManufactorEntityRender implements BlockEntityRenderer<BasicMetalManufactorEntity> {
	
	public BasicMetalManufactorEntityRender(BlockEntityRendererProvider.Context context) {
	}
	
	@SuppressWarnings("resource")
	@Override
	public void render(BasicMetalManufactorEntity entity, float partialTick, PoseStack pPoseStack, MultiBufferSource pBuffer,
			int packedLight, int packedOverlay) {
			entity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(ItemHandler->{
				ItemStack output = ItemHandler.getStackInSlot(5);
				Direction direction = entity.getBlockState().getValue(BlockStateProperties.FACING);
				if(!output.isEmpty()) {
					var itemrender = Minecraft.getInstance().getItemRenderer();
					pPoseStack.pushPose();
					Float scale = 1.0f/0.6f;
					pPoseStack.scale(1.0f/scale, 1.0f/scale, 1.0f/scale);
					switch (direction) {
					case NORTH: 
						pPoseStack.translate(0.8f*scale/1.6f, 0.9f*scale/1.6f+0.15f/1.6f, 1.0f*scale/1.6f);
						pPoseStack.mulPose(Axis.XN.rotationDegrees(-90f));
						break;
					case SOUTH: 
						pPoseStack.translate(0.8f*scale/1.6f, 0.9f*scale/1.6f+0.15f/1.6f, 0.6f*scale/1.6f);
						pPoseStack.mulPose(Axis.XN.rotationDegrees(-90f));
						break;
					case WEST: 
						pPoseStack.translate(1.0f*scale/1.6f, 0.9f*scale/1.6f+0.15f/1.6f, 0.8f*scale/1.6f);
						pPoseStack.mulPose(Axis.XN.rotationDegrees(-90f));
						break;
					case EAST: 
						pPoseStack.translate(0.6f*scale/1.6f, 0.9f*scale/1.6f+0.15f/1.6f, 0.8f*scale/1.6f);
						pPoseStack.mulPose(Axis.XN.rotationDegrees(-90f));
						break;
						
					default:
						pPoseStack.translate(scale, scale, scale);//5*0.2=1看scale
						break;
					}
					
					itemrender.renderStatic(output, ItemDisplayContext.FIXED, packedLight, packedOverlay, pPoseStack, pBuffer, Minecraft.getInstance().level, 0);
					pPoseStack.popPose();
				}
		});
		/*
		// Add code to render a black line from (0,0,0) to (5,5,5)
		pPoseStack.pushPose();
		VertexConsumer vertexConsumer = pBuffer.getBuffer(RenderType.lines());
		pPoseStack.translate(0.0D, 0.0D, 0.0D); // Translate to the starting position of the line
		
		// Define the starting and ending points of the line
		float startX = 0.0f;
		float startY = 0.0f;
		float startZ = 0.0f;
		float endX = 3.0f;
		float endY = 4.0f;
		float endZ = 5.0f;
	
		// Define the color of the line (black in this case)
		float red = 0.0f;
		float green = 0.0f;
		float blue = 0.0f;
		float alpha = 1.0f;

		// Define the normal vector
		float normalX = 1.0f;
		float normalY = 1.0f;
		float normalZ = 1.0f;

		// Render the line with normal
		vertexConsumer.vertex(pPoseStack.last().pose(), startX, startY, startZ)
			.color(red, green, blue, alpha)
			.normal(pPoseStack.last().normal(), normalX, normalY, normalZ)
			.endVertex();
		vertexConsumer.vertex(pPoseStack.last().pose(), endX, endY, endZ)
			.color(red, green, blue, alpha)
			.normal(pPoseStack.last().normal(), normalX, normalY, normalZ)
			.endVertex();
		
		pPoseStack.popPose();
		*/
	}
}
