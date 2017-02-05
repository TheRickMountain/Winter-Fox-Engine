package com.wfe.blueprints;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class DoorWallBlueprint extends Blueprint {

	public DoorWallBlueprint() {
		super(ResourceManager.getMesh("door_wall"),
				new Material(ResourceManager.getTexture("walls")), new Transformation());
		transform.setScale(0.5f);
	}

	@Override
	public Entity createInstance() {
		return new Entity(mesh, material, new Transformation(transform));
	}

	@Override
	public Entity createInstanceWithComponents(Transformation transform) {
		Entity entity = new Entity(mesh, material, new Transformation(transform));
		entity.getTransform().setScale(0.5f);
		return entity;
	}

}
