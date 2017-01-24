package com.wfe.entities;

import com.wfe.components.BoundingBox;
import com.wfe.components.GatherableComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.StaticEntity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;
import com.wfe.gui.Item;

public class Amanita extends StaticEntity {

	public Amanita(StaticEntity player, Transformation transform) {
		super(ResourceManager.getMesh("amanita"), 
				new Material(ResourceManager.getTexture("amanita")), transform);
		addComponent(new GatherableComponent(Item.AMANITA, player, getTransform(), 
				new BoundingBox(0.5f, 0.5f, 0.5f, getTransform()), 0.5f));
		transform.setScale(0.5f);
	}

}
