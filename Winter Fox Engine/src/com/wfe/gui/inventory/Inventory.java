package com.wfe.gui.inventory;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.StaticEntity;
import com.wfe.game.World;
import com.wfe.gui.Item;
import com.wfe.gui.ItemDatabase;
import com.wfe.gui.ItemType;
import com.wfe.gui.Slot;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.math.Vector3f;
import com.wfe.textures.Texture;
import com.wfe.utils.MousePicker;


public class Inventory {
	
	private int slotsX = 8;
	private int slotsY = 4;
	
	private int slotSize = 50;
	
	private Texture slotTexture = ResourceManager.getTexture("slot_ui");
	private List<Slot> slots = new ArrayList<Slot>(8 * 3);
	private List<Slot> quickSlots = new ArrayList<Slot>(8);
	
	
	private boolean showInventory = false;
	
	// Building
	private StaticEntity currentBuildingEntity;
	private int buildingEntityRotation;
	
	public Inventory() {
		for(int i = 0; i < slotsX * slotsY; i++) {
			slots.add(new Slot(0, 0, slotSize, slotSize, slotTexture));
		}
		
		updatePosition();
	}
	
	public void update() {
		if(Keyboard.isKeyDown(Key.KEY_TAB)) {
			showInventory = !showInventory;
		}
		
		// Drag items
		if(Mouse.isButtonDown(0)) {
			for(int i = 0; i < slotsX * slotsY; i++) {
				if(!showInventory) {
					if(i >= 24) {
						updateSlot(i, false);
					}
				} else {
					updateSlot(i, false);
				}
			}
		}
		
		// Consume consumable items
		if(Mouse.isButtonDown(1)) {
			for(int i = 0; i < slotsX * slotsY; i++) {
				if(!showInventory) {
					if(i >= 24) {
						updateSlot(i, true);
					}
				} else {
					updateSlot(i, true);
				}
			}
		}
		
		building();
		
		if(Display.isResized()) {
			updatePosition();
		}
	}
	
	private void building() {
		int x = 0;
		int z = 0;
		Vector3f tPos = MousePicker.getCurrentTerrainPoint();
		if(tPos != null) {
			x = (int)tPos.x;
			z = (int)tPos.z;
		}
		
		if(currentBuildingEntity != null) {			
			if(Keyboard.isKeyDown(Key.KEY_R)) {
				buildingEntityRotation += 45;
				
				if(buildingEntityRotation == 360) {
					buildingEntityRotation = 0;
				}
			}
			
			currentBuildingEntity.getTransform().setPosition(x + 0.5f, 0, z + 0.5f);
			currentBuildingEntity.getTransform().setRotY(buildingEntityRotation);
			
			if(Mouse.isButtonDown(0) && !Mouse.isActiveInGUI()) {
				StaticEntity entity = GUIManager.draggedItem.entityBlueprint
						.createInstanceWithComponents(currentBuildingEntity.getTransform());
				World.getWorld().addEntityToTile(entity);
			}
		}
		
		if(Mouse.isButtonDown(1)) {
			if(!Mouse.isActiveInGUI()) {
				if(GUIManager.getGUI().equipment.getHandSlotItemID() == ItemDatabase.HOE) {
					World.getWorld().setTile(x, z, 3);
				}
			}
		}
	}
	
	public void render() {
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
	}
	
	public void renderText() {
		for(int i = 0; i < slotsX * slotsY; i++) {
			if(!showInventory) {
				if(i >= 24) {
					Slot slot = slots.get(i);
					slot.renderText();
				}
			} else {
				Slot slot = slots.get(i);
				slot.renderText();
			}
		}
	}
	
	public boolean addItem(Item item) {
		// Check quick slots
		for(int i = 24; i < 32; i++) {
			Slot slot = slots.get(i);
			if(!slot.isHasItem()) {
				slot.addItem(item);
				slot.setItemsAmount(1);
				return true;
			} else if(slot.getItem().equals(item)) {
				slot.setItemsAmount(slot.getItemsAmount() + 1);
				return true;
			}
		} 
		
		// Check all slots
		for(Slot slot : slots) {
			if(!slot.isHasItem()) {
				slot.addItem(item);
				slot.setItemsAmount(1);
				return true;
			} else if(slot.getItem().equals(item)) {
				slot.setItemsAmount(slot.getItemsAmount() + 1);
				return true;
			}
		}
		
		return false;
	}
	
	private void updateSlot(int index, boolean use) {
		Slot slot = slots.get(index);
		if(slot.isMouseOvered()) {
			Mouse.setActiveInGUI(true);
			if(!use) {
				if(GUIManager.draggedItem != null) {
					if(!slot.isHasItem()) {
						slot.addItem(GUIManager.draggedItem);
						GUIManager.draggedItem = null;
					} else {
						if(currentBuildingEntity != null) {
							World.getWorld().removeEntity(currentBuildingEntity);
							currentBuildingEntity = null;
						}
						
						Item tempItem = slot.getItem();
						slot.removeItem();
						slot.addItem(GUIManager.draggedItem);
						GUIManager.draggedItem = tempItem;
					}
				} else {
					if(slot.isHasItem()) {
						GUIManager.draggedItem = slot.getItem();
						slot.removeItem();
					}
				}
				
				checkBuildingItem();
			} else {
				Item item = slot.getItem();
				if(item != null) {
					if(item.type.equals(ItemType.FOOD)) {
						GUIManager.getGUI().status.hungerBar.increase(item.starvation);
						slot.removeItem();
					}
				}
			}
		}
	}
	
	private void checkBuildingItem() {
		if(GUIManager.draggedItem != null && GUIManager.draggedItem.type.equals(ItemType.BUILDING)) {
			currentBuildingEntity = GUIManager.draggedItem.entityBlueprint.createInstance();
			World.getWorld().addEntity(currentBuildingEntity);
			return;
		}
		
		if(currentBuildingEntity != null) {
			World.getWorld().removeEntity(currentBuildingEntity);
			currentBuildingEntity = null;
		}
			
	}

	private void updatePosition() {
		int totalSlotsXLength = slotsX * slotSize;
		int countX = 0;
		int countY = 0;
		int offsetBetweenSlots = 0;
		for(int i = 0; i < slotsX * slotsY; i++) {
			int xPos = (Display.getWidth() / 2) - (totalSlotsXLength / 2);
			int yPos = Display.getHeight() - ((slotsY * slotSize) + slotSize);
			slots.get(i).setPosition(
					xPos + (countX * slotSize), 
					yPos + (countY * slotSize + offsetBetweenSlots));
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
		
}
