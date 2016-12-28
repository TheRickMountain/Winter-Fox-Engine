package com.wfe.components;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.ecs.Transformation;
import com.wfe.input.Keyboard;
import com.wfe.input.Key;

public class GatherableComponent implements Component {
	
	private Transformation transform;
	
	public GatherableComponent(Transformation transform) {
		this.transform = transform;
	}
	
	@Override
	public void update(float dt) {
		if(Keyboard.isKeyDown(Key.KEY_G)) {
			transform.getParent().remove();
		}
	}

	@Override
	public ComponentType getType() {
		return ComponentType.GATHERABLE;
	}

}
