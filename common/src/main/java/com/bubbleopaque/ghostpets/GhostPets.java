package com.bubbleopaque.ghostpets;

import com.bubbleopaque.ghostpets.blocks.WolfGraveBlock;
import com.bubbleopaque.ghostpets.utils.BlockBFS;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.block.BlockState;
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
    
    private static BlockPos findSolidGround(World world, BlockPos initialOccupiedPos) {
        int bottom = -64;
        
        for (int i = initialOccupiedPos.getY(); i > bottom; i--) {
            // check below
            BlockPos currentGroundPos = new BlockPos(initialOccupiedPos.getX(), i - 1, initialOccupiedPos.getZ());
            BlockState currentGroundBlock = world.getBlockState(currentGroundPos);
            if (!currentGroundBlock.isIn(BlockTags.REPLACEABLE)) {
                LOGGER.info("found solid ground");
                return new BlockPos(initialOccupiedPos.getX(), i, initialOccupiedPos.getZ());
            }
        }
        
        LOGGER.info("no ground :(");
        return null;
        
    }

    public static void init() {
//        LOGGER.info("ghostpets init");

        GhostPetsBlocks.register();
        GhostPetsItems.register();

        EntityEvent.LIVING_DEATH.register((entity, source) -> {
//            if (!(entity instanceof WolfEntity wolf && wolf.getOwner() instanceof PlayerEntity player)) { return EventResult.pass(); }
            if (!(entity instanceof ChickenEntity chicken)) { return EventResult.pass(); }

            LOGGER.info("death :(");
            World world = entity.getWorld();
            
            BlockPos occupiedPos = entity.getBlockPos();
            BlockPos groundPos = new BlockPos(occupiedPos.getX(), occupiedPos.getY() - 1, occupiedPos.getZ());
            BlockState occupiedBlock = world.getBlockState(occupiedPos);
            BlockState groundBlock = world.getBlockState(groundPos);
            
            boolean occupiedOK = occupiedBlock.isIn(BlockTags.REPLACEABLE);
            boolean groundOK = !groundBlock.isIn(BlockTags.REPLACEABLE);
            
            if (occupiedOK && groundOK) {
                LOGGER.info("both OK");
                // place block
                world.setBlockState(occupiedPos, GhostPetsBlocks.WOLF_GRAVE_BLOCK.get().getDefaultState());
                return EventResult.pass();
            } else if (occupiedOK) {
                BlockPos placeBlockAt = findSolidGround(world, occupiedPos);
                if (placeBlockAt != null) {
                    // place block
                    world.setBlockState(placeBlockAt, GhostPetsBlocks.WOLF_GRAVE_BLOCK.get().getDefaultState());
                } else {
                    // place at spawn i guess
                    LOGGER.info("placed at spawn");
                    world.setBlockState(world.getSpawnPos(), GhostPetsBlocks.WOLF_GRAVE_BLOCK.get().getDefaultState());
                }
            } else {
                // occupiedBlock is solid- run BFS
                LOGGER.info("running BFS");
                BlockBFS BFS = new BlockBFS(world, occupiedPos);
                BlockPos placeBlockAt = BFS.runBFS();
                world.setBlockState(placeBlockAt, GhostPetsBlocks.WOLF_GRAVE_BLOCK.get().getDefaultState());
                // (handle null here)
            }
            
//             if (occupiedBlock.isIn(BlockTags.REPLACEABLE)) {
//                 if (!(groundBlock.isIn(BlockTags.REPLACEABLE))) {
//                     // place block
//                     LOGGER.info("A");
//                     world.setBlockState(occupiedPos, GhostPetsBlocks.WOLF_GRAVE_BLOCK.get().getDefaultState());
//                 } else {
//                     boolean placed = findSolidGround(world, groundPos);
//                     if (!placed) {
//                         LOGGER.info("not placed");
//                         // at this point we've gone to the bottom of the world and haven't found a single block
//                         // BFS from original position
//                         LOGGER.info("B");
//                         BlockBFS BFS = new BlockBFS(world, occupiedPos);
//                         BlockPos placeBlockAt = BFS.runBFS();
//                         // place block
//                         if (placeBlockAt == null) {
//                             // if BFS returns null, just give the item to the player
////                             player.giveItemStack(GhostPetsBlocks.WOLF_GRAVE_BLOCK.get().asItem().getDefaultStack());
//                         } else {
//                             world.setBlockState(placeBlockAt, GhostPetsBlocks.WOLF_GRAVE_BLOCK.get().getDefaultState());
//                         }
//                     }
//                 }
//             } else {
//                 // BFS search for open space
//                 LOGGER.info("C");
//                 BlockBFS BFS = new BlockBFS(world, occupiedPos);
//                 BlockPos placeBlockAt = BFS.runBFS();
//                 // place block
//                 if (placeBlockAt == null) {
////                     player.giveItemStack(GhostPetsBlocks.WOLF_GRAVE_BLOCK.get().asItem().getDefaultStack());
//                 } else {
//                     world.setBlockState(placeBlockAt, GhostPetsBlocks.WOLF_GRAVE_BLOCK.get().getDefaultState());
//                 }
//             }
            
            

            return EventResult.pass();
        });

    }
}
