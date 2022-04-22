package de.wiener234.testmod.item;

import de.wiener234.testmod.Testmod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Testmod.MOD_ID);

    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register( "test_item",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TEST_TAB)));

    public static final RegistryObject<Item> MELTED_GOLD_ITEM = ITEMS.register( "melted_gold_item",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TEST_TAB)));

    public static final RegistryObject<Item> COIN_ITEM = ITEMS.register( "coin_item",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TEST_TAB)));

    public static final RegistryObject<Item> HAMMER_ITEM = ITEMS.register( "hammer_item",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TEST_TAB).durability(16)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
