package com.wfe.ecs;

public abstract class Component {

	private Entity parent;
	public abstract void update(float dt);
	public abstract ComponentType getType();
	
	protected void setParent(Entity parent) {
		this.parent = parent;
	}
	
	public Entity getParent() {
		return parent;
	}
	
}
