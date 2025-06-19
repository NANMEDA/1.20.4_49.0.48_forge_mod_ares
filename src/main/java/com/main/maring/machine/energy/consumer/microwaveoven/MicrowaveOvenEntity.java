package com.main.maring.machine.energy.consumer.microwaveoven;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.main.maring.item.ItemRegister;
import com.main.maring.machine.energy.consumer.ConsumerEntity;
import com.main.maring.machine.energy.consumer.IConsumer;
import com.main.maring.machine.registry.MBlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import com.main.maring.tags.register.TagkeyRegistry;
import com.main.maring.util.net.EnergyNetProcess;

/**
 * 加热食物或海绵
 * 加热罐头或者鸡蛋导致爆炸
 * 时间不定
 * @author NANMEDA
 * */
public class MicrowaveOvenEntity extends ConsumerEntity implements IConsumer{

	private int energy_consume = 0;
	private int energy_supply = 100;
	private int pg_max = 100;
	private int render = 0;
	private short process_progress = -1;//这是倒着来着，看下面就知道了
	public boolean is_button = false;
	private boolean should_playsound = false;
	
	static protected int itemstack_number = 2;
	
	public int getRenderDis() {
		return render;
	}
	
	@Override
    public boolean lineVisible() {
    	return false;
    }
	
	public MicrowaveOvenEntity(BlockPos pos, BlockState pBlockState) {
		super(MBlockEntityRegister.microwaveoven_BLOCKENTITY.get(), pos, pBlockState);
		this.FULL_ENERGY = 8;
	}
	
    protected final ItemStackHandler item = new ItemStackHandler(itemstack_number) {//必须要在这里创建，ItemStackHandler不可被修改
        @Override
        public void onLoad() {
            super.onLoad();
            //System.out.println("entity is load");
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(slot==1) {
            	//不可以检测大于一不更新，因为一加热会加热一堆，而不是一个一个
            	level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        }
    };

    protected final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> item);

    public ItemStackHandler getItems() {
        return item;
    }


    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        savedata(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        loaddata(tag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        savedata(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        if (tag != null) {
            loaddata(tag);
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet) {
        CompoundTag tag = packet.getTag();
        if (tag != null) {
            loaddata(tag);
        }
    }
    
	public void drop() {
		for (int slot = 0; slot < item.getSlots(); slot++) {
		    ItemStack stackInSlot = item.getStackInSlot(slot);
		    if (!stackInSlot.isEmpty()) {
		        Containers.dropContents(this.level, this.worldPosition, NonNullList.of(ItemStack.EMPTY, stackInSlot));
		    }
		}
	}
	
	
	@Override
	public <T>LazyOptional<T> getCapability(Capability<T> cap,Direction side){
		if(cap == ForgeCapabilities.ITEM_HANDLER) {
			return itemHandler.cast();
		}else {
			return super.getCapability(cap, side);
		}
	}

	private static final String TAG_NAME = "Item";
	private static final String TAG_PROGRESS = "progress";
	private static final String tAG_IS_BUTTON = "button";
	private static final String TAG_RENDER = "com/main/maring/render";	//不放在tag里面的不会被同步到client ！！！!!!即使设置同步
	
	protected void savedata(CompoundTag tag) {
		super.savedata(tag);
		tag.put(TAG_NAME, item.serializeNBT());
		tag.putShort(TAG_PROGRESS, process_progress);
		tag.putBoolean(tAG_IS_BUTTON, is_button);
		tag.putInt(TAG_RENDER, render);
	 
	}
	
	protected void loaddata(CompoundTag tag) {
		super.loaddata(tag);
		if(tag.contains(TAG_NAME)) {
			item.deserializeNBT(tag.getCompound(TAG_NAME));
		}
		if(tag.contains(TAG_PROGRESS)) {
			this.process_progress = tag.getShort(TAG_PROGRESS);
		}
		if(tag.contains(tAG_IS_BUTTON)) {
			this.is_button = tag.getBoolean(tAG_IS_BUTTON);
		}
		if(tag.contains(TAG_RENDER)) {
			this.render = tag.getShort(TAG_RENDER);
		}
	}

	RecipeType<SmokingRecipe> RECIPE_TYPE = RecipeType.SMOKING;

	@Override
	public boolean servertick(boolean u) {
		this.energy_consume = 0;
		this.energy_supply = getEnergySupplyLevel();

		ItemStack input = item.getStackInSlot(0);
		ItemStack output = item.getStackInSlot(1);

		// 输入为空或输出不为空时无法工作
		if (input.isEmpty() || !output.isEmpty()) {
			this.is_button = false;
			this.process_progress = -1;
			if (render != 0) {
				this.render = 0;
				level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
			}
			return false;
		}

		// 正在加工
		if (this.process_progress > 0) {
			int speed = (energy_supply > 75) ? 3 : ((energy_supply > 50) ? 2 : ((energy_supply > 25) ? 1 : 0));
			this.process_progress -= speed;

			if (this.energy_supply == 0) {
				this.is_button = false;
				this.process_progress = -1;
				if (render != 0) {
					this.render = 0;
					level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
				}
				return false;
			}

			if (this.process_progress < 0) this.process_progress = 0;
			this.energy_consume = this.FULL_ENERGY;

			int progress = 10 - 10 * process_progress / pg_max;
			int newRender = progress * 22 / 10;
			if (newRender != this.render) {
				this.render = newRender;
			}

			setChanged();
			level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
			return false;
		}

		// 非按钮状态，不能开始加工
		if (!is_button) return false;

		// 特殊物品：湿海绵 → 干海绵
		if (input.getItem() == Items.WET_SPONGE) {
			if (this.process_progress == 0) {
				item.setStackInSlot(0, ItemStack.EMPTY);
				item.setStackInSlot(1, new ItemStack(Items.SPONGE, input.getCount()));
				this.process_progress = -1;
				this.is_button = false;
				this.should_playsound = true;
				this.render = 0;
				level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
			} else if (this.process_progress == -1) {
				this.process_progress = 5 * 60;
				this.pg_max = this.process_progress;
			}
			setChanged();
			return false;
		}else if (input.is(ItemTags.create(new ResourceLocation("forge", "eggs"))) ||
				input.getItem() == ItemRegister.CAN.get()) {
			return true;
		}

		// 配方检查：烟熏炉
		Optional<SmokingRecipe> recipeOptional = level.getRecipeManager()
				.getRecipeFor(RecipeType.SMOKING, new SimpleContainer(input), level);

		if (recipeOptional.isPresent()) {
			SmokingRecipe recipe = recipeOptional.get();
			ItemStack result = recipe.getResultItem(level.registryAccess());

			if (this.process_progress == 0) {
				item.setStackInSlot(0, ItemStack.EMPTY);
				item.setStackInSlot(1, new ItemStack(result.getItem(), input.getCount()));
				this.process_progress = -1;
				this.is_button = false;
				this.should_playsound = true;
				this.render = 0;
				level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
			} else if (this.process_progress == -1) {
				this.process_progress = (short) ((input.getCount() > 32) ? 30*60 : ((input.getCount() > 16) ? 20*60 : ((input.getCount() > 6) ? 10*60 : 5*60)));
				this.pg_max = this.process_progress;
			}
			setChanged();
			return false;
		}

		// 无法处理的物品
		this.is_button = false;
		this.process_progress = -1;
		if (render != 0) {
			this.render = 0;
			level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
		}

		return false;
	}

	
	@Override
	public void clienttick() {
		if(this.should_playsound) {
			this.should_playsound = false;
			SoundEvent soundEvent = SoundEvents.VILLAGER_YES;
	        if (soundEvent != null) {
	        	level.playLocalSound(worldPosition,soundEvent,null, 1.0f, 1.0f,true);
	        }
		}
		return;
	}
	
	@Override
	public int getEnergyConsume() {
		return this.energy_consume;
	}
	
	@Override
	public int getEnergySupplyLevel() {
		if(this.haveNet()) {
			return EnergyNetProcess.getEnergyNet(this.getNet()).getSupplyLevel();
		}else {
			return 0;
		}
	}

	@Override
	protected void servertick() {
	}

}