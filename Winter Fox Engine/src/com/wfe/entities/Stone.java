package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Stone extends Entity {

	public Stone(Transformation transform) {
		super(ResourceManager.getMesh("stone"), new Material(ResourceManager.getTexture("stone")), 
				transform);
		getTransform().setScale(0.6f);
	}

}
