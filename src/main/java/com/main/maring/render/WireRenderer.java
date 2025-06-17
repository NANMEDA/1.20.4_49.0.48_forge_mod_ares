package com.main.maring.render;

import com.main.maring.Maring;
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
	private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(Maring.MODID,"textures/entity/line_knot.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutout(TEXTURE_LOCATION);

    /**
     * 渲染一条从 startPos 到 endPos 的导线，包括线段本体和连接点的装饰。
     *
     * @param startPos 导线起点的世界坐标
     * @param endPos 导线终点的世界坐标
     * @param poseStack 渲染用的位姿堆栈
     * @param bufferSource 渲染缓冲区来源
     * @param light 当前光照强度
     */
    public static void renderWire(Vec3 startPos, Vec3 endPos, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        /*
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
        }*/
    	
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


    /**
     * 渲染导线两端的图案（四个顶点构成的方形）
     *
     * @param p_254464_ 顶点消费者
     * @param p_254085_ 位置矩阵
     * @param p_253962_ 法线矩阵
     * @param p_254296_ 光照值
     * @param p_253632_ 顶点X方向的偏移
     * @param p_254132_ 顶点Y方向的偏移
     * @param p_254171_ 纹理U坐标
     * @param p_254026_ 纹理V坐标
     */
    private static void vertex(VertexConsumer p_254464_, Matrix4f p_254085_, Matrix3f p_253962_, int p_254296_, float p_253632_, int p_254132_, int p_254171_, int p_254026_) {
        p_254464_.vertex(p_254085_, p_253632_ - 0.5F, (float)p_254132_ - 0.5F, 0.0F)
                  .color(255, 255, 255, 255)
                  .uv((float)p_254171_, (float)p_254026_)
                  .overlayCoords(OverlayTexture.NO_OVERLAY)
                  .uv2(p_254296_)
                  .normal(p_253962_, 0.0F, 1.0F, 0.0F)
                  .endVertex();
    }



    /**
     * 渲染导线中部的一个分段，使用向下的抛物线模拟自然下垂的电线。
     * X 和 Z 使用线性插值，Y 使用抛物线插值。
     *
     * @param dx X方向的偏移总量
     * @param dy Y方向的偏移总量
     * @param dz Z方向的偏移总量
     * @param vertexConsumer 顶点消费者
     * @param pose 当前渲染姿势（含矩阵）
     * @param t 当前分段的起点t值（0~1）
     * @param nextT 当前分段的终点t值（0~1）
     */
    private static void stringVertex(float dx, float dy, float dz, VertexConsumer vertexConsumer, PoseStack.Pose pose, float t, float nextT) {
        float archHeight = Math.min(0.2F*Math.abs(dx + dy),5.0f); // 下垂幅度（越大越弯）

        // 起点
        float x1 = dx * t;
        float y1 = dy * t - archHeight * (1.0f - 4.0f * (t - 0.5f) * (t - 0.5f));
        float z1 = dz * t;

        // 终点
        float x2 = dx * nextT;
        float y2 = dy * nextT - archHeight * (1.0f - 4.0f * (nextT - 0.5f) * (nextT - 0.5f));
        float z2 = dz * nextT;

        // 法线（方向向量单位化）
        float dxn = x2 - x1;
        float dyn = y2 - y1;
        float dzn = z2 - z1;
        float len = Mth.sqrt(dxn * dxn + dyn * dyn + dzn * dzn);
        if (len != 0.0f) {
            dxn /= len;
            dyn /= len;
            dzn /= len;
        }

        vertexConsumer.vertex(pose.pose(), x1, y1, z1)
                .color(0, 0, 0, 255)
                .normal(pose.normal(), dxn, dyn, dzn)
                .endVertex();
    }

    private static float fraction(int p_114691_, int p_114692_) {
        return (float)p_114691_ / (float)p_114692_;
    }

}
