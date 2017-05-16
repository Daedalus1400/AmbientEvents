package com.daedalus.ambientevents.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

import com.daedalus.ambientevents.Config;

public class CommonStartupProxy {

	public static Configuration config;
	
	public void preInit(FMLPreInitializationEvent e) {
		File directory = e.getModConfigurationDirectory();
		config = new Configuration(new File(directory.getPath(), "AmbientEvents.cfg"));
		Config.readConfig();
	}

	public void init(FMLInitializationEvent e) {
		// TODO Auto-generated method stub

	}

	public void postInit(FMLPostInitializationEvent e) {
		if (config.hasChanged()) {
			config.save();
		}
	}

}