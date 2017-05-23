package com.daedalus.ambientevents.proxy;

import com.daedalus.ambientevents.ModItems;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonStartupProxy {

	public void preInit(FMLPreInitializationEvent e) {
		ModItems.init();
	}

	public void init(FMLInitializationEvent e) {

	}

	public void postInit(FMLPostInitializationEvent e) {

	}

}
