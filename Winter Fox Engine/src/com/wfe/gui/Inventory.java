package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.audio.AudioMaster;
import com.wfe.audio.Source;
import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.game.Game;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.input.Scroll;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Rect;

public class Inventory {
	
	private Source source;
	
	private int columns = 6;
	private int rows = 3;
	
	protected GUIFrame hotbarFrame;
	private List<Slot> hotbarSlots = new ArrayList<>();
	
	public GUIFrame inventoryFrame;
	private List<Slot> inventorySlots = new ArrayList<>();
	
	private List<Slot> allSlots = new ArrayList<>();
	
	private Item selectedItem;
	private int selected = 0;
	private int lastSelected = 0;

	public Inventory() {
		source = new Source();
		
		// Hotbar
		hotbarFrame = new GUIFrame(new Rect(0, 0, 
				20 + (columns - 1) * 5 + columns * Slot.SIZE, 
				10 + Slot.SIZE), false);
		
		for(int i = 0; i < columns; i++) {
			hotbarSlots.add(new Slot(new Rect(0, 0, Slot.SIZE, Slot.SIZE)));
			allSlots.add(hotbarSlots.get(i));
		}
		
		// Inventory
		inventoryFrame = new GUIFrame(new Rect(0, 0, 
				20 + (columns - 1) * 5 + columns * Slot.SIZE, 
				20 + (rows - 1) * 5 + rows * Slot.SIZE), false);
		
		for(int i = 0; i < rows * columns; i++) {
			inventorySlots.add(new Slot(new Rect(0, 0, Slot.SIZE, Slot.SIZE)));
			allSlots.add(inventorySlots.get(i));
		}
		
		hotbarSlots.get(selected).setSelected(true);
		selectedItem = ItemDatabase.getItem(Item.NULL);
		
		updatePositions();
	}
	
	public void updateHotbar() {
		if(Keyboard.isKeyDown(Key.KEY_1)) selected = 0;
		else if(Keyboard.isKeyDown(Key.KEY_2)) selected = 1;
		else if(Keyboard.isKeyDown(Key.KEY_3)) selected = 2;
		else if(Keyboard.isKeyDown(Key.KEY_4)) selected = 3;
		else if(Keyboard.isKeyDown(Key.KEY_5)) selected = 4;
		else if(Keyboard.isKeyDown(Key.KEY_6)) selected = 5;
		
		selected -= Scroll.getScroll();
		
		if(selected != lastSelected) {
			for(Slot slot : hotbarSlots) {
				slot.setSelected(false);
			}
			
			if(selected >= hotbarSlots.size()) selected = 0;
			else if(selected < 0) selected = hotbarSlots.size() - 1;
			
			lastSelected = selected;
			
			hotbarSlots.get(selected).setSelected(true);
			selectedItem = hotbarSlots.get(selected).getItem();
			
			AudioMaster.defaultSource.play(ResourceManager.getSound("tick"));
			
			checkActiveSlot();
		}
	}
	
	public void update() {
		if(Mouse.isButtonDown(1)) {
			for(Slot slot : allSlots) {
				if(slot.rect.isMouseOvered()) {
					if(slot.isHasItem()) {
						Item item = slot.getItem();
						if(item.type.equals(ItemType.CONSUMABLE)) {
							GUIManager.stats.addHunger(item.hunger);
							GUIManager.stats.addThirst(item.thirst);
							slot.setCount(slot.getCount() - 1);
							if(slot.getCount() == 0) {
								slot.addItem(ItemDatabase.getItem(Item.NULL), 0);
							}
							source.play(ResourceManager.getSound("eating"));
						}
					}
				}
			}
		}

		for(Slot slot : allSlots) {
			if(slot.rect.isMouseOvered()) {
				if(slot.isHasItem()) {
					GUIManager.showPopUp(slot.getItem());
				}
			}
		}
		
		if(Mouse.isButtonDown(0)) {
			for(Slot slot : allSlots) {
				if(slot.rect.isMouseOvered()) {
					if(slot.isHasItem()) {
						if(GUIManager.draggedItem.id == Item.NULL) {
							GUIManager.setDraggedItem(slot.getItem(), slot.getCount());
							slot.addItem(ItemDatabase.getItem(Item.NULL), 0);
							if(slot.isSelected()) {
								Game.player.playerController.removeEquipment();
							}
						} else {
							Item item = slot.getItem();
							int count = slot.getCount();

							if(item.id == GUIManager.draggedItem.id) {
								int totalCount = count + GUIManager.draggedItemCount;
								if(totalCount <= item.stackSize) {
									slot.addItem(item, totalCount);
									GUIManager.setDraggedItem(ItemDatabase.getItem(Item.NULL), 0);
								} else {
									slot.addItem(item, item.stackSize);
									GUIManager.setDraggedItem(item, totalCount - item.stackSize);
								}
							} else {
								slot.addItem(GUIManager.draggedItem, GUIManager.draggedItemCount);
								GUIManager.setDraggedItem(item, count);

								checkActiveSlot();
							}
						}
					} else {
						if(GUIManager.draggedItem.id != Item.NULL) {
							slot.addItem(GUIManager.draggedItem, GUIManager.draggedItemCount);
							GUIManager.setDraggedItem(ItemDatabase.getItem(Item.NULL), 0);

							checkActiveSlot();
						}
					}
				}
			}
		}
	}
	
	public void checkActiveSlot() {
		Slot slot = hotbarSlots.get(selected);
		if(slot.isSelected()) {
			if(slot.isHasItem()) {
				Item item = slot.getItem();
				switch(item.type) {
				case TOOL:
				case WEAPON:
					Entity equipment = Game.player.playerController.getEquipment();
					if(equipment != null) {
						if(!equipment.getTag().equals(item.name.toLowerCase())) {
							Game.player.playerController.addEquipment(
									item.entity.getInstanceNoComponents());
						}
					} else {
						Game.player.playerController.addEquipment(
								item.entity.getInstanceNoComponents());
					}
					break;
				case BUILDING:
					Entity building = Game.player.playerController.getBuilding();
					if(building != null) {
						if(!building.getTag().equals(item.name.toLowerCase())) {
							Game.player.playerController.addBuilding(
									item.entity.getInstanceNoComponents());
						}
					} else {
						Game.player.playerController.addBuilding(
								item.entity.getInstanceNoComponents());
					}
					break;
				default:
					Game.player.playerController.removeEquipment();
					Game.player.playerController.removeBuilding();
					break;
				}
			} else {
				Game.player.playerController.removeBuilding();
				Game.player.playerController.removeEquipment();
			}
			
			selectedItem = slot.getItem();
		}
	}
	
	public void renderHotbar() {
		GUIRenderer.render(hotbarFrame.getFrameTextures());
		
		for(Slot slot : hotbarSlots) {
			slot.render();
		}
	}
	
	public void render() {
		GUIRenderer.render(inventoryFrame.getFrameTextures());

		for(Slot slot : inventorySlots) {
			slot.render();
		}
	}
	
	public void renderHotbarText() {
		for(Slot slot : hotbarSlots) {
			slot.renderText();
		}
	}

	public void renderText() {
		for(Slot slot : inventorySlots) {
			slot.renderText();
		}
	}
	
	public void updatePositions() {
		hotbarFrame.setPosition(Display.getWidth() / 2 - hotbarFrame.getWidth() / 2, 
				Display.getHeight() - hotbarFrame.getHeight());
		
		for(int i = 0; i < columns; i++) {
			hotbarSlots.get(i).rect.setPosition(hotbarFrame.getX() + (i * (Slot.SIZE + 5)), hotbarFrame.getY() - 5);
		}
		
		inventoryFrame.setPosition(
				Display.getWidth() / 2 - inventoryFrame.getWidth() / 2, 
				hotbarFrame.rect.y - inventoryFrame.getHeight() - Slot.SIZE / 2);
		
		int countX = 0;
		int countY = 0;
		for(int i = 0; i < rows * columns; i++) {
			inventorySlots.get(i).rect.setPosition(
					inventoryFrame.getX() + (countX * (Slot.SIZE + 5)),
					inventoryFrame.getY() + (countY * (Slot.SIZE + 5)));
			
			countX++;
			if(countX == columns) {
				countX = 0;
				countY++;
			}
		}
	}
	
	public int hasItem(Item item) {
		int count = 0;
		for(Slot slot : allSlots) {
			if(slot.isHasItem()) {
				if(slot.getItem() == item) {
					count += slot.getCount();
				}
			}
		}
		return count;
	}

	public int addItem(Item item, int count) {
		List<Slot> slotsWithSimilarItem = new ArrayList<Slot>();
		for(Slot slot : allSlots) {
			if(slot.isHasItem()) {
				if(slot.getItem().id == item.id) {
					if(slot.getCount() < item.stackSize) {
						slotsWithSimilarItem.add(slot);
					}
				}
			}
		}
		
		for(Slot slot : slotsWithSimilarItem) {
			int totalCount = slot.getCount() + count;
			if(totalCount <= item.stackSize) {
				slot.addItem(item, totalCount);
				count = 0;
				break;
			} else {
				slot.addItem(item, item.stackSize);
				count = totalCount - item.stackSize;
			}
		}
		
		if(count > 0) {
			for(Slot slot : allSlots) {
				if(!slot.isHasItem()) {
					if(count > item.stackSize) {
						slot.addItem(item, item.stackSize);
						count -= item.stackSize;
					} else {
						slot.addItem(item, count);
						count = 0;
						break;
					}
				}
			}
		}
		
		checkActiveSlot();
		
		return count;
	}
	
	public void removeItem(Item item, int count) {
		for(Slot slot : allSlots) {
			if(slot.isHasItem()) {
				if(slot.getItem() == item) {
					if(slot.getCount() > count) {
						slot.addItem(item, slot.getCount() - count);
						return;
					} else if(slot.getCount() == count) {
						slot.addItem(ItemDatabase.getItem(Item.NULL), 0);
						return;
					} else if(slot.getCount() < count) {
						count -= slot.getCount();
						slot.addItem(ItemDatabase.getItem(Item.NULL), 0);
						removeItem(item, count);
					}
				}
			}
		}
	}
	
	public Item getSelectedItem() {
		return selectedItem;
	}
	
}
