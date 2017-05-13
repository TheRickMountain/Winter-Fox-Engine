package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.audio.AudioMaster;
import com.wfe.components.ChestComponent;
import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.font.GUIText;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.textures.Texture;
import com.wfe.utils.Color;
import com.wfe.utils.Rect;

public class GUIManager  {
	
	public enum GUIState {
		CRAFTING,
		CHEST,
		DIALOGUE,
		GAME
	}
	
	public static Inventory inventory;
	public static Crafting crafting;
	public static Chest chest;
	public static PlayerStats stats;
	
	private static boolean showPopUp;
	private static GUIFrame popUp;
	private static GUIText popUpText;
	private static Item popUpItem;
	private static int iconSize;
	private static List<GUITexture> icons = new ArrayList<GUITexture>();
	private static List<GUIText> iconsText = new ArrayList<GUIText>();
	private static float mouseOffsetX;
	private static float mouseOffsetY;
	
	protected static Item draggedItem;
	protected static GUIText draggedItemText;
	protected static int draggedItemCount;
	
	public static boolean showProgressBar;
	public static ProgressBar progressBar;
	
	public static DialogueSystem dialogueSystem;
	
	public static GUIState state = GUIState.GAME;
	
	public static void init() {
		ItemDatabase.create();
		inventory = new Inventory();
		crafting = new Crafting();
		chest = new Chest();
		stats = new PlayerStats();
		
		popUp = new GUIFrame(new Rect(0, 0, 256, 64), true);
		popUpText = new GUIText("", FontRenderer.ARIAL);
		popUpItem = ItemDatabase.getItem(Item.NULL);
		iconSize = 20;
		mouseOffsetX = 30;
		mouseOffsetY = 15;
		
		draggedItem = ItemDatabase.getItem(Item.NULL);
		draggedItemText = new GUIText("", FontRenderer.ARIAL);
		draggedItemText.setScale(0.8f);
		draggedItemCount = 0;
		
		progressBar = new ProgressBar(new Rect(0, 0, 385, 10), new Color(255, 140, 0, 255).convert());
		
		dialogueSystem = new DialogueSystem();
		
		updatePositions();
	}
	
	public static void update() {	
		if(Keyboard.isKeyDown(Key.KEY_F)) {
			inventory.addItem(ItemDatabase.getItem(Item.PICKAXE), 1);
			inventory.addItem(ItemDatabase.getItem(Item.AXE), 1);
			inventory.addItem(ItemDatabase.getItem(Item.CLUB), 1);
			inventory.addItem(ItemDatabase.getItem(Item.HOE), 1);
			inventory.addItem(ItemDatabase.getItem(Item.APPLE), 1);
			inventory.addItem(ItemDatabase.getItem(Item.HONEY), 1);
			inventory.addItem(ItemDatabase.getItem(Item.FLINT), 40);
			inventory.addItem(ItemDatabase.getItem(Item.LOG), 120);
			inventory.addItem(ItemDatabase.getItem(Item.CLAY), 40);
			inventory.addItem(ItemDatabase.getItem(Item.BASKET), 1);
			inventory.addItem(ItemDatabase.getItem(Item.FURNACE), 1);
			inventory.addItem(ItemDatabase.getItem(Item.JUG), 1);
			
			stats.addCowry(2000);
		}
		
		if(Keyboard.isKeyDown(Key.KEY_E)) {
			switch(state) {
			case GAME:
				state = GUIState.CRAFTING;
				crafting.updateRecipes();
				Mouse.setActiveInGUI(true);
				Display.setCursor(Display.defaultCursor);
				
				AudioMaster.defaultSource.play(ResourceManager.getSound("inventory"));
				break;
			case CRAFTING:
			case CHEST:
				state = GUIState.GAME;
				Mouse.setActiveInGUI(false);
				
				AudioMaster.defaultSource.play(ResourceManager.getSound("inventory"));
				break;
			}
		}
		
		inventory.updateHotbar();
		
		switch (state) {
		case CRAFTING:
			crafting.update();
			inventory.update();
			break;
		case CHEST:
			chest.update();
			inventory.update();
			break;
		case DIALOGUE:
			dialogueSystem.update();
			break;
		}
		
		if(showPopUp) {
			if(Mouse.getY() + mouseOffsetY + popUp.rect.height <= Display.getHeight()) {
				popUp.setPosition(Mouse.getX() + mouseOffsetX, Mouse.getY() + mouseOffsetY);
			} else {
				popUp.setPosition(Mouse.getX() + mouseOffsetX, 
						Display.getHeight() - popUp.rect.height);
			}
			
			popUpText.setPosition(popUp.getX(), popUp.getY());
			
			for(int i = 0, n = icons.size(); i < n; i++) {
				GUITexture icon = icons.get(i);
				icon.rect.setPosition(popUp.getX(), 
						popUpText.getY() + popUpText.getHeight() + (i * (iconSize + 5)) + 5);
				
				GUIText text = iconsText.get(i);
				text.setPosition(icon.rect.getX() + icon.rect.getWidth() + 5, icon.rect.getY());
			}
		}
		
		stats.update();
		
		if(Display.isResized()) {
			updatePositions();
		}
	}
	
	public static void render() {
		inventory.renderHotbar();
		
		switch (state) {
		case CRAFTING:
			inventory.render();
			crafting.render();
			break;
		case CHEST:
			inventory.render();
			chest.render();
			break;
		case DIALOGUE:
			dialogueSystem.render();
			break;
		}
		
		stats.render();
		
		if(showProgressBar)
			progressBar.render();
	}
	
	public static void renderText() {
		inventory.renderHotbarText();
		
		switch (state) {
		case CRAFTING:
			inventory.renderText();
			break;
		case CHEST:
			inventory.renderText();
			chest.renderText();
			break;
		case DIALOGUE:
			dialogueSystem.renderText();
			break;
		}
		
		stats.renderText();
	}
	
	public static void renderPopUp() {
		if(draggedItem.id != Item.NULL) {
			GUIRenderer.render(draggedItem.icon, Color.WHITE, 
					Mouse.getX() - Slot.SIZE / 2, Mouse.getY() - Slot.SIZE / 2, 
					0, Slot.SIZE, Slot.SIZE, false);
		}
		
		if(showPopUp) {
			GUIRenderer.render(popUp.getFrameTextures());
			
			for(GUITexture icon : icons) {
				GUIRenderer.render(icon);
			}
		}
	}
	
	public static void renderPopUpText() {
		if(draggedItem.id != Item.NULL) {
			draggedItemText.setPosition(
					Mouse.getX() - Slot.SIZE / 2 + 4f, 
					Mouse.getY() - Slot.SIZE / 2 + 3f);
			FontRenderer.render(draggedItemText);
		}
		
		if(showPopUp) {
			FontRenderer.render(popUpText);
			
			for(GUIText text : iconsText) {
				FontRenderer.render(text);
			}
		}
		
		showPopUp = false;
	}
	
	private static void updatePositions() {
		inventory.updatePositions();
		crafting.updatePositions();
		chest.updatePositions();
		
		progressBar.rect.setPosition(Display.getWidth() / 2 - progressBar.rect.width / 2, 
				inventory.hotbarFrame.rect.y - progressBar.rect.height - 5);
	}
	
	protected static void setDraggedItem(Item item, int count) {
		draggedItem = item;
		draggedItemCount = count;
		if(count > 1)
			draggedItemText.setText("" + count);
		else
			draggedItemText.setText("");
	}
	
	public static void openChest(ChestComponent chestComponent) {
		chest.open(chestComponent);
		state = GUIState.CHEST;
	}
	
	private static boolean inv = false;
	private static boolean crf = false;
	
	public static void showPopUpInfo(Item item) {
		if(!inv) {
			crf = false;
			inv = true;
			
			popUpItem = ItemDatabase.getItem(Item.NULL);
		}
		
		if(popUpItem.id != item.id) {
			popUpItem = item;
			
			popUpText.setText(item.name);
			
			icons.clear();
			iconsText.clear();
			
			float height = 20 + popUpText.getHeight();
			float width = 20 + popUpText.getWidth();
			
			int count = 0;
			
			if(item.health != 0) {
				height += iconSize + 5;
				createInfoLine(ResourceManager.getTexture("health_icon_ui"), item.health, "Health", false);
				
				float tmpWidth = 20 + iconSize + 5 + iconsText.get(count).getWidth();
				if(tmpWidth > width) {
					width = tmpWidth;
				}
				
				count++;
			}
			
			if(item.hunger != 0) {
				height += iconSize + 5;
				createInfoLine(ResourceManager.getTexture("hunger_icon_ui"), item.hunger, "Hunger", false);
				
				float tmpWidth = 20 + iconSize + 5 + iconsText.get(count).getWidth();
				if(tmpWidth > width) {
					width = tmpWidth;
				}
				
				count++;
			}
			
			if(item.thirst != 0) {
				height += iconSize + 5;
				createInfoLine(ResourceManager.getTexture("thirst_icon_ui"), item.thirst, "Thirst", false);
				
				float tmpWidth = 20 + iconSize + 5 + iconsText.get(count).getWidth();
				if(tmpWidth > width) {
					width = tmpWidth;
				}
			}
			
			popUp.setSize(width, height);
		}
		
		showPopUp = true;
	}
	
	public static void showPopUpCraftInfo(Item item) {
		if(!crf) {
			crf = true;
			inv = false;
			
			popUpItem = ItemDatabase.getItem(Item.NULL);
		}
		
		if(popUpItem.id != item.id) {
			popUpItem = item;
			
			popUpText.setText(item.name);
			
			icons.clear();
			iconsText.clear();
			
			float height = 20 + popUpText.getHeight();
			float width = 20 + popUpText.getWidth();
			
			
			int[] ingredients = item.ingredients;
			int count = 0;
			for(int i = 0, n = ingredients.length; i < n; i += 2) {
				Item tmp = ItemDatabase.getItem(ingredients[i]);
				createInfoLine(tmp.icon, ingredients[i + 1], tmp.name, true);
				height += iconSize + 5;
				
				float tmpWidth = 20 + iconSize + 5 + iconsText.get(count).getWidth();
				if(tmpWidth > width) {
					width = tmpWidth;
				}
				
				count++;
			}
			
			popUp.setSize(width, height);
		}
		
		showPopUp = true;
	}
	
	private static void createInfoLine(Texture texture, int value, String name, boolean craft) {
		icons.add(new GUITexture(texture, new Rect(0, 0, iconSize, iconSize), false));
		
		GUIText text;
		if(craft) {
			text = new GUIText(String.valueOf(value) + " " + name, FontRenderer.ARIAL);
			text.setScale(0.8f);
			iconsText.add(text);
			
			text.getColor().set(1.0f, 1.0f, 1.0f);
			
		} else {
			text = new GUIText(((value > 0) ? "+" : "") + 
					String.valueOf(value) + " " + name, FontRenderer.ARIAL);
			text.setScale(0.8f);
			iconsText.add(text);
		
			if(value > 0) {
				text.getColor().set(0.1f, 1.0f, 0.1f);
			} else {
				text.getColor().set(1.0f, 0.1f, 0.1f);
			}
		}
	}
	
}
