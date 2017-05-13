package com.wfe.core;

import com.wfe.audio.AudioMaster;
import com.wfe.ecs.EntityCache;
import com.wfe.ecs.Transformation;
import com.wfe.entities.Player;
import com.wfe.entities.Settler;

public class Game {
	
	public static Player player;

	public void init(World world, Camera camera, Display display) throws Exception {
		EntityCache.init();
		
		player = new Player(camera, new Transformation(80, 0.65f, 80));
		world.addEntity(player);
		
		Settler settler = new Settler(new Transformation(70, 0.65f, 70));
		world.addEntity(settler);
		
		AudioMaster.ambientSource.play(ResourceManager.getSound("hills"));
		
		System.out.println("Save loading...");
		world.loadPlayer("saves/player.dat");
		world.loadWorld("saves/world.dat");
		System.out.println("Save loaded");
	}


}
