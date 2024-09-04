package block.entity.neutral.blueprintbuilder;

import net.minecraft.world.item.ItemStack;

/**
 * 存放着修建蓝图所需材料
 * */
public class BlueprintBuilderHelper {

	/***
	 *  "basic_metal_parts", // 金属铸机	4铁+1金|合成表8铁绕1金							//深蓝色系
     *  "advanced_metal_parts", // 精密金属铸机 2金属零件+2青金石	+钻石					//浅蓝色系
     *  "bioplastic_parts", // 塑料合成仪 nutrition土豆小麦面包/煤炭/log					//土黄色系
     *  "semiconductor_parts", // 电子组装仪	2塑料+钻+红石*8+2金+2铜						//紫灰色系
     *  "crystal_parts", //晶体生长仪 石英*16 慢+钻石,绿宝石,紫水金碎片可加速(不用电用水)	//橙红色系
	 * ***/
	public static int[] getNeedMaterial(int tech, int level) {
		switch (tech) {
			case 0: {
				switch (level) {
					case 0: return new int[]{10,10,0,10,0,0};
					case 1: return new int[]{4,15,5,5,0,0};
					case 2: return new int[]{8,10,0,10,0,0};
					case 3: return new int[]{12,10,0,10,0,0};
					case 4: return new int[]{10,10,0,10,0,0};
					case 5: return new int[]{10,10,0,10,0,0};
				}
			}	
			case 1:{
			}
		}
		return new int[]{0,0,0,0,0,0};
	}

	public static ItemStack getOutput(int tech, int level) {
		switch (tech) {
		case 0: {
			switch (level) {
				case 0: return new ItemStack(block.norm.fastbuild.FastBuildRegister.basiceclipsedorm_BLOCK_ITEM.get());
				case 1: return new ItemStack(block.norm.fastbuild.FastBuildRegister.spheredoor_BLOCK_ITEM.get());
				case 2: return new ItemStack(block.norm.fastbuild.FastBuildRegister.basicflatspheredorm_BLOCK_ITEM.get());
				case 3: return new ItemStack(block.norm.fastbuild.FastBuildRegister.basiceclipsedorm_BLOCK_ITEM.get());
				case 4: return new ItemStack(block.norm.fastbuild.FastBuildRegister.basicflatspheredorm_BLOCK_ITEM.get());
				case 5: return new ItemStack(block.norm.fastbuild.FastBuildRegister.basiccylinderdorm_BLOCK_ITEM.get());
			}
		}	
		case 1:{
		}
	}
	return ItemStack.EMPTY;
	}
}
