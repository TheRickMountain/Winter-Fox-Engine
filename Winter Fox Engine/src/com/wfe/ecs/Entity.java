package com.wfe.ecs;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
	
	protected Transformation transform;
	
	protected List<Component> components = new ArrayList<Component>();
	protected List<Entity> childs = new ArrayList<Entity>();
	protected boolean hasParent;
	protected String name;
	
	private Entity parent;

	private boolean isVisible = true;
	
	protected boolean remove = false;
	
	public abstract void update(float dt);
	
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
	
	public void addChild(Entity entity) {
		entity.setParent(this);
		this.childs.add(entity);
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
	
	public void remove() {
		this.remove = true;
	}

	public Entity getParent() {
		return parent;
	}

	private void setParent(Entity parent) {
		this.parent = parent;
		this.hasParent = true;
	}
	
	public boolean isHasParent() {
		return hasParent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
