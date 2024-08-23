package item;

import net.minecraft.world.item.Item;
import util.json.ItemJSON;

public class ItemBuildingStructure extends Item {

	public static final String global_name = "building_structure";

	public ItemBuildingStructure(Properties p_41383_) {
		super(p_41383_);
	}
	
	static {
		ItemJSON.GenJSON(global_name);
	}

}
