package com.wfe.gui.inventory;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.game.World;
import com.wfe.gui.GUIButton;
import com.wfe.gui.GUIFrame;
import com.wfe.gui.GUIText;
import com.wfe.gui.GUITexture;
import com.wfe.gui.Item;
import com.wfe.gui.ItemDatabase;
import com.wfe.gui.Slot;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;

public class Crafting {
	
	private GUIFrame frame;
	
	private List<Slot> slots = new ArrayList<Slot>();
	
	private int slotsX = 4;
	private int slotsY = 5;
	
	private int slotSize = 50;
	
	private GUITexture resultIcon;
	private GUIText resultText;
	
	private GUIButton craftButton;
	
	private Item activeItem;
	
	private boolean showCrafting = false;
	
	public Crafting() {		
		frame = new GUIFrame(0, 0, 217 + 10, 270 + 10);
		
		for(int i = 0; i < slotsX * slotsY; i++) {
			slots.add(new Slot(0, 0, slotSize, slotSize, ResourceManager.getTexture("slot_light_ui")));
		}
		
		slots.get(0).addItem(ItemDatabase.getItem(Item.AXE));
		slots.get(1).addItem(ItemDatabase.getItem(Item.HOE));
		slots.get(2).addItem(ItemDatabase.getItem(Item.ROPE));
		
		resultIcon = new GUITexture(ResourceManager.getTexture("apple_ui"));
		resultIcon.setScale(70, 70);
		
		resultText = new GUIText("Hello, World", 1.4f, FontRenderer.font, 0, 0, 1.0f, false);
		resultText.setColor(1.0f, 1.0f, 1.0f);
		
		craftButton = new GUIButton("Craft", 0, 0, 100, 30);
		
		updatePositions();
	}
	
	public void update() {
		if(showCrafting) {
			
			if(Mouse.isButtonDown(0)) {
				for(Slot slot : slots) {
					Mouse.setActiveInGUI(true);
					if(slot.isMouseOvered()) {
						if(slot.isHasItem()) {
							activeItem = slot.getItem();
							resultIcon.setTexture(activeItem.icon);
							resultText.setText(activeItem.name);
						}
					}
				}
				
				if(craftButton.isMouseOvered()) {
					Mouse.setActiveInGUI(true);
					if(checkRecipe(activeItem)) {
						GUIManager.getGUI().inventory.addItem(activeItem, 1);
						
						// В случае если все ингредиенты присутствуют удаляем их из инвентаря
						int[] ingredients = activeItem.ingredients;
						for(int i = 0; i < ingredients.length; i+=2) {
							GUIManager.getGUI().inventory
							.removeItem(ItemDatabase.getItem(ingredients[i]), ingredients[i + 1]);
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
		if(showCrafting) {
			for(Slot slot : slots) {
				slot.render();
			}
			
			GUIRenderer.render(resultIcon);
			
			craftButton.render();
		}
	}
	
	public void renderText() {
		if(showCrafting) {
			for(Slot slot : slots) {
				slot.renderText();
			}
			
			FontRenderer.render(resultText);
			
			craftButton.renderText();
		}
	}
	
	private void updatePositions() {
		float posX = (Display.getWidth() / 2) - (435 / 2) - 10;
		float posY = Display.getHeight() / 3 - 10;
		frame.setPosition(Display.getWidth() / 2, posY);
	
		int count = 0;
		for(int y = 0; y < slotsY; y++) {
			for(int x = 0; x < slotsX; x++) {
				slots.get(count).setPosition(
						posX + (x * (slotSize + 5)) + 5, 
						posY + (y * (slotSize + 5)) + 5);
				count++;
			}
		}
		
		resultIcon.setPosition(frame.getX() + 5, 
				frame.getY() + 5);
		
		resultText.setPosition(
				(1.0f / Display.getWidth()) * (resultIcon.getX() + resultIcon.getScaleX()), 
				(1.0f / Display.getHeight()) * (frame.getY() + 5));
		
		craftButton.setPosition(frame.getX() + frame.getScaleX() - craftButton.getScaleX() - 5, 
				frame.getY() + frame.getScaleY() - craftButton.getScaleY() - 5);
		
	}

	protected boolean isShowCrafting() {
		return showCrafting;
	}

	protected void setShowCrafting(boolean showCrafting) {
		if(this.showCrafting == showCrafting)
			return;
		
		this.showCrafting = showCrafting;
		
		if(this.showCrafting) {
			World.getWorld().addGUITextures(frame.getFrameTextures());
		} else {
			World.getWorld().removeGUITextures(frame.getFrameTextures());
		}
	}
	
	private boolean checkRecipe(Item item) {
		int[] ingredients = item.ingredients;
		boolean craft = true;
		for(int i = 0; i < ingredients.length; i+=2) {
			if(GUIManager.getGUI().inventory.getItemAmount(ingredients[i]) < ingredients[i + 1]) {
				craft = false;
			}
		}
		return craft;
	}
	
}
