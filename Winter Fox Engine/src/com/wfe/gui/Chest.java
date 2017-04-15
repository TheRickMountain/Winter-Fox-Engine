package com.wfe.gui;

import java.util.List;

import com.wfe.audio.AudioMaster;
import com.wfe.components.ChestComponent;
import com.wfe.core.Display;
import com.wfe.core.Game;
import com.wfe.core.ResourceManager;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Rect;

public class Chest {

	public GUIFrame background;
	private List<Slot> slots;
	
	private int columns;
	private int rows;
	
	public Chest() {
		background = new GUIFrame(new Rect(0, 0, 0, 0), false);
	}
	
	public void update() {
		if(Mouse.isButtonDown(1)) {
			for(Slot slot : slots) {
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
							AudioMaster.defaultSource.play(ResourceManager.getSound("eating"));
						}
					}
				}
			}
		}
		
		if(background.rect.isMouseOvered()) {
			for(Slot slot : slots) {
				if(slot.rect.isMouseOvered()) {
					if(slot.isHasItem()) {
						GUIManager.showPopUpInfo(slot.getItem());
					}
				}
			}
		}
			
		if(Mouse.isButtonDown(0)) {
			for(Slot slot : slots) {
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
							}
						}
					} else {
						if(GUIManager.draggedItem.id != Item.NULL) {
							slot.addItem(GUIManager.draggedItem, GUIManager.draggedItemCount);
							GUIManager.setDraggedItem(ItemDatabase.getItem(Item.NULL), 0);
						}
					}
				}
			}
		}
		
		if(Display.isResized()) {
			updatePositions();
		}
	}
	
	public void render() {
		GUIRenderer.render(background.getFrameTextures());
		
		for(Slot slot : slots) {
			slot.render();
		}
	}
	
	public void renderText() {
		for(Slot slot : slots) {
			slot.renderText();
		}
	}
	
	public void open(ChestComponent chest) {
		slots = chest.getSlots();
		
		columns = chest.getColumns();
		rows = chest.getRows();
		
		int width = 20 + (columns - 1) * 5 + columns * Slot.SIZE;
		int height = 20 + (rows - 1) * 5 + rows * Slot.SIZE;
		background.setSize(width, height);
		
		updatePositions();
	}
	
	public void updatePositions() {
		if(slots == null) {
			return;
		}
		
		background.setPosition(
				Display.getWidth() / 2 - background.getWidth() / 2, 
				GUIManager.inventory.inventoryFrame.rect.y - background.getHeight() - Slot.SIZE / 2);
		
		int countX = 0;
		int countY = 0;
		for(Slot recipe : slots) {
			recipe.rect.setPosition(background.getX() + (countX * (recipe.rect.width + 5)), 
					background.getY() + (countY * (recipe.rect.height + 5)));
			
			countX++;
			if(countX == columns) {
				countX = 0;
				countY++;
			}
		}
	}
	
}
