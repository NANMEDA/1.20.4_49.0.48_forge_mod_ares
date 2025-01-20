package block.norm.fastbuild;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import util.json.BlockJSON;

public class DormJunction extends Block {

	public static final String global_name = "dorm_junction";
	private BlockPos point;
    
	public DormJunction(Properties p_49795_) {
		super(p_49795_);
	}

	 static {
	        BlockJSON.GenModelsJSONBasic(global_name);
	        BlockJSON.GenBlockStateJSONBasic(global_name);
	        BlockJSON.GenItemJSONBasic(global_name);
	        BlockJSON.GenLootTableJSONBasic(global_name);
	 }

}
