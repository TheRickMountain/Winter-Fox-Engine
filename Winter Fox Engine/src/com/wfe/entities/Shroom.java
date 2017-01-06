package com.wfe.entities;

import com.wfe.components.GatherableComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.StaticEntity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;
import com.wfe.gui.ItemDatabase;

public class Shroom extends StaticEntity {

	public Shroom(StaticEntity player, Transformation transform) {
		super(ResourceManager.getMesh("shroom"), 
				new Material(ResourceManager.getTexture("shroom")), transform);
		addComponent(new GatherableComponent(ItemDatabase.SHROOM, player, getTransform()));
		transform.setScale(0.5f);
	}

}
