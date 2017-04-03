package com.wfe.ecs;

import java.util.ArrayList;
import java.util.List;

import com.wfe.game.World;
import com.wfe.graph.Material;
import com.wfe.graph.Mesh;

public class Entity {
	
	protected Transformation transform;
	
	protected List<Component> components = new ArrayList<Component>();
	protected List<Entity> childs = new ArrayList<Entity>();
	protected boolean hasParent;
	protected String tag = "";
	
	private Entity parent;

	private boolean isVisible = true;
	private boolean walkable = true;
	
	protected boolean remove = false;
	
	private Mesh mesh;
	private Material material;
	
	private int textureIndex = 0;
	
	public Entity(Mesh mesh, Material material, Transformation transform) {
		this.transform = transform;
		transform.setParent(this);
		this.mesh = mesh;
		this.material = material;
		this.components = new ArrayList<Component>();
	}
	
	public void update(float dt) {		
		if(remove) {
			if(!childs.isEmpty()) {
				for(Entity child : childs)
					child.remove();
			}
			
			World.getWorld().removeEntity(this);
		}
		
		for(Component component : components) {
			component.update(dt);
		}
		
		if(hasParent) {
			transform.update(dt);
		}
	}
	
	public void addComponent(Component component) {
		component.setParent(this);
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
	
	public void removeChild(Entity entity) {
		this.childs.remove(entity);
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
	
	public boolean isWalkable() {
		return walkable;
	}

	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
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

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public float getTextureXOffset(){
        int column = textureIndex % material.getNumberOfRows();
        return (float) column / (float) material.getNumberOfRows();
    }

    public float getTextureYOffset(){
        int row = textureIndex / material.getNumberOfRows();
        return (float) row / (float)material.getNumberOfRows();
    }
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public int getTextureIndex() {
		return textureIndex;
	}

	public Entity setTextureIndex(int textureIndex) {
		int rowSquare = material.getNumberOfRows() * material.getNumberOfRows() - 1;
		if(textureIndex < 0) {
			textureIndex = 0;
		} else if(textureIndex > rowSquare) {
			textureIndex = rowSquare;
		} else {
			this.textureIndex = textureIndex;
		}
		
		return this;
	}
	
	public Entity getInstanceNoComponents() {
		Entity entity = new Entity(mesh, material, new Transformation(transform));
		entity.setTag(getTag());
		entity.setWalkable(isWalkable());
		entity.setTextureIndex(getTextureIndex());
		return entity;
	}
	
	public Entity getInstance() {
		Entity entity = getInstanceNoComponents();
		for(Component c : components) {
			entity.addComponent(c.getInstance());
		}
		return entity;
	}

	public void delete() {
		mesh.delete();
		material.delete();
	}
	
}
