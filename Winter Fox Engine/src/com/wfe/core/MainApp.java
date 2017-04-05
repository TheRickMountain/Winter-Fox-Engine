package com.wfe.core;

import org.lwjgl.opengl.GL11;

import com.wfe.audio.SoundManager;
import com.wfe.ecs.Transformation;
import com.wfe.entities.Fern;
import com.wfe.entities.Flint;
import com.wfe.entities.Grass;
import com.wfe.entities.Mushroom;
import com.wfe.entities.Pine;
import com.wfe.entities.Rock;
import com.wfe.entities.Settler;
import com.wfe.entities.Stick;
import com.wfe.entities.Wheat;
import com.wfe.gui.GUIManager;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.math.Vector3f;
import com.wfe.renderEngine.RenderEngine;
import com.wfe.utils.MyRandom;

public class MainApp {
	

	public static void main(String[] args) throws Exception {
		Display display = new Display("Paleon", 1152, 648, false);
		Camera camera = new Camera(new Vector3f(80, 0, 80));
		RenderEngine renderEngine = RenderEngine.create(camera);
		SoundManager.init();
		
		Resources.loadResources();
		World world = World.create(camera, renderEngine);
		world.init();
		GUIManager.init();
		
		world.addEntity(new Settler(world.getTile(85, 85), new Transformation(85.5f, 0.65f, 85.5f)));
		world.addEntity(new Settler(world.getTile(87, 85), new Transformation(87.5f, 0.65f, 85.5f)));
		
		/*** TODO: replace this section ***/
		world.addEntityToTile(new Wheat(new Transformation(83.5f, 0, 83.5f)));
		world.addEntityToTile(new Wheat(new Transformation(90.5f, 0, 85.5f)));
		world.addEntityToTile(new Wheat(new Transformation(72.5f, 0, 94.5f)));
		world.addEntityToTile(new Wheat(new Transformation(80.5f, 0, 75.5f)));
		
		for(int i = 0; i < 100; i++) {		
			Mushroom shroom = new Mushroom(new Transformation(MyRandom.nextInt(160) + 0.5f, 0, MyRandom.nextInt(160) + 0.5f));
			world.addEntityToTile(shroom);
			
			Fern fern = new Fern(new Transformation(MyRandom.nextInt(160) + 0.5f, 0, MyRandom.nextInt(160) + 0.5f));
			fern.setTextureIndex(MyRandom.nextInt(4));
			world.addEntityToTile(fern);
		
			world.addEntityToTile(new Flint(new Transformation(MyRandom.nextInt(160) + 0.5f, 0, 
					MyRandom.nextInt(160) + 0.5f)));
			
			world.addEntityToTile(new Stick(new Transformation(MyRandom.nextInt(160) + 0.5f, 0, 
					MyRandom.nextInt(160) + 0.5f)));
			
			world.addEntityToTile(new Pine(new Transformation(MyRandom.nextInt(160) + 0.5f, 0, 
					MyRandom.nextInt(160) + 0.5f)));
			
			world.addEntityToTile(new Rock(new Transformation(MyRandom.nextInt(160) + 0.5f, 0, 
					MyRandom.nextInt(160) + 0.5f)));
		}
		
		for(int i = 0; i < 160; i++) {
			for(int j = 0; j < 160; j++) {
				int num = MyRandom.nextInt(10);
				if(num == 5) {
					Grass grass = new Grass(new Transformation(i, 0, j));
					grass.setTextureIndex(MyRandom.nextInt(3, 7));		
					world.addEntityToTile(grass);
				}
			}
		}
		/*** *** ***/
		
		while(!display.isCloseRequested()) {
			display.pollEvents();
			
			Keyboard.startEventFrame();
			Mouse.startEventFrame();
			
			float dt = Display.getDeltaInSeconds();
			world.update(dt);
			GUIManager.update(dt);
			
			Keyboard.clearEventFrame();
			Mouse.clearEventFrame();
			
			world.render();
			
			display.update();
			
			if(Display.isResized()) {
				GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
				display.setResized(false);
			}
		}
		
		SoundManager.cleanup();
		world.cleanup();
		display.shutdown();
	}

}
