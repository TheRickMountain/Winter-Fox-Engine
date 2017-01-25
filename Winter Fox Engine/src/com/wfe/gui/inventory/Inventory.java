package com.wfe.gui.inventory;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
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
				StaticEntity entity = GUIManager.draggedItem.entityBlueprint
						.createInstanceWithComponents(currentBuildingEntity.getTransform());
				if(World.getWorld().addEntityToTile(entity)){
					GUIManager.getGUI().setDraggedItemAmount(GUIManager.getGUI().getDraggedItemAmount() - 1);
					
					if(GUIManager.getGUI().getDraggedItemAmount() == 0) {
						GUIManager.draggedItem = null;
						World.getWorld().removeEntity(currentBuildingEntity);
						currentBuildingEntity = null;
					}
				}
			}
		}
		
		if(Mouse.isButtonDown(1)) {
			if(!Mouse.isActiveInGUI()) {
				if(GUIManager.getGUI().equipment.getHandSlotItemID() == Item.HOE) {
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
		//TODO: Проверить, нет ли одинаковых предметов
		//TODO: Если есть, то стакать
		//TODO: Если нет, то добавить предмет в первый попавшийся слот
		
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
				if(GUIManager.draggedItem != null) {
					if(!slot.isHasItem()) {
						slot.addItem(GUIManager.draggedItem);
						slot.setItemsAmount(GUIManager.getGUI().getDraggedItemAmount());
						GUIManager.draggedItem = null;
					} else {
						if(currentBuildingEntity != null) {
							World.getWorld().removeEntity(currentBuildingEntity);
							currentBuildingEntity = null;
						}
						
						if(GUIManager.draggedItem.equals(slot.getItem())) {
							if(slot.getItemsAmount() == slot.getItem().stack) {
								int draggedItemAmount = GUIManager.getGUI().getDraggedItemAmount();
								GUIManager.getGUI().setDraggedItemAmount(slot.getItemsAmount());
								slot.setItemsAmount(draggedItemAmount);
							} else {
								int sum = slot.getItemsAmount() + GUIManager.getGUI().getDraggedItemAmount();
								if(sum == GUIManager.draggedItem.stack) {
									slot.setItemsAmount(sum);
									GUIManager.draggedItem = null;
									GUIManager.getGUI().setDraggedItemAmount(0);
								} else {
									if(sum > GUIManager.draggedItem.stack) {
										int surplus = sum % GUIManager.draggedItem.stack;
										slot.setItemsAmount(GUIManager.draggedItem.stack);
										GUIManager.getGUI().setDraggedItemAmount(surplus);
									} else {
										slot.setItemsAmount(sum);
										GUIManager.draggedItem = null;
										GUIManager.getGUI().setDraggedItemAmount(0);
									}
								}
							}
						} else {
							Item tempItem = GUIManager.draggedItem;
							int tempItemAmount = GUIManager.getGUI().getDraggedItemAmount();
							
							GUIManager.draggedItem = slot.getItem();
							GUIManager.getGUI().setDraggedItemAmount(slot.getItemsAmount());
							
							slot.removeItem();
							slot.addItem(tempItem);
							slot.setItemsAmount(tempItemAmount);
						}
					}
				} else {
					if(slot.isHasItem()) {
						GUIManager.draggedItem = slot.getItem();
						GUIManager.getGUI().setDraggedItemAmount(slot.getItemsAmount());
						slot.removeItem();
					}
				}
				
				checkBuildingItem();
			} else {
				Item item = slot.getItem();
				if(item != null) {
					if(item.type.equals(ItemType.FOOD)) {
						GUIManager.getGUI().status.hungerBar.increase(item.starvation);
						if(slot.getItemsAmount() > 1) {
							slot.setItemsAmount(slot.getItemsAmount() - 1);
						} else {
							slot.removeItem();
						}
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
