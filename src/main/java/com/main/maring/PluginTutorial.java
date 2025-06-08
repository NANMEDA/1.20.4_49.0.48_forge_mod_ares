package com.main.maring;

import java.util.List;

import com.main.maring.menu.reseachtable.TechNode;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import com.main.maring.util.tech.TechManager;

/**
 * Use @MaringPlugin to be as the entrance
 * */
@MaringPlugin
public class PluginTutorial{

	/**
	 * register a tech-node like this
	 * then, the code might organize it automatically
	 * */
	public void TechNodeRegister() {
		TechNode node = new TechNode.Builder()
			    .name("tutorial")	
			    .genUI()
			    .reference(1)
			    .time(1200)
			    .resource(() -> List.of(new ItemStack(Items.PAPER, 8)))
			    .build();
		
		/**
		 * for example , add a Prerequisite
		 * You don't need to add a Point of "calculus", the code do it itself
		 * */
		node.addPrerequisite(TechManager.findTechNodeByName("calculus"));
		TechManager.register(node);	
	}
}