package com.wfe.components;

import com.wfe.ecs.Component;
import com.wfe.ecs.ComponentType;
import com.wfe.input.Mouse;

public class GatherableComponent extends Component {

	@Override
	public void update(float dt) {
		if(Mouse.isButtonDown(1)) {
			
		}
	}

	@Override
	public ComponentType getType() {
		return ComponentType.GATHERABLE;
	}

}
