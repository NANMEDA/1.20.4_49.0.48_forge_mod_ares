package event.forge;

import java.util.List;
import java.util.Random;

import com.item.ItemRegister;
import animal.entity.villager.ModVillager;
import block.norm.BlockElectricBasic;
import block.norm.BlockRegister;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = "maring")
public class VillagerEvent {
	
	@SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
		Random random = new Random();
        if(event.getType() == ModVillager.HANDMAKE_MASTER.get()) {
        	Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack1 = new ItemStack(ItemRegister.MATERIAL_ITEMS[1].get(), 1);
            ItemStack stack2 = new ItemStack(ItemRegister.MATERIAL_ITEMS[5].get(), 1);
            ItemStack stack3 = new ItemStack(ItemRegister.MATERIAL_ITEMS[3].get(), 1);
            ItemStack stack4 = new ItemStack(ItemRegister.MATERIAL_ITEMS[2].get(), 1);
            ItemStack stack5 = new ItemStack(ItemRegister.MATERIAL_ITEMS[4].get(), 1);//小心顺序哦
            ItemStack stack6 = new ItemStack(BlockRegister.ELECTRIC_BLOCK_ITEMS[BlockElectricBasic.getIdFromName("villager_burried_package")].get(), 1);
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 4 + random.nextInt(3)),
                new ItemStack(Items.GOLD_INGOT, 1),
                stack1,  // 输出物品
                12,  // 最大交易次数
                2,   // 交易经验值
                0.03F));  // 交易增量
            trades.get(2).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 6 + random.nextInt(3)), stack2, 8, 2, 0.05F));
            trades.get(2).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 20 + random.nextInt(6)),new ItemStack(Items.QUARTZ, 4), stack3, 6, 4, 0.06F)); 
            trades.get(3).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD_BLOCK, 2 + random.nextInt(3)), stack4, 8, 6, 0.06F)); 
            trades.get(4).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD_BLOCK, 16 + random.nextInt(5)),new ItemStack(Items.DIAMOND, 1), stack5, 8, 8, 0.10F)); 
            trades.get(5).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD_BLOCK, 1),new ItemStack(Items.WOODEN_SHOVEL, 1), stack6, 3, 10, 0.10F)); 
        }
    }
}
