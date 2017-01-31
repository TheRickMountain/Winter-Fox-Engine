package com.wfe.gui;

import java.util.List;

import com.wfe.core.Display;

public class Inventory implements GUIComponent {
	
	public GUIGrid quickInventory;
	private GUIGrid mainInventory;
	
	public Inventory() {
		quickInventory = new GUIGrid(6, 1);
		mainInventory = new GUIGrid(6, 3);
		
		updatePositions();
	}
	
	@Override
	public void update() {
		quickInventory.update();
		mainInventory.update();
		
		if(Display.isResized()) {
			updatePositions();
		}
	}

	@Override
	public void render() {
		mainInventory.render();
	}

	@Override
	public void renderText() {
		mainInventory.renderText();
	}

	@Override
	public void renderPopUp() {
		
	}

	@Override
	public void renderPopUpText() {
		
	}
	
	private void updatePositions() {
		quickInventory.setPosition(
				(Display.getWidth() / 2) - (quickInventory.rect.width / 2), 
				Display.getHeight() - quickInventory.rect.height);
		
		mainInventory.setPosition(
				(Display.getWidth() / 2) - (mainInventory.rect.width / 2), 
				quickInventory.rect.y - mainInventory.rect.height - (GUIGrid.SLOT_SIZE / 2));
	}
	
	public void update(int[] slots, int[] count) {
		for(int i = 0; i < 24; i++) {
			addItem(i, slots[i], count[i]);
		}
	}
	
	public void addItem(int slot, int item, int count) {
		List<GUISlot> quickSlots = quickInventory.getSlots();
		List<GUISlot> mainSlots = mainInventory.getSlots();
		if(item != -1) {
			if(slot < quickSlots.size()) {
				quickSlots.get(slot).addItem(ItemDatabase.getItem(item), count);
			} else {
				mainSlots.get(slot - quickSlots.size()).addItem(ItemDatabase.getItem(item), count);
			}
		}
	}

}
