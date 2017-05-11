package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Axe extends Entity {

	public Axe() {
		super(ResourceManager.getMesh("axe"), new Material(ResourceManager.getTexture("axe")), 
				new Transformation());	
		setTag("axe");
		setId(0);
		transform.setScale(0.5f);
		transform.localScaleX = transform.localScaleY = transform.localScaleZ = 2;
		transform.localY = -0.5f;
		transform.localX = 0.5f;
	}

}
