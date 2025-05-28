package keybinds;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

/**
 * 注册按键用的
 * */
public class KeyVariables {
	
    public static final String MARING_KEY_CATEGORY = "maring.key.category";

    public static final KeyMapping ROCKET_SETOFF_KEY = new KeyMapping("maring.key.sendrocket", KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_SPACE,MARING_KEY_CATEGORY);
}
