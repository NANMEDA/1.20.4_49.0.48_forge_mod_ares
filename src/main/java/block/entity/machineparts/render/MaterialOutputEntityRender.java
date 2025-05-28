package block.entity.machineparts.render;

import com.mojang.blaze3d.vertex.PoseStack;

import block.entity.machineparts.MaterialOutputEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

/**
 * 显示
 * author NANMEDA
 */
public class MaterialOutputEntityRender implements BlockEntityRenderer<MaterialOutputEntity> {
	
	public MaterialOutputEntityRender(BlockEntityRendererProvider.Context context) {
	}
	
	@SuppressWarnings("resource")
	@Override
	public void render(MaterialOutputEntity entity, float partialTick, PoseStack pPoseStack, MultiBufferSource pBuffer,
			int packedLight, int packedOverlay) {
			entity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(ItemHandler->{
				ItemStack output = ItemHandler.getStackInSlot(0);
				int height = entity.getBlockState().getValue(BlockStateProperties.LEVEL);
				if(!output.isEmpty()) {
				}
		});

	}

}
