package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Axe extends Entity {
	
	public Axe(Entity player, Transformation transform) {
		super(ResourceManager.getMesh("axe"), 
				new Material(ResourceManager.getTexture("axe")), transform);
		transform.setScale(0.5f);
	}

}
