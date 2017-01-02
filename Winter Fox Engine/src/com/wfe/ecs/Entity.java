package com.wfe.ecs;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
	
	protected Transformation transform;
	
	protected List<Component> components = new ArrayList<Component>();

	public abstract void update(float dt);
	
	private boolean isVisible = true;
	
	public void addComponent(Component component) {
		this.components.add(component);
	}
	
	public boolean hasComponent(ComponentType type) {
		for(Component component : components) {
			if(component.getType().equals(type)) {
				return true;
			}
		}
		return false;
	}
	
	public Component getComponent(ComponentType type) {
		for(Component component : components) {
			if(component.getType().equals(type)) {
				return component;
			}
		}
		return null;
	}
	
	public Transformation getTransform() {
		return transform;
	}
	
	public boolean isVisible() {
		return isVisible;
	}
	
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
}
