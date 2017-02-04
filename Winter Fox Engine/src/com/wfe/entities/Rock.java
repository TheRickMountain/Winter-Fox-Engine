package com.wfe.entities;

import com.wfe.components.ColliderComponent;
import com.wfe.components.MineableComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;
import com.wfe.gui.Item;
import com.wfe.utils.MyRandom;

public class Rock extends Entity {

	public Rock(Entity player, Transformation transform) {
		super(ResourceManager.getMesh("rock"), 
				new Material(ResourceManager.getTexture("rock")), transform);
		setTag("stone");
		transform.setScale(0.5f);
		
		addComponent(new ColliderComponent(0.5f, 1, 0.5f, transform));
		
		addComponent(new MineableComponent(Item.FLINT, MyRandom.nextInt(1, 2), 
				ResourceManager.getSound("mine"), Item.AXE, 2));
	}

}
