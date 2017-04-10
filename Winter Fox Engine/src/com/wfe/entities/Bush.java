package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Bush extends Entity {

	public Bush(Transformation transform) {
		super(ResourceManager.getMesh("bush"), new Material(ResourceManager.getTexture("bush"))
				.setHasTransparency(true).setHasFakeLighting(true), transform);
		transform.setScale(0.5f);
	}
	
}
