package com.wfe.entities;

import com.wfe.animation.AnimatedEntity;
import com.wfe.components.GatherableComponent;
import com.wfe.core.ResourceManager;
import com.wfe.ecs.StaticEntity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;
import com.wfe.gui.ItemDatabase;
import com.wfe.utils.MyRandom;

public class Shroom extends StaticEntity {

	public Shroom(AnimatedEntity player, Transformation transform) {
		super(ResourceManager.getMesh("shroom"), 
				new Material(ResourceManager.getTexture("shroom")), transform);
		addComponent(new GatherableComponent(ItemDatabase.SHROOM, player, getTransform()));
		getTransform().setScale(0.125f);
		getTransform().setPosition(
				getTransform().getX() + MyRandom.nextFloat(0.2f, 0.8f), 
				getTransform().getY(), 
				getTransform().getZ() + MyRandom.nextFloat(0.2f, 0.8f));
	}

}
