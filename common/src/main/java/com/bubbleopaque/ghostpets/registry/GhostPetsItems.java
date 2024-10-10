package com.bubbleopaque.ghostpets.registry;

import java.util.function.Supplier;

import com.bubbleopaque.ghostpets.GhostPets;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class GhostPetsItems {

    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(GhostPets.MOD_ID, RegistryKeys.ITEM);
    public static final DeferredRegister<ItemGroup> TABS = DeferredRegister.create(GhostPets.MOD_ID, RegistryKeys.ITEM_GROUP);

//    public static final RegistrySupplier<Item> WOLF_GRAVE_ITEM = item("wolf_grave_item",
//        () -> new Item(defaultSettings())
//        );

    public static final RegistrySupplier<ItemGroup> GHOST_PETS_GROUP = TABS.register("ghostpets_tab", () ->
        CreativeTabRegistry.create(Text.translatable("itemgroup.ghostpets.general"),
            () -> new ItemStack(Items.REDSTONE)));

    public static <T extends Item> RegistrySupplier<T> item(String name, Supplier<T> item) {
        return ITEMS.register(Identifier.of(GhostPets.MOD_ID, name), item);
    }

    public static Item.Settings defaultSettings() {
        return new Item.Settings().arch$tab(GHOST_PETS_GROUP);
    }

    // tell the registry that we're ready for registering.
    // note: should be called after RPIModBlocks.register() since the block items rely on the blocks being registered.
    //
    public static void register(){
        TABS.register();
        ITEMS.register();
    }

}
