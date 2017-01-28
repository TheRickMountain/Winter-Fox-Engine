package com.wfe.gui.inventory;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.StaticEntity;
import com.wfe.game.World;
import com.wfe.gui.GUIElement;
import com.wfe.gui.Item;
import com.wfe.gui.ItemType;
import com.wfe.gui.Slot;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.math.Vector3f;
import com.wfe.utils.Color;
import com.wfe.utils.MousePicker;


public class Inventory implements GUIElement {
	
	private int quickSlotsAmount = 8;
	
	private int inventorySlotsX = 4;
	private int inventorySlotsY = 5;
	
	private int slotSize = 50;
	
	private int offsetBetweenSlots = 5;
	
	private Color slotColor = new Color(151, 148, 146, 125).convert();
	private List<Slot> slots = new ArrayList<Slot>();
	
	public List<Slot> quickSlots = new ArrayList<Slot>();
	private List<Slot> inventorySlots = new ArrayList<Slot>();

	public boolean showInventory = false;
	
	// Building
	private StaticEntity currentBuildingEntity;
	private int buildingEntityRotation;
	
	public Inventory() {
		for(int i = 0; i < quickSlotsAmount; i++) {
			quickSlots.add(new Slot(0, 0, slotSize, slotSize, slotColor));
			slots.add(quickSlots.get(i));
		}
		
		for(int i = 0; i < inventorySlotsX * inventorySlotsY; i++) {
			inventorySlots.add(new Slot(0, 0, slotSize, slotSize, slotColor));
			slots.add(inventorySlots.get(i));
		}
		
		updatePosition();
	}
	
	public void update() {
		if(Keyboard.isKeyDown(Key.KEY_TAB)) {
			showInventory = !showInventory;
		}
		
		// Drag items
		if(Mouse.isButtonDown(0)) {
			for(Slot slot : quickSlots) {
				updateSlot(slot, false);
			}
			
			if(showInventory) {
				for(Slot slot : inventorySlots) {
					updateSlot(slot, false);
				}
			}
		}
		
		// Consume consumable items
		if(Mouse.isButtonDown(1)) {
			for(Slot slot : quickSlots) {
				updateSlot(slot, true);
			}
			
			if(showInventory) {
				for(Slot slot : inventorySlots) {
					updateSlot(slot, true);
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
				buildingEntityRotation += 90;
				
				if(buildingEntityRotation == 360) {
					buildingEntityRotation = 0;
				}
			}
			
			currentBuildingEntity.getTransform().setPosition(x + 0.5f, 0, z + 0.5f);
			currentBuildingEntity.getTransform().setRotY(buildingEntityRotation);
			
			if(Mouse.isButtonDown(0) && !Mouse.isActiveInGUI()) {
				StaticEntity entity = GUI.draggedItem.entityBlueprint
						.createInstanceWithComponents(currentBuildingEntity.getTransform());
				if(World.getWorld().addEntityToTile(entity)){
					GUI.getGUI().setDraggedItemAmount(GUI.getGUI().getDraggedItemAmount() - 1);
					
					if(GUI.getGUI().getDraggedItemAmount() == 0) {
						GUI.draggedItem = null;
						World.getWorld().removeEntity(currentBuildingEntity);
						currentBuildingEntity = null;
					}
					
					GUI.soundSource.play(ResourceManager.getSound("taking"));
				}
			}
		}
		
		if(Mouse.isButtonDown(1)) {
			if(!Mouse.isActiveInGUI()) {
				if(GUI.getGUI().equipment.getHandSlotItemID() == Item.HOE) {
					World.getWorld().setTile(x, z, 3);
				}
			}
		}
	}
	
	public void render() {
		if(showInventory) {			
			for(Slot slot : inventorySlots) {
				slot.render();
			}
		}
		
		for(Slot slot : quickSlots) {
			slot.render();
		}
	}
	
	public void renderText() {
		if(showInventory) {
			for(Slot slot : inventorySlots) {
				slot.renderText();
			}
		}
		
		for(Slot slot : quickSlots) {
			slot.renderText();
		}
	}
	
	public boolean addItem(Item item, int amount) {
		boolean hasItem = getItemAmount(item.ID) >= 1;
		
		if(hasItem) {
			for(int i = 0; i < slots.size(); i++) {
				Slot slot = slots.get(i);
				if(slot.isHasItem() && slot.getItem().equals(item)) {
					if(slot.getItemsAmount() == item.stack) {
						continue;
					} else {
						int fullAmount = slot.getItemsAmount() + amount;
						if(fullAmount > item.stack) {
							slot.setItemsAmount(item.stack);
							amount = fullAmount - item.stack;
							continue;
						} else {
							slot.setItemsAmount(fullAmount);
							return true;
						}
					}
				}
			}
		}
		
		for(Slot slot : slots) {
			if(!slot.isHasItem()) {
				slot.addItem(item);
				if(amount > item.stack) {
					slot.setItemsAmount(item.stack);
					amount -= item.stack;
					continue;
				} else {
					slot.setItemsAmount(amount);	
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void removeItem(Item item, int amount) {
		for(Slot slot : slots) {
			if(slot.isHasItem()) {
				if(slot.getItem().equals(item)) {
					if(slot.getItemsAmount() == amount) {
						slot.removeItem();
						return;
					} else if(slot.getItemsAmount() > amount) {
						slot.setItemsAmount(slot.getItemsAmount() - amount);
						return;
					} else {
						amount -= slot.getItemsAmount();
						slot.removeItem();
						continue;
					}
				}
			}
		}
	}
	
	private void updateSlot(Slot slot, boolean use) {
		if(slot.isMouseOvered()) {
			Mouse.setActiveInGUI(true);
			if(!use) {
				if(GUI.draggedItem != null) {
					if(!slot.isHasItem()) {
						slot.addItem(GUI.draggedItem);
						slot.setItemsAmount(GUI.getGUI().getDraggedItemAmount());
						GUI.draggedItem = null;
					} else {
						if(currentBuildingEntity != null) {
							World.getWorld().removeEntity(currentBuildingEntity);
							currentBuildingEntity = null;
						}
						
						if(GUI.draggedItem.equals(slot.getItem())) {
							if(slot.getItemsAmount() == slot.getItem().stack) {
								int draggedItemAmount = GUI.getGUI().getDraggedItemAmount();
								GUI.getGUI().setDraggedItemAmount(slot.getItemsAmount());
								slot.setItemsAmount(draggedItemAmount);
							} else {
								int sum = slot.getItemsAmount() + GUI.getGUI().getDraggedItemAmount();
								if(sum == GUI.draggedItem.stack) {
									slot.setItemsAmount(sum);
									GUI.draggedItem = null;
									GUI.getGUI().setDraggedItemAmount(0);
								} else {
									if(sum > GUI.draggedItem.stack) {
										int surplus = sum % GUI.draggedItem.stack;
										slot.setItemsAmount(GUI.draggedItem.stack);
										GUI.getGUI().setDraggedItemAmount(surplus);
									} else {
										slot.setItemsAmount(sum);
										GUI.draggedItem = null;
										GUI.getGUI().setDraggedItemAmount(0);
									}
								}
							}
						} else {
							Item tempItem = GUI.draggedItem;
							int tempItemAmount = GUI.getGUI().getDraggedItemAmount();
							
							GUI.draggedItem = slot.getItem();
							GUI.getGUI().setDraggedItemAmount(slot.getItemsAmount());
							
							slot.removeItem();
							slot.addItem(tempItem);
							slot.setItemsAmount(tempItemAmount);
						}
					}
				} else {
					if(slot.isHasItem()) {
						GUI.draggedItem = slot.getItem();
						GUI.getGUI().setDraggedItemAmount(slot.getItemsAmount());
						slot.removeItem();
					}
				}
				
				checkBuildingItem();
			} else {
				if(slot.isHasItem()) {
					Item item = slot.getItem();
					if(item.type.equals(ItemType.FOOD)) {
						GUI.getGUI().status.hungerBar.increase(item.starvation);
						if(slot.getItemsAmount() > 1) {
							slot.setItemsAmount(slot.getItemsAmount() - 1);
						} else {
							slot.removeItem();
						}
						GUI.soundSource.play(ResourceManager.getSound("eating"));
					}
				}
			}
		}
	}
	
	private void checkBuildingItem() {
		if(GUI.draggedItem != null && GUI.draggedItem.type.equals(ItemType.BUILDING)) {
			currentBuildingEntity = GUI.draggedItem.entityBlueprint.createInstance();
			World.getWorld().addEntity(currentBuildingEntity);
			return;
		}
		
		if(currentBuildingEntity != null) {
			World.getWorld().removeEntity(currentBuildingEntity);
			currentBuildingEntity = null;
		}
			
	}

	private void updatePosition() {
		for(int i = 0; i < quickSlotsAmount; i++){
			quickSlots.get(i).setPosition(
					(Display.getWidth() / 2) - (4 * (slotSize + offsetBetweenSlots)) + (i * (slotSize + offsetBetweenSlots)), 
					Display.getHeight() - slotSize);
		}
		
		int countX = 0;
		int countY = 0;
		for(int i = 0; i < inventorySlotsX * inventorySlotsY; i++) {
			float posX = (Display.getWidth() - (inventorySlotsX * (slotSize + 5)) - 55 + 
					(countX * (slotSize + offsetBetweenSlots)));
			float posY = (Display.getHeight() / 3) + (countY * (slotSize + offsetBetweenSlots));
			inventorySlots.get(i).setPosition(posX, posY);
			
			countX++;
			
			if((countX != 0) && (countX % inventorySlotsX == 0)) {
				countX = 0;
				countY++;
			}
		}
		
	}
	
	public int getItemAmount(int id) {
		int amount = 0;
		for(Slot slot : slots) {
			if(slot.isHasItem()) {
				if(slot.getItem().ID == id) {
					amount += slot.getItemsAmount();
				}
			}
		}
		return amount;
	}
		
}
