package com.bubbleopaque.ghostpets;

import com.bubbleopaque.ghostpets.blocks.WolfGraveBlock;
import com.bubbleopaque.ghostpets.utils.BlockBFS;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bubbleopaque.ghostpets.registry.*;

public final class GhostPets {
    public static final String MOD_ID = "ghostpets";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
//    private static boolean findSolidGround(World world, BlockPos groundPos) {
//        int bottom = -64;
//        boolean placed = false;
//
//        for (int i = groundPos.getY(); i > bottom; i--) {
//            BlockPos groundPosI = new BlockPos(groundPos.getX(), i, groundPos.getZ());
//            if (world.getBlockState(groundPosI).isIn(BlockTags.REPLACEABLE)) {
//                // place block
//                LOGGER.info("found solid ground");
//                BlockPos occupiedPosI = new BlockPos(groundPosI.getX(), groundPosI.getY() + 1, groundPosI.getZ());
//                world.setBlockState(occupiedPosI, GhostPetsBlocks.WOLF_GRAVE_BLOCK.get().getDefaultState());
//                placed = true;
//                break;
//            }
//        }
//        return placed;
//
//    }
    


    public static void init() {
//        LOGGER.info("ghostpets init");

        GhostPetsBlocks.register();
        GhostPetsItems.register();

        EntityEvent.LIVING_DEATH.register((entity, source) -> {

        });

    }
}
