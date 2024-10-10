package com.bubbleopaque.ghostpets.fabric;

import net.fabricmc.api.ModInitializer;

import com.bubbleopaque.ghostpets.GhostPets;

public final class GhostPetsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        GhostPets.init();
    }
}
