package com.wfe.blueprints;

import com.wfe.components.ColliderComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class BarrelBlueprint extends Blueprint {

	public BarrelBlueprint(Transformation transform) {
		super(ResourceManager.getMesh("barrel"),
				new Material(ResourceManager.getTexture("barrel")), transform);	
		transform.setScale(0.4f);
	}

	@Override
	public Entity createInstance() {
		return new Entity(mesh, material, new Transformation(transform));
	}

	@Override
	public Entity createInstanceWithComponents(Transformation transform) {
		Entity entity = new Entity(mesh, material, new Transformation(transform));
		entity.addComponent(new ColliderComponent(0.75f, 1, 0.75f, entity.getTransform()));
		return entity;
	}

}
