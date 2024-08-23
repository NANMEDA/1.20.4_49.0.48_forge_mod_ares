package item.tool;

import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import util.json.ItemJSON;

public class OminousShovel extends ShovelItem{
	public static String global_name = "ominous_shovel";
	
	public OminousShovel(Tier p_41336_, float p_41337_, float p_41338_, Properties p_41339_) {
		super(ToolTier.OMINOUS, 1.5F, -3.0F, p_41339_);
	}
	static {
		ItemJSON.GenJSON(global_name);
	}

}
