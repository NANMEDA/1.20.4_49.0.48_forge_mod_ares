package item.blueprint;

import menu.show.ShowBlockMenuProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import util.json.ItemJSON;

public class ItemBlueprint extends Item {

	public static final String global_name = "blue_print";
	public String content;
	
	public ItemBlueprint(Properties p_41383_) {
		super(p_41383_);
		this.content = null;
	}
	
	@Override
    public Component getName(ItemStack stack) {
    	String content = ItemBlueprintNBT.getContent(stack);
    	MutableComponent name = Component.empty();
    	
    	if(content!=null && content!= "null") {
    		name.append(Component.translatable(content));
    	}
        return name.append(Component.translatable("blueprint.last"));
     }
	
	@Override
    public InteractionResult useOn(UseOnContext p_41427_) {
		Player player = p_41427_.getPlayer();
		if(!player.level().isClientSide()) {
			ServerPlayer ifpe = (ServerPlayer)player;
			//ifpe.openMenu(new ShowItemStackMenuProvider(new ItemStack(this), player.getOnPos()), player.getOnPos());
			ifpe.openMenu(new ShowBlockMenuProvider(player.getOnPos()));
		}
		return InteractionResult.SUCCESS;
    }
	
	
	static {
		ItemJSON.GenJSON(global_name);
	}

}