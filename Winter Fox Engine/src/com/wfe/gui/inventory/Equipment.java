package com.wfe.gui.inventory;

import com.wfe.core.Display;
import com.wfe.gui.GUIElement;
import com.wfe.gui.Item;
import com.wfe.gui.ItemType;
import com.wfe.gui.Slot;
import com.wfe.input.Mouse;
import com.wfe.utils.Color;

public class Equipment implements GUIElement {
	
	private int slotSize = 50;
	
	private Color slotColor = new Color(128f / 255f, 128f / 255f, 128f / 255f, 1.0f);
	private Slot handSlot;
	
	public Equipment() {
		handSlot = new Slot(0, 0, slotSize, slotSize, slotColor);
		updatePositions();
	}
	
	public void update() {
		if(Mouse.isButtonDown(0)) {
			if(handSlot.isMouseOvered()) {
				if(GUIManager.draggedItem != null) {
					if(handSlot.isHasItem()) {
						Item temp = handSlot.getItem();
						handSlot.removeItem();
						handSlot.addItem(GUIManager.draggedItem);
						GUIManager.draggedItem = temp;
					} else {
						if(addItem(GUIManager.draggedItem)){
							GUIManager.draggedItem = null;
						}
					}
				} else {
					if(handSlot.isHasItem()) {
						GUIManager.draggedItem = handSlot.getItem();
						handSlot.removeItem();
					}
				}
			}
		}
		
		if(Display.isResized()) {
			updatePositions();
		}
	}
	
	public void render() {
		handSlot.render();
	}
	
	@Override
	public void renderText() {
		
	}

	private void updatePositions() {
		handSlot.setPosition(
				Display.getWidth() - slotSize - 10, 
				Display.getHeight() - slotSize);
	}
	
	public boolean addItem(Item item) {
		if(item.type.equals(ItemType.TOOL) || item.type.equals(ItemType.WEAPON)) {
			handSlot.addItem(item);
			return true;
		}
		return false;
	}
	
	public int getHandSlotItemID() {
		if(handSlot.getItem() == null) {
			return -1;
		} else {
			return handSlot.getItem().ID;
		}
	}
	
}
