package com.wfe.blueprints;

import com.wfe.components.BoundingBox;
import com.wfe.components.ColliderComponent;
import com.wfe.components.FurnaceComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.StaticEntity;
import com.wfe.ecs.Transformation;
import com.wfe.game.Game;
import com.wfe.graph.Material;

public class FurnaceBlueprint extends Blueprint {

	public FurnaceBlueprint(Transformation transform) {
		super(ResourceManager.getMesh("furnace"),
				new Material(ResourceManager.getTexture("furnace")), transform);	
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
		entity.addComponent(new FurnaceComponent(Game.player, transform, new BoundingBox(1, 1.25f, 1, transform)));
		return entity;
	}

}
