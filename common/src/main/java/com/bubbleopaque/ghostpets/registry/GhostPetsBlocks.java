package com.bubbleopaque.ghostpets.registry;

import java.util.function.Supplier;

import com.bubbleopaque.ghostpets.GhostPets;
import com.bubbleopaque.ghostpets.blocks.WolfGraveBlock;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class GhostPetsBlocks {

    public static DeferredRegister<Block> blocks = DeferredRegister.create(GhostPets.MOD_ID, RegistryKeys.BLOCK);

    public static final RegistrySupplier<Block> WOLF_GRAVE_BLOCK = block("wolf_grave",
        () -> new WolfGraveBlock(
            AbstractBlock.Settings.create()
                .mapColor(MapColor.GRAY)
                .requiresTool()
                .strength(3.0F, 3.0F)
            ));


    // A helper method we'll use for registering our blocks and their corresponding block item.
    public static <T extends Block> RegistrySupplier<T> block(String name, Supplier<T> block, Item.Settings settings) {
        RegistrySupplier<T> blockRegistered = blockNoItem(name, block);
        GhostPetsItems.item(name, () -> new BlockItem(blockRegistered.get(), settings));
        return blockRegistered;
    }

    // Another helper method but uses the default item settings.
    public static <T extends Block> RegistrySupplier<T> block(String name, Supplier<T> block) {
        return block(name, block, GhostPetsItems.defaultSettings());
    }

    // Registers only the block, not the block item.
    public static <T extends Block> RegistrySupplier<T> blockNoItem(String name, Supplier<T> block) {
        return blocks.register(Identifier.of(GhostPets.MOD_ID, name), block);
    }

    // tells the registry that we're ready for registering. shouldn't add any more after calling this.
    // note that this should be called before calling RPIModItems.register()
    public static void register() {
        blocks.register();
    }

}
