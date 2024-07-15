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

	}

}
