package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Hive extends Entity {

	public Hive(Transformation transform) {
		super(ResourceManager.getMesh("hive"), 
				new Material(ResourceManager.getTexture("hive")), transform);
		setTag("hive");
		setWalkable(false);
		
		transform.setScale(0.65f);
	}

}
