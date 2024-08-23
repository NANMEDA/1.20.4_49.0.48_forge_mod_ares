package machine.energy.producer.solar;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import util.json.BlockJSON;

public class SolarPillar extends Block {

	public static final String global_name = "solar_pillar";

	public SolarPillar(Properties p_49795_) {
		super(p_49795_);
	}
	
	@Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
		return Shapes.or(
				Block.box(7, 0, 6, 9, 16, 10),
				Block.box(9, 0, 7, 10, 16, 9),
				Block.box(6, 0, 7, 7, 16, 9)
			);
    }
	
	 static {
	        BlockJSON.GenModelsJSONBasic(global_name);
	        BlockJSON.GenBlockStateJSONBasic(global_name);
	        BlockJSON.GenItemJSONBasic(global_name);
	        BlockJSON.GenLootTableJSONBasic(global_name);
	 }
}
