package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.textures.Texture;

public class Inventory {
	
	private int slotsX = 8;
	private int slotsY = 4;
	
	private int slotSize = 50;
	
	private Texture slotTexture = ResourceManager.getTexture("slot_ui");
	private List<Slot> slots = new ArrayList<Slot>(8 * 4);
	
	private Item draggedItem;
	
	private boolean showInventory = false;
	
	protected Inventory() {
		for(int i = 0; i < slotsX * slotsY; i++) {
			slots.add(new Slot(0, 0, slotSize, slotSize, slotTexture));
		}
		
		setSlotPositions();
		
		addItem(ItemDatabase.items.get(ItemDatabase.BANANA));
		addItem(ItemDatabase.items.get(ItemDatabase.COOKIE));
		addItem(ItemDatabase.items.get(ItemDatabase.SHROOM));
		addItem(ItemDatabase.items.get(ItemDatabase.AXE));
	}
	
	protected void update() {
		if(Keyboard.isKeyDown(Key.KEY_TAB)) {
			showInventory = !showInventory;
		}
		
		if(Mouse.isButtonDown(0)) {
			for(int i = 0; i < slotsX * slotsY; i++) {
				if(!showInventory) {
					if(i >= 24) {
						updateSlot(i);
					}
				} else {
					updateSlot(i);
				}
			}
		}
		
		if(Display.isResized()) {
			setSlotPositions();
		}
	}
	
	public void updateSlot(int index) {
		Slot slot = slots.get(index);
		if(slot.isMouseOvered()) {
			if(draggedItem != null) {
				if(!slot.isHasItem()) {
					slot.addItem(draggedItem);
					draggedItem = null;
				} else {
					Item tempItem = slot.getItem();
					slot.removeItem();
					slot.addItem(draggedItem);
					draggedItem = tempItem;
				}
			} else {
				if(slot.isHasItem()) {
					draggedItem = slot.getItem();
					slot.removeItem();
				}
			}
		}
	}

	protected void render() {
		for(int i = 0; i < slotsX * slotsY; i++) {
			if(!showInventory) {
				if(i >= 24) {
					Slot slot = slots.get(i);
					slot.render();
				}
			} else {
				Slot slot = slots.get(i);
				slot.render();
			}
		}
		
		if(draggedItem != null) {
			GUIRenderer.render(draggedItem.icon, Mouse.getX() - 25, Mouse.getY() - 25, 0, 50, 50, false);
		}
	}

	private void setSlotPositions() {
		int totalSlotsXLength = slotsX * slotSize;
		int countX = 0;
		int countY = 0;
		int offsetBetweenSlots = 0;
		for(int i = 0; i < slotsX * slotsY; i++) {
			int xPos = (Display.getWidth() / 2) - (totalSlotsXLength / 2);
			int yPos = Display.getHeight() - ((slotsY * slotSize) + slotSize);
			slots.get(i).setX(xPos + (countX * slotSize));
			slots.get(i).setY(yPos + (countY * slotSize + offsetBetweenSlots));
			
			countX++;
			if(countX >= 8) {
				countX = 0;
				countY++;
				if(countY == 3) {
					offsetBetweenSlots = 50;
				}
			}
		}
	}
	
	
	public void addItem(Item item) {
		for(Slot slot : slots) {
			if(!slot.isHasItem()) {
				slot.addItem(item);
				return;
			}
		}
	}

}
