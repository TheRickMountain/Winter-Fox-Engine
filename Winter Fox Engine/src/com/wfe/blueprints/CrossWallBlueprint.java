package com.wfe.blueprints;

import com.wfe.components.ColliderComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.StaticEntity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class CrossWallBlueprint extends Blueprint {

	public CrossWallBlueprint(Transformation transform) {
		super(ResourceManager.getMesh("cross_wall"),
				new Material(ResourceManager.getTexture("wall")), transform);
		transform.setScale(0.5f);
	}

	@Override
	public StaticEntity createInstance() {
		 return new StaticEntity(mesh, material, new Transformation(transform));
	}

	@Override
	public StaticEntity createInstanceWithComponents(Transformation transform) {
		StaticEntity entity = new StaticEntity(mesh, material, new Transformation(transform));
		entity.addComponent(new ColliderComponent(1, 1, 1, entity.getTransform()));
		return entity;
	}

}
