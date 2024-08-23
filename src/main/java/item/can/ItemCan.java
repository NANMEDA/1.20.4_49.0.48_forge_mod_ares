package item.can;

import java.util.List;

import javax.annotation.Nullable;

import effect.registry.EffectRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ItemCan extends Item {

	public int Nutrition;
	public Float Full;
	
	public int Vegetable;
	public int Meat;
	public int Fish;
	public int Corn;
	public int Fruit;
	
	public ItemCan() {
	    super(new Item.Properties());
	    this.Nutrition = 0;
	    this.Full = 0.0f;
	    this.Vegetable = 0;
	    this.Meat = 0;
	    this.Fish = 0;
	    this.Corn = 0;
	    this.Fruit = 0;
	}
	
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
    	int Vegetable = ItemCanNBT.getVegetable(stack);
    	int Meat= ItemCanNBT.getMeat(stack);
    	int Fish= ItemCanNBT.getFish(stack);
    	int Corn= ItemCanNBT.getCorn(stack);
    	int Fruit= ItemCanNBT.getFruit(stack);
        tooltip.add(Component.translatable("item.can.show.vegetable", Vegetable)
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_BLUE).withItalic(true)));
        tooltip.add(Component.translatable("item.can.show.meat", Meat)        		
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_BLUE).withItalic(true)));
        tooltip.add(Component.translatable("item.can.show.fish", Fish)
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_BLUE).withItalic(true)));
        tooltip.add(Component.translatable("item.can.show.corn", Corn)
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_BLUE).withItalic(true)));
        tooltip.add(Component.translatable("item.can.show.fruit", Fruit)
        		.setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_BLUE).withItalic(true)));
    }
	
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player) {
            player.getFoodData().eat(ItemCanNBT.getNutrition(stack), ItemCanNBT.getSaturation(stack));
        	int Vegetable = ItemCanNBT.getVegetable(stack);
        	int Meat= ItemCanNBT.getMeat(stack);
        	int Fish= ItemCanNBT.getFish(stack);
        	int Corn= ItemCanNBT.getCorn(stack);
        	int Fruit= ItemCanNBT.getFruit(stack);
        	if(Fish>=4) {
        		player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 600, 1));
        	}
        	if(Corn>=5) {
        		player.addEffect(new MobEffectInstance(EffectRegister.FULLING.get(), 800));
        	}
        	if(Meat>=4) {
        		player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 800, 1));
        	}
            if(Vegetable>=2&&Fruit>=2) {
            	player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 800, 1));
            }
        }

        if (!level.isClientSide) {
            stack.shrink(1);
        }

        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 16;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }
	
    @Override
    public Component getName(ItemStack stack) {
    	boolean first = false,second = false;
    	int Vegetable = ItemCanNBT.getVegetable(stack);
    	int Meat= ItemCanNBT.getMeat(stack);
    	int Fish= ItemCanNBT.getFish(stack);
    	int corn= ItemCanNBT.getCorn(stack);
    	int Fruit= ItemCanNBT.getFruit(stack);
    	MutableComponent name = Component.empty();
    	if(Fish>=4) {
    		return Component.translatable("cans.axolotl");
    	}
    	
    	if(corn>1) {
    		name.append(Component.translatable("cans.first.iscorn"));
    		first = true;
    	}else {
    		if(Meat+Fish>0) {
    		name.append(Component.translatable("cans.first.nocorn")
    				.setStyle(Style.EMPTY.withItalic(true)));
    		}
    	}
    	if(Fish>0) {
    		if(first) {
    			name.append(Component.translatable("cans.first.is"));
    		}
    		name.append(Component.translatable("cans.second.havefish"));
    		second = true;
    	}else {
    		if(Meat>0) {
        		if(first) {
        			name.append(Component.translatable("cans.first.is")
        					.setStyle(Style.EMPTY.withItalic(true)));
        		}
        		name.append(Component.translatable("cans.second.havemeat"));
        		second = true;
        	}
    	}
    	if(Vegetable>0) {
    		if(second||first) {
    			name.append(Component.translatable("cans.second.is")
    					.setStyle(Style.EMPTY.withItalic(true)));
    		}
    		if(Fruit>0) {
        		name.append(Component.translatable("cans.third.havefruitandvegetable"));
        	}else {
        		name.append(Component.translatable("cans.third.havevegetable"));
        	}
    	}else {
	    	if(Fruit>0) {
	    		if(second||first) {
	    			name.append(Component.translatable("cans.second.is"));
	    		}
	    		name.append(Component.translatable("cans.third.havefruit"));
	    	}
    	}
    	
        return name.append(Component.translatable("cans.last"));
     }


}