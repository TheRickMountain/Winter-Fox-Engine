package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Shroom extends Entity {

	public Shroom(Entity player, Transformation transform) {
		super(ResourceManager.getMesh("shroom"), 
				new Material(ResourceManager.getTexture("shroom")), transform);
		setTag("consumable");
		transform.setScale(0.5f);
	}

}
