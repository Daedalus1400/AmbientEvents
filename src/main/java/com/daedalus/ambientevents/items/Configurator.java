package com.daedalus.ambientevents.items;

import com.daedalus.ambientevents.gui.ConfiguratorGUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Configurator extends Item {

	public Configurator() {
		this.setRegistryName("configurator");
		this.setUnlocalizedName("configurator");
		this.setCreativeTab(CreativeTabs.MISC);
		GameRegistry.register(this);
	}

	public void initModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0,
				new ModelResourceLocation(this.getRegistryName(), "inventory"));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (worldIn.isRemote) {
			Minecraft.getMinecraft().displayGuiScreen(new ConfiguratorGUI());
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}
