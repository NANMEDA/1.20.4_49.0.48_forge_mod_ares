package animal.client.render;

import animal.client.model.JumpSpiderModel;
import animal.entity.JumpSpider;
import animal.entity.MonsterRegister;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "maring", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class JumpSpiderRenderer extends MobRenderer<JumpSpider, JumpSpiderModel> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("maring", "textures/entity/jump_spider.png");

    public JumpSpiderRenderer(EntityRendererProvider.Context context) {
        super(context, new JumpSpiderModel(context.bakeLayer(JumpSpiderModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(JumpSpider entity) {
        return TEXTURE;
    }

    /*
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(MonsterRegister.JUMP_SPIDER.get(), JumpSpiderRenderer::new);
    }*/
}
