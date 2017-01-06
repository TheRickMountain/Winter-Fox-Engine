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
				.setNumberOfRows(4), transform);
		getTransform().setScale(0.5f);
		getTransform().setPosition(
				getTransform().getX() + MyRandom.nextFloat(0.2f, 0.8f), 
				getTransform().getY(), 
				getTransform().getZ() + MyRandom.nextFloat(0.2f, 0.8f));
	}

}
