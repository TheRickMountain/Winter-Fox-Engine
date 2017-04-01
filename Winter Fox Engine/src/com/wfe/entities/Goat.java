package com.wfe.entities;

import com.wfe.components.MobComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Goat extends Entity {

	public Goat(Transformation transform) {
		super(ResourceManager.getMesh("goat"), new Material(ResourceManager.getTexture("goat")), transform);
		getTransform().setScale(0.7f);
		
		addComponent(new MobComponent(transform));
	}

}