package com.main.maring.vehicle.rocket;

import com.main.maring.Maring;
import com.main.maring.block.norm.BlockBasic;
import com.main.maring.block.norm.BlockRegister;
import com.main.maring.event.forge.SetRocketItemStackEvent;
import com.main.maring.event.forge.TeleportAndCreateLanderEvent;
import com.main.maring.item.ItemRegister;
import com.main.maring.menu.coredigger.CoreDiggerMenuProvider;
import com.main.maring.menu.rocket.RocketMenuProvider;
import com.main.maring.util.Methods;
import com.main.maring.vehicle.VehicleRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

/**
 * 代码主要来自
 * BeyondEarth
 * https://github.com/MrScautHD/Beyond-Earth
 * */
public class RocketEntity extends IRocketEntity {

	public static final int DEFAULT_FUEL_BUCKETS = 3;
	private static final int BUCKET_SIZE = 1000;

	private static ResourceKey<Level> limboKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(Maring.MODID, "limbo"));
	private static ResourceKey<Level> marKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(Maring.MODID, "maringmar"));


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
		itemStack.getOrCreateTag().putInt(Maring.MODID + ":fuel", this.getEntityData().get(FUEL));
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
					((ServerLevel) this.level()).sendParticles(p, ParticleTypes.DRIPPING_LAVA, true, this.getX() - vec.x, this.getY() - vec.y - 0.1, this.getZ() - vec.z, 6, 0.1, 0.1, 0.1, 0.023);
				}
			} else {
				for (ServerPlayer p : ((ServerLevel) this.level()).getServer().getPlayerList().getPlayers()) {
					((ServerLevel) this.level()).sendParticles(p, ParticleTypes.CAMPFIRE_COSY_SMOKE, true, this.getX() - vec.x, this.getY() - vec.y - 0.1, this.getZ() - vec.z, 6, 0.1, 0.1, 0.1, 0.023);
				}
			}
		}
	}

	@Override
	public InteractionResult interact(Player player, InteractionHand hand) {
		InteractionResult result = InteractionResult.sidedSuccess(this.level().isClientSide);
		if (!this.level().isClientSide) {
			if (player.isCrouching()) {
				this.openCustomInventoryScreen(player);
				return InteractionResult.CONSUME;
			}
			player.startRiding(this);
			return InteractionResult.CONSUME;
		}

		return result;
	}


	public void startRocket() {
		Player player = this.getFirstPlayerPassenger();

		if (player != null) {
			SynchedEntityData data = this.getEntityData();

			if (data.get(IRocketEntity.FUEL) >= this.getFuelCapacity()/2) {
				Level level = this.level();

				if (!data.get(IRocketEntity.ROCKET_START)) {
					data.set(IRocketEntity.ROCKET_START, true);
					//this.level().playSound(null, this, SoundRegistry.ROCKET_SOUND.get(), SoundSource.NEUTRAL, 1, 1);
				}
			} else {
				Methods.sendVehicleHasNoFuelMessage(player);
			}
		}
	}


	@Override
	public void openCustomInventoryScreen(Player player) {
		if (player instanceof ServerPlayer serverPlayer) {
			ServerPlayer ifpe = (ServerPlayer)player;
			NetworkHooks.openScreen(ifpe, new RocketMenuProvider(this.getId()), buf -> buf.writeInt(this.getId()));
			//ifpe.openMenu(new RocketMenuProvider(this.getId()));
		}
	}

	public void fillUpRocket() {
		ItemStack slotItem0 = this.getInventory().getStackInSlot(0);
		ItemStack slotItem1 = this.getInventory().getStackInSlot(1);

		if (slotItem0.getItem() == ItemRegister.BOTTLED_FUEL.get() && this.entityData.get(FUEL) + BUCKET_SIZE <= this.getFuelCapacity()) {
			if (slotItem1.getCount() != slotItem1.getMaxStackSize()) {
				this.getInventory().extractItem(0, 1, false);
				this.getInventory().insertItem(1, new ItemStack(ItemRegister.PRESSURIZED_CAN.get()), false);

				this.getEntityData().set(FUEL, this.entityData.get(FUEL) + BUCKET_SIZE);
			}
		}
	}

	private final ItemStackHandler inventory = new ItemStackHandler(8) {

	};
	public ItemStackHandler getInventory() {
		return this.inventory;
	}

	private final CombinedInvWrapper combined = new CombinedInvWrapper(this.inventory);

	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
		if (this.isAlive() && capability == ForgeCapabilities.ITEM_HANDLER && side == null) {
			return LazyOptional.of(() -> this.combined).cast();
		}
		return super.getCapability(capability, side);
	}


	public void GoingTo() {
		if (this.yo > 200) {
			Player player = this.getFirstPlayerPassenger();
			if(player==null) return;
			Level level = this.level();
			if(!level.isClientSide && level.dimension()==Level.OVERWORLD) {
				ServerLevel mar = player.level().getServer().getLevel(marKey);
				if (mar == null) {
					System.out.println("We Lost Mar!? it should be an error");
					return;
				}
				double sendX = player.getX();
				double sendZ = player.getZ();
				player.teleportTo(mar, sendX/4, 800, sendZ/4, null, player.getYRot(), player.getXRot());
				Level newLevel = player.level();
				if(!newLevel.isClientSide) {
					RocketEntity newRocket = new RocketEntity(VehicleRegister.ROCKET_ENTITY.get(), newLevel);
					SynchedEntityData data = newRocket.getEntityData();
					if (!data.get(IRocketEntity.ROCKET_IS_DROP)) {
						data.set(IRocketEntity.ROCKET_IS_DROP, true);
						data.set(IRocketEntity.FUEL,this.getFuel());
						for(int i=0;i<8;i++){
							ItemStack stack = this.getInventory().getStackInSlot(i);
							newRocket.getInventory().setStackInSlot(i,stack);
						}
					}
					newRocket.moveTo(player.position());
					newLevel.addFreshEntity(newRocket);
					MinecraftForge.EVENT_BUS.post(new TeleportAndCreateLanderEvent(newRocket, player));
					player.startRiding(newRocket);
				}
				return;
			}else if(!level.isClientSide && level.dimension() == level.getServer().getLevel(marKey).dimension()){
				ServerLevel overworld = level.getServer().getLevel(Level.OVERWORLD);
				double sendX = player.getX();
				double sendZ = player.getZ();
				player.teleportTo(overworld, sendX*4, 800, sendZ*4, null, player.getYRot(), player.getXRot());
				Level newLevel = player.level();
				if(!newLevel.isClientSide) {
					RocketEntity newRocket = new RocketEntity(VehicleRegister.ROCKET_ENTITY.get(), newLevel);
					SynchedEntityData data = newRocket.getEntityData();
					if (!data.get(IRocketEntity.ROCKET_IS_DROP)) {
						data.set(IRocketEntity.ROCKET_IS_DROP, true);
						data.set(IRocketEntity.FUEL,this.getFuel());
						for(int i=0;i<8;i++){
							ItemStack stack = this.getInventory().getStackInSlot(i);
							newRocket.getInventory().setStackInSlot(i,stack);
						}
					}
					newRocket.moveTo(player.position());
					newLevel.addFreshEntity(newRocket);
					MinecraftForge.EVENT_BUS.post(new TeleportAndCreateLanderEvent(newRocket, player));
					player.startRiding(newRocket);
				}
				return;
			}else if(!level.isClientSide){
				ServerLevel overworld = level.getServer().getLevel(Level.OVERWORLD);
				double sendX = player.getX();
				double sendZ = player.getZ();
				player.teleportTo(overworld, sendX*4, 300, sendZ*4, null, player.getYRot(), player.getXRot());
				Level newLevel = player.level();
				if(!newLevel.isClientSide) {
					RocketEntity newRocket = new RocketEntity(VehicleRegister.ROCKET_ENTITY.get(), newLevel);
					SynchedEntityData data = newRocket.getEntityData();
					if (!data.get(IRocketEntity.ROCKET_IS_DROP)) {
						data.set(IRocketEntity.ROCKET_IS_DROP, true);
					}
					newRocket.moveTo(player.position());
					newLevel.addFreshEntity(newRocket);
					MinecraftForge.EVENT_BUS.post(new TeleportAndCreateLanderEvent(newRocket, player));
					player.startRiding(newRocket);
				}
				return;
			}
			if (player != null) {

				if (player.containerMenu == player.inventoryMenu) {
					player.closeContainer();
				}

				player.getPersistentData().putBoolean(Maring.MODID + ":planet_selection_menu_open", true);

				/** SAVE ITEMS IN THE PLAYER */
				ListTag tag = new ListTag();

				tag.add(new ItemStack(this.getRocketItem().getItem()).save(new CompoundTag()));

				for (int i = 0; i <= this.getInventory().getSlots() - 1; i++) {
					tag.add(this.getInventory().getStackInSlot(i).save(new CompoundTag()));
				}

				player.getPersistentData().put(Maring.MODID + ":rocket_item_list", tag);
				player.setNoGravity(false);

				/** STOP ROCKET SOUND */
                /*
                if (player instanceof ServerPlayer serverPlayer) {
                    Methods.stopSound(serverPlayer, SoundRegistry.ROCKET_SOUND.getId(), SoundSource.NEUTRAL);
                }

                MinecraftForge.EVENT_BUS.post(new SetPlanetSelectionMenuNeededNbtEvent(player, this));
                */

				if (!this.level().isClientSide) {
					this.remove(RemovalReason.DISCARDED);
				}
			}
		}
	}


	private double dropSpeed = -2;

	protected void IsDrop() {
		BlockPos entityPos = this.blockPosition();
		// 向下搜索非空气方块
		double dis = 0;
		//if(this.level().isClientSide) return;
		Level level = this.level();
		if(level.isEmptyBlock(new BlockPos(entityPos.getX(), entityPos.getY()-20, entityPos.getZ()))) {
			this.setDeltaMovement(this.getDeltaMovement().x, dropSpeed, this.getDeltaMovement().z);
			this.setPos(this.xo+this.getDeltaMovement().x, this.yo+ this.getDeltaMovement().y, this.zo + this.getDeltaMovement().z);
			return;
		}
		BlockPos mutablePos = new BlockPos(entityPos.getX(), entityPos.getY(), entityPos.getZ());
		while (dis<20 && level.isEmptyBlock(mutablePos)) {
			mutablePos = mutablePos.below();
			dis++;
		}
		double K = dis/20.0;
		if (this.getDeltaMovement().y < -0.05&& dis>1) {
			this.setDeltaMovement(this.getDeltaMovement().x, dropSpeed*K*K, this.getDeltaMovement().z);
		} else{
			this.setDeltaMovement(this.getDeltaMovement().x, 0, this.getDeltaMovement().z);
			SynchedEntityData data = this.getEntityData();
			if (data.get(IRocketEntity.ROCKET_IS_DROP)) {
				data.set(IRocketEntity.ROCKET_IS_DROP, false);
			}
			changeNineBelowBlock();
		}
		this.setPos(this.xo+this.getDeltaMovement().x, this.yo+ this.getDeltaMovement().y, this.zo + this.getDeltaMovement().z);

	}

	protected void dropEquipment() {
		for (int i = 0; i < this.inventory.getSlots(); ++i) {
			ItemStack itemstack = this.inventory.getStackInSlot(i);
			if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
				this.spawnAtLocation(itemstack);
			}
		}
	}

	public boolean doesDrop(BlockState state, BlockPos pos) {
		if (this.onGround() || this.isInFluidType()) {

			BlockState state2 = this.level().getBlockState(new BlockPos((int)Math.floor(this.getX()), (int)(this.getY() - 0.2), (int)Math.floor(this.getZ())));

			if (!this.level().isEmptyBlock(pos)) {

				this.dropEquipment();
				this.spawnRocketItem();

				if (!this.level().isClientSide) {
					this.remove(RemovalReason.DISCARDED);
				}

				return true;
			}
		}

		return false;
	}

	public void startTimerAndFlyMovement() {
		int start_time = this.entityData.get(START_TIMER);
		if (start_time < 200) {
			System.out.println("add timer to: " + this.entityData.get(START_TIMER));
			this.entityData.set(START_TIMER, start_time + 1);
			if(start_time==199) {
				changeNineBelowBlock();
			}
		}

		if (start_time == 200) {
			//System.out.println("ok for fly");
			int fuel = this.getEntityData().get(FUEL);
			if(fuel == 0) return;
			this.getEntityData().set(FUEL, fuel-1);
			if (this.getDeltaMovement().y < this.getRocketSpeed() - 0.1) {
				this.setDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y + 0.1, this.getDeltaMovement().z);
			} else {
				this.setDeltaMovement(this.getDeltaMovement().x, this.getRocketSpeed(), this.getDeltaMovement().z);
			}
			this.setPos(this.xo+this.getDeltaMovement().x, this.yo+ this.getDeltaMovement().y, this.zo + this.getDeltaMovement().z);
		}
	}



	private void changeNineBelowBlock() {
		Level level = this.level();
		if(level.isClientSide) return;
		BlockPos entityPos = this.blockPosition();
		Random random = new Random();
		for (int x = -8; x <= 8; x++) {
			for (int y = -2; y <= 0; y++) {
				for (int z = -8; z <= 8; z++) {
					if (random.nextBoolean()) continue;
					if(x*x+z*z>64) continue;
					BlockPos position = entityPos.offset(x, y, z);
					BlockState state = level.getBlockState(position);
					if(state.is(Blocks.GRASS_BLOCK)||state.is(Blocks.DIRT)||state.is(Blocks.ROOTED_DIRT)||state.is(Blocks.MUD)) {
						BlockState newState = Blocks.COARSE_DIRT.defaultBlockState();
						level.setBlock(position, newState, 0);
						level.sendBlockUpdated(position, newState, newState, Block.UPDATE_CLIENTS);
					}else if(state.is(Blocks.SAND)||state.is(Blocks.RED_SAND)) {
						BlockState newState = Blocks.GLASS.defaultBlockState();
						level.setBlock(position, newState, 0);
						level.sendBlockUpdated(position, newState, newState, Block.UPDATE_CLIENTS);
					}else if(state.is(BlockRegister.COMMON_BLOCKS[BlockBasic.getIdFromName("mar_surface")].get())||state.is(BlockRegister.COMMON_BLOCKS[BlockBasic.getIdFromName("methane_vents")].get())) {
						BlockState newState = BlockRegister.COMMON_BLOCKS[BlockBasic.getIdFromName("mar_baked_surface")].get().defaultBlockState();
						level.setBlock(position, newState, 0);
						level.sendBlockUpdated(position, newState, newState, Block.UPDATE_CLIENTS);
					}
				}
			}
		}

	}

	@Override
	public void kill() {
		this.dropEquipment();
		this.spawnRocketItem();

		if (!this.level().isClientSide) {
			this.remove(RemovalReason.DISCARDED);
		}
	}



	@Override
	public boolean hurt(DamageSource source, float p_21017_) {
		Entity sourceEntity = source.getEntity();

		if (sourceEntity != null && sourceEntity.isCrouching() && !this.isVehicle()) {

			this.spawnRocketItem();
			this.dropEquipment();

			if (!this.level().isClientSide) {
				this.remove(RemovalReason.DISCARDED);
			}

			return true;
		}

		return false;
	}


	@Override
	public void tick() {
		super.tick();
		this.rotateRocket();
		this.checkOnBlocks();
		this.fillUpRocket();
		this.rocketExplosion();
		this.burnEntities();

		if (this.entityData.get(ROCKET_START)) {
			this.spawnParticle();
			this.startTimerAndFlyMovement();
			this.GoingTo();
		}else if(this.entityData.get(ROCKET_IS_DROP)) {
			this.IsDrop();
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.put("InventoryCustom", this.inventory.serializeNBT());

		compound.putBoolean("rocket_start", this.getEntityData().get(ROCKET_START));
		compound.putBoolean("rocket_is_drop", this.getEntityData().get(ROCKET_IS_DROP));
		compound.putInt("fuel", this.getEntityData().get(FUEL));
		compound.putInt("start_timer", this.getEntityData().get(START_TIMER));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);

		Tag inventoryCustom = compound.get("InventoryCustom");
		if (inventoryCustom instanceof CompoundTag) {
			this.inventory.deserializeNBT((CompoundTag) inventoryCustom);
		}

		this.getEntityData().set(ROCKET_START, compound.getBoolean("rocket_start"));
		this.getEntityData().set(ROCKET_IS_DROP, compound.getBoolean("rocket_is_drop"));
		this.getEntityData().set(FUEL, compound.getInt("fuel"));
		this.getEntityData().set(START_TIMER, compound.getInt("start_timer"));
	}

}