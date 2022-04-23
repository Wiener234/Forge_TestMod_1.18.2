package de.wiener234.testmod.block.entity.custom;

import de.wiener234.testmod.block.entity.ModBlockEntities;
import de.wiener234.testmod.item.ModItems;
import de.wiener234.testmod.screen.CoinTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Random;

public class CoinTableEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(3){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();


    public CoinTableEntity(BlockPos pPos, BlockState pState) {
        super(ModBlockEntities.COIN_TABLE_ENTITY.get(), pPos, pState);
    }







    @Override
    public Component getDisplayName() {
        return new TextComponent("Coin Table");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pCointainerId, Inventory pInvetory, Player pPlayer) {
        return new CoinTableMenu(pCointainerId, pInvetory, this);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, CoinTableEntity pBlockEntity) {
        if(hasRecipe(pBlockEntity) && hasNotReachedStackLimit(pBlockEntity)) {
            craftItem(pBlockEntity);
        }
    }

    private static void craftItem(CoinTableEntity entity) {
        entity.itemHandler.extractItem(0, 1, false);
        entity.itemHandler.getStackInSlot(1).hurt(1, new Random(), null);

        entity.itemHandler.setStackInSlot(2, new ItemStack(ModItems.COIN_ITEM.get(),
                entity.itemHandler.getStackInSlot(2).getCount() + 1));
    }

    private static boolean hasRecipe(CoinTableEntity entity) {
        boolean hasItemInIngredientSlot = entity.itemHandler.getStackInSlot(0).getItem() == ModItems.MELTED_GOLD_ITEM.get();
        boolean hasItemInToolSlot = entity.itemHandler.getStackInSlot(1).getItem() == ModItems.HAMMER_ITEM.get();


        return hasItemInIngredientSlot && hasItemInToolSlot;
    }

    private static boolean hasNotReachedStackLimit(CoinTableEntity entity) {
        return entity.itemHandler.getStackInSlot(2).getCount() < entity.itemHandler.getStackInSlot(2).getMaxStackSize();
    }
}
