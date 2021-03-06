package com.wfe.core;

import com.wfe.audio.AudioMaster;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.math.Vector3f;

public class PaleonApp {
	
	public static void main(String[] args) throws Exception {
		Display display = new Display("Paleon", 1152, 648, false);
		display.init();

		AudioMaster.init();
		
		ResourceLoader.load();

		Camera camera = new Camera(new Vector3f(16, 0, 16));
		
		World world = World.createWorld(camera);
		world.init(10, 10);
		
		Game game = new Game();
		game.init(world, camera, display);
		
		while(!display.isCloseRequested()) {
			display.pollEvents();
			
			Keyboard.startEventFrame();
			Mouse.startEventFrame();
			world.update(Display.getDeltaInSeconds());
			Keyboard.clearEventFrame();
			Mouse.clearEventFrame();
			
			world.render();
			
			display.swapBuffers();
			
			if(Display.isResized()) {
				display.setResized(false);
			}
		}
		
		world.cleanup();
		AudioMaster.cleanup();
		display.shutdown();
	}

}
