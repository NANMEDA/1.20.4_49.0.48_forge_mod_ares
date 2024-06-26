package vehicle.rocket;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vehicle.VehicleRenderer;

@OnlyIn(Dist.CLIENT)
public class RocketTier1Renderer extends VehicleRenderer<RocketEntity, RocketModel<RocketEntity>> {

    private static final String MODID = "maring";
	public static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/vehicle/rocket_t1.png");

    public RocketTier1Renderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new RocketModel<>(renderManagerIn.bakeLayer(RocketModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(RocketEntity p_114482_) {
        return TEXTURE;
    }

    @Override
    protected boolean isShaking(RocketEntity p_115304_) {
        return p_115304_.getEntityData().get(IRocketEntity.ROCKET_START);
    }
}