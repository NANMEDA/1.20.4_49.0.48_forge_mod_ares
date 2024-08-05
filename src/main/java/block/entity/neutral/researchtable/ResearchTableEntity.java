package block.entity.neutral.researchtable;


import com.item.ItemRegister;
import com.item.blueprint.ItemBlueprintNBT;

import block.entity.BlockEntityRegister;
import block.entity.consumer.PowerConsumerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * 研究台
 * 研究可以由 FlashDisk 进行提取或者覆盖
 * 注意，每个研究分支都是用Tag来记录，要保证和 FlashDisk 同步
 * 比如 <高级穹顶 III> 相当于 TAG: dormHigh 的值为 2 ,从0开始
 * @author NANMEDA
 * */
public class ResearchTableEntity extends PowerConsumerEntity{

	private int techKind = 3;
	public int tech1 = 0;
	public int tech2 = 0;
	public int tech3 = 0;
	public int atTech = -1;
	public int atLevel = -1;
	static protected int itemstack_number=2;
	
	public ResearchTableEntity(BlockPos pos, BlockState pBlockState) {
		super(BlockEntityRegister.researchtable_BLOCKENTITY.get(), pos, pBlockState);
		this.energy_consume = 0;
	}
	
    protected final ItemStackHandler item = new ItemStackHandler(itemstack_number) {//必须要在这里创建，ItemStackHandler不可被修改
        @Override
        public void onLoad() {
            super.onLoad();
        }

        @Override
        protected void onContentsChanged(int slot) {
        	setChanged();
        	level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
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

	
	private final String TAG_1 = "tech1";
	private final String TAG_2 = "tech2";
	private final String TAG_3 = "tech3";
	private final String TAG_T = "atTech";
	private final String TAG_L = "atLevel";
	private final String TAG_ITEM = "Item";
	
	protected void savedata(CompoundTag tag) {
		tag.put(TAG_ITEM, item.serializeNBT());
		tag.putInt(TAG_1, tech1);
		tag.putInt(TAG_2, tech2);
		tag.putInt(TAG_3, tech3);
		tag.putInt(TAG_T, atTech);
		tag.putInt(TAG_L, atLevel);
	}
	
	protected void loaddata(CompoundTag tag) {
		if(tag.contains(TAG_ITEM)) {
			item.deserializeNBT(tag.getCompound(TAG_ITEM));
		}
		if(tag.contains(TAG_1)) {
			tech1 = tag.getInt(TAG_1);
		}
		if(tag.contains(TAG_2)) {
			tech2 = tag.getInt(TAG_2);
		}
		if(tag.contains(TAG_3)) {
			tech3 = tag.getInt(TAG_3);
		}
		if(tag.contains(TAG_T)) {
			atTech = tag.getInt(TAG_T);
		}
		if(tag.contains(TAG_L)) {
			atLevel = tag.getInt(TAG_L);
		}
	}
	
	public void getBlueprint(int line, int level) {
		ItemStack in = item.getStackInSlot(0);
		if(in.isEmpty()||!item.getStackInSlot(1).isEmpty()) return;
		ItemStack out = new ItemStack(ItemRegister.BLUE_PRINT.get(),1);
		String contentString = null;
		switch (line) {
			case 0: 
				contentString = dormTech(level);
				break;
			default:
				break;
		}
		ItemBlueprintNBT.setContent(out, contentString);
		ItemBlueprintNBT.setTech(out, line);
		ItemBlueprintNBT.setLevel(out, level);
		in.shrink(1);
		item.setStackInSlot(0, in);
		item.setStackInSlot(1, out);
		setChanged();
	}
	
	public String dormTech(int level) {
		//System.out.println("level is" + level);
		switch (level) {
		case 0: return "dorm.basic.sphere";
		case 1: return "dorm.basic.door";
		case 2: return "dorm.basic.flatsphere";
		case 3: return "dorm.basic.eclipse";
		case 4: return "dorm.basic.flateclipse";
		case 5: return "dorm.basic.cylinder";
		default:
			return null;
		}
	}

	@Override
	public void servertick() {
		//System.out.println("ser is: at tech" + atTech + " at level" + atLevel + "tech1" + tech1);
		if(atTech>=0&&atLevel>=0) {
			System.out.println("attech " + atTech + "atLevel" + atLevel);
			getBlueprint(atTech,atLevel);
		}
	}
	
	@Override
	public void clienttick() {
		//System.out.println("cli is at tech" + atTech + " at level" + atLevel+ "tech1" + tech1);
	}


	public int getLines() {
		return techKind;
	}


	public String[] getLineState(int k) {
		switch (k) {
		case 0:
			return new String[]{
				"dorm.basic.sphere",
				"dorm.basic.door",
				"dorm.basic.flatsphere",
				"dorm.basic.eclipse",
				"dorm.basic.flateclipse",
				"dorm.basic.cylinder"
			};
		case 1:
			return new String[]{
				"vehicle.wonder",
				"vehicle.flyer"
			};
		case 2:
			return new String[]{
				"machine.big1",
				"machine.big2"
			};
		default:
			return null;
		}
	}


	public String getTechName(int k) {
		switch (k) {
		case 0:
			return "Dorm";
		case 1:
			return "Rocket";
		case 2:
			return "Science";
		default:
			return null;
		}
	}


	public int getTechLevel(int k) {
		switch (k) {
		case 0:
			return tech1;
		case 1:
			return tech2;
		case 2:
			return tech3;
		default:
			return 0;
		}
	}


	public void setTech(int col, int i) {
		switch(col) {
			case 0:
				tech1 = i;
				return;
			case 1:
				tech2 = i;
				return;
			case 2:
				tech3 = i;
				return;
		}
	}

	

}