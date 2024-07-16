package keybinds;

import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.settings.KeyConflictContext;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

public class KeyVariables {
	
    public static final String KEY_CATEGORY_EXAMPLE_MOD = "maring.key.sendrocket";

    // 使用KeyMapping创建一个我们自己的热键
    // 参数含义已经在上面介绍了。
    public static final KeyMapping ROCKET_SETOFF_KEY = new KeyMapping(KEY_CATEGORY_EXAMPLE_MOD, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_SPACE,"maring.key.category");
}
