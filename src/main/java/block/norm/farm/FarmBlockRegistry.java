package block.norm.farm;

import block.norm.BlockRegister;
import item.food.farm.FrostfireFruit;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import util.json.BlockJSON;

public class FarmBlockRegistry {
	
    private static DeferredRegister<Block> BLOCKS = BlockRegister.BLOCKS;
    
    public static final RegistryObject<Block> FROSTFIRE_FRUIT_B = BLOCKS.register(FrostfireFruitBlock.global_name, () ->
            new FrostfireFruitBlock(BlockBehaviour.Properties.of()
            		.mapColor(MapColor.PLANT)
            		.noCollission()
            		.randomTicks()
            		.instabreak()
            		.sound(SoundType.CROP)
            		.pushReaction(PushReaction.DESTROY)
                   ));

    
    public static void init() {
    	gen(FrostfireFruitBlock.global_name,FrostfireFruit.global_name,7);
    }
    
    private static void gen(String name,String crop,int stage) {
    	for(int i=0;i<=stage;i++) {
            BlockJSON.GenModelsJSONFarm(name+"_stage"+i);
    	}
        BlockJSON.GenBlockStateJSONFarm(name, stage);
        BlockJSON.GenLootTableJSONBasic(name,crop);
        BlockJSON.GenToolJSON("hoe", name);
        BlockJSON.GenToolLevelJSON(0, name);
    }
}
