package com.wfe.entities;

import com.wfe.components.GatherableComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;
import com.wfe.gui.Item;
import com.wfe.gui.ItemDatabase;

public class Flint extends Entity {

	public Flint(Transformation transform) {
		super(ResourceManager.getMesh("flint"), 
				new Material(ResourceManager.getTexture("flint")), transform);
		setTag("flint");
		transform.setScale(0.125f);
		addComponent(new GatherableComponent(ItemDatabase.getItem(Item.FLINT), 1, ResourceManager.getSound("taking")));
	}

}
