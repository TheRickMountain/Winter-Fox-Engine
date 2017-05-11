package com.wfe.entities;

import com.wfe.components.GatherableComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;
import com.wfe.gui.Item;
import com.wfe.gui.ItemDatabase;

public class Wheat extends Entity {

	public Wheat(Transformation transform, int stage) {
		super(ResourceManager.getMesh("wheat"),
				new Material(ResourceManager.getTexture("wheat_stage_" + stage))
				.setHasTransparency(true)
				.setHasFakeLighting(true), transform);
		setTag("wheat");
		getTransform().setScale(0.5f);
		addComponent(new GatherableComponent(ItemDatabase.getItem(Item.WHEAT), 1, ResourceManager.getSound("taking")));
	}

}
