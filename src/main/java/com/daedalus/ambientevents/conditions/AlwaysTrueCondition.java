package com.daedalus.ambientevents.conditions;

import net.minecraft.entity.player.EntityPlayer;

public class AlwaysTrueCondition implements ICondition {

	@Override
	public boolean isMet(EntityPlayer player) {
		return true;
	}

}
