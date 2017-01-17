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
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;

public class Crafting {
	
	private GUIFrame frame;
	
	private List<Slot> slots = new ArrayList<Slot>();
	
	private int slotsX = 5;
	private int slotsY = 7;
	
	private int slotSize = 50;
	private int ingredientSize = 50;
	
	private GUITexture resultIcon;
	private GUIText resultText;
	
	private GUIButton craftButton;
	
	private Item activeItem;
	
	private boolean showCrafting = false;
	
	private List<Slot> ingredients = new ArrayList<Slot>();
	private List<GUIText> ingredientsAmount = new ArrayList<GUIText>();
	
	private GUIButton lessButton, moreButton;
	private GUIText amountText;
	private int amount = 1;
	
	public Crafting() {		
		frame = new GUIFrame(0, 0, 545 + 10, 380 + 10);
		
		for(int i = 0; i < slotsX * slotsY; i++) {
			slots.add(new Slot(0, 0, slotSize, slotSize, ResourceManager.getTexture("slot_ui")));
			slots.get(i).showBackground = false;
		}
		
		slots.get(0).addItem(ItemDatabase.getItem(Item.AXE));
		slots.get(1).addItem(ItemDatabase.getItem(Item.HOE));
		slots.get(2).addItem(ItemDatabase.getItem(Item.ROPE));
		slots.get(3).addItem(ItemDatabase.getItem(Item.BREAD));
		
		resultIcon = new GUITexture(ResourceManager.getTexture("slot_ui"));
		resultIcon.setScale(80, 80);
		
		resultText = new GUIText("", 1.4f, FontRenderer.font, 0, 0, 1.0f, false);
		
		for(int i = 0; i < 5; i++) {
			ingredients.add(new Slot(0, 0, ingredientSize, ingredientSize, ResourceManager.getTexture("slot_ui")));
			ingredients.get(i).showBackground = false;
			ingredientsAmount.add(new GUIText("",
					1.1f, FontRenderer.font, 0.0f, 0.0f, 1.0f, false).setColor(1.0f, 1.0f, 1.0f));
		}
		
		craftButton = new GUIButton(ResourceManager.getTexture("list_ui"), "Craft", 0, 0, 100, 30);
		
		lessButton = new GUIButton(ResourceManager.getTexture("list_ui"), "<", 0, 0, 25, 25);
		moreButton = new GUIButton(ResourceManager.getTexture("list_ui"), ">", 0, 0, 25, 25);
		amountText = new GUIText(amount + "", 1.2f, FontRenderer.font, 0.0f, 0.0f, 1.0f, false);
		
		updatePositions();
	}
	
	public void update() {
		if(showCrafting) {
			if(Keyboard.isKeyDown(Key.KEY_E)) {
				setShowCrafting(false);
			}
			
			if(Mouse.isButtonDown(0)) {
				for(Slot slot : slots) {
					Mouse.setActiveInGUI(true);
					if(slot.isMouseOvered()) {
						if(slot.isHasItem()) {
							activeItem = slot.getItem();
							updateRecipe();
						}
						
						checkAllRecipes();
					}
				}
				
				if(craftButton.isMouseOvered()) {
					Mouse.setActiveInGUI(true);
					if(checkRecipe(activeItem)) {
						GUIManager.getGUI().inventory.addItem(activeItem, amount);
						
						// В случае если все ингредиенты присутствуют удаляем их из инвентаря
						int[] ingredients = activeItem.ingredients;
						for(int i = 0; i < ingredients.length; i+=2) {
							GUIManager.getGUI().inventory
							.removeItem(ItemDatabase.getItem(ingredients[i]), ingredients[i + 1] * amount);
						}
					}
					
					checkAllRecipes();
				}
				
				if(lessButton.isMouseOvered()) {
					if(amount != 1) {
						amount--;
						amountText.setText(amount + "");
					}
					checkAllRecipes();
				} else if(moreButton.isMouseOvered()) {
					if(amount != 50) {
						amount++;
						amountText.setText(amount + "");
					}
					checkAllRecipes();
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
			
			for(Slot slot : ingredients) {
				slot.render();
			}
			
			craftButton.render();
			
			lessButton.render();
			moreButton.render();
		}
	}
	
	public void renderText() {
		if(showCrafting) {
			for(Slot slot : slots) {
				slot.renderText();
			}
			
			FontRenderer.render(resultText);
			
			craftButton.renderText();
		
			for(GUIText text : ingredientsAmount) {
				FontRenderer.render(text);
			}
			
			lessButton.renderText();
			FontRenderer.render(amountText);
			moreButton.renderText();
		}
	}
	
	private void updatePositions() {
		float posX = (Display.getWidth() / 2) - (545 / 2) - 10;
		float posY = Display.getHeight() / 4 - 10;
		frame.setPosition(posX, posY);
	
		int count = 0;
		for(int y = 0; y < slotsY; y++) {
			for(int x = 0; x < slotsX; x++) {
				slots.get(count).setPosition(
						posX + (x * (slotSize + 5)) + 5, 
						posY + (y * (slotSize + 5)) + 5);
				count++;
			}
		}
		
		resultIcon.setPosition(frame.getX() + (frame.getScaleX() / 2) + 5, frame.getY() + 5);
		
		lessButton.setPosition(resultIcon.getX(), resultIcon.getY() + resultIcon.getScaleY() + 10);
		amountText.setPosition(
				(1.0f / Display.getWidth()) * (lessButton.getPosX() + lessButton.getScaleX()), 
				(1.0f / Display.getHeight()) * lessButton.getPosY());
		moreButton.setPosition(lessButton.getPosX() + lessButton.getScaleX() + 28, lessButton.getPosY());
		
		resultText.setPosition(
				(1.0f / Display.getWidth()) * (resultIcon.getX() + resultIcon.getScaleX()), 
				(1.0f / Display.getHeight()) * (frame.getY() + 5));
		
		for(int i = 0; i < ingredients.size(); i++) {
			Slot slot = ingredients.get(i);
			slot.setPosition(resultIcon.getX() + (i * (ingredientSize + 5)), 
					lessButton.getPosY() + lessButton.getScaleY() + 10);
			
			ingredientsAmount.get(i).setPosition((1.0f / Display.getWidth()) * (slot.getX() + slot.getScaleX() / 3), 
					(1.0f / Display.getHeight()) * (slot.getY() + slot.getScaleY()));
		}
		
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
			if(activeItem == null) {
				activeItem = slots.get(0).getItem();
			}
			
			World.getWorld().addGUITextures(frame.getFrameTextures());
			checkAllRecipes();
		} else {
			World.getWorld().removeGUITextures(frame.getFrameTextures());
		}
	}
	
	private void updateRecipe() {
		if(activeItem != null) {
			resultIcon.setTexture(activeItem.icon);
			resultText.setText(activeItem.name);
			
			int count = 0;
			for(Slot i : ingredients)
				i.removeItem();
			
			for(GUIText t : ingredientsAmount) {
				t.setText("");
			}
			
			for(int i = 0; i < activeItem.ingredients.length; i += 2) {
				ingredients.get(count).addItem(ItemDatabase.getItem(activeItem.ingredients[i]));
				
				int currentItemsAmount = GUIManager.getGUI().inventory.getItemAmount(activeItem.ingredients[i]);
				int requiredItemsAmount = activeItem.ingredients[i + 1] * amount;
				if(currentItemsAmount < requiredItemsAmount) {
					ingredientsAmount.get(count).setColor(1.0f, 0.0f, 0.0f);
				} else {
					ingredientsAmount.get(count).setColor(0.0f, 0.6f, 0.0f);
				}
				
				ingredientsAmount.get(count)
					.setText(currentItemsAmount + "/" + requiredItemsAmount);
				count++;
			}
		}
	}
	
	private void checkAllRecipes() {
		updateRecipe();
		
		for(Slot slot : slots) {
			if(slot.isHasItem()) {
				slot.setActive(checkRecipe(slot.getItem()));
			}
		}
	}
	
	private boolean checkRecipe(Item item) {
		int[] ingredients = item.ingredients;
		boolean craft = true;
		for(int i = 0; i < ingredients.length; i+=2) {
			if(GUIManager.getGUI().inventory.getItemAmount(ingredients[i]) < ingredients[i + 1] * amount) {
				craft = false;
			}
		}
		return craft;
	}
	
}
