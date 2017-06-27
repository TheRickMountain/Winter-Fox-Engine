package com.wfe.entities;

import com.wfe.components.ColliderComponent;
import com.wfe.components.EnemyComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Enemy extends Entity {

	public Enemy(Transformation transform) {
		super(ResourceManager.getMesh("enemy"), new Material(ResourceManager.getTexture("enemy")), transform);
		transform.setScale(0.5f);
		setTag("Enemy");
		
		addComponent(new ColliderComponent(0.5f, 0.5f, 0.5f));
		addComponent(new EnemyComponent());	
	}

}
