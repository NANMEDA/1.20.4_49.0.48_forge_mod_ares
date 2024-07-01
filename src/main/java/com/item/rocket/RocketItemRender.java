package com.item.rocket;

import net.minecraft.client.Minecraft;


public class RocketItemRender {
	
	public static final RocketTier1ItemRenderer<?> ROCKET_TIER_1_ITEM_RENDERER = new RocketTier1ItemRenderer<>(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());

}
