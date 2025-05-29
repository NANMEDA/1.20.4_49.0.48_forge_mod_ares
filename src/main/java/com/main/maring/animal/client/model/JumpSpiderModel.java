package com.main.maring.animal.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import com.main.maring.animal.entity.jumpspider.JumpSpider;
import com.main.maring.animal.entity.jumpspider.JumpSpiderAnimation;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class JumpSpiderModel extends HierarchicalModel<JumpSpider>{

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("maring", "jump_spider"), "main");
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private final ModelPart MAIN;

	public JumpSpiderModel(ModelPart root) {
		this.MAIN = root.getChild("MAIN");

	}

	@SuppressWarnings("unused")
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition MAIN = partdefinition.addOrReplaceChild("MAIN", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, 21.0F, 3.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition BODY = MAIN.addOrReplaceChild("BODY", CubeListBuilder.create().texOffs(18, 20).addBox(-10.0F, -4.0F, 0.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-7.0F, -7.0F, -1.0F, 8.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(1.0F, -4.0F, 1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leg1 = MAIN.addOrReplaceChild("leg1", CubeListBuilder.create(), PartPose.offset(-6.0F, -6.0F, 6.0F));

		PartDefinition leg_r1 = leg1.addOrReplaceChild("leg_r1", CubeListBuilder.create().texOffs(24, 7).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4F, -1.6F, -11.6F, -0.4137F, -0.0843F, -0.5682F));

		PartDefinition leg_r2 = leg1.addOrReplaceChild("leg_r2", CubeListBuilder.create().texOffs(0, 20).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.459F, -0.0843F, -0.2628F));

		PartDefinition f1 = leg1.addOrReplaceChild("f1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition f1_r1 = f1.addOrReplaceChild("f1_r1", CubeListBuilder.create().texOffs(29, 28).addBox(0.0F, -11.0F, -1.0F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 9.0F, 5.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition f1_r2 = f1.addOrReplaceChild("f1_r2", CubeListBuilder.create().texOffs(4, 34).addBox(0.0F, -11.0F, -1.0F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 9.0F, -12.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition leg2 = MAIN.addOrReplaceChild("leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leg2_r1 = leg2.addOrReplaceChild("leg2_r1", CubeListBuilder.create().texOffs(8, 21).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -7.6F, -5.6F, -0.4363F, 0.0F, 0.0F));

		PartDefinition leg2_r2 = leg2.addOrReplaceChild("leg2_r2", CubeListBuilder.create().texOffs(16, 13).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -6.0F, 6.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition feet2 = leg2.addOrReplaceChild("feet2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition f2_r1 = feet2.addOrReplaceChild("f2_r1", CubeListBuilder.create().texOffs(25, 28).addBox(0.0F, -11.0F, -1.0F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 3.0F, 11.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition f2_r2 = feet2.addOrReplaceChild("f2_r2", CubeListBuilder.create().texOffs(0, 34).addBox(0.0F, -11.0F, -1.0F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 3.0F, -6.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition leg3 = MAIN.addOrReplaceChild("leg3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leg3_r1 = leg3.addOrReplaceChild("leg3_r1", CubeListBuilder.create().texOffs(26, 21).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -7.6F, -5.6F, -0.4363F, 0.0F, 0.0F));

		PartDefinition leg3_r2 = leg3.addOrReplaceChild("leg3_r2", CubeListBuilder.create().texOffs(8, 14).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -6.0F, 6.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition f3 = leg3.addOrReplaceChild("f3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition f3_r1 = f3.addOrReplaceChild("f3_r1", CubeListBuilder.create().texOffs(21, 27).addBox(0.0F, -11.0F, -1.0F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 3.0F, 11.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition f3_r2 = f3.addOrReplaceChild("f3_r2", CubeListBuilder.create().texOffs(33, 28).addBox(0.0F, -11.0F, -1.0F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 3.0F, -6.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition leg4 = MAIN.addOrReplaceChild("leg4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leg4_r1 = leg4.addOrReplaceChild("leg4_r1", CubeListBuilder.create().texOffs(0, 27).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.6F, -5.6F, -0.4363F, 0.0F, 0.4363F));

		PartDefinition leg4_r2 = leg4.addOrReplaceChild("leg4_r2", CubeListBuilder.create().texOffs(0, 13).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6F, -6.0F, 6.0F, 0.4363F, 0.0F, 0.4363F));

		PartDefinition f4 = leg4.addOrReplaceChild("f4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition f4_r1 = f4.addOrReplaceChild("f4_r1", CubeListBuilder.create().texOffs(14, 28).addBox(0.0F, -11.0F, -1.0F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 11.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition f4_r2 = f4.addOrReplaceChild("f4_r2", CubeListBuilder.create().texOffs(32, 0).addBox(0.0F, -11.0F, -1.0F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -6.0F, -0.0873F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}



	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		MAIN.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setupAnim(JumpSpider p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_,
			float p_102623_) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		
		this.animateWalk(JumpSpiderAnimation.breaking, p_102619_, p_102620_, 2.0f, 2.5f);
		this.animate(p_102618_.snapstate, JumpSpiderAnimation.breaking, p_102621_,1.0f);
	}

	@Override
	public ModelPart root() {
		// TODO 自动生成的方法存根
		return MAIN;
	}

}
