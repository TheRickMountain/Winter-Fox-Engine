package com.wfe.entities;

import com.wfe.components.ArrowComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.StaticEntity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Arrow extends StaticEntity {

	public Arrow(Player player, StaticEntity target, Transformation transform) {
		super(ResourceManager.getMesh("arrow"), 
				new Material(ResourceManager.getTexture("arrow"))
				.setHasFakeLighting(true)
				.setHasTransparency(true), transform);
		transform.setScale(0.75f);
		addComponent(new ArrowComponent(player, target, this));
	}

}
