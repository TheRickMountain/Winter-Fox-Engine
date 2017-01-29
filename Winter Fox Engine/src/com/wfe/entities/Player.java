package com.wfe.entities;

import com.wfe.components.PlayerComponent;
import com.wfe.core.Camera;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.game.World;
import com.wfe.graph.Material;

public class Player extends Entity {

	public Player(Camera camera, Transformation transform) {
		super(null, 
				null, transform);
		transform.setScale(0.3f);
		
		Entity body = new Entity(ResourceManager.getMesh("body"), 
				new Material(ResourceManager.getTexture("player")), new Transformation());
		addChild(body);
		World.getWorld().addEntity(body);
		
		Entity head = new Entity(ResourceManager.getMesh("head"), 
				new Material(ResourceManager.getTexture("player")), new Transformation());
		head.getTransform().localY = 0.85f;
		body.addChild(head);
		World.getWorld().addEntity(head);
		
		Entity eyes = new Entity(ResourceManager.getMesh("eyes"),
				new Material(ResourceManager.getTexture("eyes")), new Transformation());
		eyes.getTransform().localY = -1.0f;
		head.addChild(eyes);
		World.getWorld().addEntity(eyes);
		
		addComponent(new PlayerComponent(camera, transform));
	}


}
