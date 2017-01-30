package com.wfe.entities;

import com.wfe.components.ColliderComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.game.World;
import com.wfe.graph.Material;

public class Pine extends Entity {

	public Pine(Player player, Transformation transform) {
		super(ResourceManager.getMesh("pine_bark"),
				new Material(ResourceManager.getTexture("pine_bark")), transform);
		addComponent(new ColliderComponent(0.5f, 1, 0.5f, transform));
		
		Entity pineLeaves = new Entity(ResourceManager.getMesh("pine_leaves"),
				new Material(ResourceManager.getTexture("pine_leaves")).setHasTransparency(true), transform);
		addChild(pineLeaves);
		World.getWorld().addEntity(pineLeaves);
		
		transform.setScale(0.4f);
	}

}
