package com.wfe.gui.inventory;

import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.gui.Item;
import com.wfe.gui.ItemType;
import com.wfe.gui.Slot;
import com.wfe.input.Mouse;
import com.wfe.textures.Texture;

public class Equipment {
	
	private int slotSize = 50;
	
	private Texture slotTexture = ResourceManager.getTexture("slot_ui");
	private Slot handSlot;
	
	public Equipment() {
		handSlot = new Slot(0, 0, slotSize, slotSize, slotTexture);
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
	}
	
	public void render() {
		handSlot.render();
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
