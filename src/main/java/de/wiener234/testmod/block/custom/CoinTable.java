package de.wiener234.testmod.block.custom;

import de.wiener234.testmod.block.entity.ModBlockEntities;
import de.wiener234.testmod.block.entity.custom.CoinTableEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class CoinTable extends BaseEntityBlock {
    public CoinTable(Properties p_49795_) {
        super(p_49795_);
    }

    private static final VoxelShape BASE = Block.box(1., 0, 1, 15, 2, 15);
    private static final VoxelShape X_LEG = Block.box(4, 2, 4, 12, 8, 12);
    private static final VoxelShape Y_LEG = Block.box(4, 2, 4, 12, 8, 12);
    private static final VoxelShape TOP = Block.box(0, 10, 0, 16, 15, 16);
    private static final VoxelShape SHAPE = Shapes.or(BASE, X_LEG, Y_LEG, TOP);



    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pGetter, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return null;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof CoinTableEntity) {
                ((CoinTableEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            System.out.print(entity);
            System.out.print(pPos);
            if(entity instanceof CoinTableEntity) {
                NetworkHooks.openGui(((ServerPlayer) pPlayer), (CoinTableEntity) entity, pPos);
            }
//            } else {
//                throw new IllegalStateException("Our Container provider is missing!");
//            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.COIN_TABLE_ENTITY.get(),
                CoinTableEntity::tick);
    }
}
