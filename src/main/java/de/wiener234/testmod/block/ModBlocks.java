package de.wiener234.testmod.block;

import de.wiener234.testmod.Testmod;
import de.wiener234.testmod.block.custom.ElevatorBlock;
import de.wiener234.testmod.item.ModCreativeModeTab;
import de.wiener234.testmod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.util.LoaderExceptionModCrash;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;
import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Testmod.MOD_ID);

    public static final RegistryObject<Block> TEST_BLOCK = registerBlock("test_block",
            ()-> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(9f).requiresCorrectToolForDrops()), ModCreativeModeTab.TEST_TAB);

    public static final RegistryObject<Block> ELEVATOR_BLOCK = registerBlock("elevator_block",
            ()-> new ElevatorBlock(BlockBehaviour.Properties.of(Material.METAL).strength(7f)), ModCreativeModeTab.TEST_TAB);


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }


    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        return ModItems.ITEMS.register(name, ()-> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }


    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }

}
