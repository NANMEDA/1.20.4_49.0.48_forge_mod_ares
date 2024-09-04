package machine.energy.producer.solar;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import util.json.BlockJSON;

public class SolarPanel extends Block{

	public SolarPanel(Properties p_49795_) {
		super(p_49795_
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_GRAY)
	            );
	}

	public static final String global_name = "solar_panel";
	
	@Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
		return Shapes.or(
				Block.box(0, 0, 0, 16, 2, 16),
				Block.box(9, 2, 9, 15, 3, 15),
				Block.box(1, 2, 9, 7, 3, 15),
				Block.box(1, 2, 1, 7, 3, 7),
				Block.box(9, 2, 1, 15, 3, 7)
			);
    }
	
	 static {
	        BlockJSON.GenModelsJSONBasic(global_name);
	        BlockJSON.GenBlockStateJSONBasic(global_name);
	        BlockJSON.GenItemJSONBasic(global_name);
	        BlockJSON.GenLootTableJSONBasic(global_name);
	 }
}
