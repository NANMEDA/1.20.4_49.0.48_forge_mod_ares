package item.rocket;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import vehicle.rocket.RocketEntity;
import vehicle.rocket.RocketModel;

@Mod.EventBusSubscriber(modid = "maring", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RocketTier1ItemRenderer<T extends RocketEntity> extends BlockEntityWithoutLevelRenderer {

    private static final String MODID = "maring";

	/** TEXTURE */
    public static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/vehicle/rocket_t1.png");

    /** MODEL */
    private RocketModel model;

    public RocketTier1ItemRenderer(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
        super(p_172550_, p_172551_);
    }

    @Override
    public void renderByItem(ItemStack p_108830_, ItemDisplayContext p_108831_, PoseStack matrixStackIn, MultiBufferSource buffer, int combinedLight, int p_108835_) {
        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Axis.ZP.rotationDegrees(180));
        matrixStackIn.translate(0.0, -1.5, 0.0);

        Minecraft mc = Minecraft.getInstance();

        VertexConsumer vertexBuilder;

        /** TEXTURE BINDING */
        vertexBuilder = buffer.getBuffer(RenderType.entityCutoutNoCullZOffset(TEXTURE));

        if (this.model == null) {
            this.model = new RocketModel(mc.getEntityModels().bakeLayer(RocketModel.LAYER_LOCATION));
        }

        this.model.renderToBuffer(matrixStackIn, vertexBuilder, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStackIn.popPose();
    }
}