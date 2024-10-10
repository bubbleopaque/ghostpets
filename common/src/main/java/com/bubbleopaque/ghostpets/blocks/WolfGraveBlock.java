package com.bubbleopaque.ghostpets.blocks;

import com.bubbleopaque.ghostpets.registry.GhostPetsBlocks;
import com.bubbleopaque.ghostpets.utils.BlockBFS;
import dev.architectury.event.EventResult;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WolfGraveBlock extends Block {
    public WolfGraveBlock(Settings settings) { super(settings); }
    
    private static BlockPos findSolidGround(World world, BlockPos initialOccupiedPos) {
        int bottom = -64;
        
        for (int i = initialOccupiedPos.getY(); i > bottom; i--) {
            // check below
            BlockPos currentGroundPos = new BlockPos(initialOccupiedPos.getX(), i - 1, initialOccupiedPos.getZ());
            BlockState currentGroundBlock = world.getBlockState(currentGroundPos);
            if (!currentGroundBlock.isIn(BlockTags.REPLACEABLE)) {
//                LOGGER.info("found solid ground");
                return new BlockPos(initialOccupiedPos.getX(), i, initialOccupiedPos.getZ());
            }
        }
        
        return null;
    }
    
    private static void placeWithBFS(World world, BlockPos occupiedPos, PlayerEntity player) {
        // occupiedBlock is solid- run BFS
        BlockPos placeBlockAt = BlockBFS.runBFS(world, occupiedPos);
        if (placeBlockAt != null) {
            // place block
            world.setBlockState(placeBlockAt, GhostPetsBlocks.WOLF_GRAVE_BLOCK.get().getDefaultState());
        } else {
            // just give to player
            player.giveItemStack(GhostPetsBlocks.WOLF_GRAVE_BLOCK.get().asItem().getDefaultStack());
        }
    }
    
    public static EventResult spawnOnDeath(LivingEntity entity, DamageSource source) {
//      if (!(entity instanceof WolfEntity wolf && wolf.getOwner() instanceof PlayerEntity player)) { return EventResult.pass(); }
        World world = entity.getWorld();
        
        if (world instanceof ClientWorld) { return EventResult.pass(); }
        if (!(entity instanceof ChickenEntity chicken)) { return EventResult.pass(); }
        
        BlockPos occupiedPos = entity.getBlockPos();
        BlockPos groundPos = new BlockPos(occupiedPos.getX(), occupiedPos.getY() - 1, occupiedPos.getZ());
        BlockState occupiedBlock = world.getBlockState(occupiedPos);
        BlockState groundBlock = world.getBlockState(groundPos);
        
        // temp
        PlayerEntity player = world.getClosestPlayer(entity, 128);
        
        boolean occupiedOK = occupiedBlock.isIn(BlockTags.REPLACEABLE);
        boolean groundOK = !groundBlock.isIn(BlockTags.REPLACEABLE);
        
        if (occupiedOK && groundOK) {
            // place block
            world.setBlockState(occupiedPos, GhostPetsBlocks.WOLF_GRAVE_BLOCK.get().getDefaultState());
        } else if (occupiedOK) {
            BlockPos placeBlockAt = findSolidGround(world, occupiedPos);
            if (placeBlockAt != null) {
                // place block
                world.setBlockState(placeBlockAt, GhostPetsBlocks.WOLF_GRAVE_BLOCK.get().getDefaultState());
            } else {
                // BFS from original location
                placeWithBFS(world, occupiedPos, player);
            }
        } else {
            // occupiedBlock is solid- run BFS
            placeWithBFS(world, occupiedPos, player);
        }

        return EventResult.pass();
    }

}
