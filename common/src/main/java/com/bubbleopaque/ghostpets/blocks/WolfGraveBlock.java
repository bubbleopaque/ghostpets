package com.bubbleopaque.ghostpets.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WolfGraveBlock extends Block {
    public WolfGraveBlock(Settings settings) { super(settings); }

//    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//        if (world.isClient) { return ActionResult.SUCCESS; }
//
//        WolfEntity wolf = new WolfEntity(EntityType.WOLF, world);
//
//
//    }

}
