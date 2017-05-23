package com.daedalus.ambientevents.proxy;

import com.daedalus.ambientevents.AmbientEvents;
import com.daedalus.ambientevents.ModItems;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientStartupProxy extends CommonStartupProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		MinecraftForge.EVENT_BUS.register(AmbientEvents.clientEventHandler);
		AmbientEvents.clientEventHandler.configPath = e.getModConfigurationDirectory();
		ModItems.initModels();
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		AmbientEvents.clientEventHandler.init();
	}
}
