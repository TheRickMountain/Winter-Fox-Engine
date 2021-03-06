package com.wfe.entities;

import com.wfe.components.ColliderComponent;
import com.wfe.components.MineableComponent;
import com.wfe.core.ResourceManager;
import com.wfe.core.World;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;
import com.wfe.gui.Item;
import com.wfe.gui.ItemDatabase;

public class Pine extends Entity {

	public Pine(Transformation transform) {
		super(ResourceManager.getMesh("pine_bark"),
				new Material(ResourceManager.getTexture("pine_bark")), transform);
		setId(22);
		setTag("tree");
		addComponent(new ColliderComponent(0.5f, 1, 0.5f));
		
		Entity leaves = new Entity(ResourceManager.getMesh("pine_leaves"),
				new Material(ResourceManager.getTexture("pine_leaves")).setHasTransparency(true), transform);
		leaves.setTag("leaves");
		addChild(leaves);
		
		transform.setScale(0.4f);
		setWalkable(false);
		addComponent(new MineableComponent(ItemDatabase.getItem(Item.LOG), 20, 
				ResourceManager.getSound("chop"), ItemDatabase.getItem(Item.AXE), 5));
	}

}
