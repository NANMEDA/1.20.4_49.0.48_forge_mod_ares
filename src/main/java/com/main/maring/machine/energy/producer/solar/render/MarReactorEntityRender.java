package com.main.maring.machine.energy.producer.solar.render;

import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;

import com.main.maring.machine.energy.producer.reactor.mar.MarReactorEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import com.main.maring.render.WireRenderer;
import net.minecraft.world.phys.Vec3;


public class MarReactorEntityRender implements BlockEntityRenderer<MarReactorEntity> {
	
	public MarReactorEntityRender(BlockEntityRendererProvider.Context context) {
	}
	
	@SuppressWarnings("resource")
	@Override
	public void render(MarReactorEntity entity, float partialTick, PoseStack pPoseStack, MultiBufferSource pBuffer,
			int packedLight, int packedOverlay) {
		Map<BlockPos, Boolean> connection =  entity.getConnections();
		if(connection.isEmpty()){
			return;
		}
		BlockPos pos = entity.getBlockPos();
	    Vec3 startPos = new Vec3((double)pos.getX()+0.5d, (double)pos.getY()+0.5d, (double)pos.getZ()+0.5d);  // 当前实体的位置
		for(BlockPos p : connection.keySet()) {
			if(connection.get(p)) {
				Vec3 endPos = new Vec3((double)p.getX()+0.5d, (double)p.getY()+0.5d, (double)p.getZ()+0.5d);  // 连接点的位置 p
				WireRenderer.renderWire(startPos, endPos, pPoseStack, pBuffer, packedLight);
			}
		}
	}

}
