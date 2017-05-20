package com.daedalus.ambientevents;

import com.daedalus.ambientevents.items.Configurator;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

	public static Configurator configurator;
	
	public static void init() {
		configurator = new Configurator();
	}
	
	public static void initModels() {
		configurator.initModels();
	}
}
