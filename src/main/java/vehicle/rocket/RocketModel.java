package vehicle.rocket;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class RocketModel<T extends RocketEntity> extends EntityModel<T> {
	/*

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("maring", "jump_spider"), "main");
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private final ModelPart MAIN;

	public RocketModel(ModelPart root) {
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
	public void setupAnim(RocketEntity p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_,
			float p_102623_) {
		
	}

*/
	
	
	
	
    private static final String MODID = "maring";
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(MODID, "rocket_t1"), "main");
    private final ModelPart rocket;

    public RocketModel(ModelPart root) {
        this.rocket = root.getChild("rocket");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rocket = partdefinition.addOrReplaceChild("rocket", CubeListBuilder.create(), PartPose.offset(0.0F, 25.0F, 0.0F));

        PartDefinition top = rocket.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 48).addBox(10.0F, -52.0F, -10.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 68).addBox(-10.0F, -52.0F, -10.0F, 20.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 48).addBox(-10.0F, -52.0F, -10.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 68).addBox(-10.0F, -52.0F, 10.0F, 20.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(104, 67).addBox(-3.0F, -75.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(88, 69).addBox(-2.0F, -77.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(80, 69).addBox(-1.0F, -89.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(64, 69).addBox(-2.0F, -90.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r1 = top.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(120, 37).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 27.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -71.0F, 0.0F, -0.48F, -0.7854F, 0.0F));

        PartDefinition cube_r2 = top.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(120, 37).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 27.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -71.0F, 0.0F, -0.48F, -2.3562F, 0.0F));

        PartDefinition cube_r3 = top.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(120, 37).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 27.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -71.0F, 0.0F, -0.48F, 2.3562F, 0.0F));

        PartDefinition cube_r4 = top.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(120, 37).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 27.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -71.0F, 0.0F, -0.48F, 0.7854F, 0.0F));

        PartDefinition cube_r5 = top.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(65, 45).addBox(-8.0F, -20.8F, 8.5175F, 16.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -51.0F, 0.0F, 0.3491F, -1.5708F, 0.0F));

        PartDefinition cube_r6 = top.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(65, 45).addBox(-8.0F, -20.8F, 8.5175F, 16.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -51.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r7 = top.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(65, 45).addBox(-8.0F, -20.8F, 8.5175F, 16.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -51.0F, 0.0F, 0.3491F, 1.5708F, 0.0F));

        PartDefinition cube_r8 = top.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(65, 45).addBox(-8.0F, -20.8F, 8.5175F, 16.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -51.0F, 0.0F, 0.3491F, 3.1416F, 0.0F));

        PartDefinition body = rocket.addOrReplaceChild("body", CubeListBuilder.create().texOffs(36, 44).addBox(-6.0F, -42.0F, -10.0F, 12.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(35, 58).addBox(-4.0F, -32.0F, -10.0F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(63, 43).addBox(4.0F, -40.0F, -10.0F, 0.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(35, 58).addBox(-4.0F, -40.0F, -10.0F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(63, 43).addBox(-4.0F, -40.0F, -10.0F, 0.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-9.0F, -51.0F, -9.0F, 18.0F, 44.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 77).addBox(-9.0F, -51.0F, -9.0F, 2.0F, 44.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, -18).addBox(-9.0F, -51.0F, -9.0F, 0.0F, 44.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(36, 0).addBox(-9.0F, -51.0F, 9.0F, 18.0F, 44.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(36, -18).addBox(9.0F, -51.0F, -9.0F, 0.0F, 44.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 62).addBox(-10.0F, -15.0F, -10.0F, 20.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 42).addBox(10.0F, -15.0F, -10.0F, 0.0F, 5.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 62).addBox(-10.0F, -15.0F, 10.0F, 20.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 42).addBox(-10.0F, -15.0F, -10.0F, 0.0F, 5.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(-18, 44).addBox(-9.0F, -7.0F, -9.0F, 18.0F, 0.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(-18, 44).addBox(-9.0F, -50.0F, -9.0F, 18.0F, 0.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(88, 0).addBox(-13.0F, -17.0F, -3.0F, 4.0F, 9.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 71).addBox(-12.0F, -20.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r9 = body.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 71).addBox(-12.0F, -21.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(88, 0).addBox(-13.0F, -18.0F, -3.0F, 4.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r10 = body.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 77).addBox(-1.0F, -22.0F, -1.0F, 2.0F, 44.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -29.0F, 8.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r11 = body.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 77).addBox(-1.0F, -22.0F, -1.0F, 2.0F, 44.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -29.0F, 8.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r12 = body.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 77).addBox(-1.0F, -22.0F, -1.0F, 2.0F, 44.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -29.0F, -8.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bottom = body.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(94, 15).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(94, -1).addBox(-8.0F, -4.0F, -8.0F, 0.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(94, 15).addBox(-8.0F, -4.0F, 8.0F, 16.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(94, -1).addBox(8.0F, -4.0F, -8.0F, 0.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(78, 22).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(80, 81).addBox(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(94, 19).addBox(-6.0F, -7.0F, 6.0F, 12.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(94, 19).addBox(-6.0F, -7.0F, -6.0F, 12.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(94, 7).addBox(6.0F, -7.0F, -6.0F, 0.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(94, 7).addBox(-6.0F, -7.0F, -6.0F, 0.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition fins = rocket.addOrReplaceChild("fins", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r13 = fins.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(72, 21).addBox(-1.0F, 1.0F, 13.0F, 2.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.0F, -1.0F, 1.1345F, 2.3562F, 0.0F));

        PartDefinition cube_r14 = fins.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(72, 0).addBox(-2.0F, -17.0F, 20.0F, 4.0F, 17.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.0F, -1.0F, 0.0F, 2.3562F, 0.0F));

        PartDefinition cube_r15 = fins.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(72, 0).addBox(-2.0F, -17.0F, 20.0F, 4.0F, 17.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, -1.0F, 0.0F, -2.3562F, 0.0F));

        PartDefinition cube_r16 = fins.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(72, 21).addBox(-1.0F, 1.0F, 13.0F, 2.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.0F, -1.0F, 1.1345F, -2.3562F, 0.0F));

        PartDefinition cube_r17 = fins.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(72, 0).addBox(-2.0F, -17.0F, 20.0F, 4.0F, 17.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, 1.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r18 = fins.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(72, 21).addBox(-1.0F, 1.0F, 13.0F, 2.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.0F, 1.0F, 1.1345F, -0.7854F, 0.0F));

        PartDefinition cube_r19 = fins.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(72, 0).addBox(-2.0F, -17.0F, 20.0F, 4.0F, 17.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.0F, 1.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r20 = fins.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(72, 21).addBox(-1.0F, 1.0F, 13.0F, 2.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.0F, 1.0F, 1.1345F, 0.7854F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }


    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.rocket.yRot = netHeadYaw / (180F / (float) Math.PI);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        rocket.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}