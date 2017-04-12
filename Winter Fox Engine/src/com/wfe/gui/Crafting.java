package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Rect;

public class Crafting {
	
	private GUIFrame background;

	private boolean open = false;
	
	private List<Slot> slots = new ArrayList<>();
	
	public Crafting() {
		background = new GUIFrame(new Rect(0, 0, 405, 275));
		
		for(Item item : ItemDatabase.getDatabase()) {
			if(item.isHasCraft()) {
				Slot slot = new Slot(new Rect(0, 0, 60, 60));
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
		
		if(Keyboard.isKeyDown(Key.KEY_E)) {
			open = !open;	
		}
		
		if(open) {
			if(Mouse.isButtonDown(0)) {
				 for(Slot recipe : slots) {
					 if(recipe.rect.isMouseOvered()) {
						 GUIManager.inventory.addItem(recipe.getItem(), 1);
					 }
				 }
			}
		}
	}
	
	public void render() {
		if(open) {
			GUIRenderer.render(background.getFrameTextures());
			
			for(Slot recipe : slots) {
				recipe.render();
			}
		
		}
	}
	
	
	private void updatePositions() {
		background.setPosition(
				Display.getWidth() / 2 - background.getWidth() / 2, 
				GUIManager.inventory.inventoryFrame.rect.y - background.getHeight() - Slot.SIZE / 2);
		
		int countX = 0;
		int countY = 0;
		for(Slot recipe : slots) {
			recipe.rect.setPosition(background.getX() + (countX * (recipe.rect.width + 5)), 
					background.getY() + (countY * (recipe.rect.height + 5)));
			
			countX++;
			if(countX == 6) {
				countX = 0;
				countY++;
			}
		}
	}
	
}
