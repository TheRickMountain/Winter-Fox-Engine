package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Club extends Entity {
	
	public Club() {
		super(ResourceManager.getMesh("club"),
				new Material(ResourceManager.getTexture("club")), new Transformation());
		setTag("club");
		transform.setScale(0.5f);
		transform.localScaleX = transform.localScaleY = transform.localScaleZ = 2;
		transform.localY = -0.5f;
		transform.localX = 0.6f;
	}

}
