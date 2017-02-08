package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.audio.AudioMaster;
import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Rect;

public class Inventory implements GUIComponent {
	
	private GUIFrame inventoryFrame;
	private GUIFrame inspectFrame;
	
	private List<Slot> hotbarSlots = new ArrayList<Slot>();
	private List<Slot> inventorySlots = new ArrayList<Slot>();
	private float offset = 5;
	
	private boolean open = false;
	
	private Item selectedItem;
	private GUIText name;
	private GUIText description;
	
	public Inventory() {
		inventoryFrame = new GUIFrame(new Rect(0, 0, 275, 345));
		inspectFrame = new GUIFrame(new Rect(0, 0, 275, 345));
		
		for(int i = 0; i < 6; i++) {
			hotbarSlots.add(new Slot(new Rect(0, 0, Slot.SLOT_SIZE, Slot.SLOT_SIZE)));
		}
		
		for(int i = 0; i < 18; i++) {
			inventorySlots.add(new Slot(new Rect(0, 0, Slot.SLOT_SIZE, Slot.SLOT_SIZE)));
		}
		
		name = new GUIText("", 1.4f, FontRenderer.font, 0.0f, 0.0f, inspectFrame.rect.width, true);
		name.setColor(1.0f, 1.0f, 1.0f);
		description = new GUIText("", 1.1f, FontRenderer.font, 0.0f, 0.0f, 
				inspectFrame.rect.width - 20, false);
		description.setColor(0.9f, 0.9f, 0.9f);
		
		updatePositions();
	}
	
	@Override
	public void update() {	
		if(Keyboard.isKeyDown(Key.KEY_E)) {
			open = !open;
			AudioMaster.defaultSource.play(ResourceManager.getSound("inventory"));
		}
		
		if(open) {
			if(Mouse.isButtonDown(0)) {
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
		
		if(Display.isResized()) {
			updatePositions();
		}
	}

	@Override
	public void render() {
		for(Slot slot : hotbarSlots) {
			slot.render();
		}
		
		if(open) {
			GUIRenderer.render(inventoryFrame.getFrameTextures());
			
			for(Slot slot : inventorySlots) {
				slot.render();
			}
			
			GUIRenderer.render(inspectFrame.getFrameTextures());
			
			if(selectedItem != null) {
				GUIRenderer.render(selectedItem.icon, inspectFrame.rect.x + inspectFrame.rect.width / 2, 
						inventoryFrame.getY() + offset + 60, 0, 120, 120, true);
			}
		}
	}

	@Override
	public void renderText() {
		for(Slot slot : hotbarSlots) {
			slot.renderText();
		}
		
		if(open) {
			for(Slot slot : inventorySlots) {
				slot.renderText();
			}
			
			if(selectedItem != null) {
				FontRenderer.render(name, inspectFrame.getX(), 
						inventoryFrame.getY() + offset + 120);
				
				FontRenderer.render(description, inspectFrame.getX() + offset, 
						inventoryFrame.getY() + offset + 120 + 25);
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
		float totalWidth = (hotbarSlots.size() * Slot.SLOT_SIZE) + ((hotbarSlots.size() - 1) * offset);
		for(int i = 0; i < hotbarSlots.size(); i++) {
			hotbarSlots.get(i).rect.setPosition(
					(Display.getWidth() / 2) - (totalWidth / 2) + (i * (Slot.SLOT_SIZE + offset)), 
					Display.getHeight() - Slot.SLOT_SIZE - offset);
		}
		
		inventoryFrame.setPosition(Display.getWidth() / 2 - 275 - (offset / 2), Display.getHeight() / 2 - 172.5f);
		inspectFrame.setPosition(Display.getWidth() / 2 + (offset / 2), Display.getHeight() / 2 - 172.5f);
		
		int countX = 0;
		int countY = 0;
		for(int i = 0; i < inventorySlots.size(); i++) {
			inventorySlots.get(i).rect.setPosition(
					(inventoryFrame.getX() + offset) + (countX * (Slot.SLOT_SIZE + offset)), 
					(inventoryFrame.getY() + offset) + (countY * (Slot.SLOT_SIZE + offset)));
			
			countX++;
			if(countX == 4) {
				countX = 0;
				countY++;
			}
		}
	}
	
	public void update(int[] slots, int[] count) {
		// Quick slots update
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
