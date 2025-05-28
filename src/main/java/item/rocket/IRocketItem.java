package item.rocket;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.MinecraftForge;
import util.gauge.GaugeTextHelper;
import util.gauge.GaugeValueHelper;
import vehicle.rocket.IRocketEntity;
import vehicle.rocket.RocketEntity;

import java.util.List;
import java.util.function.Consumer;

import event.forge.PlaceRocketEvent;

public abstract class IRocketItem extends VehicleItem {

    private static final String MODID = "maing";
    
	public static final String FUEL_TAG = MODID + ":fuel";
    public static final String BUCKET_TAG = MODID + ":buckets";

	private static final int BUCKET_SIZE = 1000;

    public IRocketItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos().above();
        BlockState state = level.getBlockState(pos);
        InteractionHand hand = context.getHand();
        ItemStack itemStack = context.getItemInHand();

        if (level.isClientSide()) {
            return InteractionResult.PASS;
        }

        /** POS */
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if (state.isAir()) {

            BlockPlaceContext blockplacecontext = new BlockPlaceContext(context);
            BlockPos blockpos = blockplacecontext.getClickedPos();
            Vec3 vec3 = Vec3.upFromBottomCenterOf(blockpos, this.getRocketPlaceHigh());
            AABB aabb = this.getEntityType().getDimensions().makeBoundingBox(vec3.x(), vec3.y(), vec3.z());

            if (level.noCollision(aabb)||true) {

                /** CHECK IF NO ENTITY ON THE LAUNCH PAD */
            	/*
                AABB scanAbove = new AABB(x, y, z, x + 1, y + 1, z + 1);
                List<Entity> entities = player.getCommandSenderWorld().getEntitiesOfClass(Entity.class, scanAbove);
*/
                IRocketEntity rocket = this.getRocket(context.getLevel());

                /** SET PRE POS */
                rocket.setPos(pos.getX() + 0.5D,  pos.getY() + 1, pos.getZ() + 0.5D);

                double d0 = IRocketItem.getYOffset(level, pos, true, rocket.getBoundingBox());
                float f = (float) Mth.floor((Mth.wrapDegrees(context.getRotation() - 180.0F) + 45.0F) / 90.0F) * 90.0F;

                /** SET FINAL POS */
                rocket.moveTo(pos.getX() + 0.5D, pos.getY() + d0, pos.getZ() + 0.5D, f, 0.0F);

                rocket.yRotO = rocket.getYRot();

                level.addFreshEntity(rocket);

                /** SET TAGS */
                rocket.getEntityData().set(RocketEntity.FUEL, itemStack.getOrCreateTag().getInt(FUEL_TAG));

                /** CALL PLACE ROCKET EVENT */
                MinecraftForge.EVENT_BUS.post(new PlaceRocketEvent(rocket, context));

                /** ITEM REMOVE */
                if (!player.getAbilities().instabuild) {
                    player.setItemInHand(hand, ItemStack.EMPTY);
                }

                /** PLACE SOUND */
                this.rocketPlaceSound(pos, level);

                return InteractionResult.SUCCESS;
            }
        }

        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level level, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, level, list, flag);

        int fuel = itemstack.getOrCreateTag().getInt(FUEL_TAG);
        int capacity = this.getFuelBuckets() * BUCKET_SIZE;
        list.add(GaugeTextHelper.buildFuelStorageTooltip(GaugeValueHelper.getFuel(fuel, capacity), ChatFormatting.GRAY));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return IRocketItem.this.getRenderer();
            }
        });
    }

    public float getRocketPlaceHigh() {
        return -0.6F;
    }

    @OnlyIn(Dist.CLIENT)
    public abstract BlockEntityWithoutLevelRenderer getRenderer();

    public abstract EntityType<? extends IRocketEntity> getEntityType();

    public abstract IRocketEntity getRocket(Level level);

    public abstract int getFuelBuckets();

    public void rocketPlaceSound(BlockPos pos, Level world) {
        world.playSound(null, pos, SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 1,1);
    }
}
