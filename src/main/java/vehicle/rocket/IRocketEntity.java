package vehicle.rocket;

import com.google.common.collect.Sets;

import block.norm.BlockBasic;
import block.norm.BlockRegister;
import event.forge.TeleportAndCreateLanderEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import util.Methods;
import util.gauge.GaugeValueHelper;
import util.gauge.IGaugeValue;
import util.gauge.IGaugeValuesProvider;
import vehicle.ModVehicle;
import vehicle.VehicleRegister;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Random;

/**
 * 代码主要来自
 * BeyondEarth
 * https://github.com/MrScautHD/Beyond-Earth
 * */
public abstract class IRocketEntity extends ModVehicle implements IGaugeValuesProvider{

	private static final String MODID = "maring";

    public static final EntityDataAccessor<Boolean> ROCKET_START = SynchedEntityData.defineId(IRocketEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> ROCKET_IS_DROP = SynchedEntityData.defineId(IRocketEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> FUEL = SynchedEntityData.defineId(IRocketEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> START_TIMER = SynchedEntityData.defineId(IRocketEntity.class, EntityDataSerializers.INT);

    
	private static final int BUCKET_SIZE = 1000;
	

    public IRocketEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.entityData.define(ROCKET_START, false);
        this.entityData.define(ROCKET_IS_DROP, false);
        this.entityData.define(FUEL, 0);
        this.entityData.define(START_TIMER, 0);
    }

    public abstract double getRocketSpeed();

    public abstract int getTier();

    public abstract int getBucketsOfFull();

    public int getFuelCapacity() {
        return this.getBucketsOfFull() * BUCKET_SIZE;
	}

	public IGaugeValue getFuelGauge() {
		int fuel = this.getEntityData().get(FUEL);
		int capacity = this.getFuelCapacity();
		return GaugeValueHelper.getFuel(fuel, capacity);
	}

	@Override
	public List<IGaugeValue> getDisplayGaugeValues() {
		return Collections.singletonList(this.getFuelGauge());
	}

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public void push(Entity entity) {

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

    public abstract ItemStack getRocketItem();

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return this.getRocketItem();
    }

    protected void spawnRocketItem() {
        ItemEntity entityToSpawn = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), this.getRocketItem());
        entityToSpawn.setPickUpDelay(10);

        this.level().addFreshEntity(entityToSpawn);
    }

    protected void dropEquipment() {
        for (int i = 0; i < this.inventory.getSlots(); ++i) {
            ItemStack itemstack = this.inventory.getStackInSlot(i);
            if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
                this.spawnAtLocation(itemstack);
            }
        }
    }

    private final ItemStackHandler inventory = new ItemStackHandler(10) {
        @Override
        public int getSlotLimit(int slot) {
            return 64;
        }
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

    public IItemHandlerModifiable getItemHandler() {
        return (IItemHandlerModifiable) this.getCapability(ForgeCapabilities.ITEM_HANDLER, null).resolve().get();
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

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        super.interact(player, hand);
        InteractionResult result = InteractionResult.sidedSuccess(this.level().isClientSide);

        if (!this.level().isClientSide) {
            if (player.isCrouching()) {
                //this.openCustomInventoryScreen(player);
                return InteractionResult.CONSUME;
            }

            player.startRiding(this);
            return InteractionResult.CONSUME;
        }

        return result;
    }

    /*
    @Override
    public void openCustomInventoryScreen(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
        	IForgeServerPlayer ifpe = (IForgeServerPlayer)player;
			ifpe.openScreen(new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return IRocketEntity.this.getName();
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                    FriendlyByteBuf packetBuffer = new FriendlyByteBuf(Unpooled.buffer());
                    packetBuffer.writeVarInt(IRocketEntity.this.getId());
                    return new RocketMenu.GuiContainer(id, inventory, packetBuffer);
                }
            }, buf -> {
                buf.writeVarInt(IRocketEntity.this.getId());
            });
        }
    }
*/
    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity livingEntity) {
        Vec3[] avector3d = new Vec3[]{getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot()), getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot() - 22.5F), getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot() + 22.5F), getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot() - 45.0F), getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot() + 45.0F)};
        Set<BlockPos> set = Sets.newLinkedHashSet();
        double d0 = this.getBoundingBox().maxY;
        double d1 = this.getBoundingBox().minY - 0.5D;
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

        for(Vec3 vector3d : avector3d) {
            blockpos$mutable.set(this.getX() + vector3d.x, d0, this.getZ() + vector3d.z);

            for(double d2 = d0; d2 > d1; --d2) {
                set.add(blockpos$mutable.immutable());
                blockpos$mutable.move(Direction.DOWN);
            }
        }

        for(BlockPos blockpos : set) {
            if (!this.level().getFluidState(blockpos).is(FluidTags.LAVA)) {
                double d3 = this.level().getBlockFloorHeight(blockpos);
                if (DismountHelper.isBlockFloorValid(d3)) {
                    Vec3 vector3d1 = Vec3.upFromBottomCenterOf(blockpos, d3);

                    for(Pose pose : livingEntity.getDismountPoses()) {
                        if (DismountHelper.isBlockFloorValid(this.level().getBlockFloorHeight(blockpos))) {
                            livingEntity.setPose(pose);
                            return vector3d1;
                        }
                    }
                }
            }
        }

        return new Vec3(this.getX(), this.getBoundingBox().maxY, this.getZ());
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
    
    static double dropSpeed = -2;

    private void IsDrop() {
		BlockPos entityPos = this.blockPosition();
		// 向下搜索非空气方块
		double dis = 0;
		if(this.level().isClientSide) return;
		Level level = this.level();
		if(level.isEmptyBlock(new BlockPos(entityPos.getX(), entityPos.getY()-20, entityPos.getZ()))) {
			dropSpeed = this.getDeltaMovement().y;
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

	public abstract void spawnParticle();

    public void fillUpRocket() {
        ItemStack slotItem0 = this.getInventory().getStackInSlot(0);
        ItemStack slotItem1 = this.getInventory().getStackInSlot(1);

        if (slotItem0.getItem() == Items.LAVA_BUCKET && this.entityData.get(FUEL) + BUCKET_SIZE <= this.getFuelCapacity()) {
            if (slotItem1.getCount() != slotItem1.getMaxStackSize()) {
                this.getInventory().extractItem(0, 1, false);
                this.getInventory().insertItem(1, new ItemStack(Items.BUCKET), false);

                this.getEntityData().set(FUEL, this.entityData.get(FUEL) + BUCKET_SIZE);
            }
        }
    }

    public Player getFirstPlayerPassenger() {
        if (!this.getPassengers().isEmpty() && this.getPassengers().get(0) instanceof Player player) {
            return player;
        }

        return null;
    }

    public void rotateRocket() {
        Player player = this.getFirstPlayerPassenger();

        if (player != null) {
        	/*
            if (KeyVariables.isHoldingRight(player) && KeyVariables.isHoldingLeft(player)) {
                return;
            }

            if (KeyVariables.isHoldingRight(player)) {
                Methods.setEntityRotation(this, 1);
            }

            if (KeyVariables.isHoldingLeft(player)) {
                Methods.setEntityRotation(this, -1);
            }
            */
        }
    }

    public void startRocket() {
        Player player = this.getFirstPlayerPassenger();

        if (player != null) {
            SynchedEntityData data = this.getEntityData();

            if (true||data.get(IRocketEntity.FUEL) == this.getFuelCapacity()) {
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

    protected void checkOnBlocks() {
        AABB aabb = this.getBoundingBox();
        BlockPos blockPos1 = new BlockPos((int)aabb.minX, (int)(aabb.minY - 0.2), (int)aabb.minZ);
        BlockPos blockPos2 = new BlockPos((int)aabb.maxX, (int)aabb.minY, (int)aabb.maxZ);

        if (this.level().hasChunksAt(blockPos1, blockPos2)) {
            for (int i = blockPos1.getX(); i <= blockPos2.getX(); ++i) {
                for (int j = blockPos1.getY(); j <= blockPos2.getY(); ++j) {
                    for (int k = blockPos1.getZ(); k <= blockPos2.getZ(); ++k) {
                        BlockPos pos = new BlockPos(i, j, k);
                        BlockState state = this.level().getBlockState(pos);
                        /*
                        if (this.doesDrop(state, pos)) {
                            //return;
                        }*/
                    }
                }
            }
        }
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
            if (this.getDeltaMovement().y < this.getRocketSpeed() - 0.1) {
                this.setDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y + 0.1, this.getDeltaMovement().z);
            } else {
                this.setDeltaMovement(this.getDeltaMovement().x, this.getRocketSpeed(), this.getDeltaMovement().z);
            }
            this.setPos(this.xo+this.getDeltaMovement().x, this.yo+ this.getDeltaMovement().y, this.zo + this.getDeltaMovement().z);
        }
    }
    
  
    private static ResourceKey<Level> limboKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("maring", "limbo"));
    private static ResourceKey<Level> marKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("maring", "maringmar"));
 
    public void GoingTo() {
    	if (this.yo > 1200) {
    	Player player = this.getFirstPlayerPassenger();
        if(player==null) return;
        Level level = this.level();
    	if(!level.isClientSide && level.dimension()==Level.OVERWORLD) {
        	ServerLevel mar = player.level().getServer().getLevel(marKey);
			if (mar == null) {
				System.out.println("找不到Mar");
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

                player.getPersistentData().putBoolean(MODID + ":planet_selection_menu_open", true);

                /** SAVE ITEMS IN THE PLAYER */
                ListTag tag = new ListTag();

                tag.add(new ItemStack(this.getRocketItem().getItem()).save(new CompoundTag()));

                for (int i = 0; i <= this.getInventory().getSlots() - 1; i++) {
                    tag.add(this.getInventory().getStackInSlot(i).save(new CompoundTag()));
                }

                player.getPersistentData().put(MODID + ":rocket_item_list", tag);
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

    public void rocketExplosion() {
        if (this.entityData.get(START_TIMER) == 200) {
            if (this.getDeltaMovement().y < -10.0) {
                if (!this.level().isClientSide) {
                    this.level().explode(this, this.getX(), this.getBoundingBox().maxY, this.getZ(), 10, true, Level.ExplosionInteraction.TNT);
                    this.remove(RemovalReason.DISCARDED);
                }
            }
        }
    }

    public void burnEntities() {
        if (this.entityData.get(START_TIMER) == 200) {
            AABB aabb = AABB.ofSize(new Vec3(this.getX(), this.getY() - 2, this.getZ()), 2, 2, 2);
            List<LivingEntity> entities = this.getCommandSenderWorld().getEntitiesOfClass(LivingEntity.class, aabb);

            for (LivingEntity entity : entities) {
                entity.setSecondsOnFire(15);
            }
        }
    }
}
