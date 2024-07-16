package vehicle.rocket;

import com.item.ItemRegister;

import event.forge.SetRocketItemStackEvent;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;

/**
 * 代码主要来自
 * BeyondEarth
 * https://github.com/MrScautHD/Beyond-Earth
 * */
public class RocketEntity extends IRocketEntity {

	public static final int DEFAULT_FUEL_BUCKETS = 3;
	private static final String MODID = "maring";

	public RocketEntity(EntityType<?> type, Level level) {
		super(type, level);
	}

	@Override
	public double getRocketSpeed() {
		return 0.63;
	}

	@Override
	public int getTier() {
		return 1;
	}

	@Override
	public int getBucketsOfFull() {
		return 10;
	}
	
	public static int staticGetBucketsOfFull() {
		return 10;
	}

	/*
	@Override
	public double getPassengerRidingPosition() {
		return super.getPassengersRidingOffset() - 2.35;
	}*/

	@Override
	public ItemStack getRocketItem() {
		ItemStack itemStack = new ItemStack(ItemRegister.ROCKET_ITEM.get(), 1);
		itemStack.getOrCreateTag().putInt(MODID + ":fuel", this.getEntityData().get(FUEL));
		MinecraftForge.EVENT_BUS.post(new SetRocketItemStackEvent(this, itemStack));

		return itemStack;
	}

	@Override
	public void spawnParticle() {
		Vec3 vec = this.getDeltaMovement();

		if (this.level() instanceof ServerLevel) {
			if (this.entityData.get(START_TIMER) == 200) {
				for (ServerPlayer p : ((ServerLevel) this.level()).getServer().getPlayerList().getPlayers()) {
					//((ServerLevel) this.level()).sendParticles(p, (ParticleOptions) ParticleRegistry.LARGE_FLAME_PARTICLE.get(), true, this.getX() - vec.x, this.getY() - vec.y - 2.2, this.getZ() - vec.z, 20, 0.1, 0.1, 0.1, 0.001);
					//((ServerLevel) this.level()).sendParticles(p, (ParticleOptions) ParticleRegistry.LARGE_SMOKE_PARTICLE.get(), true, this.getX() - vec.x, this.getY() - vec.y - 3.2, this.getZ() - vec.z, 10, 0.1, 0.1, 0.1, 0.04);
				}
			} else {
				for (ServerPlayer p : ((ServerLevel) this.level()).getServer().getPlayerList().getPlayers()) {
					((ServerLevel) this.level()).sendParticles(p, ParticleTypes.CAMPFIRE_COSY_SMOKE, true, this.getX() - vec.x, this.getY() - vec.y - 0.1, this.getZ() - vec.z, 6, 0.1, 0.1, 0.1, 0.023);
				}
			}
		}
	}
}