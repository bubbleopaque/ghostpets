package com.bubbleopaque.ghostpets.utils;

// https://github.com/SamsTheNerd/monthofswords/blob/main/common/src/main/java/com/samsthenerd/monthofswords/utils/BFSHelper.java

import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.*;

public class BlockBFS {
    
    static World world;
    static BlockPos initial;
    
    public BlockBFS() { }
    
    public BlockBFS(World _world, BlockPos _initial) {
        world = _world;
        initial = _initial;
    }
    
    public BlockPos runBFS() {
    
        HashSet<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();
        queue.add(initial);
        
        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();
            
            if (!visited.add(current)) {
                continue;
            }
            
            // check current
            if (isPosValid(current)) {
                return current;
            }
            
            // check distance
            if (!current.isWithinDistance(initial, 128)) {
                BlockBFS spawn = new BlockBFS(world, world.getSpawnPos());
                return spawn.runBFS();
            }
            
            // get neighbors
            for (Direction dir : Direction.values()) {
                BlockPos neighbor = current.offset(dir);
                queue.add(neighbor);
            }
            
        }
        
        return null;
    
    }
    
    public static boolean isPosValid(BlockPos pos) {
        boolean occupiedIsReplaceable = world.getBlockState(pos).isIn(BlockTags.REPLACEABLE);
        boolean groundIsNotReplaceable = !(world.getBlockState(pos).isIn(BlockTags.REPLACEABLE));
        return (occupiedIsReplaceable && groundIsNotReplaceable);
    }
    
}
