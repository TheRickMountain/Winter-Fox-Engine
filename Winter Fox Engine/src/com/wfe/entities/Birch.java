package com.wfe.entities;

import com.wfe.components.ColliderComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.StaticEntity;
import com.wfe.ecs.Transformation;
import com.wfe.game.World;
import com.wfe.graph.Material;

public class Birch extends StaticEntity {

	public Birch(Transformation transform) {
		super(ResourceManager.getMesh("birch_bark"),
				new Material(ResourceManager.getTexture("birch_bark")), transform);
		addComponent(new ColliderComponent(0.5f, 1, 0.5f, transform));
		
		StaticEntity pineLeaves = new StaticEntity(ResourceManager.getMesh("birch_leaves"),
				new Material(ResourceManager.getTexture("birch_leaves")).setHasTransparency(true), transform);
		addChild(pineLeaves);
		World.getWorld().addEntity(pineLeaves);
		
		transform.setScale(0.8f);
	}

}
