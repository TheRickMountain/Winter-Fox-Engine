package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.audio.AudioMaster;
import com.wfe.audio.Source;
import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.font.GUIText;
import com.wfe.game.Game;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.input.Scroll;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Color;
import com.wfe.utils.Rect;

public class Inventory {
	
	private Source source;
	
	private int rows = 3;
	private int columns = 6;
	
	protected Rect hotbarRect;
	private List<Slot> hotbarSlots = new ArrayList<>();
	
	protected Rect inventoryRect;
	private List<Slot> inventorySlots = new ArrayList<>();
	
	private List<Slot> allSlots = new ArrayList<>();
	
	private boolean open = false;
	
	private Item draggedItem;
	private GUIText draggedItemText;
	private int draggedItemCount;
	
	private Item selectedItem;
	private int selected = 0;
	private int lastSelected = 0;

	public Inventory() {
		source = new Source();
		
		// Hotbar
		hotbarRect = new Rect(0, 0, 385, 70);

		for(int i = 0; i < columns; i++) {
			hotbarSlots.add(new Slot(new Rect(0, 0, Slot.SIZE, Slot.SIZE)));
			allSlots.add(hotbarSlots.get(i));
		}
		
		// Inventory
		inventoryRect = new Rect(0, 0, 385, 190);
		
		for(int i = 0; i < rows * columns; i++) {
			inventorySlots.add(new Slot(new Rect(0, 0, Slot.SIZE, Slot.SIZE)));
			allSlots.add(inventorySlots.get(i));
		}
		
		hotbarSlots.get(selected).setSelected(true);
		selectedItem = ItemDatabase.getItem(Item.NULL);
		
		updatePositions();
		
		draggedItem = ItemDatabase.getItem(Item.NULL);
		draggedItemText = new GUIText("", FontRenderer.ARIAL);
		draggedItemText.setScale(0.8f);
		draggedItemCount = 0;
	}
	
	public void update() {
		if(Keyboard.isKeyDown(Key.KEY_G)) {
			addItem(ItemDatabase.getItem(Item.AXE), 1);
			addItem(ItemDatabase.getItem(Item.PICKAXE), 1);
			addItem(ItemDatabase.getItem(Item.HOE), 1);
			addItem(ItemDatabase.getItem(Item.CLUB), 1);
			addItem(ItemDatabase.getItem(Item.WALL), 5);
		}
		
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
		
		if(Keyboard.isKeyDown(Key.KEY_E)) {
			open = !open;
			source.play(ResourceManager.getSound("inventory"));
			
			Display.setCursor(Display.defaultCursor);
			Mouse.setActiveInGUI(open);
		}
		
		if(open) {
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
			
			if(Mouse.isButtonDown(0)) {
				for(Slot slot : allSlots) {
					if(slot.rect.isMouseOvered()) {
						if(slot.isHasItem()) {
							if(draggedItem.id == Item.NULL) {
								setDraggedItem(slot.getItem(), slot.getCount());
								slot.addItem(ItemDatabase.getItem(Item.NULL), 0);
								if(slot.isSelected()) {
									Game.player.playerController.removeEquipment();
								}
							} else {
								Item item = slot.getItem();
								int count = slot.getCount();
								
								if(item.id == draggedItem.id) {
									int totalCount = count + draggedItemCount;
									if(totalCount <= item.stackSize) {
										slot.addItem(item, totalCount);
										setDraggedItem(ItemDatabase.getItem(Item.NULL), 0);
									} else {
										slot.addItem(item, item.stackSize);
										setDraggedItem(item, totalCount - item.stackSize);
									}
								} else {
									slot.addItem(draggedItem, draggedItemCount);
									setDraggedItem(item, count);
									
									checkActiveSlot();
								}
							}
						} else {
							if(draggedItem.id != Item.NULL) {
								slot.addItem(draggedItem, draggedItemCount);
								setDraggedItem(ItemDatabase.getItem(Item.NULL), 0);
								
								checkActiveSlot();
							}
						}
					}
				}
			}
		}
		
		if(Display.isResized()) {
			updatePositions();
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
					Game.player.playerController.addEquipment(
							item.entity.getInstanceNoComponents());
					break;
				case BUILDING:
					Entity entity = item.entity.getInstanceNoComponents();
					entity.getMaterial().getColor().set(0.5f, 1.0f, 0.5f);
					Game.player.playerController.addBuilding(entity);
					break;
				default:
					Game.player.playerController.removeEquipment();
					Game.player.playerController.removeBuilding();
					break;
				}
			} else {
				Game.player.playerController.removeEquipment();
			}
			
			selectedItem = slot.getItem();
		}
	}
	
	public void render() {
		if(open) {
			for(Slot slot : inventorySlots) {
				slot.render();
			}
		}
		
		for(Slot slot : hotbarSlots) {
			slot.render();
		}
	}

	public void renderText() {
		if(open) {
			for(Slot slot : inventorySlots) {
				slot.renderText();
			}
		}
		
		for(Slot slot : hotbarSlots) {
			slot.renderText();
		}
	}

	public void renderPopUp() {
		if(draggedItem.id != Item.NULL) {
			GUIRenderer.render(draggedItem.icon, Color.WHITE, 
					Mouse.getX() - Slot.SIZE / 2, Mouse.getY() - Slot.SIZE / 2, 
					0, Slot.SIZE, Slot.SIZE, false);
		}
	}

	public void renderPopUpText() {
		if(draggedItem.id != Item.NULL) {
			draggedItemText.setPosition(
					Mouse.getX() + Slot.SIZE / 2 - draggedItemText.getWidth(), 
					Mouse.getY() + Slot.SIZE / 2 - (draggedItemText.getHeight() / 2));
			FontRenderer.render(draggedItemText);
		}
	}
	
	private void updatePositions() {
		hotbarRect.x = Display.getWidth() / 2 - hotbarRect.width / 2;
		hotbarRect.y = Display.getHeight() - hotbarRect.height;
		
		for(int i = 0; i < columns; i++) {
			hotbarSlots.get(i).rect.setPosition(hotbarRect.x + (i * (Slot.SIZE + 5)), hotbarRect.y);
		}
		
		inventoryRect.x = Display.getWidth() / 2 - inventoryRect.width / 2;
		inventoryRect.y = hotbarRect.y - inventoryRect.height - Slot.SIZE / 2;
		
		int countX = 0;
		int countY = 0;
		for(int i = 0; i < rows * columns; i++) {
			inventorySlots.get(i).rect.setPosition(
					inventoryRect.x + (countX * (Slot.SIZE + 5)),
					inventoryRect.y + (countY * (Slot.SIZE + 5)));
			
			countX++;
			if(countX == columns) {
				countX = 0;
				countY++;
			}
		}
	}
	
	private void setDraggedItem(Item item, int count) {
		draggedItem = item;
		draggedItemCount = count;
		if(count > 1)
			draggedItemText.setText("" + count);
		else
			draggedItemText.setText("");
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
