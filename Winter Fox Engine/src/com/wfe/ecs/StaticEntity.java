package com.wfe.ecs;

import java.util.ArrayList;
import java.util.List;

import com.wfe.game.World;
import com.wfe.graph.Material;
import com.wfe.graph.Mesh;

public class StaticEntity {
	
	private int id;
	private Transformation transform;
	private Mesh mesh;
	private Material material;
	private List<Component> components;
	
	private boolean remove = false;
	
	public boolean building = false;
	
	private int textureIndex = 0;
	
	public StaticEntity(Mesh mesh, Material material, Transformation transform) {
		this.transform = transform;
		transform.setParent(this);
		this.mesh = mesh;
		this.material = material;
		this.components = new ArrayList<Component>();
	}
	
	public void update(float dt) {
		if(remove) {
			World.getWorld().removeEntity(this);
		}
		
		for(Component component : components) {
			component.update(dt);
		}
	}
	
	public float getTextureXOffset(){
        int column = textureIndex % material.getNumberOfRows();
        return (float) column / (float) material.getNumberOfRows();
    }

    public float getTextureYOffset(){
        int row = textureIndex / material.getNumberOfRows();
        return (float) row / (float)material.getNumberOfRows();
    }
	
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
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}
	
	public void remove() {
		this.remove = true;
	}
	
	public int getTextureIndex() {
		return textureIndex;
	}

	public void setTextureIndex(int textureIndex) {
		int rowSquare = material.getNumberOfRows() * material.getNumberOfRows() - 1;
		if(textureIndex < 0) {
			textureIndex = 0;
		} else if(textureIndex > rowSquare) {
			textureIndex = rowSquare;
		} else {
			this.textureIndex = textureIndex;
		}
	}

	public void delete() {
		mesh.delete();
		material.delete();
	}

}
