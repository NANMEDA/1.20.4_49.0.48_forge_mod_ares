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

    public ItemCan() {
        super(new Item.Properties());
    }

    public ItemCan(int nutrition, Float full, int vegetable, int meat, int fish, int corn, int fruit) {
        super(new Item.Properties());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        int vegetable = ItemCanNBT.getVegetable(stack);
        int meat = ItemCanNBT.getMeat(stack);
        int fish = ItemCanNBT.getFish(stack);
        int corn = ItemCanNBT.getCorn(stack);
        int fruit = ItemCanNBT.getFruit(stack);

        tooltip.add(Component.translatable("item.can.show.vegetable", vegetable)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_BLUE).withItalic(true)));
        tooltip.add(Component.translatable("item.can.show.meat", meat)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_BLUE).withItalic(true)));
        tooltip.add(Component.translatable("item.can.show.fish", fish)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_BLUE).withItalic(true)));
        tooltip.add(Component.translatable("item.can.show.corn", corn)
                .setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_BLUE).withItalic(true)));
        tooltip.add(Component.translatable("item.can.show.fruit", fruit)
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

            int vegetable = ItemCanNBT.getVegetable(stack);
            int meat = ItemCanNBT.getMeat(stack);
            int fish = ItemCanNBT.getFish(stack);
            int corn = ItemCanNBT.getCorn(stack);
            int fruit = ItemCanNBT.getFruit(stack);

            if (fish >= 4) {
                player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 600, 1));
            }
            if (corn >= 5) {
                player.addEffect(new MobEffectInstance(EffectRegister.FULLING.get(), 800));
            }
            if (meat >= 4) {
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 800, 1));
            }
            if (vegetable >= 2 && fruit >= 2) {
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
        return ItemCanNBT.getName(stack);
    }

    public static Component computeName(ItemStack stack) {
        boolean first = false, second = false;
        int vegetable = ItemCanNBT.getVegetable(stack);
        int meat = ItemCanNBT.getMeat(stack);
        int fish = ItemCanNBT.getFish(stack);
        int corn = ItemCanNBT.getCorn(stack);
        int fruit = ItemCanNBT.getFruit(stack);

        MutableComponent name = Component.empty();
        if (fish >= 4) {
            return Component.translatable("cans.axolotl");
        }

        if (corn > 1) {
            name.append(Component.translatable("cans.first.iscorn"));
            first = true;
        } else {
            if (meat + fish > 0) {
                name.append(Component.translatable("cans.first.nocorn")
                        .setStyle(Style.EMPTY.withItalic(true)));
            }
        }

        if (fish > 0) {
            if (first) {
                name.append(Component.translatable("cans.first.is"));
            }
            name.append(Component.translatable("cans.second.havefish"));
            second = true;
        } else {
            if (meat > 0) {
                if (first) {
                    name.append(Component.translatable("cans.first.is")
                            .setStyle(Style.EMPTY.withItalic(true)));
                }
                name.append(Component.translatable("cans.second.havemeat"));
                second = true;
            }
        }

        if (vegetable > 0) {
            if (second || first) {
                name.append(Component.translatable("cans.second.is")
                        .setStyle(Style.EMPTY.withItalic(true)));
            }
            if (fruit > 0) {
                name.append(Component.translatable("cans.third.havefruitandvegetable"));
            } else {
                name.append(Component.translatable("cans.third.havevegetable"));
            }
        } else {
            if (fruit > 0) {
                if (second || first) {
                    name.append(Component.translatable("cans.second.is"));
                }
                name.append(Component.translatable("cans.third.havefruit"));
            }
        }

        return name.append(Component.translatable("cans.last"));
    }
}
