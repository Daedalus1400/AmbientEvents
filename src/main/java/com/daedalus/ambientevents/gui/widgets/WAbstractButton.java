package com.daedalus.ambientevents.gui.widgets;

import java.util.function.Consumer;

import com.daedalus.ambientevents.actions.PlaySoundAction;

import net.minecraft.util.SoundCategory;

public class WAbstractButton extends WWidget {

	protected Consumer<Integer> callback;
	
	public WAbstractButton(WWidget parentIn) {
		super(parentIn);
		if (PlaySoundAction.registry == null) {
			PlaySoundAction.InitRegistry();
		}
	}

	public void setOnClickAction(Consumer<Integer> onClick) {
		this.callback = onClick;
	}
	
	@Override
	public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
		mc.world.playSound(mc.player.posX,  mc.player.posY,  mc.player.posZ, PlaySoundAction.registry.get("ui.button.click"), SoundCategory.AMBIENT, 0.3f, 1.0f, true);
		if (callback != null) {
			this.callback.accept(mouseButton);
		}
		super.onMouseClick(mouseX, mouseY, mouseButton);
	}
}
