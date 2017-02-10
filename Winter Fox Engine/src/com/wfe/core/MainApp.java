package com.wfe.core;

import org.lwjgl.opengl.GL11;

import com.wfe.game.Game;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;

public class MainApp {
	
	public static void main(String[] args) throws Exception {
		Display display = new Display("Paleon", 1152, 648, false);
		display.init();

		IGameLogic gameLogic = new Game();
		
		gameLogic.loadResources();
		
		gameLogic.onEnter(display);
		
		while(!display.isCloseRequested()) {
			display.pollEvents();
			
			Keyboard.startEventFrame();
			Mouse.startEventFrame();
			gameLogic.update(Display.getDeltaInSeconds());
			Keyboard.clearEventFrame();
			Mouse.clearEventFrame();
			
			gameLogic.render();
			
			display.swapBuffers();
			
			if(Display.isResized()) {
				GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
				display.setResized(false);
			}
		}
		
		gameLogic.onExit();
		display.shutdown();
	}

}
