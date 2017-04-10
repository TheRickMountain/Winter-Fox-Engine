package com.wfe.blueprints;

import com.wfe.components.ColliderComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class WindowWallBlueprint extends Blueprint {

	public WindowWallBlueprint() {
		super(ResourceManager.getMesh("window_wall"),
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
		entity.addComponent(new ColliderComponent(1, 1, 1, entity.getTransform()));
		return entity;
	}

}
