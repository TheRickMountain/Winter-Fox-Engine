package com.wfe.gui;

import com.wfe.audio.AudioMaster;
import com.wfe.components.ChestComponent;
import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.renderEngine.FontRenderer;
import com.wfe.renderEngine.GUIRenderer;

public class GUIManager  {
	
	public static Inventory inventory;
	
	private static boolean open = false;
	
	private static Item draggedItem;
	private static int draggedItemCount;
	private static GUIText draggedItemCountText;
	
	public static void init() {
		ItemDatabase.create();
		inventory = new Inventory();
		
		draggedItemCountText = new GUIText("", 1.1f, FontRenderer.font, 0.0f, 0.0f, 
				(1.0f / Display.getWidth()) * GUIGrid.SLOT_SIZE, true);
		draggedItemCountText.setColor(1.0f, 1.0f, 1.0f);
	}
	
	public static void update() {	
		if(Keyboard.isKeyDown(Key.KEY_E)) {
			AudioMaster.defaultSource.play(ResourceManager.getSound("inventory"));
			open = !open;
		}
		
		inventory.update();
	}
	
	public static void render() {
		if(open) {
			inventory.render();
		}
		inventory.quickInventory.render();
	}

	public static void renderText() {
		if(open) {
			inventory.renderText();
		}
		inventory.quickInventory.renderText();
	}

	public static void renderPopUp() {
		if(draggedItem != null) {
			GUIRenderer.render(draggedItem.icon, 
					Mouse.getX() - (GUIGrid.SLOT_SIZE / 2), 
					Mouse.getY() - (GUIGrid.SLOT_SIZE / 2), 0, 
					GUIGrid.SLOT_SIZE, GUIGrid.SLOT_SIZE, false);
		}
	}

	public static void renderPopUpText() {
		if(draggedItem != null && (draggedItemCount > 1)) {
			FontRenderer.render(draggedItemCountText, 
					Mouse.getX() - (GUIGrid.SLOT_SIZE / 2), 
					Mouse.getY() - (GUIGrid.SLOT_SIZE / 2));
		}
	}
	
	public static void setDraggedItem(Item item, int count) {
		draggedItem = item;
		draggedItemCount = count;
		draggedItemCountText.setText("" + count);
	}
	
	public static void removeDraggedItem() {
		draggedItem = null;
		draggedItemCount = 0;
	}
	
	public static Item getDraggedItem() {
		return draggedItem;
	}
	
	public static int getDraggedItemCount() {
		return draggedItemCount;
	}
	
	public static void open(ChestComponent chest) {
		open = !open;
		
		if(open) {
			
		} else {
			
		}
	}

}
