package com.main.maring.animal.client.render;

import com.main.maring.Maring;
import com.main.maring.animal.client.model.JumpSpiderModel;
import com.main.maring.animal.entity.jumpspider.JumpSpider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class JumpSpiderRenderer extends MobRenderer<JumpSpider, JumpSpiderModel> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Maring.MODID, "textures/entity/jump_spider.png");

    public JumpSpiderRenderer(EntityRendererProvider.Context context) {
        super(context, new JumpSpiderModel(context.bakeLayer(JumpSpiderModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(JumpSpider entity) {
        return TEXTURE;
    }

}
