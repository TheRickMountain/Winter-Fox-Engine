package com.wfe.entities;

import com.wfe.components.BoundingBox;
import com.wfe.components.GatherableComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Flint extends Entity {

	public Flint(Entity player, Transformation transform) {
		super(ResourceManager.getMesh("flint"), 
				new Material(ResourceManager.getTexture("flint")), transform);
		addComponent(new GatherableComponent(0, player, getTransform(), 
				new BoundingBox(0.5f, 0.5f, 0.5f, getTransform()), 0.25f));
		transform.setScale(0.125f);
	}

}
