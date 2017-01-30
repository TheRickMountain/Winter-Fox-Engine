package com.wfe.ecs;

public abstract class Component {

	protected Entity parent;
	public abstract void update(float dt);
	public abstract ComponentType getType();
	
}
