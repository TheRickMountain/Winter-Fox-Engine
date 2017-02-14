package com.wfe.entities;

import com.wfe.components.ColliderComponent;
import com.wfe.components.HiveComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;
import com.wfe.gui.Item;
import com.wfe.gui.ItemDatabase;

public class Hive extends Entity {

	public Hive(Transformation transform) {
		super(ResourceManager.getMesh("hive"), 
				new Material(ResourceManager.getTexture("hive")), transform);
		setTag("hive");
		transform.setScale(0.65f);
		
		addComponent(new ColliderComponent(0.5f, 1, 0.5f, transform));
		addComponent(new HiveComponent(120, ItemDatabase.getItem(Item.HONEY), 
				1, 2, ResourceManager.getSound("taking")));
	}

}
