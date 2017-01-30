package com.wfe.userInterfaces;

import com.wfe.core.Display;
import com.wfe.ecs.Transformation;
import com.wfe.game.Game;
import com.wfe.gui.GUIElement;
import com.wfe.gui.Item;
import com.wfe.gui.ItemType;
import com.wfe.gui.Slot;
import com.wfe.input.Mouse;
import com.wfe.utils.Color;

public class Equipment implements GUIElement {
	
	private int slotSize = 50;
	
	private Color slotColor = new Color(131, 128, 126, 150).convert();
	public Slot handSlot;
	
	public Equipment() {
		handSlot = new Slot(0, 0, slotSize, slotSize, slotColor);
		updatePositions();
	}
	
	public void update() {
		if(Mouse.isButtonDown(0)) {
			if(handSlot.isMouseOvered()) {
				if(GUI.draggedItem != null) {
					if(handSlot.isHasItem()) {
						Item temp = handSlot.getItem();
						handSlot.removeItem();
						handSlot.addItem(GUI.draggedItem);
						GUI.draggedItem = temp;
					} else {
						if(addItem(GUI.draggedItem)){
							Game.player.addWeapon(GUI.draggedItem.entityBlueprint
									.createInstanceWithComponents(new Transformation()));
							GUI.draggedItem = null;
						}
					}
				} else {
					if(handSlot.isHasItem()) {
						GUI.draggedItem = handSlot.getItem();
						handSlot.removeItem();
						Game.player.removeWeapon();
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
			return handSlot.getItem().id;
		}
	}
	
}
