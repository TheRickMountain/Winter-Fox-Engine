package com.wfe.game;

import com.wfe.audio.AudioMaster;
import com.wfe.core.Camera;
import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Transformation;
import com.wfe.entities.Fern;
import com.wfe.entities.Flint;
import com.wfe.entities.Goat;
import com.wfe.entities.Grass;
import com.wfe.entities.Mushroom;
import com.wfe.entities.Pine;
import com.wfe.entities.Player;
import com.wfe.entities.Rock;
import com.wfe.entities.Stick;
import com.wfe.entities.Wheat;
import com.wfe.utils.MyRandom;

public class Game {
	
	public static Player player;

	public void init(World world, Camera camera, Display display) throws Exception {		
		player = new Player(camera, new Transformation(80, 0.65f, 80));
		world.addEntity(player);
		
		world.addEntityToTile(new Wheat(new Transformation(83.5f, 0, 83.5f), 5));
		world.addEntityToTile(new Wheat(new Transformation(90.5f, 0, 85.5f), 5));
		world.addEntityToTile(new Wheat(new Transformation(72.5f, 0, 94.5f), 5));
		world.addEntityToTile(new Wheat(new Transformation(80.5f, 0, 75.5f), 5));
		
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
		
		world.addEntity(new Goat(new Transformation(80, 0, 85)));
		
		AudioMaster.ambientSource.play(ResourceManager.getSound("hills"));
	}


}
