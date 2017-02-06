package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.audio.AudioMaster;
import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.utils.Rect;

public class Inventory implements GUIComponent {
	
	private List<Slot> quickSlots = new ArrayList<Slot>();
	private List<Slot> mainSlots = new ArrayList<Slot>();
	private float offset = 2.5f;
	
	private boolean open = false;
	
	public Inventory() {
		for(int i = 0; i < 6; i++) {
			quickSlots.add(new Slot(new Rect(0, 0, Slot.SLOT_SIZE, Slot.SLOT_SIZE)));
		}
		
		for(int i = 0; i < 18; i++) {
			mainSlots.add(new Slot(new Rect(0, 0, Slot.SLOT_SIZE, Slot.SLOT_SIZE)));
		}
		
		updatePositions();
	}
	
	@Override
	public void update() {	
		if(Keyboard.isKeyDown(Key.KEY_E)) {
			open = !open;
			AudioMaster.defaultSource.play(ResourceManager.getSound("inventory"));
		}
		
		if(Display.isResized()) {
			updatePositions();
		}
	}

	@Override
	public void render() {
		for(Slot slot : quickSlots) {
			slot.render();
		}
		
		if(open) {
			for(Slot slot : mainSlots) {
				slot.render();
			}
		}
	}

	@Override
	public void renderText() {
		for(Slot slot : quickSlots) {
			slot.renderText();
		}
		
		if(open) {
			for(Slot slot : mainSlots) {
				slot.renderText();
			}
		}
	}

	@Override
	public void renderPopUp() {
		
	}

	@Override
	public void renderPopUpText() {
		
	}
	
	private void updatePositions() {
		float totalWidth = (quickSlots.size() * Slot.SLOT_SIZE) + ((quickSlots.size() - 1) * offset);
		for(int i = 0; i < quickSlots.size(); i++) {
			quickSlots.get(i).rect.setPosition(
					(Display.getWidth() / 2) - (totalWidth / 2) + (i * (Slot.SLOT_SIZE + offset)), 
					Display.getHeight() - Slot.SLOT_SIZE - offset);
		}
		
		int countX = 0;
		int countY = 0;
		for(int i = 0; i < mainSlots.size(); i++) {
			mainSlots.get(i).rect.setPosition(
					(Display.getWidth() / 2) - (totalWidth / 2) + (countX * (Slot.SLOT_SIZE + offset)), 
					((quickSlots.get(0).rect.y - Slot.SLOT_SIZE / 2) - ((Slot.SLOT_SIZE - offset) * 3))
					+ (countY * (Slot.SLOT_SIZE + offset)));
			
			countX++;
			if(countX == 6) {
				countX = 0;
				countY++;
			}
		}
	}
	
	public void update(int[] slots, int[] count) {
		// Quick slots update
		for(Slot slot : this.quickSlots) {
			slot.removeItem();
		}
		
		for(int i = 0; i < 6; i++) {
			int slot = slots[i];
			if(slot >= 0) {
				this.quickSlots.get(i).addItem(ItemDatabase.getItem(slot), count[i]);
			}
		}
		
		// Main slots update
		for(Slot slot : this.mainSlots) {
			slot.removeItem();
		}
		
		for(int i = 6; i < 24; i++) {
			int slot = slots[i];
			if(slot >= 0) {
				this.mainSlots.get(i - 6).addItem(ItemDatabase.getItem(slot), count[i]);
			}
		}
	}
	
	public void setSelected(int slot) {
		for(Slot s : quickSlots) {
			s.selected = false;
		}
		
		quickSlots.get(slot).selected = true;
	}

}
