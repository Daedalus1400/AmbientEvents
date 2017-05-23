package com.daedalus.ambientevents;

import org.apache.logging.log4j.Logger;

import com.daedalus.ambientevents.handlers.ClientEventHandler;
import com.daedalus.ambientevents.proxy.CommonStartupProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Ref.MODID, name = Ref.NAME, version = Ref.VERSION)
public class AmbientEvents {

	@SidedProxy(clientSide = "com.daedalus.ambientevents.proxy.ClientStartupProxy", serverSide = "com.daedalus.ambientevents.proxy.ServerStartupProxy")
	public static CommonStartupProxy startupProxy;

	@SideOnly(Side.CLIENT)
	public static ClientEventHandler clientEventHandler = new ClientEventHandler();

	public static Logger logger;

	@Mod.Instance
	public static AmbientEvents instance;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		logger = e.getModLog();
		startupProxy.preInit(e);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		startupProxy.init(e);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		startupProxy.postInit(e);
	}
}
