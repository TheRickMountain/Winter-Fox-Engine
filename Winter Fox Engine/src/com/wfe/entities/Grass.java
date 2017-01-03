package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.StaticEntity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;
import com.wfe.utils.MyRandom;

public class Grass extends StaticEntity {

	public Grass(Transformation transform) {
		super(ResourceManager.getMesh("grass"),
				new Material(ResourceManager.getTexture("grass"))
				.setHasTransparency(true)
				.setHasFakeLighting(true)
				.setNumberOfRows(2), transform);
		getTransform().setPosition(
				getTransform().getX() + 0.5f, 
				getTransform().getY(), 
				getTransform().getZ() + 0.5f);
	}

}
