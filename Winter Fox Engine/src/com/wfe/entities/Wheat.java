package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Wheat extends Entity {

	public Wheat(Transformation transform) {
		super(ResourceManager.getMesh("wheat"),
				new Material(ResourceManager.getTexture("wheat_stage_0"))
				.setHasTransparency(true)
				.setHasFakeLighting(true), transform);
		getTransform().setScale(0.5f);
	}

}
