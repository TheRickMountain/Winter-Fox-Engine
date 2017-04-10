package com.wfe.blueprints;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class BushBlueprint extends Blueprint {

	public BushBlueprint(Transformation transform) {
		super(ResourceManager.getMesh("bush"), new Material(ResourceManager.getTexture("bush"))
				.setHasTransparency(true).setHasFakeLighting(true), transform);
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
