package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Bed extends Entity {
	
	public Bed() {
		super(ResourceManager.getMesh("bed"), new Material(ResourceManager.getTexture("bed")), 
				new Transformation());	
		setTag("bed");
		setId(30);
		transform.setScale(0.5f);
	}

}
