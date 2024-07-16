package animal.entity.jumpspider;

import animal.entity.MonsterRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 抱子蜘蛛
 * 死后有概率召唤更多抱子蜘蛛
 * 和 灾难事件 有关
 * 不会自然生成
 * @author NANMEDA
 * */
public class JumpSpider extends Spider {

    public JumpSpider(EntityType<? extends Spider> entityType, Level level) {
        super(entityType, level);
    }
    
    @Override
    protected void registerGoals() {
    	super.registerGoals();
    	//this.goalSelector.addGoal(0, new FloatGoal(this));
    	
    	//this.goalSelector.addGoal(1, new TemptGoal(this, 1.2D, Ingredient.of(Items.SPIDER_EYE), false));
    	//this.goalSelector.addGoal(2, new RandomStrollGoal(this,1.5D));
    } 
    
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 5.0)
                .add(Attributes.MOVEMENT_SPEED, 1.0);
    }
    
    
    
    
    public final AnimationState snapstate = new AnimationState();
    private int snapTimeout = 0;
    
    @Override
    public void tick() {
    	super.tick();
    	if(this.level().isClientSide()) {
    		setupAnimationState();
    	}
    }
    
    private void setupAnimationState() {
    	if(this.snapTimeout<=0) {
    		this.snapTimeout = this.random.nextInt(40)+40;
    		this.snapstate.start(this.tickCount); 
    	}else {
    		--this.snapTimeout;
    	}
    }
    
    @Override
    protected void updateWalkAnimation(float partialTick) {
    	float f;
    	if(this.getPose() == Pose.STANDING) {
    		f = Math.min(partialTick*6.0f, 1.0f);
    	}else {
    		f=0f;
    	}
    	this.walkAnimation.update(f, 0.2f);
    }
    
    /*
    @Override
    protected void jumpFromGround() {
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x, (double)(this.getJumpPower()*((double)this.random.nextInt(2)-0.5)), vec3.z);
        this.hasImpulse = true;
     }*/
    
    @Override
    protected void playStepSound(BlockPos p_33804_, BlockState p_33805_) {
        this.playSound(SoundEvents.SPIDER_STEP, 0.7F, 1.0F);
     }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SPIDER_STEP;
     }
    
    @SuppressWarnings("resource")
	@Override
    protected SoundEvent getDeathSound() {
    	if (!this.level().isClientSide) {
    	    if (this.level().random.nextFloat() < 0.001f) {//爆小蜘蛛了
    	        for (int i = 0; i < 20; i++) {
    	            JumpSpider jumpSpider = MonsterRegister.JUMP_SPIDER.get().create(this.level());
    	            if (jumpSpider != null) {
    	                double offsetX = this.level().random.nextGaussian() * 0.2; 
    	                double offsetZ = this.level().random.nextGaussian() * 0.2;
    	                jumpSpider.setPos(this.getX() + offsetX, this.getY(), this.getZ() + offsetZ); 
    	                this.level().addFreshEntity(jumpSpider);
    	            }
    	        }
    	    }else if(this.level().random.nextFloat() < 0.05f) {
    	        for (int i = 0; i < 8; i++) {
    	            JumpSpider jumpSpider = MonsterRegister.JUMP_SPIDER.get().create(this.level());
    	            if (jumpSpider != null) {
    	                double offsetX = this.level().random.nextGaussian() * 0.2; 
    	                double offsetZ = this.level().random.nextGaussian() * 0.2;
    	                jumpSpider.setPos(this.getX() + offsetX, this.getY(), this.getZ() + offsetZ); 
    	                this.level().addFreshEntity(jumpSpider);
    	            }
    	        }
    	    }
    	}
        return SoundEvents.SPIDER_DEATH;
     }
    
    //@Override
    //protected boolean spawnCustomParticles() { return true; }

    /*
	@Override
	public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
		// TODO 自动生成的方法存根
		return null;
	}*/

}
