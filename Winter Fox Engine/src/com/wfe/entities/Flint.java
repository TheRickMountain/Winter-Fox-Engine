package com.wfe.entities;

import com.wfe.components.BoundingBox;
import com.wfe.components.GatherableComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.StaticEntity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;
import com.wfe.gui.Item;

public class Flint extends StaticEntity {

	public Flint(StaticEntity player, Transformation transform) {
		super(ResourceManager.getMesh("flint"), 
				new Material(ResourceManager.getTexture("flint")), transform);
		addComponent(new GatherableComponent(Item.FLINT, player, getTransform(), 
				new BoundingBox(0.5f, 0.5f, 0.5f, getTransform())));
		transform.setScale(0.125f);
	}

}