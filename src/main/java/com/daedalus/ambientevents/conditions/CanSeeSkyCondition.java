package com.daedalus.ambientevents.conditions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class CanSeeSkyCondition implements ICondition {

	@Override
	public boolean isMet(EntityPlayer player) {
		return player.world.canSeeSky(new BlockPos(player.posX, player.posY, player.posZ));
	}

}
