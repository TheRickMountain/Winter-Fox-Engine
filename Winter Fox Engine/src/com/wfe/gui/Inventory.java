package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Rect;

public class Inventory implements GUIComponent {
	
	private List<Slot> hotbarSlots = new ArrayList<Slot>();
	private List<Slot> inventorySlots = new ArrayList<Slot>();
	private float offset = 5;
	
	private Item selectedItem;
	private GUIText name;
	private GUIText description;
	
	public Inventory() {	
		for(int i = 0; i < 6; i++) {
			hotbarSlots.add(new Slot(new Rect(0, 0, Slot.SLOT_SIZE, Slot.SLOT_SIZE)));
		}
		
		for(int i = 0; i < 18; i++) {
			inventorySlots.add(new Slot(new Rect(0, 0, Slot.SLOT_SIZE, Slot.SLOT_SIZE)));
		}
		
		name = new GUIText("", 1.4f, FontRenderer.font, 0.0f, 0.0f, GUIManager.inspectFrame.rect.width, true);
		name.setColor(1.0f, 1.0f, 1.0f);
		description = new GUIText("", 1.1f, FontRenderer.font, 0.0f, 0.0f, 
				GUIManager.inspectFrame.rect.width - 20, false);
		description.setColor(0.9f, 0.9f, 0.9f);
	}
	
	@Override
	public void update() {	
		if(Mouse.isButtonDown(1)) {
			for(Slot slot : inventorySlots) {
				if(slot.rect.isMouseOvered()) {
					if(slot.isHasItem()) {
						selectedItem = slot.getItem();
						name.setText(selectedItem.name);
						description.setText(selectedItem.description);
					}
				}
			}
		}
	}

	@Override
	public void render() {
		for(Slot slot : inventorySlots) {
			slot.render();
		}
		
		if(selectedItem != null) {
			GUIRenderer.render(selectedItem.icon, 
					GUIManager.inspectFrame.rect.x + GUIManager.inspectFrame.rect.width / 2, 
					GUIManager.mainFrame.getY() + offset + 60, 0, 120, 120, true);
		}
	}
	
	public void renderHotbar() {
		for(Slot slot : hotbarSlots) {
			slot.render();
		}
	}

	@Override
	public void renderText() {
		for(Slot slot : inventorySlots) {
			slot.renderText();
		}
		
		if(selectedItem != null) {
			FontRenderer.render(name, GUIManager.inspectFrame.getX(), 
					GUIManager.mainFrame.getY() + offset + 120);
			
			FontRenderer.render(description, GUIManager.inspectFrame.getX() + offset, 
					GUIManager.mainFrame.getY() + offset + 120 + 25);
		}
	}
	
	public void renderHotbarText() {
		for(Slot slot : hotbarSlots) {
			slot.renderText();
		}
	}

	@Override
	public void renderPopUp() {
		
	}

	@Override
	public void renderPopUpText() {
		
	}
	
	public void updatePositions() {
		float totalWidth = (hotbarSlots.size() * Slot.SLOT_SIZE) + ((hotbarSlots.size() - 1) * offset);
		for(int i = 0; i < hotbarSlots.size(); i++) {
			hotbarSlots.get(i).rect.setPosition(
					(Display.getWidth() / 2) - (totalWidth / 2) + (i * (Slot.SLOT_SIZE + offset)), 
					Display.getHeight() - Slot.SLOT_SIZE - offset);
		}
		
		int countX = 0;
		int countY = 0;
		for(int i = 0; i < inventorySlots.size(); i++) {
			inventorySlots.get(i).rect.setPosition(
					(GUIManager.mainFrame.getX() + offset) + (countX * (Slot.SLOT_SIZE + offset)), 
					(GUIManager.mainFrame.getY() + offset) + (countY * (Slot.SLOT_SIZE + offset)));
			
			countX++;
			if(countX == 4) {
				countX = 0;
				countY++;
			}
		}
	}
	
	public void update(int[] slots, int[] count) {
		// Hotbar slots update
		for(Slot slot : this.hotbarSlots) {
			slot.removeItem();
		}
		
		for(int i = 0; i < 6; i++) {
			int slot = slots[i];
			if(slot >= 0) {
				this.hotbarSlots.get(i).addItem(ItemDatabase.getItem(slot), count[i]);
			}
		}
		
		// Main slots update
		for(Slot slot : this.inventorySlots) {
			slot.removeItem();
		}
		
		for(int i = 6; i < 24; i++) {
			int slot = slots[i];
			if(slot >= 0) {
				this.inventorySlots.get(i - 6).addItem(ItemDatabase.getItem(slot), count[i]);
			}
		}
	}
	
	public void setSelected(int slot) {
		for(Slot s : hotbarSlots) {
			s.selected = false;
		}
		
		hotbarSlots.get(slot).selected = true;
	}

}
