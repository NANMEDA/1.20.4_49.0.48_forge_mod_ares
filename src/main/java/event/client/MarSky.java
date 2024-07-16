package event.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class MarSky extends DimensionSpecialEffects {

	@Nullable
	private VertexBuffer darkBuffer;
	@Nullable
	private VertexBuffer starBuffer;
	@Nullable
	private VertexBuffer skyBuffer;
	
	private final Minecraft minecraft;
	
	//private static final ResourceLocation MOON_LOCATION = new ResourceLocation("textures/environment/moon_phases.png");
	private static final ResourceLocation SUN_LOCATION = new ResourceLocation("textures/environment/sun.png");
	
	
    public MarSky(float cloudLevel, boolean hasGround, SkyType p_108868_, boolean forceBrightLightmap, boolean constantAmbientLight) {
		super(cloudLevel, hasGround, p_108868_, forceBrightLightmap, constantAmbientLight);
		this.createStars();
		this.createLightSky();
		this.createDarkSky();
		this.minecraft = Minecraft.getInstance();
		
		// TODO 自动生成的构造函数存根
	}
	private final float[] sunriseCol = new float[4];

    @SuppressWarnings("unused")
	@Nullable
    @Override
    public float[] getSunriseColor(float time, float partialTick) {
        float f = 0.4F;
        float f1 = Mth.cos(time * ((float)Math.PI * 2F)) - 0.0F;
        float f2 = -0.0F;
        if (f1 >= -0.4F && f1 <= 0.4F) {
            float f3 = (f1 - -0.0F) / 0.4F * 0.5F + 0.5F;
            float f4 = 1.0F - (1.0F - Mth.sin(f3 * (float)Math.PI)) * 0.99F;
            f4 *= f4;
           this.sunriseCol[2] = f3 * 0.3F + 0.5F;
           this.sunriseCol[1] = f3 * f3 * 0.7F + 0.2F;
           this.sunriseCol[0] = f3 * f3 * 0.2F + 0.2F;
           this.sunriseCol[3] = f4;
           return this.sunriseCol;
        } else {
           return null;
        }
    }

    @Override
    public boolean renderClouds(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, double camX,
                                double camY, double camZ, Matrix4f projectionMatrix) {
        return true;	// ! true才是不渲染云 :(
    }

	@Override
	public Vec3 getBrightnessDependentFogColor(Vec3 colour, float brightness) {
		// TODO 自动生成的方法存根
		return colour.multiply(brightness * 0.94F + 0.06F, brightness * 0.94F + 0.06F, brightness * 0.91F + 0.09F);
	}

	@Override
	public boolean isFoggyAt(int p_108874_, int p_108875_) {
		// TODO 自动生成的方法存根
		return false;
	}
	
	/***
	 * 火星的天空
	 * 无云 + 无月亮 + 蓝色的日出日落
	 * 建议保留部分源码以及修改的注释
	 * 以方便参考
	 * ***/
	@SuppressWarnings("unused")
	@Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera,
                             Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
		  setupFog.run();
		  if (!isFoggy) {
		      FogType fogtype = camera.getFluidInCamera();
		      if (fogtype != FogType.POWDER_SNOW && fogtype != FogType.LAVA && !this.doesMobEffectBlockSky(camera)) {
		        Vec3 vec3 = level.getSkyColor(this.minecraft.gameRenderer.getMainCamera().getPosition(), partialTick);
		        float f = (float)vec3.x;
		        float f1 = (float)vec3.y;
		        float f2 = (float)vec3.z;
		        FogRenderer.levelFogColor();
		        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		        RenderSystem.depthMask(false);
		        RenderSystem.setShaderColor(f, f1, f2, 1.0F);
		        ShaderInstance shaderinstance = RenderSystem.getShader();
		        this.skyBuffer.bind();
		        this.skyBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, shaderinstance);
		        VertexBuffer.unbind();
		        RenderSystem.enableBlend();
		        float[] afloat = level.effects().getSunriseColor(level.getTimeOfDay(partialTick), partialTick);
		        if (afloat != null) {
		           RenderSystem.setShader(GameRenderer::getPositionColorShader);
		           RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		           poseStack.pushPose();
		           poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
		           float f3 = Mth.sin(level.getSunAngle(partialTick)) < 0.0F ? 180.0F : 0.0F;
		           poseStack.mulPose(Axis.ZP.rotationDegrees(f3));
		           poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
		           float f4 = afloat[0];
		           float f5 = afloat[1];
		           float f6 = afloat[2];
		           Matrix4f matrix4f = poseStack.last().pose();
		           bufferbuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
		           bufferbuilder.vertex(matrix4f, 0.0F, 100.0F, 0.0F).color(f4, f5, f6, afloat[3]).endVertex();
		           int i = 16;
		
		           for(int j = 0; j <= 16; ++j) {
		              float f7 = (float)j * ((float)Math.PI * 2F) / 16.0F;
		              float f8 = Mth.sin(f7);
		              float f9 = Mth.cos(f7);
		              bufferbuilder.vertex(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * afloat[3]).color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
		           }
		
		           BufferUploader.drawWithShader(bufferbuilder.end());
		           poseStack.popPose();
		        }
		
		        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		        poseStack.pushPose();
	
			     // 计算雨量对颜色的影响
			     float f11 = 1.0F - level.getRainLevel(partialTick);
			     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f11);
	
			     // 设置旋转矩阵
			     poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
			     poseStack.mulPose(Axis.XP.rotationDegrees(level.getTimeOfDay(partialTick) * 360.0F));
	
			     // 获取当前的矩阵变换
			     Matrix4f matrix4f1 = poseStack.last().pose();
			     
			     float f12 = 15.0F;//比原版小一半
			     // 设置并绘制太阳纹理
			     RenderSystem.setShader(GameRenderer::getPositionTexShader);
			     RenderSystem.setShaderTexture(0, SUN_LOCATION);
			     bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
			     bufferbuilder.vertex(matrix4f1, -f12, 100.0F, -f12).uv(0.0F, 0.0F).endVertex();
			     bufferbuilder.vertex(matrix4f1, f12, 100.0F, -f12).uv(1.0F, 0.0F).endVertex();
			     bufferbuilder.vertex(matrix4f1, f12, 100.0F, f12).uv(1.0F, 1.0F).endVertex();
			     bufferbuilder.vertex(matrix4f1, -f12, 100.0F, f12).uv(0.0F, 1.0F).endVertex();
			     BufferUploader.drawWithShader(bufferbuilder.end());
	
			     /*
			     // 设置并绘制月亮纹理
			     f12 = 20.0F;
			     RenderSystem.setShaderTexture(0, MOON_LOCATION);
			     int k = level.getMoonPhase();
			     int l = k % 4;
			     int i1 = k / 4 % 2;
			     float f13 = (float)(l + 0) / 4.0F;
			     float f14 = (float)(i1 + 0) / 2.0F;
			     float f15 = (float)(l + 1) / 4.0F;
			     float f16 = (float)(i1 + 1) / 2.0F;
			     bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
			     bufferbuilder.vertex(matrix4f1, -f12, -100.0F, f12).uv(f15, f16).endVertex();
			     bufferbuilder.vertex(matrix4f1, f12, -100.0F, f12).uv(f13, f16).endVertex();
			     bufferbuilder.vertex(matrix4f1, f12, -100.0F, -f12).uv(f13, f14).endVertex();
			     bufferbuilder.vertex(matrix4f1, -f12, -100.0F, -f12).uv(f15, f14).endVertex();
			     BufferUploader.drawWithShader(bufferbuilder.end());
			     */
			     
			     // 获取星星的亮度并绘制星星
			     float f10 = level.getStarBrightness(partialTick) * f11 * 1.5f;
			     if (f10 > 0.0F) {
			         RenderSystem.setShaderColor(f10, f10, f10, f10);
			         FogRenderer.setupNoFog();
			         this.starBuffer.bind();
			         this.starBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, GameRenderer.getPositionShader());
			         VertexBuffer.unbind();
			         setupFog.run();
			     }
	
			     // 重置颜色并关闭混合模式
			     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			     RenderSystem.disableBlend();
			     RenderSystem.defaultBlendFunc();
			     poseStack.popPose();

		        RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
		        double d0 = this.minecraft.player.getEyePosition(partialTick).y - level.getLevelData().getHorizonHeight(level);
		        if (d0 < 0.0D) {
		           poseStack.pushPose();
		           poseStack.translate(0.0F, 12.0F, 0.0F);
		           this.darkBuffer.bind();
		           this.darkBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, shaderinstance);
		           VertexBuffer.unbind();
		           poseStack.popPose();
		        }
		
		        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		        RenderSystem.depthMask(true);
		      }
		  }
		  return true;
    }

	   private boolean doesMobEffectBlockSky(Camera p_234311_) {
		      Entity entity = p_234311_.getEntity();
		      if (!(entity instanceof LivingEntity livingentity)) {
		         return false;
		      } else {
		         return livingentity.hasEffect(MobEffects.BLINDNESS) || livingentity.hasEffect(MobEffects.DARKNESS);
		      }
		   }
	   
	   private void createStars() {
		      Tesselator tesselator = Tesselator.getInstance();
		      BufferBuilder bufferbuilder = tesselator.getBuilder();
		      RenderSystem.setShader(GameRenderer::getPositionShader);
		      if (this.starBuffer != null) {
		         this.starBuffer.close();
		      }

		      this.starBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		      BufferBuilder.RenderedBuffer bufferbuilder$renderedbuffer = this.drawStars(bufferbuilder);
		      this.starBuffer.bind();
		      this.starBuffer.upload(bufferbuilder$renderedbuffer);
		      VertexBuffer.unbind();
		   }
	
	   private void createDarkSky() {
		      Tesselator tesselator = Tesselator.getInstance();
		      BufferBuilder bufferbuilder = tesselator.getBuilder();
		      if (this.darkBuffer != null) {
		         this.darkBuffer.close();
		      }

		      this.darkBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		      BufferBuilder.RenderedBuffer bufferbuilder$renderedbuffer = buildSkyDisc(bufferbuilder, -16.0F);
		      this.darkBuffer.bind();
		      this.darkBuffer.upload(bufferbuilder$renderedbuffer);
		      VertexBuffer.unbind();
		   }
	   
	   private void createLightSky() {
		      Tesselator tesselator = Tesselator.getInstance();
		      BufferBuilder bufferbuilder = tesselator.getBuilder();
		      if (this.skyBuffer != null) {
		         this.skyBuffer.close();
		      }

		      this.skyBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		      BufferBuilder.RenderedBuffer bufferbuilder$renderedbuffer = buildSkyDisc(bufferbuilder, 16.0F);
		      this.skyBuffer.bind();
		      this.skyBuffer.upload(bufferbuilder$renderedbuffer);
		      VertexBuffer.unbind();
		   }

	   @SuppressWarnings("unused")
	private static BufferBuilder.RenderedBuffer buildSkyDisc(BufferBuilder p_234268_, float p_234269_) {
		      float f = Math.signum(p_234269_) * 512.0F;
		      float f1 = 512.0F;
		      RenderSystem.setShader(GameRenderer::getPositionShader);
		      p_234268_.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION);
		      p_234268_.vertex(0.0D, (double)p_234269_, 0.0D).endVertex();

		      for(int i = -180; i <= 180; i += 45) {
		         p_234268_.vertex((double)(f * Mth.cos((float)i * ((float)Math.PI / 180F))), (double)p_234269_, (double)(512.0F * Mth.sin((float)i * ((float)Math.PI / 180F)))).endVertex();
		      }

		      return p_234268_.end();
		   }
	   
	   /***
	    * 星星的生成
	    * 有小小的修改
	    * ***/
	   private BufferBuilder.RenderedBuffer drawStars(BufferBuilder p_234260_) {
		    RandomSource randomsource = RandomSource.create(10842L);
		    p_234260_.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

		    for(int i = 0; i < 1500; ++i) {
		    	float d0_ = randomsource.nextFloat();
		    	double d1;
		    	if(d0_>0.3) {
		    		d1 = (double)(randomsource.nextFloat() * 2.0F - 1.0F);
		    	}else {
		    		d1 = (double)(randomsource.nextFloat() * 0.3F - 0.3f);
		    	}
		        double d0 = (double)(randomsource.nextFloat() * 2.0F - 1.0F);
		        double d2 = (double)(randomsource.nextFloat() * 2.0F - 1.0F);

		        // 随机生成星星的大小 (0.15 到 0.35)//原版（0.15-0.25）
		        double d3 = (double)(0.15F + randomsource.nextFloat() * 0.2F);

		        // 计算向量的长度的平方
		        double d4 = d0 * d0 + d1 * d1 + d2 * d2;

		        // 过滤掉一些向量，使它们不在单位球体内或非常接近原点
		        if (d4 < 1.0D && d4 > 0.01D) {
		            // 将向量归一化
		            d4 = 1.0D / Math.sqrt(d4);
		            d0 *= d4;
		            d1 *= d4;
		            d2 *= d4;

		            // 将向量放大 100 倍
		            double d5 = d0 * 100.0D;
		            double d6 = d1 * 100.0D;
		            double d7 = d2 * 100.0D;

		            // 计算旋转角度
		            double d8 = Math.atan2(d0, d2);
		            double d9 = Math.sin(d8);
		            double d10 = Math.cos(d8);
		            double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
		            double d12 = Math.sin(d11);
		            double d13 = Math.cos(d11);

		            // 随机生成旋转角度
		            double d14 = randomsource.nextDouble() * Math.PI * 2.0D;
		            double d15 = Math.sin(d14);
		            double d16 = Math.cos(d14);

		            // 为每个星星生成四个顶点（一个四边形）
		            for(int j = 0; j < 4; ++j) {
		                // 计算顶点位置
		                double d17 = 0.0D;
		                double d18 = (double)((j & 2) - 1) * d3;
		                double d19 = (double)((j + 1 & 2) - 1) * d3;
		                double d20 = 0.0D;
		                double d21 = d18 * d16 - d19 * d15;
		                double d22 = d19 * d16 + d18 * d15;
		                double d23 = d21 * d12 + 0.0D * d13;
		                double d24 = 0.0D * d12 - d21 * d13;
		                double d25 = d24 * d9 - d22 * d10;
		                double d26 = d22 * d9 + d24 * d10;

		                // 添加顶点到 BufferBuilder
		                p_234260_.vertex(d5 + d25, d6 + d23, d7 + d26).endVertex();
		            }
		        }
		    }

		    // 结束顶点构建并返回渲染后的缓冲区
		    return p_234260_.end();
		}
	   

}