package com.main.maring.event.forge;

import java.util.List;
import java.util.Random;

import com.main.maring.animal.entity.villager.ModVillager;
import com.main.maring.block.norm.BlockRegister;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import com.main.maring.item.ItemRegister;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 这里定义了所有村民交易具体的内容
 * */
@Mod.EventBusSubscriber
public class VillagerEvent {
	
	/***
	 * 这个为玩家提供基本的五大零件
	 * 加上一个礼包
	 * @author NANMEDA
	 * ***/
	@SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
		Random random = new Random();
        if(event.getType() == ModVillager.HANDMAKE_MASTER.get()) {
        	Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack1 = new ItemStack(ItemRegister.BASIC_METAL_PARTS.get(), 1);
            ItemStack stack2 = new ItemStack(ItemRegister.BIOPLASTIC_PARTS.get(), 1);
            ItemStack stack3 = new ItemStack(ItemRegister.CRYSTAL_PARTS.get(), 1);
            ItemStack stack4 = new ItemStack(ItemRegister.ADVANCED_METAL_PARTS.get(), 1);
            ItemStack stack5 = new ItemStack(ItemRegister.SEMICONDUCTOR_PARTS.get(), 1);//小心顺序哦
            ItemStack stack6 = new ItemStack(BlockRegister.VILLAGER_BURRIED_PACKAGE_BLOCK_ITEMS.get(), 1);
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
