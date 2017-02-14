package com.wfe.blueprints;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class AxeBlueprint extends Blueprint {

	public AxeBlueprint() {
		super(ResourceManager.getMesh("axe"),
				new Material(ResourceManager.getTexture("axe")), new Transformation());	
		transform.setScale(0.5f);
	}

	@Override
	public Entity createInstance() {
		return new Entity(mesh, material, new Transformation(transform));
	}

	@Override
	public Entity createInstanceWithComponents(Transformation transform) {
		transform.localScaleX = transform.localScaleY = transform.localScaleZ = 2;
		transform.localY = -0.5f;
		transform.localX = 0.5f;
		Entity entity = new Entity(mesh, material, new Transformation(transform));
		entity.setTag("axe");
		return entity;
	}

}
