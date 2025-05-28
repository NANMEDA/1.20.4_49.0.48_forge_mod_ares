package keybinds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 实现按键和函数的对应
 * */
@Mod.EventBusSubscriber(modid = "maring")
public class KeyHandler {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        // 如果我们的热键被按下了，那么就给玩家发送一个消息press the key
        // 注意这里是客户端发送的，所以并不是所有玩家都能看到的。
        if(KeyVariables.ROCKET_SETOFF_KEY.consumeClick()){
        	LocalPlayer player = Minecraft.getInstance().player;
            KeyMethods.startRocket(player);
        }
    }

}

