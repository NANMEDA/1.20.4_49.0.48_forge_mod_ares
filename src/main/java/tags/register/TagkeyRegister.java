package tags.register;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagkeyRegister {
	private static final String MODID = "maring";
	
	public static TagKey<Block> UNBREAKABLE_BLOCK_TAG;
	public static TagKey<Item> CAN_FOOD_TAG;
	
	public static void init() {
		UNBREAKABLE_BLOCK_TAG = BlockTags.create(new ResourceLocation(MODID, "unbreakable_block"));
		
		
		CAN_FOOD_TAG = ItemTags.create(new ResourceLocation(MODID, "can_food"));

	}
	
    /*
    if (block.defaultBlockState().is(TagKeyRegister.EXAMPLE_BLOCK_TAG)) {
        // Do something with the block
    }
*/
	
}