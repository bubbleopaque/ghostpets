package com.bubbleopaque.ghostpets.utils;

// https://github.com/SamsTheNerd/monthofswords/blob/main/common/src/main/java/com/samsthenerd/monthofswords/utils/BFSHelper.java

import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.*;

public class BlockBFS {
    
    public static BlockPos runBFS(World world, BlockPos initial) {
    
        HashSet<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();
        queue.add(initial);
        
        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();
            
            if (!visited.add(current)) {
                continue;
            }
            
            // check current
            if (isPosValid(world, current)) {
                return current;
            }
            
            // check distance
            if (!current.isWithinDistance(initial, 128)) {
                return null;
            }
            
            // get neighbors
            for (Direction dir : Direction.values()) {
                BlockPos neighbor = current.offset(dir);
                queue.add(neighbor);
            }
            
        }
        
        return null;
    
    }
    
    public static boolean isPosValid(World world, BlockPos pos) {
        boolean occupiedIsReplaceable = world.getBlockState(pos).isIn(BlockTags.REPLACEABLE);
        boolean groundIsNotReplaceable = !(world.getBlockState(pos).isIn(BlockTags.REPLACEABLE));
        return (occupiedIsReplaceable && groundIsNotReplaceable);
    }
    
}
