package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.StaticEntity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class WindowWall extends StaticEntity {

	public WindowWall(Transformation transform) {
		super(ResourceManager.getMesh("window_wall"),
				new Material(ResourceManager.getTexture("wall")), transform);
		building = true;
		
		getTransform().setScale(0.5f);
	}

}
