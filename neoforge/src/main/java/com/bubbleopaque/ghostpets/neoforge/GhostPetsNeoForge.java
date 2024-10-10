package com.bubbleopaque.ghostpets.neoforge;

import net.neoforged.fml.common.Mod;

import com.bubbleopaque.ghostpets.GhostPets;

@Mod(GhostPets.MOD_ID)
public final class GhostPetsNeoForge {
    public GhostPetsNeoForge() {
        // Run our common setup.
        GhostPets.init();
    }
}
