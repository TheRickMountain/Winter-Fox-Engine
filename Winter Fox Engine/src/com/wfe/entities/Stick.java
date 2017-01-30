package com.wfe.entities;

import com.wfe.components.BoundingBox;
import com.wfe.components.GatherableComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;
import com.wfe.utils.MyRandom;

public class Stick extends Entity {

	public Stick(Entity player, Transformation transform) {
		super(ResourceManager.getMesh("stick"), 
				new Material(ResourceManager.getTexture("stick")), transform);
		addComponent(new GatherableComponent(0, player, getTransform(), 
				new BoundingBox(0.5f, 0.5f, 0.5f, getTransform()), 0.25f));
		transform.setRotY(MyRandom.nextInt(360));
		transform.setScale(0.2f);
	}

}
