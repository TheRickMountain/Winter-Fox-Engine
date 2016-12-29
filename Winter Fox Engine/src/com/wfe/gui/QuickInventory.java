package com.wfe.gui;

import com.wfe.core.Display;
import com.wfe.game.World;
import com.wfe.input.Mouse;

public class QuickInventory {
	
	private static int slotsAmount = 8;
	private static float slotSize = 50;
	private static float offsetBetweenSlots = 10;
	
	private float totalLength;
	
	private GUIFrame frame;
	
	protected QuickInventory() {
		totalLength = (slotsAmount * slotSize) + (offsetBetweenSlots * (slotsAmount - 1));
		frame = new GUIFrame(Display.getWidth() / 2 - totalLength / 2, 
				Display.getHeight() - slotSize - offsetBetweenSlots, totalLength, 50);
		World.getWorld().addGUITextures(frame.getFrameTextures());
	}
	
	protected void update() {
		if(frame.isMouseOvered()) {
			Mouse.setActiveInGUI(true);
		}
		
		if(Display.isResized()) {
			frame.setPosition(Display.getWidth() / 2 - totalLength / 2, 
					Display.getHeight() - slotSize - offsetBetweenSlots);
		}
	}
	
	protected void render() {
		
	}

}
