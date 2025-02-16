package block.norm.farm;

import block.norm.BlockRegister;
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

public class Register {
	
    private static DeferredRegister<Block> BLOCKS = BlockRegister.BLOCKS;
    //private static DeferredRegister<Item> BLOCK_ITEMS = BlockRegister.BLOCK_ITEMS;
    
    public static final RegistryObject<Block> TOMATO_B = BLOCKS.register(TomatoBlock.global_name, () ->
            new TomatoBlock(BlockBehaviour.Properties.of()
            		.mapColor(MapColor.PLANT)
            		.noCollission()
            		.randomTicks()
            		.instabreak()
            		.sound(SoundType.CROP)
            		.pushReaction(PushReaction.DESTROY)
                   ));

    
    public static void init() {
    	gen(TomatoBlock.global_name,7);
    }
    
    private static void gen(String name,int stage) {
    	for(int i=0;i<=stage;i++) {
            BlockJSON.GenModelsJSONFarm(name+"_stage"+i);
    	}
        BlockJSON.GenBlockStateJSONFarm(name, stage);
        BlockJSON.GenLootTableJSONBasic(name,"mar_tomato");
        BlockJSON.GenToolJSON("hoe", name);
        BlockJSON.GenToolLevelJSON(0, name);
    }
}
