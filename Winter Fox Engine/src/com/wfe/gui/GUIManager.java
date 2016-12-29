package com.wfe.gui;

import com.wfe.input.Mouse;

public class GUIManager {

	private static GUIManager guiManager;
	
	private QuickInventory quickInventory;
	
	private GUIManager() {
		quickInventory = new QuickInventory();
	}
	
	public void update() {
		Mouse.setActiveInGUI(false);
		
		quickInventory.update();
		
		//System.out.println(Mouse.isActiveInGUI());
	}
	
	public void render() {
		quickInventory.render();
	}
	
	public static GUIManager create() {
		if(guiManager == null) {
			guiManager = new GUIManager();
		}
		
		return guiManager;
	}
	
}
