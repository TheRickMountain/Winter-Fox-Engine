package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Pickaxe extends Entity {

	public Pickaxe() {
		super(ResourceManager.getMesh("pickaxe"),
				new Material(ResourceManager.getTexture("pickaxe")), new Transformation());	
		setId(0);
		setTag("pickaxe");
		transform.setScale(0.5f);
		transform.localScaleX = transform.localScaleY = transform.localScaleZ = 2f;
		transform.localY = -0.5f;
		transform.localX = 0.5f;
	}
	
}
