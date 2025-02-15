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
 * 这个是用来链接电力网络的
 * <br>TODO:<br> 要在两个用电器之间渲染导线未写
 * <br>TODO:<br> 创建第一个电网卡顿
 * @author NANMEDA
 * */
public class WireCreator extends Item {
	private BlockPos startPos = null;
	private long startNet = 0;

	public WireCreator(Properties p_41383_) {
		super(p_41383_);
		// TODO 自动生成的构造函数存根
	}
	
	public static final String global_name = "wire_creator";
 
	@Override
    public int getMaxDamage(ItemStack stack) {
        return 1024;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }
	
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if(!level.isClientSide)
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
	        			
	        			int dx = Math.abs(startPos.getX()-pos.getX());
	        			int dy = Math.abs(startPos.getY()-pos.getY());
	        			int dz = Math.abs(startPos.getZ()-pos.getZ());
	        			int dis = dx + dy + dz;
	        			if(dis>128) {
	        				context.getPlayer().sendSystemMessage(Component.translatable("energynet.linetoolong"));
	        				return InteractionResult.PASS;
	        			}
	        			ItemStack stack = context.getItemInHand();
	        			stack.hurtAndBreak(dis, context.getPlayer(), (e) -> e.broadcastBreakEvent(context.getHand()));
    					
	        			if(startNet == endNet) {
	        				if(startNet != 0) {	//	已经有链接了
	        					EnergyNet net = EnergyNetProcess.getEnergyNet(startNet);
	        					net.addEdge(startPos, pos);
	        					
	        					EnergyEntity startEntity = (EnergyEntity) level.getBlockEntity(startPos);
	                			EnergyEntity.addMutualConnection(startEntity, blockentity);
	        					startPos = null;
	                			startNet = 0;
	        					return InteractionResult.PASS;
	        				}else {				//就是两个都是0,都没有链接
	        					EnergyNet net = EnergyNetProcess.createEnergyNet(context.getLevel().dimension().location());
	        					EnergyEntity startEntity = (EnergyEntity) level.getBlockEntity(startPos);
	        					net.addBlockPos(pos, blockentity);
	        					net.addBlockPos(startPos, startEntity);
	        					net.addEdge(startPos, pos);
	        					EnergyEntity.addMutualConnection(startEntity, blockentity);
	        					blockentity.setNet(net.getId());
	                			startEntity.setNet(net.getId());
	        				}
	        			}else {
	        				if(this.startNet == 0) {
	        					EnergyNet net = EnergyNetProcess.getEnergyNet(endNet);
	        					EnergyEntity startEntity = (EnergyEntity) level.getBlockEntity(startPos);
	        					EnergyEntity.addMutualConnection(startEntity, blockentity);
	        					net.addBlockPos(startPos, startEntity);
	        					net.addEdge(startPos, pos);
	                			startEntity.setNet(endNet);
	        				}else if(endNet == 0) {
	        					EnergyNet net = EnergyNetProcess.getEnergyNet(startNet);
	        					net.addBlockPos(pos, blockentity);
	        					net.addEdge(startPos, pos);
	        					EnergyEntity startEntity = (EnergyEntity) level.getBlockEntity(startPos);
	        					EnergyEntity.addMutualConnection(startEntity, blockentity);
	        					blockentity.setNet(startNet);
	        				}else {	//需要整合NET
	        					EnergyNetProcess.mergeEnergyNets(startNet, endNet);
	        					EnergyNet net = EnergyNetProcess.getEnergyNet(startNet);
	        					net.addEdge(startPos, pos);
	        					blockentity.setNet(startNet);
	        					EnergyEntity startEntity = (EnergyEntity) level.getBlockEntity(startPos);
	                			startEntity.setNet(startNet);
	                			EnergyEntity.addMutualConnection(startEntity, blockentity);
	        				}
	        			}
	        			this.startPos = null;
	        			this.startNet = 0;
	        		}
        		}
        	}

        return InteractionResult.PASS;
    }
    
	static {
		ItemJSON.GenJSON(global_name);
	}

}
