package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Mushroom extends Entity {

	public Mushroom(Transformation transform) {
		super(ResourceManager.getMesh("mushroom"), 
				new Material(ResourceManager.getTexture("mushroom")), transform);
		setTag("mushroom");
		transform.setScale(0.5f);
	}

}
