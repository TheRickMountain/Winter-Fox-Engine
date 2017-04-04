package com.wfe.gui;

import com.wfe.core.Display;

public class GUIManager  {
	
	private static DayWheel wheel;
	
	public static void init() {
		wheel = new DayWheel();
	}
	
	public static void update(float dt) {	
		wheel.update(dt);
		
		if(Display.isResized()) {
			wheel.updatePosition();
		}
	}
	
	public static void render() {
		wheel.render();
	}
	
	public static void renderText() {
		
	}
	
	public static void renderPopUp() {
		
	}
	
	public static void renderPopUpText() {
		
	}
	
}
