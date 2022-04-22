package de.wiener234.testmod.block.custom;


import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ElevatorBlock extends Block {
    private static final Logger LOGGER = LogManager.getLogger();

    int _blockPosYdown = 1;
    int _blockPosYup = 1;

    public ElevatorBlock(Properties properties) {
        super(properties);
    }


    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {

        if (blockBelow(pLevel, pPos, pState) != null && pEntity.isCrouching() && pEntity instanceof Player) {
            pEntity.setPos(pPos.getX() + .5, pPos.getY() + 1 - _blockPosYdown, pPos.getZ() + .5);
        }

        super.stepOn(pLevel, pPos, pState, pEntity);
    }

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float p_152430_) {

        LOGGER.info(pPos.getY() + _blockPosYup);

        if (blockAbove(pLevel, pPos, pState) != null && pEntity instanceof Player) {
            pEntity.setPos(pPos.getX() + .5, pPos.getY() + 1 + _blockPosYup, pPos.getZ() + .5);
        }

        super.fallOn(pLevel, pState, pPos, pEntity, p_152430_);
    }

    private boolean isElevatorBlock(Block block, BlockState blockSteptOn){
        return block == blockSteptOn.getBlock();
    }


    private Block blockBelow(Level level, BlockPos pos, BlockState blockState){

        for(int i = 1; i <= 390; i++){
            if(isElevatorBlock(level.getBlockState(pos.below(i)).getBlock(), blockState)) {
                _blockPosYdown = i;
                return level.getBlockState(pos.below(i)).getBlock();
            }
        }

        return null;
    }

    private Block blockAbove(Level level, BlockPos pos, BlockState blockState){

        for(int i =1; i <= 390 ; i++){
            if(isElevatorBlock(level.getBlockState(pos.above(i)).getBlock(), blockState)) {
                LOGGER.info(level.getBlockState(pos.above(i)).getBlock());
                _blockPosYup = i;
                return level.getBlockState(pos.above(i)).getBlock();
            }
        }

        return null;
    }
}