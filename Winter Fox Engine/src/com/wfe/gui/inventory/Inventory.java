package com.wfe.gui.inventory;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.StaticEntity;
import com.wfe.game.World;
import com.wfe.gui.Item;
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
	private List<Slot> slots = new ArrayList<Slot>();
	
	private List<Slot> quickSlots = new ArrayList<Slot>();
	private List<Slot> inventorySlots = new ArrayList<Slot>();

	private boolean showInventory = false;
	
	// Building
	private StaticEntity currentBuildingEntity;
	private int buildingEntityRotation;
	
	public Inventory() {
		for(int i = 0; i < slotsX * slotsY; i++) {
			slots.add(new Slot(0, 0, slotSize, slotSize, slotTexture));
			
			if(i >= 24) {
				quickSlots.add(slots.get(i));
			} else {
				inventorySlots.add(slots.get(i));
			}
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
		for(Slot slot : quickSlots) {
			if(!slot.isHasItem()) {
				slot.addItem(item);
				if(amount > item.stack) {
					slot.setItemsAmount(item.stack);
					amount -= item.stack;
					continue;
				} else {
					slot.setItemsAmount(amount);
				}
				return true;
			} else if(slot.getItem().equals(item)) {
				if(slot.getItemsAmount() == item.stack) {
					continue;
				} else {
					slot.setItemsAmount(slot.getItemsAmount() + amount);
					return true;
				}
			}
		}
		
		for(Slot slot : inventorySlots) {
			if(!slot.isHasItem()) {
				slot.addItem(item);
				if(amount > item.stack) {
					slot.setItemsAmount(item.stack);
					amount -= item.stack;
					continue;
				} else {
					slot.setItemsAmount(amount);
				}
				return true;
			} else if(slot.getItem().equals(item)) {
				if(slot.getItemsAmount() == item.stack) {
					continue;
				} else {
					slot.setItemsAmount(slot.getItemsAmount() + amount);
					return true;
				}
			}
		}
		
		return false;
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
