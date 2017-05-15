package com.daedalus.ambientevents.conditions;

import net.minecraft.entity.player.EntityPlayer;

public interface ICondition {
	
	public boolean isMet(EntityPlayer player);
}
