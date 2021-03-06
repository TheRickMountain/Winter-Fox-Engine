package com.wfe.entities;

import com.wfe.components.GatherableComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;
import com.wfe.gui.Item;
import com.wfe.gui.ItemDatabase;
import com.wfe.utils.MyRandom;

public class Stick extends Entity {

	public Stick(Transformation transform) {
		super(ResourceManager.getMesh("stick"), 
				new Material(ResourceManager.getTexture("stick")), transform);
		setId(25);
		setTag("stick");
		transform.setRotY(MyRandom.nextInt(360));
		transform.setScale(0.2f);
		addComponent(new GatherableComponent(ItemDatabase.getItem(Item.STICK), 1,
				ResourceManager.getSound("taking")));
	}

}
