package com.wfe.blueprints;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.StaticEntity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class BushBlueprint extends Blueprint {

	public BushBlueprint(Transformation transform) {
		super(ResourceManager.getMesh("bush"), new Material(ResourceManager.getTexture("bush"))
				.setHasTransparency(true), transform);
		transform.setScale(0.5f);
	}

	@Override
	public StaticEntity createInstance() {
		return new StaticEntity(mesh, material, new Transformation(transform));
	}

	@Override
	public StaticEntity createInstanceWithComponents(Transformation transform) {
		StaticEntity entity = new StaticEntity(mesh, material, new Transformation(transform));
		return entity;
	}

}
