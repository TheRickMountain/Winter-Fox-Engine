package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Club extends Entity {

	public Club() {
		super(ResourceManager.getMesh("club"), new Material(ResourceManager.getTexture("club")), 
				new Transformation());	
		transform.setScale(0.5f);
	}
	
}
