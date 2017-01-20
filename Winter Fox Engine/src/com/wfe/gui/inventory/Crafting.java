package com.wfe.gui.inventory;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.gui.GUIButton;
import com.wfe.gui.GUIElement;
import com.wfe.gui.GUIText;
import com.wfe.gui.GUITexture;
import com.wfe.gui.Item;
import com.wfe.gui.ItemDatabase;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Color;
import com.wfe.utils.Rect;

public class Crafting implements GUIElement {

	private Color backgroundColor = new Color(131, 128, 126, 150).convert();
	
	private GUITexture background;
	
	/*** Information table ***/
	private GUITexture infoBackground;
	private GUITexture infoIcon;
	private GUIText infoName;
	private GUIText infoDesc;
	private Item activeItem;
	/*** *** ***/
	
	private List<Ingredient> ingredients = new ArrayList<Ingredient>();
	
	private GUIButton craftButton;
	
	private float borderOffset = 5;
	private float elementOffset = 5;
	
	private List<Element> list = new ArrayList<Element>();
	
	public boolean showCrafting = false;
	
	public Crafting() {
		background = new GUITexture(new Color(50f, 50f, 50f, 230).convert(), 0, 0, 
				545 + (borderOffset * 2), 325 + (borderOffset * 2), false);
		
		infoBackground = new GUITexture(backgroundColor,
				0, 0, 270, 160, false);
		infoIcon = new GUITexture(ResourceManager.getTexture("flint_ui"), 0, 0, 70, 70, false);
		infoName = new GUIText("Flint", 1.4f, FontRenderer.font, 0.0f, 0.0f, 1.0f, false)
				.setColor(1.0f, 1.0f, 1.0f);
		infoDesc = new GUIText("Description text", 1.0f, FontRenderer.font, 0.0f, 0.0f, 
				(1.0f / Display.getWidth()) * 185, false)
				.setColor(1.0f, 1.0f, 1.0f);
		
		ingredients.add(new Ingredient(new Rect(0, 0, 50, 65), backgroundColor, ItemDatabase.getItem(Item.FLINT)));
		ingredients.add(new Ingredient(new Rect(0, 0, 50, 65), backgroundColor, ItemDatabase.getItem(Item.FLINT)));
		ingredients.add(new Ingredient(new Rect(0, 0, 50, 65), backgroundColor, ItemDatabase.getItem(Item.FLINT)));
		ingredients.add(new Ingredient(new Rect(0, 0, 50, 65), backgroundColor, ItemDatabase.getItem(Item.FLINT)));
		ingredients.add(new Ingredient(new Rect(0, 0, 50, 65), backgroundColor, ItemDatabase.getItem(Item.FLINT)));
		
		craftButton = new GUIButton(new Rect(0, 0, 80, 25), new Color(96, 148, 205, 255).convert(), "Create");
		
		list.add(new Element(new Rect(0, 0, 270, 50), backgroundColor,
				ItemDatabase.getItem(Item.AXE)));
		list.add(new Element(new Rect(0, 0, 270, 50), backgroundColor,
				ItemDatabase.getItem(Item.BREAD)));
		list.add(new Element(new Rect(0, 0, 270, 50), backgroundColor,
				ItemDatabase.getItem(Item.ROPE)));
		list.add(new Element(new Rect(0, 0, 270, 50), backgroundColor,
				ItemDatabase.getItem(Item.BOW)));
		
		updatePositions();
		
		activeItem = ItemDatabase.getItem(Item.AXE);
	}
	
	public void update() {	
		if(showCrafting) {
			if(Mouse.isButtonDown(0)) {
				for(Element element : list) {
					if(element.isMouseOvered()) {
						activeItem = element.item;
						updateRecipe(false);
						break;
					}
				}
				
				if(craftButton.rect.isMouseOvered()) {
					if(updateRecipe(true)) {
						GUIManager.getGUI().inventory.addItem(activeItem, 1);
						updateRecipe(false);
					}
				}
			}
		}
		
		if(Display.isResized()) {
			updatePositions();
		}
	}
	
	@Override
	public void render() {
		if(showCrafting) {
			GUIRenderer.render(background);
			GUIRenderer.render(infoBackground);
			GUIRenderer.render(infoIcon);
			for(Element element : list)
				element.render();
			for(Ingredient ingredient : ingredients)
				ingredient.render();
			craftButton.render();
		}
	}

	@Override
	public void renderText() {
		if(showCrafting) {
			FontRenderer.render(infoName);
			FontRenderer.render(infoDesc);
			
			for(Element element : list)
				element.renderText();
			for(Ingredient ingredient : ingredients)
				ingredient.renderText();
			craftButton.renderText();
		}
	}
	
	private void updatePositions() {
		background.setPosition(
				(Display.getWidth() / 2) - background.getScaleX() / 2, 
				(Display.getHeight() / 2) - background.getScaleY() / 2);
		
		for(int i = 0; i < list.size(); i++) {
			Element element = list.get(i);
			element.rect.setPosition(
					background.getPosX() + borderOffset, 
					(background.getPosY() + (i * (element.rect.height + elementOffset))) + borderOffset);
		}
		
		infoBackground.setPosition(list.get(0).rect.x + list.get(0).rect.width + 5, 
				list.get(0).rect.y);
		infoIcon.setPosition(infoBackground.getPosX() + borderOffset, infoBackground.getPosY() + borderOffset);
		infoName.setPosition(infoIcon.getPosX() + infoIcon.getScaleX() + borderOffset, 
				infoIcon.getPosY());
		infoDesc.setPosition(infoIcon.getPosX() + infoIcon.getScaleX() + borderOffset, 
				infoIcon.getPosY() + 30);
		
		for(int i = 0; i < ingredients.size(); i++) {
			Ingredient ingred = ingredients.get(i);
			ingred.rect.setPosition(infoBackground.getPosX() + (i * (ingred.rect.width + borderOffset)), 
					infoBackground.getPosY() + infoBackground.getScaleY() + borderOffset);
		}
		
		craftButton.rect.setPosition(
				(background.getPosX() + background.getScaleX() - borderOffset) - craftButton.rect.width, 
				(background.getPosY() + background.getScaleY() - borderOffset) - craftButton.rect.height);
	}
	
	public boolean updateRecipe(boolean remove) {
		infoIcon.setTexture(activeItem.icon);
		infoName.setText(activeItem.name);
		
		for(Ingredient ingredient : ingredients) {
			ingredient.active = false;
		}
		
		int count = 0;
		boolean completed = true;
		for(int i = 0; i < activeItem.ingredients.length; i+=2) {
			Ingredient ingred = ingredients.get(count);
			ingred.active = true;
			ingred.item = ItemDatabase.getItem(activeItem.ingredients[i]);
			ingred.set(GUIManager.getGUI().inventory.getItemAmount(activeItem.ingredients[i]), 
					activeItem.ingredients[i + 1]);
			count++;
			
			if(!ingred.completed){
				completed = false;
			}
		}
		
		if(completed && remove) {
			for(int i = 0; i < activeItem.ingredients.length; i+=2) {
				GUIManager.getGUI().inventory.removeItem(
						ItemDatabase.getItem(activeItem.ingredients[i]), activeItem.ingredients[i + 1]);
			}
		}
		
		return completed;
	}

	
}
