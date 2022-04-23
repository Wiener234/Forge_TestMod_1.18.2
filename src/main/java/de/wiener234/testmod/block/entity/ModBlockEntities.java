package de.wiener234.testmod.block.entity;

import de.wiener234.testmod.Testmod;
import de.wiener234.testmod.block.ModBlocks;
import de.wiener234.testmod.block.entity.custom.CoinTableEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Testmod.MOD_ID);


    public static final RegistryObject<BlockEntityType<CoinTableEntity>> COIN_TABLE_ENTITY =
            BLOCK_ENTITIES.register("coin_table_entity", () -> BlockEntityType.Builder.of(CoinTableEntity::new,
                    ModBlocks.COIN_TABLE.get()).build(null));



    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
