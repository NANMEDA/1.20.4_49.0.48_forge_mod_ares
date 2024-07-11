package event.forge;

import java.util.List;
import java.util.Random;

import com.item.ItemRegister;
import com.item.itemMaterial;

import animal.entity.villager.ModVillager;
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
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 4 + random.nextInt(3)),
                stack1,  // 输出物品
                12,  // 最大交易次数
                1,   // 交易经验值
                0.02F));  // 交易增量
            trades.get(2).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 6 + random.nextInt(3)), stack2, 8, 1, 0.02F));
            trades.get(2).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 20 + random.nextInt(6)), stack3, 8, 2, 0.02F)); 
            trades.get(3).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD_BLOCK, 2 + random.nextInt(3)), stack4, 8, 4, 0.02F)); 
            trades.get(4).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD_BLOCK, 16 + random.nextInt(5)),new ItemStack(Items.DIAMOND, 1), stack5, 8, 8, 0.08F)); 
        }
    }
}
