package com.wfe.ecs;

import java.util.ArrayList;

import com.wfe.game.World;
import com.wfe.graph.Material;
import com.wfe.graph.Mesh;

public class StaticEntity extends Entity {
	
	private Mesh mesh;
	private Material material;
	
	private boolean remove = false;
	
	private int textureIndex = 0;
	
	public StaticEntity(Mesh mesh, Material material, Transformation transform) {
		this.transform = transform;
		transform.setParent(this);
		this.mesh = mesh;
		this.material = material;
		this.components = new ArrayList<Component>();
	}
	
	@Override
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
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public void remove() {
		this.remove = true;
	}
	
	public int getTextureIndex() {
		return textureIndex;
	}

	public StaticEntity setTextureIndex(int textureIndex) {
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

	public void delete() {
		mesh.delete();
		material.delete();
	}
}
