package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.game.World;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.GUIRenderer;

public class QuickInventory {
	
	private static int slotsAmount = 8;
	private static float slotSize = 50;
	private static float offsetBetweenSlots = 5;
	
	private List<GUITexture> slots = new ArrayList<GUITexture>(slotsAmount);
	
	private float totalLength;
	
	private GUIFrame frame;
	
	protected QuickInventory() {
		totalLength = (slotsAmount * slotSize) + (offsetBetweenSlots * (slotsAmount - 1));
		frame = new GUIFrame((Display.getWidth() / 2 - totalLength / 2), 
				(Display.getHeight() - slotSize - offsetBetweenSlots), totalLength, 50);
		World.getWorld().addGUITextures(frame.getFrameTextures());
		
		float halfSlotPos = totalLength / 8;
		for(int i = 0; i < slotsAmount; i++) {
			slots.add(new GUITexture(ResourceManager.getTexture("banana_ui"), 
					frame.getX() + i * halfSlotPos + slotSize / 2, frame.getY() + slotSize / 2, 
					0, slotSize, slotSize, true));
		}
	}
	
	protected void update() {
		if(frame.isMouseOvered()) {
			Mouse.setActiveInGUI(true);
			
			for(GUITexture slot : slots) {
				if(slot.isMouseOvered()) {
					slot.setScale(slotSize + 10, slotSize + 10);
				} else {
					slot.setScale(slotSize, slotSize);
				}
			}
		} else {
			for(GUITexture slot : slots) {
				slot.setScale(slotSize, slotSize);
			}
		}
		
		if(Display.isResized()) {
			frame.setPosition(Display.getWidth() / 2 - totalLength / 2, 
					Display.getHeight() - slotSize - offsetBetweenSlots);
			
			float halfSlotPos = totalLength / 8;
			for(int i = 0; i < slotsAmount; i++) {
				GUITexture slot = slots.get(i);
				slot.setPosition(frame.getX() + i * halfSlotPos + slotSize / 2, frame.getY() + slotSize / 2);
			}
		}
	}
	
	protected void render() {
		for(GUITexture slot : slots) {
			GUIRenderer.render(slot);
		}
	}

}
