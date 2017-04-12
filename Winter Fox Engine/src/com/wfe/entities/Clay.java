package com.wfe.entities;

import com.wfe.components.ColliderComponent;
import com.wfe.components.MineableComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;
import com.wfe.gui.Item;
import com.wfe.gui.ItemDatabase;
import com.wfe.utils.MyRandom;

public class Clay extends Entity {

	public Clay(Transformation transform) {
		super(ResourceManager.getMesh("rock"), 
				new Material(ResourceManager.getTexture("clay")), transform);
		setTag("clay");
		transform.setScale(0.5f);
		
		addComponent(new ColliderComponent(0.5f, 1, 0.5f));
		
		addComponent(new MineableComponent(ItemDatabase.getItem(Item.CLAY), MyRandom.nextInt(1, 2), 
				ResourceManager.getSound("mine"), ItemDatabase.getItem(Item.PICKAXE), 2));
	}

}
