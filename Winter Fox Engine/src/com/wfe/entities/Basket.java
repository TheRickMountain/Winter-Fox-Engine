package com.wfe.entities;

import com.wfe.components.ChestComponent;
import com.wfe.components.ColliderComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Basket extends Entity {

	public Basket() {
		super(ResourceManager.getMesh("basket"),
				new Material(ResourceManager.getTexture("basket")), new Transformation());	
		setTag("basket");
		getTransform().setScale(0.75f);
		addComponent(new ChestComponent(4, 3));
		addComponent(new ColliderComponent(0.5f, 1, 0.5f));
	}

}
