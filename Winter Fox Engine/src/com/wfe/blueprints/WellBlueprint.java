package com.wfe.blueprints;

import com.wfe.components.ColliderComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class WellBlueprint extends Blueprint {

	public WellBlueprint() {
		super(ResourceManager.getMesh("well"),
				new Material(ResourceManager.getTexture("well")), new Transformation());	
		transform.setScale(0.75f);
	}

	@Override
	public Entity createInstance() {
		return new Entity(mesh, material, new Transformation(transform));
	}

	@Override
	public Entity createInstanceWithComponents(Transformation transform) {
		Entity entity = new Entity(mesh, material, new Transformation(transform));
		entity.getTransform().setScale(0.75f);
		entity.addComponent(new ColliderComponent(1f, 1f, 1f, entity.getTransform()));
		return entity;
	}

}
