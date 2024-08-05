package com.item.blueprint;

import com.item.ItemJSON;
import com.item.can.ItemCanNBT;
import com.menu.show.ShowBlockMenuProvider;
import com.menu.show.itemstack.ShowItemStackMenu;
import com.menu.show.itemstack.ShowItemStackMenuProvider;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeServerPlayer;

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
			IForgeServerPlayer ifpe = (IForgeServerPlayer)player;
			//ifpe.openMenu(new ShowItemStackMenuProvider(new ItemStack(this), player.getOnPos()), player.getOnPos());
			ifpe.openMenu(new ShowBlockMenuProvider(player.getOnPos()), player.getOnPos());
		}
		return InteractionResult.SUCCESS;
    }
	
	
	static {
		ItemJSON.GenJSON(global_name);
	}

}