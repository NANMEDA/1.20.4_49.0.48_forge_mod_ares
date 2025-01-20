package item.tool.electric;

import machine.energy.EnergyEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import util.json.ItemJSON;
import util.net.EnergyNet;
import util.net.EnergyNetProcess;


/**
 * 这个是用来Cut电力网络的
 * @author NANMEDA
 * */
public class WireCutor extends Item {
	private BlockPos startPos = null;
	private long startNet = 0;

	public WireCutor(Properties p_41383_) {
		super(p_41383_);
		// TODO 自动生成的构造函数存根
	}
	
	public static final String global_name = "wire_cutor";
 
	@Override
    public int getMaxDamage(ItemStack stack) {
        // 返回耐久度的最大值
        return 64;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        // 确保该物品可以损坏
        return true;
    }

	
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if(!level.isClientSide) {
        	if(level.getBlockEntity(pos) instanceof EnergyEntity blockentity) {
        		if(blockentity.isConnectable()) {
        			
	        		if(this.startPos==null) {
	        			this.startPos = pos;
	        			this.startNet = blockentity.getNet();
	        			context.getPlayer().sendSystemMessage(Component.translatable("first.point.ok"));
	        		}else {
	        			context.getPlayer().sendSystemMessage(Component.translatable("second.point.ok"));
	        			long endNet = blockentity.getNet();
	        			if(pos.equals(startPos)) {
	        				context.getPlayer().sendSystemMessage(Component.translatable("energynet.cantsame"));
                			startPos = null;
                			startNet = 0;
        					return InteractionResult.PASS;
	        			}
	        			if(startNet == endNet) {
	        				if(startNet != 0) {	//	已经有链接了, 也就是可以cut
	        					
	        					ItemStack stack = context.getItemInHand();
	        					stack.hurtAndBreak(1, context.getPlayer(), (e) -> e.broadcastBreakEvent(context.getHand()));
	        					
	        					EnergyNet energyNet = EnergyNetProcess.getEnergyNet(startNet);
	        					if(energyNet.canStillConnect(startPos, pos)) {
	        						energyNet.removeEdge(startPos, pos);
	        						context.getPlayer().sendSystemMessage(Component.translatable("energynet.remove.edge"));
	        					}else {
	        						context.getPlayer().sendSystemMessage(Component.translatable("energynet.split"));
	        						EnergyNet net2 = EnergyNetProcess.splitEnergyNet(pos,startPos, level, energyNet);
	        					}
	        					EnergyEntity startEntity = (EnergyEntity) level.getBlockEntity(startPos);
	        					EnergyEntity.removeMutualConnection(startEntity, blockentity);
	                			startPos = null;
	                			startNet = 0;
	        					return InteractionResult.SUCCESS;
	        				}else {				//就是两个都是0,都没有链接
	        					context.getPlayer().sendSystemMessage(Component.translatable("energynet.noline"));
	        				}
	        			}else {//两个不一样
	        				
	        			}
	        			this.startPos = null;
	        			this.startNet = 0;
	        		}
        		}
        	}
        }
        return InteractionResult.PASS;
    }
    
	static {
		ItemJSON.GenJSON(global_name);
	}

}
