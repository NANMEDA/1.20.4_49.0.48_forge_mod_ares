package item.blueprint;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

/**
 * 这是NBT相关的
 * */
public class ItemBlueprintNBT {

    public static final String TAG_NAME = "Name";
    public static final String TAG_TECH = "Tech";
    public static final String TAG_LEVEL = "Level";


    public static CompoundTag getTagSafe(ItemStack stack) {
        return stack.getOrCreateTag();
    }
    
    public static String getContent(ItemStack stack) {
        CompoundTag compound = getTagSafe(stack);
        return compound.getString(TAG_NAME);
    }

    public static void setContent(ItemStack stack, String Name) {
    	if(Name == null) {
    		getTagSafe(stack).putString(TAG_NAME, "null");
    	}else {
    		getTagSafe(stack).putString(TAG_NAME, Name);
    	}
    }
    
    public static int getTech(ItemStack stack) {
        CompoundTag compound = getTagSafe(stack);
        return compound.getInt(TAG_TECH);
    }

    public static void setTech(ItemStack stack, int tech) {
        getTagSafe(stack).putInt(TAG_TECH, tech);
    }
    
    public static int getLevel(ItemStack stack) {
        CompoundTag compound = getTagSafe(stack);
        return compound.getInt(TAG_LEVEL);
    }

    public static void setLevel(ItemStack stack, int level) {
        getTagSafe(stack).putInt(TAG_LEVEL, level);
    }
}
