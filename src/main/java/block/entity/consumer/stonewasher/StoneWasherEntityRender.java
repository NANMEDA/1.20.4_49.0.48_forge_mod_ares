package block.entity.consumer.stonewasher;

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
 * 还没写
 * */
public class StoneWasherEntityRender implements BlockEntityRenderer<StoneWasherEntity> {
	private float Rotation = 0f;
	
	public StoneWasherEntityRender(BlockEntityRendererProvider.Context context) {
	}
	
	
	@SuppressWarnings("resource")
	@Override
	public void render(StoneWasherEntity entity, float partialTick, PoseStack pPoseStack, MultiBufferSource pBuffer,
			int packedLight, int packedOverlay) {
			entity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(ItemHandler->{
				ItemStack output = ItemHandler.getStackInSlot(1);
				if(output.isEmpty()) {
					output = ItemHandler.getStackInSlot(0);
				}
				Direction direction = entity.getBlockState().getValue(BlockStateProperties.FACING);
				if(!output.isEmpty()) {
					if(!entity.is_button) {
						Rotation = 0f;
						if(Rotation>360f) {
							Rotation -= 360f;
						}
					}else {
						Rotation += 2f;
					}
					var itemrender = Minecraft.getInstance().getItemRenderer();
					pPoseStack.pushPose();
					pPoseStack.scale(0.2f, 0.2f, 0.2f);
					switch (direction) {
					case NORTH: 
						pPoseStack.translate(3f, 0.8f, 2.5f);
						pPoseStack.mulPose(Axis.YN.rotationDegrees(Rotation));
						break;
					case SOUTH: 
						pPoseStack.translate(2f, 0.8f, 2.5f);
						pPoseStack.mulPose(Axis.YN.rotationDegrees(Rotation));
						break;
					case WEST: 
						pPoseStack.translate(2.5f, 0.8f, 2f);
						pPoseStack.mulPose(Axis.YN.rotationDegrees(90f+Rotation));
						break;
					case EAST: 
						pPoseStack.translate(2.5f, 0.8f, 3f);
						pPoseStack.mulPose(Axis.YN.rotationDegrees(90f+Rotation));
						break;
						
					default:
						pPoseStack.translate(5f, 5f, 5f);//5*0.2=1看scale
						break;
					}
					itemrender.renderStatic(output, ItemDisplayContext.FIXED, packedLight, packedOverlay, pPoseStack, pBuffer, Minecraft.getInstance().level, 0);
					pPoseStack.popPose();
				}
		});
		
	}

}
