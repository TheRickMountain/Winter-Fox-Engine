package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class DoorWall extends Entity {

	public DoorWall(Transformation transform) {
		super(ResourceManager.getMesh("door_wall"),
				new Material(ResourceManager.getTexture("wall")), transform);
		building = true;
		
		getTransform().setScale(0.5f);
	}

}
