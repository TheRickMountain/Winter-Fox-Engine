package com.wfe.blueprints;

import com.wfe.components.ColliderComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class CampfireBlueprint extends Blueprint {

	public CampfireBlueprint(Transformation transform) {
		super(ResourceManager.getMesh("campfire"),
				new Material(ResourceManager.getTexture("campfire")), transform);	
		transform.setScale(0.4f);
	}

	@Override
	public Entity createInstance() {
		return new Entity(mesh, material, new Transformation(transform));
	}

	@Override
	public Entity createInstanceWithComponents(Transformation transform) {
		Entity entity = new Entity(mesh, material, new Transformation(transform));
		entity.addComponent(new ColliderComponent(0.5f, 0.5f, 0.5f, entity.getTransform()));
		return entity;
	}

}
