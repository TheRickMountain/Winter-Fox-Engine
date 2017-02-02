package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.audio.AudioMaster;
import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Scroll;
import com.wfe.utils.Rect;

public class Inventory implements GUIComponent {
	
	private List<Slot> slots = new ArrayList<Slot>();
	private float offset = 5;
	
	private int selectedSlot = 0;
	private int lastSelectedSlot = 0;
	
	public Inventory() {
		for(int i = 0; i < 6; i++) {
			slots.add(new Slot(new Rect(0, 0, Slot.SLOT_SIZE, Slot.SLOT_SIZE)));
		}
		
		updatePositions();
		
		slots.get(selectedSlot).selected = true;
	}
	
	@Override
	public void update() {
		if(Keyboard.isKeyDown(Key.KEY_1)) selectedSlot = 0;
		else if(Keyboard.isKeyDown(Key.KEY_2)) selectedSlot = 1;
		else if(Keyboard.isKeyDown(Key.KEY_3)) selectedSlot = 2;
		else if(Keyboard.isKeyDown(Key.KEY_4)) selectedSlot = 3;
		else if(Keyboard.isKeyDown(Key.KEY_5)) selectedSlot = 4;
		else if(Keyboard.isKeyDown(Key.KEY_6)) selectedSlot = 5;
		
		selectedSlot -= Scroll.getScroll();
		
		if(selectedSlot != lastSelectedSlot) {
			if(selectedSlot >= slots.size()) {
				selectedSlot = 0;
			} else if(selectedSlot < 0) {
				selectedSlot = slots.size() - 1;
			}
			lastSelectedSlot = selectedSlot;
			for(Slot slot : slots) {
				slot.selected = false;
			}
			
			slots.get(selectedSlot).selected = true;
			AudioMaster.defaultSource.play(ResourceManager.getSound("tick"));
		}
		
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
	
	public void update(int[] slots, int[] count) {
		for(int i = 0; i < slots.length; i++) {
			int slot = slots[i];
			if(slot >= 0) {
				this.slots.get(i).addItem(ItemDatabase.getItem(slot), count[i]);
			}
		}
	}

}
