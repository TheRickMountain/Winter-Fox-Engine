package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Hoe extends Entity {

	public Hoe() {
		super(ResourceManager.getMesh("hoe"),
				new Material(ResourceManager.getTexture("hoe")), new Transformation());	
		setId(0);
		setTag("hoe");
		transform.setScale(0.5f);
		transform.localScaleX = transform.localScaleY = transform.localScaleZ = 2f;
		transform.localY = -0.5f;
		transform.localX = 0.5f;
	}
	
}
