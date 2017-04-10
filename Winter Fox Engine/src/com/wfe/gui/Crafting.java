package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.font.GUIText;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Color;
import com.wfe.utils.Rect;

public class Crafting {
	
	private Color background = new Color(0.1f, 0.1f, 0.1f, 0.9f);
	
	private Rect craftingRect;
	
	private boolean open = false;
	
	private List<Recipe> recipes = new ArrayList<Recipe>();
	
	private Item activeItem;
	private GUIText activeItemName;
	private GUIText activeItemDesc;
	private GUITexture closeButton;
	private GUITexture craftButton;
	private GUIText craftButtonText;
	
	private List<Ingredient> ingredients = new ArrayList<Ingredient>();
	
	public Crafting() {
		craftingRect = new Rect(0, 0, 385, 275);
		
		for(Item item : ItemDatabase.getDatabase()) {
			if(item.isHasCraft()) {
				recipes.add(new Recipe(new Rect(0, 0, 190, 30), item));
			}
		}
		
		activeItemName = new GUIText("", FontRenderer.ARIAL);
		activeItemDesc = new GUIText("", FontRenderer.ARIAL);
		activeItemDesc.setScale(0.8f);
		
		closeButton = new GUITexture(ResourceManager.getTexture("cross_ui"), new Rect(0, 0, 25, 25), false);
		craftButton = new GUITexture(new Color(0.3f, 0.3f, 0.3f, 1.0f), new Rect(0, 0, 70, 25), false);
		craftButtonText = new GUIText("Craft", FontRenderer.ARIAL);
		craftButtonText.setScale(0.9f);
		
		for(int i = 0; i < 5; i++) {
			ingredients.add(new Ingredient(new Rect(0, 0, 30, 30)));
		}
		
		updatePositions();
	}
	
	public void update() {
		if(Display.isResized()) {
			updatePositions();
		}
		
		if(Keyboard.isKeyDown(Key.KEY_E)) {
			open = !open;	
			
			if(open) {
				if(activeItem != null) {
					updateRecipe();
				}
			}
		}
		
		if(open) {
			if(activeItem == null) {
				if(Mouse.isButtonDown(0)) {
					 for(Recipe recipe : recipes) {
						 if(recipe.rect.isMouseOvered()) {
							 activeItem = recipe.getItem();
							 updateRecipe();
						 }
					 }
				}
			} else {
				if(Mouse.isButtonDown(0)) {
					if(closeButton.rect.isMouseOvered()) {
						activeItem = null;
					} else if(craftButton.rect.isMouseOvered()) {
						boolean craft = true;
						for(Ingredient ing : ingredients) {
							if(!ing.isCompleted()) {
								craft = false;
							}
						}
						
						if(craft) {
							GUIManager.inventory.addItem(activeItem, 1);
							for(int i = 0; i < activeItem.ingredients.length; i+=2) {
								GUIManager.inventory.removeItem(ItemDatabase.getItem(activeItem.ingredients[i]), 
										activeItem.ingredients[i + 1]);
							}
							updateRecipe();
						}
					}
				}
			}
		}
	}
	
	public void updateRecipe() {
		 activeItemName.setText(activeItem.name);
		 activeItemDesc.setText(activeItem.description);
		 for(Ingredient ingredient : ingredients) {
			 ingredient.setItem(ItemDatabase.getItem(Item.NULL), 0, 0);
		 }
		 int count = 0;
		 for(int i = 0; i < activeItem.ingredients.length; i+=2) {
			 ingredients.get(count).setItem(
					 ItemDatabase.getItem(activeItem.ingredients[i]), 
					 activeItem.ingredients[i + 1], 
					 GUIManager.inventory.hasItem(ItemDatabase.getItem(activeItem.ingredients[i])));
			 count++;
		 }
	}
	
	public void render() {
		if(open) {
			GUIRenderer.render(background, craftingRect.x - 5, craftingRect.y - 5, 0, 
					craftingRect.width + 10, craftingRect.height + 10, false);
			
			if(activeItem == null) {				
				for(Recipe recipe : recipes) {
					recipe.render();
				}
			} else {
				GUIRenderer.render(activeItem.icon, Color.WHITE, craftingRect.x, craftingRect.y, 
						0, 80, 80, false);
				closeButton.rect.setPosition(craftingRect.x + craftingRect.width - closeButton.rect.width, 
						craftingRect.y);
				GUIRenderer.render(closeButton);
				craftButton.rect.setPosition(craftingRect.x + craftingRect.width - craftButton.rect.width, 
						craftingRect.y + craftingRect.height - craftButton.rect.height);
				GUIRenderer.render(craftButton);
				
				for(Ingredient ingredient : ingredients) {
					ingredient.render();
				}
			}
		}
	}
	
	public void renderText() {
		if(open) {
			if(activeItem == null) {
				for(Recipe recipe : recipes) {
					recipe.renderText();
				}
			} else {
				activeItemName.setPosition(craftingRect.x + 85, craftingRect.y);
				FontRenderer.render(activeItemName);
				activeItemDesc.setPosition(craftingRect.x + 85, craftingRect.y + activeItemName.getHeight() + 5);
				FontRenderer.render(activeItemDesc);
				craftButtonText.setPosition(craftButton.rect.x + craftButton.rect.width / 2 - craftButtonText.getWidth() / 2, 
						craftButton.rect.y);
				FontRenderer.render(craftButtonText);
				
				for(Ingredient ingredient : ingredients) {
					ingredient.renderText();
				}
			}
		}
	}
	
	private void updatePositions() {
		craftingRect.x = Display.getWidth() / 2 - craftingRect.width / 2;
		craftingRect.y = GUIManager.inventory.inventoryRect.y - craftingRect.height - Slot.SIZE / 2;
		
		int countX = 0;
		int countY = 0;
		for(Recipe recipe : recipes) {
			recipe.rect.setPosition(craftingRect.x + (countX * (recipe.rect.width + 5)), 
					craftingRect.y + (countY * (recipe.rect.height + 5)));
			
			countX++;
			if(countX == 2) {
				countX = 0;
				countY++;
			}
		}
		
		countY = 0;
		for(Ingredient ingredient : ingredients) {
			ingredient.rect.setPosition(craftingRect.x, 
					craftingRect.y + 85 + (countY * (ingredient.rect.height + 5)));
			countY++;
		}
	}
	
	static class Recipe {
		
		private static Color BACKGROUND = new Color(0.3f, 0.3f, 0.3f, 0.9f);
		
		public Rect rect;
		private Item item;
		
		private GUIText text;
		
		public Recipe(Rect rect, Item item) {
			this.rect = rect;
			this.item = item;
			
			this.text = new GUIText(item.name, FontRenderer.ARIAL);
		}
		
		public void render() {
			GUIRenderer.render(BACKGROUND, rect.x, rect.y, 0, rect.width, rect.height, false);
			GUIRenderer.render(item.icon, Color.WHITE, rect.x + 5, rect.y, 0, rect.height, rect.height, false);
		}
		
		public void renderText() {
			text.setPosition(rect.x + 10 + rect.height, rect.y);
			FontRenderer.render(text);
		}
		
		public Item getItem() {
			return item;
		}
		
	}

	static class Ingredient {
		
		public Rect rect;
		private Item item;
		
		private GUIText name;
		private GUIText count;
		
		private boolean completed = false;
		
		public Ingredient(Rect rect) {
			this.rect = rect;
			this.name = new GUIText("", FontRenderer.ARIAL);
			this.name.setScale(0.8f);
			this.count = new GUIText("", FontRenderer.ARIAL);
			this.count.setScale(0.8f);
		}
		
		public void render() {
			if(item.id != Item.NULL)
				GUIRenderer.render(item.icon, Color.WHITE, rect.x, rect.y, 0, rect.height, rect.width, false);
		}
		
		public void renderText() {
			if(item.id != Item.NULL) {
				name.setPosition(rect.x + rect.height + 5, rect.y);
				FontRenderer.render(name);
				count.setPosition(name.getX() + name.getWidth() + 15, name.getY());
				FontRenderer.render(count);
			}
		}
		
		public void setItem(Item item, int need, int have) {
			this.item = item;
			this.name.setText(item.name);
			if(have < need) {
				this.name.getColor().set(1.0f, 0.2f, 0.2f);
				completed = false;
			} else {
				this.name.getColor().set(0.2f, 1.0f, 0.2f);
				completed = true;
			}
			this.count.setText(need + " / " + have);
		}
		
		public boolean isCompleted() {
			return completed;
		}
		
	}
	
}
