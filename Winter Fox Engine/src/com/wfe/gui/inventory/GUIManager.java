package com.wfe.gui.inventory;

import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.gui.GUIElement;
import com.wfe.gui.GUIText;
import com.wfe.gui.GUITexture;
import com.wfe.gui.Item;
import com.wfe.gui.ItemDatabase;
import com.wfe.gui.ProgressBar;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.utils.Color;
import com.wfe.utils.Rect;

public class GUIManager implements GUIElement {

	private static GUIManager guiManager;
	
	public GUITexture inventoryButton;
	public GUITexture craftingButton;
	
	
	public Inventory inventory;
	public Equipment equipment;
	public Crafting crafting;
	public PlayerStatus status;
	
	public static Item draggedItem;
	private GUIText draggedItemAmountText;
	private int draggedItemAmount;
	
	public static ProgressBar progressBar;
	public static boolean showProgressBar = false;
	
	private GUIManager() {	
		ItemDatabase.create();
		
		inventoryButton = new GUITexture(ResourceManager.getTexture("sack_ui"));
		inventoryButton.setScale(55, 55);
		
		craftingButton = new GUITexture(ResourceManager.getTexture("craft_ui"));
		craftingButton.setScale(55, 55);
		
		updatePositions();
		
		inventory = new Inventory();
		equipment = new Equipment();
		crafting = new Crafting();
		status = new PlayerStatus();
		
		draggedItemAmountText = new GUIText("", 1.3f, FontRenderer.font, 0, 0, 1f, false);
		draggedItemAmountText.setColor(1.0f, 1.0f, 1.0f);
		
		progressBar = new ProgressBar(new Rect(0, 0, 100, 10), new Color(86, 198, 46, 255).convert());
		progressBar.setCurrentValue(0);
		System.out.println(progressBar.rect.width);
		
		inventory.addItem(ItemDatabase.getItem(Item.APPLE), 5);
		inventory.addItem(ItemDatabase.getItem(Item.LOG), 105);
	}
	
	public void update(float dt) {
		Mouse.setActiveInGUI(false);
		
		if(Mouse.isButtonDown(0)) {
			if(inventoryButton.isMouseOvered()) {
				Mouse.setActiveInGUI(true);
				inventory.showInventory = !inventory.showInventory;
			}
			
			if(craftingButton.isMouseOvered()) {
				Mouse.setActiveInGUI(true);
				crafting.showCrafting = !crafting.showCrafting;
				crafting.updateRecipe(false);
			}
		}
		
		if(Display.isResized()) {
			updatePositions();
		}
		
		inventory.update();
		equipment.update();
		crafting.update();
		status.update(dt);
		
		if(showProgressBar)
			progressBar.rect.setPosition(Mouse.getX(), Mouse.getY());
	}
	
	public void render() {
		GUIRenderer.render(inventoryButton);
		GUIRenderer.render(craftingButton);
		
		inventory.render();
		equipment.render();
		crafting.render();
		status.render();
		
		if(showProgressBar)
			progressBar.render();
		
		if(draggedItem != null) {
			GUIRenderer.render(draggedItem.icon, Mouse.getX() - 25, Mouse.getY() - 25, 0, 55, 55, false);
		}
	}
	
	public void renderText() {
		inventory.renderText();
		crafting.renderText();
		
		if(draggedItem != null) {
			if(draggedItemAmount > 1){
				draggedItemAmountText.setPosition(Mouse.getX() - 25, Mouse.getY() - 25);
				FontRenderer.render(draggedItemAmountText);
			}
		}
	}
	
	public static GUIManager getGUI() {
		if(guiManager == null) {
			guiManager = new GUIManager();
		}
		
		return guiManager;
	}
	
	public void setDraggedItemAmount(int amount) {
		this.draggedItemAmount = amount;
		draggedItemAmountText.setText(String.valueOf(draggedItemAmount));
	}
	
	public int getDraggedItemAmount() {
		return draggedItemAmount;
	}
	
	private void updatePositions() {
		inventoryButton.setPosition(Display.getWidth() - inventoryButton.getScaleX(), Display.getHeight() / 3);
		craftingButton.setPosition(Display.getWidth() - inventoryButton.getScaleX(), 
				inventoryButton.getPosY() + inventoryButton.getScaleY() + 5);
	}
	
}
