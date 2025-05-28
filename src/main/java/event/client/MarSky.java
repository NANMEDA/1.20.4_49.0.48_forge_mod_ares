package event.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class MarSky extends DimensionSpecialEffects {

	public static boolean shouldnotRenderClouds = true;
	
	@Nullable
	private VertexBuffer darkBuffer;
	@Nullable
	private VertexBuffer starBuffer;
	@Nullable
	private VertexBuffer skyBuffer;
	
	private final Minecraft minecraft;
	
    private final float[] rainSizeX = new float[1024];
    private final float[] rainSizeZ = new float[1024];
    
    private static final ResourceLocation RAIN_LOCATION = new ResourceLocation("textures/environment/rain.png");
    private static final ResourceLocation SNOW_LOCATION = new ResourceLocation("textures/environment/snow.png");
	
	//private static final ResourceLocation MOON_LOCATION = new ResourceLocation("textures/environment/moon_phases.png");
	private static final ResourceLocation SUN_LOCATION = new ResourceLocation("textures/environment/sun.png");
	
	
    public MarSky(float cloudLevel, boolean hasGround, SkyType p_108868_, boolean forceBrightLightmap, boolean constantAmbientLight) {
		super(cloudLevel, hasGround, p_108868_, forceBrightLightmap, constantAmbientLight);
	      for(int i = 0; i < 32; ++i) {
	          for(int j = 0; j < 32; ++j) {
	             float f = (float)(j - 16);
	             float f1 = (float)(i - 16);
	             float f2 = Mth.sqrt(f * f + f1 * f1);
	             this.rainSizeX[i << 5 | j] = -f1 / f2;
	             this.rainSizeZ[i << 5 | j] = f / f2;
	          }
	       }
		
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
    	if(shouldnotRenderClouds) {
    		return true;// ! true才是不渲染云 :(
    	};
    	return false;	
    }

	@Override
	public Vec3 getBrightnessDependentFogColor(Vec3 colour, float brightness) {
		// TODO 自动生成的方法存根
		return colour.multiply(brightness * 0.94F + 0.06F, brightness * 0.94F + 0.06F, brightness * 0.91F + 0.09F);
	}

	@Override
	public boolean isFoggyAt(int p_108874_, int p_108875_) {
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
	    		 float f11 = 1.0F ;
		    	 if(!shouldnotRenderClouds) {
		    		 f11 -= level.getRainLevel(partialTick);
		    	 }
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
	   
	   /**
	    * 这个和雨有关，还有在方块上的雨水粒子
	    * */
	   @Override
	    public boolean tickRain(ClientLevel level, int ticks, Camera camera)
	    {
	    	if(shouldnotRenderClouds) {
	    		return true;// ! true才是不渲染云 :(
	    	};
	    	return false;	
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
	   
	    @Override
	    public boolean renderSnowAndRain(ClientLevel cLevel, int ticks, float partialTick, LightTexture lightTexture, double camX, double camY, double camZ)
	    {
	    	if(shouldnotRenderClouds) {
	    		return true;
	    	};
	    	return false;	
	    	/*
	    	 if(shouldnotRenderClouds) return true;
	         float f = this.minecraft.level.getRainLevel(partialTick);
	         if (!(f <= 0.0F)) {
	            lightTexture.turnOnLightLayer();
	            Level level = this.minecraft.level;
	            int i = Mth.floor(camX);
	            int j = Mth.floor(camY);
	            int k = Mth.floor(camZ);
	            Tesselator tesselator = Tesselator.getInstance();
	            BufferBuilder bufferbuilder = tesselator.getBuilder();
	            RenderSystem.disableCull();
	            RenderSystem.enableBlend();
	            RenderSystem.enableDepthTest();
	            int l = 5;
	            if (Minecraft.useFancyGraphics()) {
	               l = 10;
	            }

	            RenderSystem.depthMask(Minecraft.useShaderTransparency());
	            int i1 = -1;
	            float f1 = (float)ticks + partialTick;
	            RenderSystem.setShader(GameRenderer::getParticleShader);
	            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

	            for(int j1 = k - l; j1 <= k + l; ++j1) {
	               for(int k1 = i - l; k1 <= i + l; ++k1) {
	                  int l1 = (j1 - k + 16) * 32 + k1 - i + 16;
	                  double d0 = (double)this.rainSizeX[l1] * 0.5D;
	                  double d1 = (double)this.rainSizeZ[l1] * 0.5D;
	                  blockpos$mutableblockpos.set((double)k1, camY, (double)j1);
	                  Biome biome = level.getBiome(blockpos$mutableblockpos).value();
	                  if (biome.hasPrecipitation()) {
	                     int i2 = level.getHeight(Heightmap.Types.MOTION_BLOCKING, k1, j1);
	                     int j2 = j - l;
	                     int k2 = j + l;
	                     if (j2 < i2) {
	                        j2 = i2;
	                     }

	                     if (k2 < i2) {
	                        k2 = i2;
	                     }

	                     int l2 = i2;
	                     if (i2 < j) {
	                        l2 = j;
	                     }

	                     if (j2 != k2) {
	                        RandomSource randomsource = RandomSource.create((long)(k1 * k1 * 3121 + k1 * 45238971 ^ j1 * j1 * 418711 + j1 * 13761));
	                        blockpos$mutableblockpos.set(k1, j2, j1);
	                        Biome.Precipitation biome$precipitation = biome.getPrecipitationAt(blockpos$mutableblockpos);
	                        if (biome$precipitation == Biome.Precipitation.RAIN) {
	                           if (i1 != 0) {
	                              if (i1 >= 0) {
	                                 tesselator.end();
	                              }

	                              i1 = 0;
	                              RenderSystem.setShaderTexture(0, RAIN_LOCATION);
	                              bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
	                           }

	                           int i3 = ticks & 131071;
	                           int j3 = k1 * k1 * 3121 + k1 * 45238971 + j1 * j1 * 418711 + j1 * 13761 & 255;
	                           float f2 = 3.0F + randomsource.nextFloat();
	                           float f3 = -((float)(i3 + j3) + partialTick) / 32.0F * f2;
	                           float f4 = f3 % 32.0F;
	                           double d2 = (double)k1 + 0.5D - camX;
	                           double d3 = (double)j1 + 0.5D - camZ;
	                           float f6 = (float)Math.sqrt(d2 * d2 + d3 * d3) / (float)l;
	                           float f7 = ((1.0F - f6 * f6) * 0.5F + 0.5F) * f;
	                           blockpos$mutableblockpos.set(k1, l2, j1);
	                           int k3 = getLightColor(level, blockpos$mutableblockpos);
	                           bufferbuilder.vertex((double)k1 - camX - d0 + 0.5D, (double)k2 - camY, (double)j1 - camZ - d1 + 0.5D).uv(0.0F, (float)j2 * 0.25F + f4).color(1.0F, 1.0F, 1.0F, f7).uv2(k3).endVertex();
	                           bufferbuilder.vertex((double)k1 - camX + d0 + 0.5D, (double)k2 - camY, (double)j1 - camZ + d1 + 0.5D).uv(1.0F, (float)j2 * 0.25F + f4).color(1.0F, 1.0F, 1.0F, f7).uv2(k3).endVertex();
	                           bufferbuilder.vertex((double)k1 - camX + d0 + 0.5D, (double)j2 - camY, (double)j1 - camZ + d1 + 0.5D).uv(1.0F, (float)k2 * 0.25F + f4).color(1.0F, 1.0F, 1.0F, f7).uv2(k3).endVertex();
	                           bufferbuilder.vertex((double)k1 - camX - d0 + 0.5D, (double)j2 - camY, (double)j1 - camZ - d1 + 0.5D).uv(0.0F, (float)k2 * 0.25F + f4).color(1.0F, 1.0F, 1.0F, f7).uv2(k3).endVertex();
	                        } else if (biome$precipitation == Biome.Precipitation.SNOW) {
	                           if (i1 != 1) {
	                              if (i1 >= 0) {
	                                 tesselator.end();
	                              }

	                              i1 = 1;
	                              RenderSystem.setShaderTexture(0, SNOW_LOCATION);
	                              bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
	                           }

	                           float f8 = -((float)(ticks & 511) + partialTick) / 512.0F;
	                           float f9 = (float)(randomsource.nextDouble() + (double)f1 * 0.01D * (double)((float)randomsource.nextGaussian()));
	                           float f10 = (float)(randomsource.nextDouble() + (double)(f1 * (float)randomsource.nextGaussian()) * 0.001D);
	                           double d4 = (double)k1 + 0.5D - camX;
	                           double d5 = (double)j1 + 0.5D - camZ;
	                           float f11 = (float)Math.sqrt(d4 * d4 + d5 * d5) / (float)l;
	                           float f5 = ((1.0F - f11 * f11) * 0.3F + 0.5F) * f;
	                           blockpos$mutableblockpos.set(k1, l2, j1);
	                           int j4 = getLightColor(level, blockpos$mutableblockpos);
	                           int k4 = j4 >> 16 & '\uffff';
	                           int l4 = j4 & '\uffff';
	                           int l3 = (k4 * 3 + 240) / 4;
	                           int i4 = (l4 * 3 + 240) / 4;
	                           bufferbuilder.vertex((double)k1 - camX - d0 + 0.5D, (double)k2 - camY, (double)j1 - camZ - d1 + 0.5D).uv(0.0F + f9, (float)j2 * 0.25F + f8 + f10).color(1.0F, 1.0F, 1.0F, f5).uv2(i4, l3).endVertex();
	                           bufferbuilder.vertex((double)k1 - camX + d0 + 0.5D, (double)k2 - camY, (double)j1 - camZ + d1 + 0.5D).uv(1.0F + f9, (float)j2 * 0.25F + f8 + f10).color(1.0F, 1.0F, 1.0F, f5).uv2(i4, l3).endVertex();
	                           bufferbuilder.vertex((double)k1 - camX + d0 + 0.5D, (double)j2 - camY, (double)j1 - camZ + d1 + 0.5D).uv(1.0F + f9, (float)k2 * 0.25F + f8 + f10).color(1.0F, 1.0F, 1.0F, f5).uv2(i4, l3).endVertex();
	                           bufferbuilder.vertex((double)k1 - camX - d0 + 0.5D, (double)j2 - camY, (double)j1 - camZ - d1 + 0.5D).uv(0.0F + f9, (float)k2 * 0.25F + f8 + f10).color(1.0F, 1.0F, 1.0F, f5).uv2(i4, l3).endVertex();
	                        }
	                     }
	                  }
	               }
	            }

	            if (i1 >= 0) {
	               tesselator.end();
	            }

	            RenderSystem.enableCull();
	            RenderSystem.disableBlend();
	            lightTexture.turnOffLightLayer();
	         }
	         return true;
	        */
	    }
	    
	    public static int getLightColor(BlockAndTintGetter p_109542_, BlockPos p_109543_) {
	       return getLightColor(p_109542_, p_109542_.getBlockState(p_109543_), p_109543_);
	    }

	    public static int getLightColor(BlockAndTintGetter p_109538_, BlockState p_109539_, BlockPos p_109540_) {
	       if (p_109539_.emissiveRendering(p_109538_, p_109540_)) {
	          return 15728880;
	       } else {
	          int i = p_109538_.getBrightness(LightLayer.SKY, p_109540_);
	          int j = p_109538_.getBrightness(LightLayer.BLOCK, p_109540_);
	          int k = p_109539_.getLightEmission(p_109538_, p_109540_);
	          if (j < k) {
	             j = k;
	          }

	          return i << 20 | j << 4;
	       }
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