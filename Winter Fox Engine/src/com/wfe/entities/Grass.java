package com.wfe.entities;

import com.wfe.components.GatherableComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;
import com.wfe.gui.Item;
import com.wfe.gui.ItemDatabase;
import com.wfe.utils.MyRandom;

public class Grass extends Entity {

	public Grass(Transformation transform) {
		super(ResourceManager.getMesh("grass"),
				new Material(ResourceManager.getTexture("grass"))
				.setHasTransparency(true)
				.setHasFakeLighting(true)
				.setNumberOfRows(4), transform);
		setId(17);
		setTag("grass");
		getTransform().setScale(0.5f);
		getTransform().setPosition(
				getTransform().getX() + MyRandom.nextFloat(0.2f, 0.8f), 
				getTransform().getY(), 
				getTransform().getZ() + MyRandom.nextFloat(0.2f, 0.8f));
		addComponent(new GatherableComponent(ItemDatabase.getItem(Item.FIBER), 1, ResourceManager.getSound("taking")));
	}

}
