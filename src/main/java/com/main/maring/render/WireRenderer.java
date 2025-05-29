package com.main.maring.render;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.util.Mth;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

/**
 * 渲染导线
 * @implNote 1.渲染时，端点假如不在视线内，会不渲染2.在方块边上，不在中间3.有时候渲染两个线
 * */
@OnlyIn(Dist.CLIENT)
public class WireRenderer {
	private static final String MODID = "maring";
	private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(MODID,"textures/entity/line_knot.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutout(TEXTURE_LOCATION);

    // 主要的电线渲染方法
    public static void renderWire(Vec3 startPos, Vec3 endPos, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        if (endPos.z > startPos.z) {
        	return;//反正能渲染好一个就可以了
        }else if(endPos.z == startPos.z) {
        	if (endPos.y > startPos.y) {
            	return;
            }else if(endPos.y == startPos.y) {
            	if (endPos.x > startPos.x) {
                	return;
                }
            }
        }
    	
    	// 计算电线的顶点和法线方向
        float f4 = (float)(endPos.x - startPos.x);
        float f5 = (float)(endPos.y - startPos.y);
        float f6 = (float)(endPos.z - startPos.z);
        
        

        // 使用渲染缓存处理电线的顶点
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RENDER_TYPE);
        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        
        // 渲染电线的起点和终点
        vertex(vertexConsumer, matrix4f, matrix3f, light, 0.0F, 0, 0, 1);
        vertex(vertexConsumer, matrix4f, matrix3f, light, 1.0F, 0, 1, 1);
        vertex(vertexConsumer, matrix4f, matrix3f, light, 1.0F, 1, 1, 0);
        vertex(vertexConsumer, matrix4f, matrix3f, light, 0.0F, 1, 0, 0);

        // 渲染电线的线段
        VertexConsumer vertexConsumer1 = bufferSource.getBuffer(RenderType.lineStrip());
        PoseStack.Pose poseStack1 = poseStack.last();
        int segments = 16;

        for (int i = 0; i <= segments; ++i) {
            stringVertex(f4, f5, f6, vertexConsumer1, poseStack1, fraction(i, segments), fraction(i + 1, segments));
        }
    }

    private static void vertex(VertexConsumer p_254464_, Matrix4f p_254085_, Matrix3f p_253962_, int p_254296_, float p_253632_, int p_254132_, int p_254171_, int p_254026_) {
        p_254464_.vertex(p_254085_, p_253632_ - 0.5F, (float)p_254132_ - 0.5F, 0.0F)
                  .color(255, 255, 255, 255)
                  .uv((float)p_254171_, (float)p_254026_)
                  .overlayCoords(OverlayTexture.NO_OVERLAY)
                  .uv2(p_254296_)
                  .normal(p_253962_, 0.0F, 1.0F, 0.0F)
                  .endVertex();
    }

    private static void stringVertex(float dx, float dy, float dz, VertexConsumer vertexConsumer, PoseStack.Pose pose, float p_174124_, float p_174125_) {
        float f = dx * p_174124_;
        float f1 = dy * (p_174124_ * p_174124_ + p_174124_) * 0.5F + 0.25F;
        float f2 = dz * p_174124_;
        float f3 = dx * p_174125_ - f;
        float f4 = dy * (p_174125_ * p_174125_ + p_174125_) * 0.5F + 0.25F - f1;
        float f5 = dz * p_174125_ - f2;
        float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
        f3 /= f6;
        f4 /= f6;
        f5 /= f6;
        vertexConsumer.vertex(pose.pose(), f, f1, f2).color(0, 0, 0, 255).normal(pose.normal(), f3, f4, f5).endVertex();
    }

    private static float fraction(int p_114691_, int p_114692_) {
        return (float)p_114691_ / (float)p_114692_;
    }

}
