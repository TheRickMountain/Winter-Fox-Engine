package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Rock extends Entity {

	public Rock(Transformation transform) {
		super(ResourceManager.getMesh("rock"), 
				new Material(ResourceManager.getTexture("rock")), transform);
		setTag("rock");
		setWalkable(false);
		
		transform.setScale(0.5f);
		
	}

}
