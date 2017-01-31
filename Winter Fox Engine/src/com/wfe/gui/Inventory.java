package com.wfe.gui;

import java.util.List;

import com.wfe.audio.AudioMaster;
import com.wfe.components.InventoryComponent;
import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.ComponentType;
import com.wfe.game.Game;
import com.wfe.input.Mouse;

public class Inventory implements GUIComponent {
	
	public GUIGrid quickInventory;
	public GUIGrid mainInventory;
	public final List<Slot> quickSlots;
	public final List<Slot> mainSlots;
	
	public Inventory() {
		quickInventory = new GUIGrid(6, 1);
		mainInventory = new GUIGrid(6, 3);
		
		quickSlots = quickInventory.getSlots();
		mainSlots = mainInventory.getSlots();
		
		updatePositions();
	}
	
	@Override
	public void update() {
		quickInventory.update();
		mainInventory.update();
		
		updateSlots(Mouse.BUTTON_1);
		updateSlots(Mouse.BUTTON_2);
		
		if(Display.isResized()) {
			updatePositions();
		}
	}
	
	private void updateSlots(int event) {
		if(Mouse.isButtonDown(event)) {
			for(Slot slot : quickSlots) {
				doSlotOperations(slot, event == 1);
			}
			
			for(Slot slot : mainSlots) {
				doSlotOperations(slot, event == 1);
			}
		}
		
		// Обновляем информацию InventoryComponent у Player
		InventoryComponent inv = (InventoryComponent)Game.player.getComponent(ComponentType.INVENTORY);
		for(int i = 0; i < quickSlots.size() + mainSlots.size(); i++) {
			Item item = null;
			int count = 0;
			if(i < quickSlots.size()) {
				Slot slot = quickSlots.get(i);
				item = slot.getItem();
				count = slot.getCount();
			} else {
				Slot slot = mainSlots.get(i - quickSlots.size());
				item = slot.getItem();
				count = slot.getCount();
			}
			
			inv.slots[i] = (item == null) ? -1 : item.id;
			inv.counts[i] = count;
		}
	}
	
	private void doSlotOperations(Slot slot, boolean eat) {
		if(slot.rect.isMouseOvered()) {
			if(!eat) {
				Item draggedItem = GUIManager.getDraggedItem();
				int draggedItemCount = GUIManager.getDraggedItemCount();
				if(draggedItem == null) {
					if(slot.isHasItem()) {
						GUIManager.setDraggedItem(slot.getItem(), slot.getCount());
						slot.removeItem();
					}
				} else {
					if(slot.isHasItem()) {
						Item item = slot.getItem();
						if(item.equals(draggedItem)) {
							int totalCount = slot.getCount() + draggedItemCount;
							if(totalCount <= item.stackSize) {
								slot.addItem(draggedItem, totalCount);
								GUIManager.removeDraggedItem();
							} else {
								totalCount -= item.stackSize;
								slot.addItem(draggedItem, item.stackSize);
								GUIManager.setDraggedItem(draggedItem, totalCount);
							}
						} else {
							int count = slot.getCount();
							slot.addItem(draggedItem, draggedItemCount);
							GUIManager.setDraggedItem(item, count);
						}
					} else {
						slot.addItem(draggedItem, draggedItemCount);
						GUIManager.removeDraggedItem();
					}
				}
			} else {
				if(slot.isHasItem()) {
					Item item = slot.getItem();
					if(item.type.equals(ItemType.CONSUMABLE)) {
						int count = slot.getCount();
						count--;
						if(count == 0) {
							slot.removeItem();
						} else {
							slot.addItem(item, slot.getCount() - 1);
						}
						AudioMaster.defaultSource.play(ResourceManager.getSound("eating"));
					}
				}
			}
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
		if(item != -1) {
			if(slot < quickSlots.size()) {
				quickSlots.get(slot).addItem(ItemDatabase.getItem(item), count);
			} else {
				mainSlots.get(slot - quickSlots.size()).addItem(ItemDatabase.getItem(item), count);
			}
		}
	}

}
