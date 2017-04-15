package com.wfe.entities;

import com.wfe.components.NeutralMobComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Goat extends Entity {

	public Goat(Transformation transform) {
		super(ResourceManager.getMesh("goat"), new Material(ResourceManager.getTexture("goat")), transform);
		transform.setScale(0.8f);
		addComponent(new NeutralMobComponent());
	}

}
