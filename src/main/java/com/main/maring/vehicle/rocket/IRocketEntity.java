package com.main.maring.vehicle.rocket;

import com.google.common.collect.Sets;

import com.main.maring.Maring;
import com.main.maring.block.norm.BlockBasic;
import com.main.maring.block.norm.BlockRegister;
import com.main.maring.event.forge.TeleportAndCreateLanderEvent;
import com.main.maring.item.ItemRegister;
import com.main.maring.menu.rocket.RocketMenuProvider;
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
import net.minecraft.server.level.ServerPlayer;
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
import com.main.maring.util.Methods;
import com.main.maring.util.gauge.GaugeValueHelper;
import com.main.maring.util.gauge.IGaugeValue;
import com.main.maring.util.gauge.IGaugeValuesProvider;
import com.main.maring.vehicle.ModVehicle;
import com.main.maring.vehicle.VehicleRegister;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Random;

/**
 * BeyondEarth
 * https://github.com/MrScautHD/Beyond-Earth
 * */
public abstract class IRocketEntity extends ModVehicle implements IGaugeValuesProvider{

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

    public int getFuel(){
        return this.getEntityData().get(FUEL);
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




    public IItemHandlerModifiable getItemHandler() {
        return (IItemHandlerModifiable) this.getCapability(ForgeCapabilities.ITEM_HANDLER, null).resolve().get();
    }



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




	public abstract void spawnParticle();


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
