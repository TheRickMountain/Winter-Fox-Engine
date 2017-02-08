package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.components.InventoryComponent;
import com.wfe.core.Display;
import com.wfe.ecs.ComponentType;
import com.wfe.game.Game;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Rect;

public class Inventory implements GUIComponent {
	
	private List<Slot> hotbarSlots = new ArrayList<Slot>();
	private List<Slot> inventorySlots = new ArrayList<Slot>();
	private List<Slot> allSlots = new ArrayList<Slot>();
	private float offset = 5;
	
	private Item selectedItem;
	private GUIText name;
	private GUIText description;
	
	private Item draggedItem;
	private int draggedItemCount;
	private GUIText draggedItemCountText;
	
	public Inventory() {	
		for(int i = 0; i < 6; i++) {
			hotbarSlots.add(new Slot(new Rect(0, 0, Slot.SLOT_SIZE, Slot.SLOT_SIZE)));
			allSlots.add(hotbarSlots.get(i));
		}
		
		for(int i = 0; i < 20; i++) {
			inventorySlots.add(new Slot(new Rect(0, 0, Slot.SLOT_SIZE, Slot.SLOT_SIZE)));
			allSlots.add(inventorySlots.get(i));
		}
		
		name = new GUIText("", 1.4f, FontRenderer.font, 0.0f, 0.0f, GUIManager.inspectFrame.rect.width, true);
		name.setColor(1.0f, 1.0f, 1.0f);
		description = new GUIText("", 1.1f, FontRenderer.font, 0.0f, 0.0f, 
				GUIManager.inspectFrame.rect.width - 20, false);
		description.setColor(0.9f, 0.9f, 0.9f);
		
		draggedItemCountText = new GUIText("", 1.2f, FontRenderer.font, 0.0f, 0.0f, Slot.SLOT_SIZE, true);
		draggedItemCountText.setColor(1.0f, 1.0f, 1.0f);
	}
	
	@Override
	public void update() {	
		if(Mouse.isButtonDown(1)) {
			for(Slot slot : allSlots) {
				if(slot.rect.isMouseOvered()) {
					if(slot.isHasItem()) {
						selectedItem = slot.getItem();
						name.setText(selectedItem.name);
						description.setText(selectedItem.description);
					}
				}
			}
		}
		
		if(Mouse.isButtonDown(0)) {
			InventoryComponent inv = (InventoryComponent) Game.player.getComponent(ComponentType.INVENTORY);
			
			int count = 0;
			for(Slot slot : allSlots) {
				if(slot.rect.isMouseOvered()) {
					if(slot.isHasItem()) {
						if(draggedItem == null) {
							draggedItem = slot.getItem();
							draggedItemCount = slot.getItemCount();
							draggedItemCountText.setText("" + draggedItemCount);
							inv.removeItemFromSlot(count);
						} else {
							if(inv.slots[count] == draggedItem.id) {
								if(inv.counts[count] == draggedItem.stackSize) {
									int tempCount = inv.counts[count];
									
									inv.counts[count] = draggedItemCount;
									
									draggedItemCount = tempCount;
									draggedItemCountText.setText("" + draggedItemCount);
								} else {
									int totalCount = inv.counts[count] + draggedItemCount;
									if(totalCount <= draggedItem.stackSize) {
										inv.counts[count] = totalCount;
										draggedItem = null;
									} else if(totalCount > draggedItem.stackSize){
										inv.counts[count] = draggedItem.stackSize;
										totalCount -= draggedItem.stackSize;
										
										draggedItemCount = totalCount;
										draggedItemCountText.setText("" + draggedItemCount);
									}
								}
							} else {
								Item temp = ItemDatabase.getItem(inv.slots[count]);
								int tempCount = inv.counts[count];
								
								inv.slots[count] = draggedItem.id;
								inv.counts[count] = draggedItemCount;
								
								draggedItem = temp;
								draggedItemCount = tempCount;
								draggedItemCountText.setText("" + draggedItemCount);
							}
						}
					} else {
						if(draggedItem != null) {
							inv.addItemToSlot(draggedItem.id, draggedItemCount, count);
							draggedItem = null;
						}
					}
				}
				count++;
			}
			
			update(inv.slots, inv.counts);
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
		if(draggedItem != null) {
			GUIRenderer.render(draggedItem.icon, Mouse.getX(), Mouse.getY(), 0, Slot.SLOT_SIZE, Slot.SLOT_SIZE, true);
		}
	}

	@Override
	public void renderPopUpText() {
		if(draggedItem != null) {
			if(draggedItemCount > 1) {
				FontRenderer.render(draggedItemCountText, 
						Mouse.getX() - Slot.SLOT_SIZE / 2, 
						Mouse.getY() - Slot.SLOT_SIZE / 2);
			}
		}
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
		
		for(int i = 6; i < 26; i++) {
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
