package com.daedalus.ambientevents.actions;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;

import com.daedalus.ambientevents.wrappers.INumber;
import com.daedalus.ambientevents.wrappers.IString;
import com.daedalus.ambientevents.wrappers.Wrapper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class PlaySoundAction extends CommonAction {
	
	protected IString sound;
	protected IString category;
	protected INumber volume;
	protected INumber pitch;
	
	protected static World world = null;
	
	public static HashMap<String, SoundEvent> registry = null;
	
	public void InitRegistry() {
		Iterator<SoundEvent> soundsIT = SoundEvent.REGISTRY.iterator();
		registry = new HashMap<String, SoundEvent>();
		
		while(soundsIT.hasNext()) {
			SoundEvent event = soundsIT.next();
			
			registry.put(event.getRegistryName().getResourcePath(), event);
		}
	}

	public PlaySoundAction(JSONObject args) throws Exception {
		super(args);
		
		if (args.has("sound")) {
			sound = Wrapper.newString(args.get("sound"));
		} else {
			throw new Exception("No sound specified");
		}
		
		if (args.has("category")) {
			category = Wrapper.newString(args.get("category"));
		} else {
			category = Wrapper.newString("ambient");
		}
		
		if (args.has("volume")) {
			volume = Wrapper.newNumber(args.get("volume"));
		} else {
			volume = Wrapper.newNumber(1);
		}
		
		if (args.has("pitch")) {
			pitch = Wrapper.newNumber(args.get("pitch"));
		} else {
			pitch = Wrapper.newNumber(1);
		}
		
		if (registry == null) {
			InitRegistry();
		}
		
		if (world == null) {
			world = Minecraft.getMinecraft().world;
		}
	}
	
	public void execute(EntityPlayer player) {
		if (chance.getValue() < 1) {
			world.playSound(player.posX, player.posY, player.posZ, registry.get(sound.getValue()), SoundCategory.valueOf(category.getValue().toUpperCase()), (float)volume.getValue(), (float)pitch.getValue(), true);
		}
	}
}
