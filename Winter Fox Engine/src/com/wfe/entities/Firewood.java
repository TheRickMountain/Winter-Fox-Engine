package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Firewood extends Entity {

	public Firewood(Transformation transform) {
		super(ResourceManager.getMesh("firewood"), new Material(ResourceManager.getTexture("firewood")), 
				transform);
		setTag("firewood");
		getTransform().setScale(0.6f);
	}
	
}
