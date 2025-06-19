package com.main.maring.machine.energy.consumer.render;

import com.main.maring.machine.energy.consumer.watergather.WaterGatherEntity;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

/**
 *
 * */
public class WaterGatherEntityRender implements BlockEntityRenderer<WaterGatherEntity> {
	
	public WaterGatherEntityRender(BlockEntityRendererProvider.Context context) {
	}
	
	@SuppressWarnings("resource")
	@Override
	public void render(WaterGatherEntity entity, float partialTick, PoseStack pPoseStack, MultiBufferSource pBuffer,
			int packedLight, int packedOverlay) {

	}

}
