package com.wfe.entities;

import com.wfe.components.BoundingBox;
import com.wfe.components.GatherableComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Wheat extends Entity {

	public Wheat(Player player, Transformation transform) {
		super(ResourceManager.getMesh("wheat"),
				new Material(ResourceManager.getTexture("wheat"))
				.setHasTransparency(true)
				.setHasFakeLighting(true), transform);
		addComponent(new GatherableComponent(0, player, getTransform(), 
				new BoundingBox(0.75f, 0.75f, 0.75f, getTransform()), 2));
		getTransform().setScale(0.5f);
	}

}
