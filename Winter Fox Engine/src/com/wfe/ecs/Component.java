package com.wfe.ecs;

public interface Component {

	public void update(float dt);
	public ComponentType getType();
	
}
