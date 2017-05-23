package com.daedalus.ambientevents.actions;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;

import com.daedalus.ambientevents.wrappers.INumber;
import com.daedalus.ambientevents.wrappers.IString;
import com.daedalus.ambientevents.wrappers.Wrapper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class PlaySoundAction extends CommonAction {

	protected IString sound;
	protected IString category;
	protected INumber volume;
	protected INumber pitch;

	protected static World world = null;

	public static HashMap<String, SoundEvent> registry = null;

	public static void InitRegistry() {
		Iterator<SoundEvent> soundsIT = SoundEvent.REGISTRY.iterator();
		registry = new HashMap<String, SoundEvent>();

		while (soundsIT.hasNext()) {
			SoundEvent event = soundsIT.next();

			registry.put(event.getRegistryName().getResourcePath(), event);
		}
	}

	public PlaySoundAction(JSONObject args) throws Exception {
		super(args);

		if (args.has("sound")) {
			this.sound = Wrapper.newString(args.get("sound"));
		} else {
			throw new Exception("No sound specified");
		}

		if (args.has("category")) {
			this.category = Wrapper.newString(args.get("category"));
		} else {
			this.category = Wrapper.newString("ambient");
		}

		if (args.has("volume")) {
			this.volume = Wrapper.newNumber(args.get("volume"));
		} else {
			this.volume = Wrapper.newNumber(1);
		}

		if (args.has("pitch")) {
			this.pitch = Wrapper.newNumber(args.get("pitch"));
		} else {
			this.pitch = Wrapper.newNumber(1);
		}

		if (registry == null) {
			InitRegistry();
		}

		if (world == null) {
			world = Minecraft.getMinecraft().world;
		}
	}

	@Override
	public void execute(EntityPlayer player) {
		if (this.chance.getValue() < 1) {
			world.playSound(player.posX, player.posY, player.posZ, registry.get(this.sound.getValue()),
					SoundCategory.valueOf(this.category.getValue().toUpperCase()), (float) this.volume.getValue(),
					(float) this.pitch.getValue(), true);
		}
	}
}
