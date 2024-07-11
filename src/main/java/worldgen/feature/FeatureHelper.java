package worldgen.feature;

import block.norm.BlockBasic;
import block.norm.BlockElectricBasic;
import block.norm.BlockRegister;
import net.minecraft.world.level.block.Block;

public class FeatureHelper {
	
    public static final Block MOIST_MUCUS = BlockRegister.COMMON_BLOCKS[BlockBasic.getIdFromName("moist_mucus")].get();
    public static final Block DRY_MUCUS = BlockRegister.COMMON_BLOCKS[BlockBasic.getIdFromName("dry_mucus")].get();
    public static final Block MAR_DEEP_STONE = BlockRegister.COMMON_BLOCKS[BlockBasic.getIdFromName("mar_deep_stone")].get();
    
    public static final Block METAL = BlockRegister.ELECTRIC_BLOCKS[BlockElectricBasic.getIdFromName("broken_metal_block")].get();
    public static final Block STRUCTURE = BlockRegister.ELECTRIC_BLOCKS[BlockElectricBasic.getIdFromName("broken_structure_block")].get();
    public static final Block ELEC = BlockRegister.ELECTRIC_BLOCKS[BlockElectricBasic.getIdFromName("broken_electronic_block")].get();
    public static final Block ELEC_A = BlockRegister.ELECTRIC_BLOCKS[BlockElectricBasic.getIdFromName("broken_advanced_electronic_block")].get();
    public static final Block CHEMICAL = BlockRegister.ELECTRIC_BLOCKS[BlockElectricBasic.getIdFromName("broken_chemical_block")].get();
}
