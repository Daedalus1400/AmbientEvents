package com.daedalus.ambientevents.proxy;

import java.io.File;

import com.daedalus.ambientevents.AmbientEvents;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ClientStartupProxy extends CommonStartupProxy {
	
	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		MinecraftForge.EVENT_BUS.register(AmbientEvents.clientEventHandler);
		AmbientEvents.clientEventHandler.configPath = e.getModConfigurationDirectory();
	}
	
	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		AmbientEvents.clientEventHandler.init();
	}
}
