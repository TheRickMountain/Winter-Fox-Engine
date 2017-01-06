package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.StaticEntity;
import com.wfe.game.World;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.math.Vector3f;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.textures.Texture;
import com.wfe.utils.MousePicker;

//TODO: Rewrite building code

public class Inventory {
	
	private int slotsX = 8;
	private int slotsY = 4;
	
	private int slotSize = 50;
	
	private Texture slotTexture = ResourceManager.getTexture("slot_ui");
	private List<Slot> slots = new ArrayList<Slot>(8 * 4);
	
	private Item draggedItem;
	
	private boolean showInventory = false;
	
	// Building
	private StaticEntity currentBuildingEntity;
	private int buildingEntityRotation;
	
	protected Inventory() {
		for(int i = 0; i < slotsX * slotsY; i++) {
			slots.add(new Slot(0, 0, slotSize, slotSize, slotTexture));
		}
		
		setSlotPositions();
		
		addItem(ItemDatabase.items.get(ItemDatabase.BANANA));
		addItem(ItemDatabase.items.get(ItemDatabase.COOKIE));
		addItem(ItemDatabase.items.get(ItemDatabase.SHROOM));
		addItem(ItemDatabase.items.get(ItemDatabase.AXE));
		addItem(ItemDatabase.items.get(ItemDatabase.WALL));
		addItem(ItemDatabase.items.get(ItemDatabase.CROSS_WALL));
		addItem(ItemDatabase.items.get(ItemDatabase.DOOR_WALL));
		addItem(ItemDatabase.items.get(ItemDatabase.WINDOW_WALL));
		addItem(ItemDatabase.items.get(ItemDatabase.APPLE));
		addItem(ItemDatabase.items.get(ItemDatabase.APPLE));
		addItem(ItemDatabase.items.get(ItemDatabase.APPLE));
		addItem(ItemDatabase.items.get(ItemDatabase.APPLE));
		addItem(ItemDatabase.items.get(ItemDatabase.BUSH));
	}
	
	protected void update() {
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
			setSlotPositions();
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
			
			currentBuildingEntity.getTransform().setPosition(x + 0.25f, 0, z + 0.5f);
			currentBuildingEntity.getTransform().setRotY(buildingEntityRotation);
			
			if(Mouse.isButtonDown(0) && !Mouse.isActiveInGUI()) {
				StaticEntity entity = draggedItem.entityBlueprint
						.createInstanceWithComponents(currentBuildingEntity.getTransform());
				World.getWorld().addEntityToTile(entity);
			}
		}
		
		if(Mouse.isButtonDown(1)) {
			if(!Mouse.isActiveInGUI()) {
				World.getWorld().setTile(x, z, 3);
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
	
	public boolean addItem(Item item) {
		// Check quick slots
		for(int i = 24; i < 32; i++) {
			Slot slot = slots.get(i);
			if(!slot.isHasItem()) {
				slot.addItem(item);
				return true;
			}
		} 
		
		// Check all slots
		for(Slot slot : slots) {
			if(!slot.isHasItem()) {
				slot.addItem(item);
				return true;
			}
		}
		
		return false;
	}
	
	private void updateSlot(int index, boolean consume) {
		Slot slot = slots.get(index);
		if(slot.isMouseOvered()) {
			Mouse.setActiveInGUI(true);
			if(!consume) {
				if(draggedItem != null) {
					if(!slot.isHasItem()) {
						slot.addItem(draggedItem);
						draggedItem = null;
					} else {
						if(currentBuildingEntity != null) {
							World.getWorld().removeEntity(currentBuildingEntity);
							currentBuildingEntity = null;
						}
						
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
		if(draggedItem != null && draggedItem.type.equals(ItemType.BUILDING)) {
			currentBuildingEntity = draggedItem.entityBlueprint.createInstance();
			World.getWorld().addEntity(currentBuildingEntity);
			return;
		}
		
		if(currentBuildingEntity != null) {
			World.getWorld().removeEntity(currentBuildingEntity);
			currentBuildingEntity = null;
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
		
}
