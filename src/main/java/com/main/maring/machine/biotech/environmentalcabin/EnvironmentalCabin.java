package com.main.maring.machine.biotech.environmentalcabin;

import com.main.maring.machine.energy.consumer.coredigger.CoreDiggerEntity;
import com.main.maring.menu.coredigger.CoreDiggerMenuProvider;
import com.main.maring.util.json.BlockJSON;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class EnvironmentalCabin extends Block implements EntityBlock {
    public static final String global_name = "environmental_cabin";

    public EnvironmentalCabin(Properties p_49795_) {
        super(p_49795_
                .sound(SoundType.AMETHYST)
                .strength(2f,5f)
                .mapColor(MapColor.COLOR_GRAY)
        );
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState pBlockState) {
        return new CoreDiggerEntity(pos, pBlockState);
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState blockstate, Level level, BlockPos pos, Player player, InteractionHand interactionhand, BlockHitResult blockHitResult) {
        return super.use(blockstate,level,pos,player,interactionhand,blockHitResult);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pBlockState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return (level,pos,state,blockentity) -> {
                if(blockentity instanceof CoreDiggerEntity entity) {
                    entity.clienttick();
                }
            };
        }else {
            return (level,pos,state,blockentity) -> {
                if(blockentity instanceof CoreDiggerEntity entity) {
                    entity.servertick();
                }
            };
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof CoreDiggerEntity d) {
                d.drop();
                d.remove(pLevel);
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    static {
        BlockJSON.fastGen(global_name);
    }
}
