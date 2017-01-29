package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Rock extends Entity {

	public Rock(Entity player, Transformation transform) {
		super(ResourceManager.getMesh("rock"), 
				new Material(ResourceManager.getTexture("rock")), transform);
		transform.setScale(0.08f);
	}

}
