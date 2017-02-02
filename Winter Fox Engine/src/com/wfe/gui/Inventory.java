package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.utils.Rect;

public class Inventory implements GUIComponent {
	
	private List<Slot> slots = new ArrayList<Slot>();
	private float offset = 5;
	
	public Inventory() {
		for(int i = 0; i < 6; i++) {
			slots.add(new Slot(new Rect(0, 0, Slot.SLOT_SIZE, Slot.SLOT_SIZE)));
		}
		
		updatePositions();
		
		slots.get(0).addItem(ItemDatabase.getItem(Item.APPLE), 2);
		slots.get(1).addItem(ItemDatabase.getItem(Item.FLINT), 1);
		slots.get(4).addItem(ItemDatabase.getItem(Item.AXE), 1);
	}
	
	@Override
	public void update() {
		if(Display.isResized()) {
			updatePositions();
		}
	}

	@Override
	public void render() {
		for(Slot slot : slots) {
			slot.render();
		}
	}

	@Override
	public void renderText() {
		for(Slot slot : slots) {
			slot.renderText();
		}
	}

	@Override
	public void renderPopUp() {
		
	}

	@Override
	public void renderPopUpText() {
		
	}
	
	private void updatePositions() {
		float totalWidth = (slots.size() * Slot.SLOT_SIZE) + ((slots.size() - 1) * offset);
		for(int i = 0; i < slots.size(); i++) {
			slots.get(i).rect.setPosition(
					(Display.getWidth() / 2) - (totalWidth / 2) + (i * (Slot.SLOT_SIZE + offset)), 
					Display.getHeight() - Slot.SLOT_SIZE - offset);
		}
	}

}
