package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.core.World;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Pine extends Entity {

	public Pine(Transformation transform) {
		super(ResourceManager.getMesh("pine_bark"),
				new Material(ResourceManager.getTexture("pine_bark")), transform);
		setTag("tree");
		setWalkable(false);
		
		Entity pineLeaves = new Entity(ResourceManager.getMesh("pine_leaves"),
				new Material(ResourceManager.getTexture("pine_leaves")).setHasTransparency(true), transform);
		addChild(pineLeaves);
		World.getWorld().addEntity(pineLeaves);
		
		transform.setScale(0.4f);
	}

}
