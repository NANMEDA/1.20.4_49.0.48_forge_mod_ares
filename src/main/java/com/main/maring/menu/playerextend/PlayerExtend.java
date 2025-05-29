package com.main.maring.menu.playerextend;
/*
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class PlayerExtend extends PlayerSavedData {
    private static final String DATA_NAME = "player_extra_inventory_data";
    private final NonNullList<ItemStack> extraInventorySlots = NonNullList.withSize(9, ItemStack.EMPTY);

    public PlayerExtend() {}

    public static PlayerExtend getForPlayer(ServerPlayer player) {
        return player.getSavedData().getOrCreate(PlayerExtend::new, DATA_NAME);
    }

    public NonNullList<ItemStack> getExtraInventorySlots() {
        return extraInventorySlots;
    }

    @Override
    public void load(CompoundNBT nbt) {
        ItemStackHelper.loadAllItems(nbt, extraInventorySlots);
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        ItemStackHelper.saveAllItems(nbt, extraInventorySlots);
        return nbt;
    }
}

// 在玩家登录事件中加载额外的背包栏数据
@Mod.EventBusSubscriber(modid = "mymod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerLoginEventHandler {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer) {
        	ServerPlayer player = (ServerPlayer) event.getEntity();
        	PlayerExtend extraInventoryData = PlayerExtend.getForPlayer(player);
            // 加载额外的背包栏数据
        }
    }
}

// 在玩家保存数据事件中保存额外的背包栏数据
@Mod.EventBusSubscriber(modid = "mymod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerSaveDataEventHandler {
    @SubscribeEvent
    public static void onPlayerSaveData(PlayerEvent.SaveToFile event) {
        if (event.getEntity() instanceof ServerPlayer) {
        	ServerPlayer player = (ServerPlayer) event.getEntity();
        	PlayerExtend extraInventoryData = PlayerExtend.getForPlayer(player);
            extraInventoryData.markDirty();
        }
    }
}

// 创建一个事件监听器，在打开背包时显示额外的背包栏
@Mod.EventBusSubscriber(modid = "maring", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OpenInventoryEventHandler {
    @SubscribeEvent
    public static void onOpenInventory(PlayerContainerEvent.Open event) {
        if (event.getContainer() instanceof PlayerExtendMenu) {
        	ServerPlayer player = ((PlayerExtendMenu) event.getContainer()).player;
            PlayerExtend extraInventoryData = PlayerExtend.getForPlayer(player);
            // 在这里添加额外的背包栏到玩家的背包容器中
        }
    }
}

// 创建一个事件监听器，在关闭背包时保存额外的背包栏数据
@Mod.EventBusSubscriber(modid = "mymod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CloseInventoryEventHandler {
    @SubscribeEvent
    public static void onCloseInventory(PlayerContainerEvent.Close event) {
        if (event.getContainer() instanceof PlayerExtendMenu) {
        	ServerPlayer player = ((PlayerExtendMenu) event.getContainer()).player;
            PlayerExtend extraInventoryData = PlayerExtend.getForPlayer(player);
            extraInventoryData.markDirty();
        }
    }
}
*/