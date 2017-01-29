package com.wfe.blueprints;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class AxeBlueprint extends Blueprint {

	public AxeBlueprint(Transformation transform) {
		super(ResourceManager.getMesh("axe"),
				new Material(ResourceManager.getTexture("axe")), transform);	
		transform.setScale(0.5f);
	}

	@Override
	public Entity createInstance() {
		return new Entity(mesh, material, new Transformation(transform));
	}

	@Override
	public Entity createInstanceWithComponents(Transformation transform) {
		Entity entity = new Entity(mesh, material, new Transformation(transform));
		return entity;
	}

}
