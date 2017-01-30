package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Amanita extends Entity {

	public Amanita(Entity player, Transformation transform) {
		super(ResourceManager.getMesh("amanita"), 
				new Material(ResourceManager.getTexture("amanita")), transform);
		transform.setScale(0.5f);
	}

}
