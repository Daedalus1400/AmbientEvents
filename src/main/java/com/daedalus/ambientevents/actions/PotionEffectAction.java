package com.daedalus.ambientevents.actions;

import org.json.JSONObject;

import com.daedalus.ambientevents.wrappers.INumber;
import com.daedalus.ambientevents.wrappers.IString;
import com.daedalus.ambientevents.wrappers.Wrapper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionEffectAction extends CommonAction {

	protected IString effect;
	protected INumber duration;
	protected INumber amplifier;

	public PotionEffectAction(JSONObject args) throws Exception {
		super(args);
		if (args.has("effect")) {
			this.effect = Wrapper.newString(args.get("effect"));
		} else {
			throw new Exception("No potion ID given");
		}

		if (args.has("duration")) {
			this.duration = Wrapper.newNumber(args.get("duration"));
		} else {
			throw new Exception("No potion duration given");
		}

		if (args.has("amplifier")) {
			this.amplifier = Wrapper.newNumber(args.get("amplifier"));
		} else {
			this.amplifier = Wrapper.newNumber(0);
		}
	}

	@Override
	public void execute(EntityPlayer player) {

		if (this.chance.getValue() >= 1) {
			return;
		}

		Potion potion;

		switch (this.effect.getValue()) {

		case "absorption":
			potion = MobEffects.ABSORPTION;
			break;
		case "blindness":
			potion = MobEffects.BLINDNESS;
			break;
		case "fireresist":
			potion = MobEffects.FIRE_RESISTANCE;
			break;
		case "glowing":
			potion = MobEffects.GLOWING;
			break;
		case "haste":
			potion = MobEffects.HASTE;
			break;
		case "healthboost":
			potion = MobEffects.HEALTH_BOOST;
			break;
		case "hunger":
			potion = MobEffects.HUNGER;
			break;
		case "instantdamage":
			potion = MobEffects.INSTANT_DAMAGE;
			break;
		case "instanthealth":
			potion = MobEffects.INSTANT_HEALTH;
			break;
		case "invisibility":
			potion = MobEffects.INVISIBILITY;
			break;
		case "jumpboost":
			potion = MobEffects.JUMP_BOOST;
			break;
		case "levitation":
			potion = MobEffects.LEVITATION;
			break;
		case "luck":
			potion = MobEffects.LUCK;
			break;
		case "miningfatigue":
			potion = MobEffects.MINING_FATIGUE;
			break;
		case "nausea":
			potion = MobEffects.NAUSEA;
			break;
		case "nightvision":
			potion = MobEffects.NIGHT_VISION;
			break;
		case "poison":
			potion = MobEffects.POISON;
			break;
		case "regeneration":
			potion = MobEffects.REGENERATION;
			break;
		case "resistance":
			potion = MobEffects.RESISTANCE;
			break;
		case "saturation":
			potion = MobEffects.SATURATION;
			break;
		case "slowness":
			potion = MobEffects.SLOWNESS;
			break;
		case "speed":
			potion = MobEffects.SPEED;
			break;
		case "strength":
			potion = MobEffects.STRENGTH;
			break;
		case "unluck":
			potion = MobEffects.UNLUCK;
			break;
		case "waterbreathing":
			potion = MobEffects.WATER_BREATHING;
			break;
		case "weakness":
			potion = MobEffects.WEAKNESS;
			break;
		case "wither":
			potion = MobEffects.WITHER;
			break;

		default:
			return;
		}

		player.addPotionEffect(
				new PotionEffect(potion, (int) this.duration.getValue(), (int) this.amplifier.getValue()));

	}

}
