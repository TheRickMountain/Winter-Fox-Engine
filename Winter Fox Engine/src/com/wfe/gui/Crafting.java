package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.components.InventoryComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.ComponentType;
import com.wfe.game.Game;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Rect;

public class Crafting implements GUIComponent {
	
	private List<Slot> slots = new ArrayList<Slot>();
	private float offset = 5;
	
	private Item selectedItem;
	//private GUIText name;
	//private GUIText description;
	
	private List<Ingredient> ingredients = new ArrayList<Ingredient>();
	
	private Button craftButton;
	
	public Crafting() {	
		for(int i = 0; i < 20; i++) {
			slots.add(new Slot(new Rect(0, 0, Slot.SLOT_SIZE, Slot.SLOT_SIZE)));
		}
		
		//name = new GUIText("", 1.2f, FontRenderer.font, 0.0f, 0.0f, GUIManager.inspectFrame.rect.width, true);
		//name.setColor(1.0f, 1.0f, 1.0f);
		//description = new GUIText("", 1.0f, FontRenderer.font, 0.0f, 0.0f, 
				//GUIManager.inspectFrame.rect.width - 20 - 80, false);
		//description.setColor(0.9f, 0.9f, 0.9f);
		
		for(int i = 0; i < 5; i++) {
			ingredients.add(new Ingredient(new Rect(0, 0, 30, 30)));
		}
		
		craftButton = new Button(new Rect(0, 0, 100, 35), ResourceManager.getTexture("slot_ui"), "Craft");
		
		updatePositions();
		checkAllRecipes();
	}
	
	@Override
	public void update() {
		if(Mouse.isButtonDown(0)) {
			for(Slot slot : slots) {
				if(slot.rect.isMouseOvered()) {
					if(slot.isHasItem()) {
						selectedItem = slot.getItem();
						//name.setText(selectedItem.name);
						//description.setText(selectedItem.description);
						
						updateIngredients();
					}
				}
			}
			
			if(craftButton.rect.isMouseOvered()) {
				if(selectedItem != null) {
					boolean canBeCrafted = true;
					
					for(Ingredient ing : ingredients) {
						if(!ing.isCompleted()) {
							canBeCrafted = false;
						}
					}
					
					if(canBeCrafted) {
						InventoryComponent inv = (InventoryComponent) Game.player.getComponent(ComponentType.INVENTORY);
						inv.addItem(selectedItem.id, 1);
						
						// Removes required ingredients from inventory
						for(int i = 0; i < selectedItem.ingredients.length; i+=2) {
							inv.removeItem(selectedItem.ingredients[i], selectedItem.ingredients[i + 1]);
						}
						
						updateIngredients();
					}
				}
			}
		}
	}
	
	public void updateIngredients() {
		if(selectedItem != null) {
			for(Ingredient ing : ingredients) {
				ing.setItem(null, 0, 0);
			}
			
			InventoryComponent inv = (InventoryComponent) Game.player.getComponent(ComponentType.INVENTORY);
			int count = 0;
			for(int i = 0; i < selectedItem.ingredients.length; i+=2) {
				ingredients.get(count).setItem(
						ItemDatabase.getItem(selectedItem.ingredients[i]), 
						inv.hasItemCount(selectedItem.ingredients[i]), 
						selectedItem.ingredients[i + 1]);
				count++;
			}
		}
	}

	@Override
	public void render() {
		for(Slot slot : slots) {
			slot.render();
		}
		
		if(selectedItem != null) {
			GUIRenderer.render(selectedItem.icon, 
					GUIManager.inspectFrame.getX(), 
					GUIManager.inspectFrame.getY(), 0, 
					80, 80, false);
		
			for(Ingredient ing : ingredients) {
				ing.render();
			}
			
			craftButton.render();
		}
	}

	@Override
	public void renderText() {
		if(selectedItem != null) {
			for(Ingredient ing : ingredients) {
				ing.renderText();
			}
			
			/*FontRenderer.render(name, 
					GUIManager.inspectFrame.getX(), 
					GUIManager.inspectFrame.getY() + offset);
			
			FontRenderer.render(description, GUIManager.inspectFrame.getX() + 80 + offset, 
					GUIManager.inspectFrame.getY() + offset + 20);*/
			
			craftButton.renderText();
		}
	}

	@Override
	public void renderPopUp() {
		
	}

	@Override
	public void renderPopUpText() {
		
	}
	
	private void checkAllRecipes() {
		int count = 0;
		List<Item> items = ItemDatabase.getDatabase();
		for(int i = 0; i < items.size(); i++) {
			if(items.get(i).isHasCraft()) {
				this.slots.get(count).addItem(items.get(i), 1);
				count++;
			}
		}
	}
	
	public void updatePositions() {
		int countX = 0;
		int countY = 0;
		for(int i = 0; i < slots.size(); i++) {
			slots.get(i).rect.setPosition(
					(GUIManager.mainFrame.getX() + offset) + (countX * (Slot.SLOT_SIZE + offset)), 
					(GUIManager.mainFrame.getY() + offset) + (countY * (Slot.SLOT_SIZE + offset)));
			
			countX++;
			if(countX == 4) {
				countX = 0;
				countY++;
			}
		}
		
		for(int i = 0; i < 5; i++) {
			Ingredient ing = ingredients.get(i);
			ing.rect.x = (GUIManager.inspectFrame.getX() + offset);
			ing.rect.y = GUIManager.inspectFrame.getY() + 80 + offset + (i * ing.rect.height);
		}
		
		craftButton.rect.setPosition(
				GUIManager.inspectFrame.rect.x + 
				GUIManager.inspectFrame.rect.width - 
				craftButton.rect.width - 10, 
				GUIManager.inspectFrame.rect.y + 
				GUIManager.inspectFrame.rect.height - 
				craftButton.rect.height - 10);
	}

}
