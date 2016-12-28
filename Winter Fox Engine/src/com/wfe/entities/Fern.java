package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.StaticEntity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Fern extends StaticEntity {

	public Fern( Transformation transform) {
		super(ResourceManager.getMesh("fern"), new Material(ResourceManager.getTexture("fern"))
				.setNumberOfRows(2).setHasTransparency(true), transform);
		getTransform().setScale(0.6f);
	}

}
