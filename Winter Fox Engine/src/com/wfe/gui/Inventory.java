package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.textures.Texture;

public class Inventory {
	
	private int slotsAmount = 8;
	private int slotSize = 50;
	
	private Texture slotTexture = ResourceManager.getTexture("slot_ui");
	private List<Slot> slots = new ArrayList<Slot>(slotsAmount);
	
	private Item draggedItem;
	
	protected Inventory() {
		int totalLength = slotsAmount * slotSize;
		for(int i = 0; i < totalLength; i+=50) {
			slots.add(new Slot(
					(Display.getWidth() / 2) - (totalLength / 2) + i, 
					Display.getHeight() - slotSize, 
					slotSize, slotSize, slotTexture));
		}
		
		addItem(ItemDatabase.items.get(ItemDatabase.BANANA));
		addItem(ItemDatabase.items.get(ItemDatabase.COOKIE));
	}
	
	protected void update() {
		if(Mouse.isButtonDown(0)) {
			for(Slot slot : slots) {
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
							System.out.println(slot.getItem().name);
							draggedItem = slot.getItem();
							slot.removeItem();
						}
					}
				}
			}
		}
	}
	
	protected void render() {
		for(Slot slot : slots) {
			slot.render();
		}
		
		if(draggedItem != null) {
			GUIRenderer.render(draggedItem.icon, Mouse.getX() - 25, Mouse.getY() - 25, 0, 50, 50, false);
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
