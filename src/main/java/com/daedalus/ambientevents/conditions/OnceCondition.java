package com.daedalus.ambientevents.conditions;

import net.minecraft.entity.player.EntityPlayer;

public class OnceCondition implements ICondition {

	protected boolean fired = false;
	
	@Override
	public boolean isMet(EntityPlayer player) {
		if (fired) {
			return false;
		}
		fired = true;
		return true;
	}

}
