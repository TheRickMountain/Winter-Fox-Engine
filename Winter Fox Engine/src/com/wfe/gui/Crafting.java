package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Rect;

public class Crafting {
	
	private GUIFrame background;
	
	private List<Slot> slots = new ArrayList<>();
	
	private int columns = 6;
	private int rows = 4;
	
	public Crafting() {
		background = new GUIFrame(new Rect(0, 0, 
				20 + (columns - 1) * 5 + columns * Slot.SIZE, 
				20 + (rows - 1) * 5 + rows * Slot.SIZE));
		
		for(Item item : ItemDatabase.getDatabase()) {
			if(item.isHasCraft()) {
				Slot slot = new Slot(new Rect(0, 0, Slot.SIZE, Slot.SIZE));
				slot.addItem(item, 1);
				slots.add(slot);
			}
		}
		updatePositions();
	}
	
	public void update() {
		if(Display.isResized()) {
			updatePositions();
		}
		
		if(Mouse.isButtonDown(0)) {
			for(Slot recipe : slots) {
				if(recipe.rect.isMouseOvered()) {
					Item item = recipe.getItem();

					boolean craft = true;
					for(int i = 0, n = item.ingredients.length; i < n; i++) {
						int count = GUIManager.inventory.hasItem(ItemDatabase.getItem(item.ingredients[i]));
						if(count < item.ingredients[++i]) {
							craft = false;
							break;
						}
					}

					if(craft) {
						GUIManager.inventory.addItem(item, 1);

						for(int i = 0, n = item.ingredients.length; i < n; i++) {
							GUIManager.inventory.removeItem(ItemDatabase.getItem(
									item.ingredients[i]), item.ingredients[++i]);
						}

						updateRecipes();
					}
				} 
			}
		}
	}
	
	public void render() {
		GUIRenderer.render(background.getFrameTextures());
		
		for(Slot recipe : slots) {
			recipe.render();
		}
	}
	
	
	public void updatePositions() {
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
	
	public void updateRecipes() {	
		for(Slot recipe : slots) {
			 Item item = recipe.getItem();
			 
			 boolean craft = true;
			 for(int i = 0, n = item.ingredients.length; i < n; i++) {
				 int count = GUIManager.inventory.hasItem(ItemDatabase.getItem(item.ingredients[i]));
				 if(count < item.ingredients[++i]) {
					 craft = false;
					 break;
				 }
			 }
			 
			 recipe.setActive(craft);
		}
	}
	
}
