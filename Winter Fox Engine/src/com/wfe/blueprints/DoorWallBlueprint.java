package com.wfe.blueprints;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.StaticEntity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class DoorWallBlueprint extends Blueprint {

	public DoorWallBlueprint(Transformation transform) {
		super(ResourceManager.getMesh("door_wall"),
				new Material(ResourceManager.getTexture("wall")), transform);
		transform.setScale(0.5f);
	}

	@Override
	public StaticEntity createInstance() {
		return new StaticEntity(mesh, material, new Transformation(transform));
	}

}
