package com.wfe.gui.inventory;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.game.World;
import com.wfe.gui.GUIFrame;
import com.wfe.gui.GUIText;
import com.wfe.gui.GUITexture;
import com.wfe.gui.Item;
import com.wfe.gui.ItemDatabase;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;
import com.wfe.textures.Texture;

public class Crafting {

	private Texture listTexture = ResourceManager.getTexture("list_ui");
	private List<Element> list = new ArrayList<Element>();
	private GUIFrame frame;
	
	private float framePosX = 376;
	private float framePosY = 195;
	
	private float listElementSizeX = 160;
	private float listElementSizeY = 50;
	
	private Item activeItem;
	private GUIText activeItemText;
	
	private GUITexture craftButton;
	
	private boolean showCrafting = false;
	
	public Crafting() {
		frame = new GUIFrame(framePosX, framePosY, 400, 200);
		//World.getWorld().addGUITextures(frame.getFrameTextures());
		
		activeItemText = new GUIText("", 2.1f, FontRenderer.font, 
				(1.0f / Display.getWidth()) * (376 + 90), 
				(1.0f / Display.getHeight()) * (framePosY + 10), 
				1.0f, false);
		activeItemText.setColor(1.0f, 1.0f, 1.0f);
		
		craftButton = new GUITexture(listTexture, framePosX + 290, framePosY + 155, 0, 100, 35, false);
	}
	
	public void update() {
		if(Keyboard.isKeyDown(Key.KEY_TAB)) {
			showCrafting = !showCrafting;
			if(showCrafting) {
				
				int fiber = GUIManager.getGUI().inventory.getItemAmount(Item.FIBER);
				int stick = GUIManager.getGUI().inventory.getItemAmount(Item.STICK);
				int flint = GUIManager.getGUI().inventory.getItemAmount(Item.FLINT);
				if(fiber >= 3 && stick >= 1 && flint >= 1) {
					addElementToList(ItemDatabase.getItem(Item.AXE));
				}
				
				World.getWorld().addGUITextures(frame.getFrameTextures());
			} else {
				World.getWorld().removeGUITextures(frame.getFrameTextures());
			}
		}
		
		if(showCrafting) {
			if(Mouse.isButtonDown(0)) {
				for(Element element : list) {
					if(element.isMouseOvered()) {
						activeItem = element.getItem();
						activeItemText.setText(activeItem.name);
					}
				}
				
				if(craftButton.isMouseOvered()) {
					if(activeItem != null) {
						GUIManager.getGUI().inventory.addItem(activeItem, 1);
					}
				}
			}
		}
	}
	
	public void render() {
		if(showCrafting) {
			for(Element element : list) {
				element.render();
			}
			
			if(activeItem != null) {
				GUIRenderer.render(activeItem.icon, framePosX + 10, framePosY + 10, 0, 70, 70, false);
				GUIRenderer.render(craftButton);
			}
		}
	}
	
	public void renderText() {
		if(showCrafting) {
			for(Element element : list) {
				element.renderText();
			}
			
			if(activeItem != null) {
				FontRenderer.render(activeItemText);
			}
		}
	}
	
	private void addElementToList(Item item) {
		list.add(new Element(item, listTexture, 
				framePosX - listElementSizeX, framePosY + (list.size() * listElementSizeY),  
				listElementSizeX, listElementSizeY));
	}
	
}
