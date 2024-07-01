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
	
    public static final String KEY_CATEGORY_EXAMPLE_MOD = "key.category.example_mod";
    // 我们添加一个按键的描述，是可以被语言化文件处理的。
    public static final String KEY_DRINK_WATER = "key.example_mod.drink_water";

    // 使用KeyMapping创建一个我们自己的热键
    // 参数含义已经在上面介绍了。
    public static final KeyMapping DRINKING_KEY = new KeyMapping(KEY_CATEGORY_EXAMPLE_MOD, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_SPACE,KEY_DRINK_WATER);
}
