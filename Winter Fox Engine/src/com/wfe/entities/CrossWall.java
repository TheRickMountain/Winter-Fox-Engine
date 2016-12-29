package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.StaticEntity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class CrossWall extends StaticEntity {

	public CrossWall(Transformation transform) {
		super(ResourceManager.getMesh("cross_wall"),
				new Material(ResourceManager.getTexture("wall")), transform);
		
		getTransform().setScale(0.5f);
	}

}
