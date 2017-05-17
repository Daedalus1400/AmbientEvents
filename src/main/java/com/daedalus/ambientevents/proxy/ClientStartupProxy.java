package com.daedalus.ambientevents.proxy;

import java.io.File;

import com.daedalus.ambientevents.AmbientEvents;
import com.daedalus.ambientevents.Config;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ClientStartupProxy extends CommonStartupProxy {
	
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		MinecraftForge.EVENT_BUS.register(AmbientEvents.eventHandler);
	}
	
	public void init(FMLInitializationEvent e) {
		super.init(e);
		AmbientEvents.eventHandler.init();
	}
	
}
