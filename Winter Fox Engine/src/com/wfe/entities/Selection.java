package com.wfe.entities;

import com.wfe.core.ResourceManager;
import com.wfe.ecs.Entity;
import com.wfe.ecs.Transformation;
import com.wfe.graph.Material;

public class Selection extends Entity {

	public Selection() {
		super(ResourceManager.getMesh("plane"), new Material(null), new Transformation());
	}

}
