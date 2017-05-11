package com.wfe.ecs;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.World;
import com.wfe.graph.Material;
import com.wfe.graph.Mesh;

public class Entity {

	protected Transformation transform;
	
	protected List<Component> components = new ArrayList<Component>();
	public List<Entity> children = new ArrayList<Entity>();
	protected boolean hasParent;
	private int id;
	protected String tag = "empty";
	
	private Entity parent;

	private boolean isVisible = true;
	
	protected boolean remove = false;
	
	private Mesh mesh;
	private Material material;
	
	private int textureIndex = 0;
	
	private boolean walkable = true;
	
	public Entity(Mesh mesh, Material material, Transformation transform) {
		this.transform = transform;
		transform.setParent(this);
		this.mesh = mesh;
		this.material = material;
		this.components = new ArrayList<Component>();
	}
	
	public void init() {
		for(Component component : components) {
			component.init();
		}
		
		for(Entity entity : children) {
			entity.init();
		}
	}
	
	public void update(float dt) {		
		if(remove) {
			if(!children.isEmpty()) {
				for(Entity child : children)
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
		this.children.add(entity);
	}
	
	public void removeChild(Entity entity) {
		this.children.remove(entity);
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

	public String getTag() {
		return tag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		Entity entity = new Entity(mesh, material.getInstance(), new Transformation(transform));
		entity.setId(getId());
		entity.setTag(getTag());
		entity.setTextureIndex(getTextureIndex());
		
		for(Entity child : children) {
			entity.addChild(child.getInstanceNoComponents());
		}
		
		return entity;
	}
	
	public Entity getInstance() {
		Entity entity = new Entity(mesh, material.getInstance(), new Transformation(transform));
		entity.setId(getId());
		entity.setTag(getTag());
		entity.setTextureIndex(getTextureIndex());
		for(Component c : components) {
			entity.addComponent(c.getInstance());
		}
		
		for(Entity child : children) {
			entity.addChild(child.getInstance());
		}
		
		return entity;
	}
	
	public boolean isWalkable() {
		return walkable;
	}
	
	public void setWalkable(boolean value) {
		this.walkable = value;
	}

	public void delete() {
		mesh.delete();
		material.delete();
	}
	
}
