package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.audio.AudioMaster;
import com.wfe.audio.Source;
import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Transformation;
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

public class GUIManager  {
	
	private static Source source;
	
	private static Rect hotbarRect;
	private static List<Slot> hotbarSlots = new ArrayList<>();
	
	private static Rect inventoryRect;
	private static List<Slot> inventorySlots = new ArrayList<>();
	
	private static List<Slot> allSlots = new ArrayList<>();
	
	private static boolean open = false;
	
	private static Item draggedItem;
	private static GUIText draggedItemText;
	private static int draggedItemCount;
	
	private static int selected = 0;
	private static int lastSelected = 0;
	
	public static void init() {
		ItemDatabase.create();
		
		source = new Source();
		
		// Hotbar
		hotbarRect = new Rect(0, 0, 385, 70);

		for(int i = 0; i < 6; i++) {
			hotbarSlots.add(new Slot(new Rect(0, 0, Slot.SIZE, Slot.SIZE)));
			allSlots.add(hotbarSlots.get(i));
		}
		
		// Inventory
		inventoryRect = new Rect(0, 0, 255, 320);
		
		for(int i = 0; i < 20; i++) {
			inventorySlots.add(new Slot(new Rect(0, 0, Slot.SIZE, Slot.SIZE)));
			allSlots.add(inventorySlots.get(i));
		}
		
		hotbarSlots.get(selected).setSelected(true);
		
		updatePositions();
		
		draggedItem = ItemDatabase.getItem(Item.NULL);
		draggedItemText = new GUIText("", FontRenderer.ARIAL);
		draggedItemText.setScale(0.8f);
		draggedItemCount = 0;
	}
	
	public static void update() {	
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
			
			AudioMaster.defaultSource.play(ResourceManager.getSound("tick"));
			
			
			Slot selectedSlot = hotbarSlots.get(selected);
			if(selectedSlot.isHasItem()) {
				Item item = selectedSlot.getItem();
				switch(item.type) {
				case TOOL:
					Game.player.playerController.addEquipment(
							item.blueprint.createInstanceWithComponents(new Transformation()));
					break;
				default:
					Game.player.playerController.removeEquipment();
					break;
				}
			} else {
				Game.player.playerController.removeEquipment();
			}
		}
		
		if(Keyboard.isKeyDown(Key.KEY_E)) {
			open = !open;
			source.play(ResourceManager.getSound("inventory"));
			
			Display.setCursor(Display.defaultCursor);
			Mouse.setActiveInGUI(open);
		}
		
		if(open) {
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
									
									checkSlotToEquipment(slot);
								}
							}
						} else {
							if(draggedItem.id != Item.NULL) {
								slot.addItem(draggedItem, draggedItemCount);
								setDraggedItem(ItemDatabase.getItem(Item.NULL), 0);
								
								checkSlotToEquipment(slot);
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
	
	public static void checkSlotToEquipment(Slot slot) {
		if(slot.isSelected()) {
			if(slot.isHasItem()) {
				Item item = slot.getItem();
				switch(item.type) {
				case TOOL:
					Game.player.playerController.addEquipment(
							item.blueprint.createInstanceWithComponents(new Transformation()));
					break;
				default:
					Game.player.playerController.removeEquipment();
					break;
				}
			}
		}
	}
	
	public static void checkSelectedSlot() {
		
	}
	
	public static void render() {
		if(open) {
			for(Slot slot : inventorySlots) {
				slot.render();
			}
		}
		
		for(Slot slot : hotbarSlots) {
			slot.render();
		}
	}

	public static void renderText() {
		if(open) {
			for(Slot slot : inventorySlots) {
				slot.renderText();
			}
		}
		
		for(Slot slot : hotbarSlots) {
			slot.renderText();
		}
	}

	public static void renderPopUp() {
		if(draggedItem.id != Item.NULL) {
			GUIRenderer.render(draggedItem.icon, Color.WHITE, 
					Mouse.getX() - Slot.SIZE / 2, Mouse.getY() - Slot.SIZE / 2, 
					0, Slot.SIZE, Slot.SIZE, false);
		}
	}

	public static void renderPopUpText() {
		if(draggedItem.id != Item.NULL) {
			draggedItemText.setPosition(
					Mouse.getX() + Slot.SIZE / 2 - draggedItemText.getWidth(), 
					Mouse.getY() + Slot.SIZE / 2 - (draggedItemText.getHeight() / 2));
			FontRenderer.render(draggedItemText);
		}
	}
	
	private static void updatePositions() {
		hotbarRect.x = Display.getWidth() / 2 - hotbarRect.width / 2;
		hotbarRect.y = Display.getHeight() - hotbarRect.height;
		
		for(int i = 0; i < 6; i++) {
			hotbarSlots.get(i).rect.setPosition(hotbarRect.x + (i * (Slot.SIZE + 5)), hotbarRect.y);
		}
		
		inventoryRect.x = Display.getWidth() / 2 - inventoryRect.width;
		inventoryRect.y = Display.getHeight() / 2 - inventoryRect.height / 2;
		
		int countX = 0;
		int countY = 0;
		for(int i = 0; i < 20; i++) {
			inventorySlots.get(i).rect.setPosition(
					inventoryRect.x + (countX * (Slot.SIZE + 5)),
					inventoryRect.y + (countY * (Slot.SIZE + 5)));
			
			countX++;
			if(countX == 4) {
				countX = 0;
				countY++;
			}
		}
	}
	
	private static void setDraggedItem(Item item, int count) {
		draggedItem = item;
		draggedItemCount = count;
		if(count > 1)
			draggedItemText.setText("" + count);
		else
			draggedItemText.setText("");
	}

	public static int addItem(Item item, int count) {
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
		
		return count;
	}
	
}
